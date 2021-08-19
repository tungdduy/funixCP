import {Component, Input, OnInit} from '@angular/core';
import {BussType} from "../../../business/entities/BussType";
import {XeScreen} from "../xe-nav/xe-nav.component";
import {SeatGroup} from "../../../business/entities/SeatGroup";
import {Notifier} from "../../notify/notify.service";
import {EntityUtil} from "../../util/entity.util";
import {XeSubscriber} from "../../model/XeSubscriber";
import {XeLabel} from "../../../business/i18n";

@Component({
  selector: 'basic-buss-scheme',
  templateUrl: './basic-buss-scheme.component.html',
  styleUrls: ['./basic-buss-scheme.component.scss']
})
export class BasicBussSchemeComponent extends XeSubscriber implements OnInit {
  @Input() bussType: BussType;
  @Input("screen") parentScreen: XeScreen;
  @Input("readMode") _readMode;
  get readMode() {
    return this._readMode === '' || this._readMode === true;
  }
  get editMode() {
    return !this.readMode;
  }

  seatGroupCriteria = SeatGroup.new();
  screens = {
    schemeEdit: "schemeEdit",
    schemeList: "schemeList"
  };
  screen = new XeScreen({home: this.screens.schemeList});

  ngOnInit(): void {
    this.seatGroupCriteria.bussType = this.bussType;
    this.bussType.seatGroups = EntityUtil.cachePkFromParent(this.bussType, BussType, 'seatGroups', SeatGroup);
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
  seatGroup: SeatGroup;

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
    return entity?.seatFrom ? entity.seatFrom : this.bussType?.totalSeats;
  }

  getCurrentSeatTo() {
    return parseInt(String(this.getCurrentSeatFrom()), 10) + parseInt(String(this.seatGroupTable.formData?.share?.entity?.totalSeats), 10) - 1;
  }
}
