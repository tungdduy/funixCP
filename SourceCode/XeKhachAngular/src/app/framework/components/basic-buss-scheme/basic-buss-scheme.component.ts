import {AfterContentInit, Component, Input, OnInit} from '@angular/core';
import {BussType} from "../../../business/entities/BussType";
import {XeScreen} from "../xe-nav/xe-nav.component";
import {SeatGroup} from "../../../business/entities/SeatGroup";
import {Notifier} from "../../notify/notify.service";
import {EntityUtil} from "../../util/EntityUtil";
import {XeSubscriber} from "../../model/XeSubscriber";
import {XeLabel} from "../../../business/i18n";
import {BussSchemeMode, EditOnRow, InputMode, InputTemplate, SeatStatus, TripUserStatus} from "../../model/EnumStatus";
import {TripUser} from "../../../business/entities/TripUser";
import {Trip} from "../../../business/entities/Trip";
import {BussSchedule} from "../../../business/entities/BussSchedule";
import {AuthUtil} from "../../auth/auth.util";
import {RegexUtil} from "../../util/regex.util";
import {ObjectUtil} from "../../util/object.util";
import {XeTableData} from "../../model/XeTableData";
import {CommonUpdateService} from "../../../business/service/common-update.service";
import {BussSchedulePoint} from "../../../business/entities/BussSchedulePoint";
import {Xe} from "../../model/Xe";
import {XeRouter} from "../../../business/service/xe-router";
import {Url} from "../../url/url.declare";
import {StorageUtil} from "../../util/storage.util";
import {configConstant} from "../../config.constant";
import {TicketInfo} from "../../model/XeFormData";
import {StringUtil} from "../../util/string.util";
import {Buss} from "../../../business/entities/Buss";

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

  @Input() buss: Buss;
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
    this.initBussAdmin();
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
          Xe.refresh(this.bussType, BussType.meta);
          this.screen.back();
        },
        postPersist: (seatGroup) => {
          this.bussType.seatGroups.unshift(seatGroup);
          this.bussType.totalSeats += seatGroup.totalSeats;
          this.screen.back();
          Notifier.success(XeLabel.SAVED_SUCCESSFULLY);
        },
        postUpdate: () => {
          Xe.refresh(this.bussType, BussType.meta, (result) => {
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
    Xe.updateFields([minSwap, maxSwap], ['seatGroupOrder'], SeatGroup.meta);
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
  @Input() preparedTripUser: TripUser;

  private _seatStatus: SeatStatus[] = [];

  seatStatus(seatNo: number): SeatStatus {
    return this._seatStatus[seatNo] || SeatStatus.available;
  }

  initOrderStatus() {
    if (!this.mode?.hasOrdering) return;
    this.updateTripOrderSeatStatuses();
    this.preparedTripUser.seats.forEach(seatNo => {
      this._seatStatus[seatNo] = SeatStatus.selected;
    });

    if (AuthUtil.instance.isUserLoggedIn) {
      this.preparedTripUser.phoneNumber = AuthUtil.instance.user.phoneNumber;
      this.preparedTripUser.fullName = AuthUtil.instance.user.fullName;
      this.preparedTripUser.email = AuthUtil.instance.user.email;
      this.preparedTripUser.user = AuthUtil.instance.user;
    }
    this.preparedTripUser.trip = this.trip;
    this.preparedTripUser.trip.bussSchedule = this.bussSchedule;
    this.tripUserTable = TripUser.tableData({
      formData: {
        action: {
          postPersist: (tripUser: TripUser) => {
            Notifier.success(this.xeLabel.ORDER_SUCCESSFULLY);
            this.screen.go(this.screens.orderSuccessfully);
            const tickets = StorageUtil.getFromJson(configConstant.TICKET_INFOS) as TicketInfo || {
              phone: "",
              email: ""
            };
            if (tickets) {
              if (typeof tickets.phone !== 'string') tickets.phone = "";
              if (typeof tickets.email !== 'string') tickets.email = "";
              if (!tickets.phone.split(",").includes(tripUser.phoneNumber)) {
                tickets.phone += (StringUtil.isBlank(tickets.phone) ? "" : ",") + tripUser.phoneNumber;
              }
              if (!tickets.email.split(",").includes(tripUser.email)) {
                tickets.email += (StringUtil.isBlank(tickets.email) ? "" : ",") + tripUser.email;
              }
            }
            StorageUtil.setItem(configConstant.TICKET_INFOS, tickets);
          }
        }
      }
    }, this.preparedTripUser);
  }

  private updateTripOrderSeatStatuses() {
    this.trip.lockedSeats.forEach(locked => {
      this._seatStatus[locked] = SeatStatus.locked;
    });
    this.trip.lockedBussSeats.forEach(locked => {
      this._seatStatus[locked] = SeatStatus.locked;
    });
    this.initSeatStatus(this.trip.preparedAvailableSeats, SeatStatus.available);
    this.initSeatStatus(this.trip.preparedBookedSeats, SeatStatus.booked);
  }

  toggleSeatOrder(seatNo: number) {
    if (this._seatStatus[seatNo].hasClassesSeatAvailable) {
      this._seatStatus[seatNo] = SeatStatus.selected;
      TripUser.addSeat(this.preparedTripUser, seatNo);
    } else if (this._seatStatus[seatNo].hasClassesSeatSelected) {
      this._seatStatus[seatNo] = SeatStatus.available;
      TripUser.removeSeat(this.preparedTripUser, seatNo);
    }
  }

  clearOrderInfo() {
    this._seatStatus.forEach((seat, index) => this._seatStatus[index] = seat.hasClassesSeatSelected ? SeatStatus.available : seat);
    TripUser.clearOrderInfo(this.preparedTripUser);
  }


  _errorMessage: string;

  get orderValidEmail() {
    return RegexUtil.isValidEmail(this.preparedTripUser.email);
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
    if (this.preparedTripUser.seats.length <= 0) {
      this.errorMessage = XeLabel.PLEASE_SELECT_SEATS;
      return;
    }

    const formError = this.tripUserTable.formData.share.xeForm.errorMessages;
    if (formError.length > 0) {
      this.errorMessage = formError.join("<br/>");
      return;
    }
    this.errorMessage = undefined;
    this.tripUserTable.formData.share.xeForm._onSubmit();
  }

  viewHistory() {
    XeRouter.navigate(Url.app.ADMIN.MY_TRIP.noHost);
  }

  findTrip() {
    location.reload();
  }

  // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< END OF ORDER
  // ###############################################################
  // ###############################################################
  // ###############################################################

  // TRIP ADMIN ========>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

  initTripAdmin() {
    if (!this.mode.hasTripAdmin) return;
    Xe.fill(this.trip, Trip.meta);
    this.initTripAdminSeatStatuses();
    this.tripUserTable = TripUser.tableData({
      display: {
        fullScreenForm: true,
        toggleOne: true
      },
      table: {
        mode: {
          selectOneOnly: true,
          hideSelectColumn: true
        },
        selectBasicColumns: ['fullName', 'totalPrice'],
        basicColumns: [
          {
            field: {
              name: 'status', mode: InputMode.inputNoTitle, template: InputTemplate.tripUserStatus
                ._postChange((currentValue, oldValue) => {
                  const status = TripUserStatus[oldValue] as TripUserStatus;
                  const currentStatus = TripUserStatus[currentValue] as TripUserStatus;
                  let confirmedBy = AuthUtil.instance.user.employee.employeeId;
                  if (currentStatus && currentStatus.isPENDING) {
                    confirmedBy = null;
                  }
                  Xe.updateFields(this.selectedTripUser, {
                    'status': currentValue,
                    'removeOverlapSeats': status.isDELETED,
                    'confirmedBy': confirmedBy
                  }, TripUser.meta, (newTripUser) => {
                    Object.assign(this.selectedTripUser, newTripUser);
                  });
                })
            }
          }
        ],
        action: {
          onClickBtnCreate: () => {
            console.log(this.selectedTripUser);
            const tripUser = EntityUtil.getAllPossibleId(this.selectedTripUser, this.tripUserTable.formData.entityIdentifier);
            tripUser['tripUserId'] = null;
            CommonUpdateService.instance.insert(tripUser as TripUser, TripUser.meta).subscribe(
              newTripUser => {
                EntityUtil.cache(newTripUser, TripUser.meta);
                EntityUtil.fill(newTripUser, TripUser.meta);
                this.tripUserTable.formData.share.entity = newTripUser;
                this.tripUserTable.formData.share.tableSource.data.unshift(newTripUser);
                this.tripUserTable.formData.share.tableSource.data = this.tripUserTable.formData.share.tableSource.data;
              }
            );
          },
          editOnRow: EditOnRow.always,
          postSelect: (selectedTripUser: TripUser) => {
            if (this.isAdminReviewing) {
              this.isAdminReviewing = false;
            }
            this.refreshTripUser(selectedTripUser, false);
          },
          postDeSelect: () => {
            this.preparedTripUser = null;
            this.initTripAdminSeatStatuses();
          }
        }
      },
      formData: {
        display: {
          noButton: false
        },
        action: {
          postPersist: (tripUser) => {
            this.tripUserTable.formData.share.entity = tripUser;
            Notifier.success(this.xeLabel.SAVED_SUCCESSFULLY);
          },
          postUpdate: (tripUser) => {
            this.tripUserTable.formData.share.entity = tripUser;
            Notifier.success(this.xeLabel.SAVED_SUCCESSFULLY);
          },
        },
        fields: []
      }
    }, TripUser.new({trip: this.trip}));

    this.bussSchedulePointTable = BussSchedulePoint.tableData({}, BussSchedulePoint.new({
      bussSchedule: this.bussSchedule
    }));
    this.bussSchedulePointInput._tableData(this.bussSchedulePointTable)
      ._criteria({})
      ._postChange((currentPoint: BussSchedulePoint, oldPoint: any, criteria) => {
        if (criteria.inputName === 'startPoint') {
          this.setSelectedTripUserStartPoint(currentPoint);
        } else if (criteria.inputName === 'endPoint') {
          this.setSelectedTripUserEndPoint(currentPoint);
        }
      });
  }

  get tripUserTableComponent() {
    return this.tripUserTable.formData?.share?.tableComponent;
  }

  private updateThenRefreshTripUser(tripUser: TripUser, fields: any) {
    Xe.updateFields(this.selectedTripUser, fields, TripUser.meta, newTripUser => {
      Xe.assignResult(newTripUser, TripUser.meta, this.selectedTripUser);
      this.refreshTripUser(this.selectedTripUser);
    });
  }

  private refreshTripUser(selectedTripUser: TripUser, updateTable = true) {
    CommonUpdateService.instance.getTripWithPreparedTripUser(this.trip.tripId,
      selectedTripUser.tripUserId)
      .subscribe(trip => {
        EntityUtil.cache(trip, Trip.meta);
        EntityUtil.fill(trip.preparedTripUser, TripUser.meta);
        this.trip = trip;
        this.preparedTripUser = trip.preparedTripUser;
        this.initTripAdminSeatStatuses();
      });
  }

  get selectedTripUser() {
    return this?.tripUserTable?.formData?.share?.entity;
  }

  get selectedTripUserStartPoint() {
    const startPointId = Xe.get(this.selectedTripUser, TripUser.meta, 'startPoint.pathPointId');
    return this.getScheduledPointByPathPointId(startPointId);
  }

  setSelectedTripUserStartPoint(point: BussSchedulePoint) {
    if (point && point.pathPointId !== Xe.get(this.selectedTripUser, TripUser.meta, 'startPoint.pathPointId')) {
      if (point.pathPoint.pointOrder >= this.selectedTripUser?.endPoint?.pointOrder) {
        Notifier.error(XeLabel.INVALID_INPUT);
        return;
      }
      this.selectedTripUser.startPoint = point.pathPoint;
      this.updateThenRefreshTripUser(this.selectedTripUser, ['startPoint']);
    }
  }

  get selectedTripUserEndPoint(): BussSchedulePoint {
    const endPointId = Xe.get(this.selectedTripUser, TripUser.meta, 'endPoint.pathPointId');
    return this.getScheduledPointByPathPointId(endPointId);
  }

  private getScheduledPointByPathPointId(pathPointId) {
    return this.bussSchedule?.sortedBussSchedulePoints
      .filter(point => point.pathPointId === pathPointId)[0];
  }

  setSelectedTripUserEndPoint(point: BussSchedulePoint) {
    if (point && point.pathPointId !== Xe.get(this.selectedTripUser, TripUser.meta, 'endPoint.pathPointId')) {
      this.selectedTripUser.endPoint = point.pathPoint;
      this.updateThenRefreshTripUser(this.selectedTripUser, ['endPoint']);
    }

  }

  get confirmedByName() {
    return Xe.get(this.selectedTripUser, TripUser.meta, 'confirmedBy.user.fullName');
  }

  tripUserTable: XeTableData<TripUser>;

  private initTripAdminSeatStatuses() {
    this.initSeatStatus(this.trip.lockedSeats, SeatStatus.lockedByTrip);
    this.initSeatStatus(this.trip.lockedBussSeats, SeatStatus.lockedByBuss);
    if (this.isAdminReviewing) {
      this.initSeatStatus(this.trip.availableSeats, SeatStatus.available);
      this.initSeatStatus(this.trip.bookedSeats, SeatStatus.booked);
      this.initSeatStatus(this.trip.confirmedSeats, SeatStatus.confirmed);
    } else {
      this.initSeatStatus(this.trip.preparedBookedSeats, SeatStatus.booked);
      this.initSeatStatus(this.trip.confirmedSeats, SeatStatus.confirmed);
      this.initSeatStatus(this.trip.preparedAvailableSeats, SeatStatus.available);
    }
    if (this.preparedTripUser) {
      this.initSeatStatus(this.preparedTripUser.seats, SeatStatus.selected);
    }

  }

  initSeatStatus(seats: number[], status: SeatStatus) {
    if (!seats) return;
    seats.forEach(seat => {
      this._seatStatus[seat] = SeatStatus[status.name];
    });
  }

  private isAdminReviewing = true;

  private toggleByTripAdmin(seatNo: number) {
    const seat = this._seatStatus[seatNo];
    if (seat.hasClassesSeatLockedByBuss) return;
    if (this.preparedTripUser && !this.isAdminReviewing) {
      this.updateToggledSeatForPreparedTrip(seatNo);
    } else {
      this.isAdminReviewing = true;
      if (seat.hasClassesSeatAvailable) {
        seat.toClassesSeatLockedByTrip();
        Trip.addLockedSeat(this.trip, seatNo);
        return;
      }
      if (seat.hasClassesSeatLockedByTrip) {
        seat.toClassesSeatAvailable();
        Trip.removeLockedSeat(this.trip, seatNo);
        return;
      }
      if (seat.hasClassesSeatBooked || seat.hasClassesSeatConfirmed) {
        this.preparedTripUser = this.seatToTripUser[seatNo];
        this.tripUserTable.formData.share.tableComponent.toggleRowWithoutAction(this.preparedTripUser);
        this.initTripAdminSeatStatuses();
      }
    }
  }

  bussSchedulePointTable: XeTableData<BussSchedulePoint>;
  bussSchedulePointInput = InputTemplate.bussSchedulePoint;

  private updateToggledSeatForPreparedTrip(seatNo: number) {
    const status = this._seatStatus[seatNo];
    if (status.hasLocked || status.hasLockedByTrip || status.hasLockedByBuss) {
      Notifier.warning(XeLabel.UNLOCK_FIRST);
    } else if (status.hasClassesSeatSelected) {
      status.resetClasses();
      if (this.selectedTripUser?.seats.includes(seatNo)) {
        TripUser.removeSeat(this.selectedTripUser, seatNo, true);
        this._seatStatus[seatNo] = SeatStatus.available;
      }
    } else if (status.hasBooked || status.hasConfirmed) {
      Notifier.warning(XeLabel.THIS_SEAT_IS_ALREADY_BOOKED);
    } else {
      status.toClassesSeatSelected();
      if (this.selectedTripUser && !this.selectedTripUser.seats?.includes(seatNo)) {
        TripUser.addSeat(this.selectedTripUser, seatNo, true);
      }
    }
  }

  _seatToTripUser;
  get seatToTripUser() {
    if (!this._seatToTripUser) this.updateSeatToTripUser();
    return this._seatToTripUser;
  }

  updateSeatToTripUser() {
    this._seatToTripUser = {};
    this.tripUserTable.formData.share.tableEntities.forEach(tripUser => {
      tripUser.seats?.forEach(seatNo => {
        this._seatToTripUser[seatNo] = tripUser;
      });
    });
  }


// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< END OF TRIP ADMIN

  // ###############################################################
  // ###############################################################
  // ###############################################################

  // BUSS ADMIN ========>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

  toggleSeat(seatNo: number) {
    if (this.mode.hasOrdering) return this.toggleSeatOrder(seatNo);
    if (this.mode.hasTripAdmin) return this.toggleByTripAdmin(seatNo);
    if (this.mode.hasBussAdmin) return this.toggleByBussAdmin(seatNo);
  }

  initBussAdmin() {
    if (!this.mode.hasBussAdmin) return;
    this.initSeatStatus(this.bussType.seats, SeatStatus.available);
    this.initSeatStatus(this.buss.lockedSeats, SeatStatus.lockedByBuss);
  }

  private toggleByBussAdmin(seatNo: number) {
    if (this.seatStatus(seatNo).hasClassesSeatAvailable) {
      Buss.addLockedSeat(this.buss, seatNo);
      this.seatStatus(seatNo).toClassesSeatLockedByBuss();
    } else {
      Buss.removeLockedSeat(this.buss, seatNo);
      this.seatStatus(seatNo).toClassesSeatAvailable();
    }
  }
}
