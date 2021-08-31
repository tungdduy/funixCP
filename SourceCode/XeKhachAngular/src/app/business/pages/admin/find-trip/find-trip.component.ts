import {AfterViewInit, Component} from '@angular/core';
import {BussSchedule} from "../../../entities/BussSchedule";
import {Observable, of} from "rxjs";
import {PathPoint} from "../../../entities/PathPoint";
import {Location} from "../../../entities/Location";
import {CommonUpdateService} from "../../../service/common-update.service";
import {ObjectUtil} from "../../../../framework/util/object.util";
import {InputTemplate} from "../../../../framework/model/EnumStatus";
import {BussScheduleCriteria} from "../my-trip/my-trip.component";
import {XeSubscriber} from "../../../../framework/model/XeSubscriber";
import {XeScreen} from "../../../../framework/components/xe-nav/xe-nav.component";

@Component({
  selector: 'xe-find-trip',
  styles: [],
  templateUrl: 'find-trip.component.html',
})
export class FindTripComponent  extends XeSubscriber implements AfterViewInit {
  screens = {
    trips: 'trips',
    newTrip: 'newTrips',
    viewSelectedSchedulePoints: 'viewSelectedSchedulePoints',
    orderBussScheme: 'orderBussScheme'
  };
  screen = new XeScreen({home: this.screens.trips});

  ngAfterViewInit(): void {
    // this.initTestBussSchedule();
  }

  bussScheduleTable = BussSchedule.tableData({
    xeScreen: this.screen,
    table: {
      mode: {
        readonly: true,
        hideSelectColumn: true,
        lazyData: () => of([]),
        hideSearchBox: true
      },
      selectBasicColumns: [
        'buss.company.companyName',
        'preparedTrip.preparedTripUser.startPoint',
        'preparedTrip.preparedTripUser.endPoint'],
      basicColumns: [
        {
          field: {name: 'preparedTrip.preparedTripUser.totalTripUserPoints'},
          action: {screen: this.screens.viewSelectedSchedulePoints},
          subColumns: [
            {field: {name: 'preparedTrip.preparedTotalAvailableSeats'}, action: {screen: this.screens.orderBussScheme}}
          ]
        },
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
      selectBasicColumns: ['path', 'pointName', 'location']
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

  postChangeSearchScheduleInput = (currentValue: Location, oldValue: Location, criteria: BussScheduleCriteria) => {
    const validResult = criteria.validator(criteria);
    if (validResult.countPointValid === 2 && !!criteria.launchDate) {
      CommonUpdateService.instance
        .findBussSchedules(criteria)
        .subscribe(result => criteria.tableComponent().updateTableData(result));
    }
  }

  initTestBussSchedule() {
    const testCriteria = {
      locationFrom: {locationId: 21305},
      locationTo: {locationId: 4721},
      launchDate: new Date(),
      tableComponent: () => this.bussScheduleTable.formData.share.tableComponent
    };
    CommonUpdateService.instance
      .findBussSchedules(testCriteria as unknown as BussScheduleCriteria)
      .subscribe(result => {
        console.log(result);
        testCriteria.tableComponent().updateTableData(result);
      });
  }

  launchDateInput = InputTemplate.date._postChange(this.postChangeSearchScheduleInput)._criteria(this.bussScheduleCriteria);
  bussSchedulePointInput = InputTemplate.scheduledLocation
    ._observable(CommonUpdateService.instance.findScheduledLocations)
    ._criteria(this.bussScheduleCriteria)
    ._postChange(this.postChangeSearchScheduleInput);

  get bussSchedule() {
    return this.bussScheduleTable.formData.share.entity;
  }
}
