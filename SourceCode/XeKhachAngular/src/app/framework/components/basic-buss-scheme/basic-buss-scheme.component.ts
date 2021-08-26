import {Component, Input, OnInit} from '@angular/core';
import {BussType} from "../../../business/entities/BussType";
import {XeScreen} from "../xe-nav/xe-nav.component";
import {SeatGroup} from "../../../business/entities/SeatGroup";
import {Notifier} from "../../notify/notify.service";
import {EntityUtil} from "../../util/EntityUtil";
import {XeSubscriber} from "../../model/XeSubscriber";
import {XeLabel} from "../../../business/i18n";
import {BussSchemeMode, SeatStatus} from "../../model/EnumStatus";
import {TripUser} from "../../../business/entities/TripUser";
import {Trip} from "../../../business/entities/Trip";
import {BussSchedule} from "../../../business/entities/BussSchedule";
import {AuthUtil} from "../../auth/auth.util";
import {CommonUpdateService} from "../../../business/service/common-update.service";

@Component({
  selector: 'basic-buss-scheme',
  templateUrl: './basic-buss-scheme.component.html',
  styleUrls: ['./basic-buss-scheme.component.scss']
})
export class BasicBussSchemeComponent extends XeSubscriber implements OnInit {
  @Input() bussType: BussType;
  @Input("screen") parentScreen: XeScreen;
  @Input("readMode") _readMode;
  @Input() mode: BussSchemeMode = BussSchemeMode.readonly;

  seatGroupCriteria = SeatGroup.new();
  screens = {
    schemeEdit: "schemeEdit",
    schemeView: "schemeList",
  };
  screen = new XeScreen({home: this.screens.schemeView});

  ngOnInit(): void {
    this.seatGroupCriteria.bussType = this.bussType;
    this.bussType.seatGroups = EntityUtil.cachePkFromParent(this.bussType, BussType.meta, 'seatGroups', SeatGroup.meta);
    this.initOrderStatus();
  }

  seatGroupTable = SeatGroup.tableData({
    formData: {
      display: {
        bare: true,
        grid: true,
        cancelBtn: "close"
      },
      control: {
        allowDelete: true
      },
      action: {
        postCancel: () => this.screen.back(),
        postDelete: () => {
          this.refresh(this.bussType, BussType);
          this.screen.back();
        },
        postPersist: (seatGroup) => {
          this.bussType.seatGroups.unshift(seatGroup);
          this.screen.back();
          Notifier.success(XeLabel.SAVED_SUCCESSFULLY);
        },
        postUpdate: () => {
          this.refresh(this.bussType, BussType, (result) => {
            this.bussType = result;
          });
          this.seatGroupCriteria.bussType = this.bussType;
        }
      }
    }
  }, this.seatGroupCriteria);

  openNewSeatRange() {
    this.seatGroupTable.formData.share.entity = SeatGroup.new({bussType: this.bussType});
    this.screen.go(this.screens.schemeEdit);
  }

  openEditSeatGroup(seatGroup: SeatGroup) {
    this.seatGroupTable.formData.share.entity = seatGroup;
    this.screen.go(this.screens.schemeEdit);
  }

  bringUp(seatGroup: SeatGroup, groupIdx: number) {
    const swapGroup = this.bussType.seatGroups[groupIdx - 1];
    this.swapThenSort(swapGroup, seatGroup);
  }

  swapThenSort(minSwap: SeatGroup, maxSwap: SeatGroup) {
    const swapOrder = minSwap.seatGroupOrder;
    minSwap.seatGroupOrder = maxSwap.seatGroupOrder;
    maxSwap.seatGroupOrder = swapOrder;
    this.bussType.seatGroups.sort((s1, s2) => s2.seatGroupOrder - s1.seatGroupOrder);
    this.update([minSwap, maxSwap], SeatGroup);
  }

  bringDown(seatGroup: SeatGroup, groupIdx: number) {
    const swapGroup = this.bussType.seatGroups[groupIdx + 1];
    this.swapThenSort(seatGroup, swapGroup);
  }

  getCurrentSeatFrom() {
    const entity = this.seatGroupTable.formData?.share?.entity;
    const seatFrom = entity?.seatFrom ? entity.seatFrom : this.bussType?.totalSeats + 1;
    return seatFrom <= 0 ? 1 : seatFrom;
  }

  getCurrentSeatTo() {
    const seatTo = parseInt(String(this.getCurrentSeatFrom()), 10) + parseInt(String(this.seatGroupTable.formData?.share?.entity?.totalSeats), 10) - 1;
    return isNaN(seatTo) ? this.getCurrentSeatFrom() : seatTo;
  }

  // PROCESS ORDERING ========>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

  get isLoggedIn() {
    return AuthUtil.instance.isUserLoggedIn;
  }
  totalPrice: number;
  selectedSeats: number[];
  @Input() bussSchedule: BussSchedule;
  @Input() trip: Trip;
  @Input() tripUser: TripUser;

  private _seatStatus: SeatStatus[] = [];

  seatStatus(seatNo: number): SeatStatus {
    return this._seatStatus[seatNo] || SeatStatus.hidden;
  }

  initOrderStatus() {
    if (!this.mode?.hasOrdering) return;
    this.trip.lockedSeats.forEach(locked => {
      this._seatStatus[locked] = SeatStatus.locked;
    });
    this.trip.availableSeats.forEach(available => {
      this._seatStatus[available] = SeatStatus.available;
    });
    this.trip.bookedSeats.forEach(booked => {
      this._seatStatus[booked] = SeatStatus.booked;
    });

    if (AuthUtil.instance.isUserLoggedIn) {
      this.tripUser.phoneNumber = AuthUtil.instance.user.phoneNumber;
      this.tripUser.fullName = AuthUtil.instance.user.fullName;
    }
  }

  toggleSeatOrder(seatNo: number) {
    if (this._seatStatus[seatNo].hasClassesSeatAvailable) {
      this._seatStatus[seatNo] = SeatStatus.selected;
      TripUser.addSeat(this.tripUser, seatNo);
    } else if (this._seatStatus[seatNo].hasClassesSeatSelected) {
      this._seatStatus[seatNo] = SeatStatus.available;
      TripUser.removeSeat(this.tripUser, seatNo);
    }
  }

  clearOrderInfo() {
    this._seatStatus.forEach((seat, index) => this._seatStatus[index] = seat.hasClassesSeatSelected ? SeatStatus.available : seat);
    TripUser.clearOrderInfo(this.tripUser);
  }


  // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< END OF ORDER


  order() {

  }
}
