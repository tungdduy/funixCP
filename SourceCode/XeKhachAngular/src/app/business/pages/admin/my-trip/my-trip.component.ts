import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {TripUser} from "../../../entities/TripUser";
import {XeSubscriber} from "../../../../framework/model/XeSubscriber";
import {User} from "../../../entities/User";
import {XeScreen} from "../../../../framework/components/xe-nav/xe-nav.component";
import {InputTemplate} from "../../../../framework/model/EnumStatus";
import {Observable, of} from "rxjs";
import {CommonUpdateService} from "../../../service/common-update.service";
import {Location} from "../../../entities/Location";
import {ObjectUtil} from "../../../../framework/util/object.util";
import {BussSchedule} from "../../../entities/BussSchedule";
import {XeTableComponent} from "../../../../framework/components/xe-table/xe-table.component";
import {PathPoint} from "../../../entities/PathPoint";

export interface BussScheduleCriteria {
  locationFrom: Location;
  locationTo: Location;
  launchDate: Date;
  inputName?: string;
  inputText?: string;
  validator?: (criteria: BussScheduleCriteria) => any;
  tableComponent?: () => XeTableComponent<any>;
}

@Component({
  selector: 'xe-my-trip',
  styles: [],
  templateUrl: 'my-trip.component.html',
})
export class MyTripComponent extends XeSubscriber implements AfterViewInit {
  user = AuthUtil.instance.user;
  screens = {
    trips: 'trips',
    newTrip: 'newTrips',
    viewSelectedSchedulePoints: 'viewSelectedSchedulePoints',
    orderBussScheme: 'orderBussScheme'
  };
  screen = new XeScreen({home: this.screens.trips});

  tripUserTable = TripUser.tableData({
    xeScreen: this.screen,
    display: {
      fullScreenForm: true
    }
  }, TripUser.new({
    user: this.user
  }));


  ngAfterViewInit(): void {
    this.refresh(this.user, User);
    this.initBussSchedule();
  }

  @ViewChild("manualSeatContent") manualSeatContent;

  bussScheduleTable = BussSchedule.tableData({
    xeScreen: this.screen,
    table: {
      mode: {
        readonly: true,
        hideSelectColumn: true,
        lazyData: () => of([]),
        hideSearchBox: true
      },
      basicColumns: [
        'buss.company.companyName',
        'preparedTrip.preparedTripUser.startPoint',
        'preparedTrip.preparedTripUser.endPoint',
        {
          field: {name: 'preparedTrip.preparedTripUser.totalTripUserPoints'},
          action: {screen: this.screens.viewSelectedSchedulePoints},
          subColumns: [
            {field: {name: 'preparedTrip.totalAvailableSeats'}, action: {screen: this.screens.orderBussScheme}}
          ]
        },
        'preparedTrip.totalAvailableSeats'
      ],
    },
    formData: {
      entityIdentifier: BussSchedule.entityIdentifier(BussSchedule.new()),
      share: {entity: BussSchedule.new()},
    }
  });

  tripUserPointTable = PathPoint.tableData({
    xeScreen: this.screen,
    table: {
      mode: {
        hideSelectColumn: true
      },
      customData: () => this.bussScheduleTable.formData.share.entity.preparedTrip.preparedTripUser.tripUserPoints,
      basicColumns: ['path', 'pointName', 'location']
    },
  });

  bussScheduleCriteria: BussScheduleCriteria = {
    locationFrom: Location.new(),
    locationTo: Location.new(),
    launchDate: new Date(),
    validator: (criteria) => this.validateBussScheduleCriteria(criteria),
    tableComponent: () => this.bussScheduleTable.formData.share.tableComponent
  };

  searchScheduledObservable(criteria: BussScheduleCriteria): Observable<any> {
    return CommonUpdateService.instance.findScheduledLocations(criteria);
  }

  validateBussScheduleCriteria(criteria): { countPointValid: number, validId: number } {
    let countPointValid = 0;
    let validId = criteria.locationFrom?.locationId;
    if (ObjectUtil.isNumberGreaterThanZero(validId)) {
      countPointValid++;
    }
    if (ObjectUtil.isNumberGreaterThanZero(this.bussScheduleCriteria.locationTo?.locationId)) {
      countPointValid++;
      validId = criteria.locationTo?.locationId;
    }
    return {countPointValid, validId};
  }

  onChangeSearchScheduleInput = (inputValue: Location, criteria: BussScheduleCriteria) => {
    const validResult = criteria.validator(criteria);
    if (validResult.countPointValid === 2 && !!criteria.launchDate) {
      CommonUpdateService.instance
        .findBussSchedules(criteria)
        .subscribe(result => criteria.tableComponent().updateTableData(result));
    }
  }

  initBussSchedule() {
    const testCriteria = {
      locationFrom: {locationId: 21305},
      locationTo: {locationId: 4721},
      launchDate: new Date(),
      tableComponent: () => this.bussScheduleTable.formData.share.tableComponent
    };
    CommonUpdateService.instance
      .findBussSchedules(testCriteria as unknown as BussScheduleCriteria)
      .subscribe(result => testCriteria.tableComponent().updateTableData(result));
  }

  bussSchedulePointInput = InputTemplate.scheduledLocation
    ._observable(CommonUpdateService.instance.findScheduledLocations)
    ._criteria(this.bussScheduleCriteria)
    ._postChange(this.onChangeSearchScheduleInput);

  get preparedTrip() {
    return this.bussScheduleTable.formData.share.entity.preparedTrip;
  }
  get preparedTripUser() {
    return this.bussScheduleTable.formData.share.entity.preparedTrip.preparedTripUser;
  }
  get selectedBussType() {
    return this.bussScheduleTable.formData.share.entity.buss.bussType;
  }

}
