import {AfterContentInit, Component, Input, OnInit} from '@angular/core';
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
import {XeFormData} from "../../model/XeFormData";
import {RegexUtil} from "../../util/regex.util";
import {ObjectUtil} from "../../util/object.util";

@Component({
  selector: 'basic-buss-scheme',
  templateUrl: './basic-buss-scheme.component.html',
  styleUrls: ['./basic-buss-scheme.component.scss']
})
export class BasicBussSchemeComponent extends XeSubscriber implements OnInit, AfterContentInit {
  ngAfterContentInit(): void {
    if (ObjectUtil.isNumberGreaterThanZero(this.bussType)) {
      this.bussType = EntityUtil.getFromCache(BussType.meta.capName, this.bussType);
    }
    if (!this.bussType || !this.bussType.bussTypeId) return;
    this.seatGroupCriteria.bussType = this.bussType;
    this.bussType.seatGroups = EntityUtil.cachePkFromParent(this.bussType, BussType.meta, 'seatGroups', SeatGroup.meta);
    this.initOrderStatus();
  }

  @Input() bussType: BussType;
  @Input("screen") parentScreen: XeScreen;
  @Input("readMode") _readMode;
  @Input() mode: BussSchemeMode = BussSchemeMode.readonly;

  seatGroupCriteria = SeatGroup.new();
  screens = {
    schemeEdit: "schemeEdit",
    schemeView: "schemeList",
    orderSuccessfully: "orderSuccessfully"
  };
  screen = new XeScreen({home: this.screens.schemeView});

  ngOnInit(): void {
    this.initOrderStatus();
    this.initTripAdmin();
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
  tripUserForm: XeFormData<TripUser>;

  private _seatStatus: SeatStatus[] = [];

  seatStatus(seatNo: number): SeatStatus {
    return this._seatStatus[seatNo] || SeatStatus.hidden;
  }

  initOrderStatus() {
    if (!this.mode?.hasOrdering) return;
    this.trip.lockedSeats.forEach(locked => {
      this._seatStatus[locked] = SeatStatus.locked;
    });
    this.trip.lockedBussSeats.forEach(locked => {
      this._seatStatus[locked] = SeatStatus.locked;
    });
    this.trip.preparedAvailableSeats.forEach(available => {
      this._seatStatus[available] = SeatStatus.available;
    });
    this.trip.preparedBookedSeats.forEach(booked => {
      this._seatStatus[booked] = SeatStatus.booked;
    });
    this.tripUser.seats.forEach(seatNo => {
      this._seatStatus[seatNo] = SeatStatus.selected;
    });

    if (AuthUtil.instance.isUserLoggedIn) {
      this.tripUser.phoneNumber = AuthUtil.instance.user.phoneNumber;
      this.tripUser.fullName = AuthUtil.instance.user.fullName;
      this.tripUser.email = AuthUtil.instance.user.email;
      this.tripUser.trip = this.trip;
      this.tripUser.trip.bussSchedule = this.bussSchedule;
      this.tripUser.user = AuthUtil.instance.user;
    }
    this.tripUserForm = TripUser.tableData({
      formData: {
        action: {
          postPersist: (tripUser) => {
            Notifier.success(this.xeLabel.ORDER_SUCCESSFULLY);
            this.screen.go(this.screens.orderSuccessfully);
          }
        }
      }
    }, this.tripUser).formData;
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


  _errorMessage: string;

  get orderValidEmail() {
    return RegexUtil.isValidEmail(this.tripUser.email);
  }

  set errorMessage(error: string) {
    this._errorMessage = error;
    setTimeout(() => {
      this._errorMessage = undefined;
    }, 5000);
  }

  get errorMessage() {
    return this._errorMessage;
  }

  order() {
    if (this.tripUser.seats.length <= 0) {
      this.errorMessage = XeLabel.PLEASE_SELECT_SEATS;
      return;
    }

    const formError = this.tripUserForm.share.xeForm.errorMessages;
    if (formError.length > 0) {
      this.errorMessage = formError.join("<br/>");
      return;
    }
    this.errorMessage = undefined;
    this.tripUserForm.share.xeForm._onSubmit();
  }

  // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< END OF ORDER
  // ###############################################################
  // ###############################################################
  // ###############################################################

  // TRIP ADMIN ========>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

  initTripAdmin() {
    if (!this.mode.hasTripAdmin) return;

  }
  // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< END OF TRIP ADMIN
}
