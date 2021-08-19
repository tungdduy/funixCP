import {Component, OnInit} from '@angular/core';
import {BussPoint} from "../../../entities/BussPoint";
import {XeScreen} from "../../../../framework/components/xe-nav/xe-nav.component";
import {FormAbstract} from "../../../../framework/model/form.abstract";
import {Location} from "../../../entities/Location";
import {Observable, Subject} from "rxjs";
import {debounceTime, distinctUntilChanged, switchMap} from "rxjs/operators";
import {LocationService} from "../../../service/location.service";
import {StringUtil} from "../../../../framework/util/string.util";
import {EntityUtil} from "../../../../framework/util/entity.util";
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {Notifier} from "../../../../framework/notify/notify.service";
import {XeLabel, XeLbl} from "../../../i18n";

@Component({
  selector: 'xe-buss-point',
  styles: [],
  templateUrl: 'buss-point.component.html',
})
export class BussPointComponent extends FormAbstract implements OnInit {
  myCompany = AuthUtil.instance.user?.employee?.company;
  screens = {
    bussPoints: 'bussPoints',
    create: 'create',
  };
  screen = new XeScreen({
    home: this.screens.bussPoints,
    homeIcon: 'map-marker-alt'
  });

  bussPointTable = BussPoint.tableData({
    table: {
      action: {
        manualCreate: () => {
          this.bussPointTable.formData.share.entity = EntityUtil.newByEntityDefine(this.bussPointTable.formData.entityIdentifier);
          this.updateNewBussPointForm();
          this.screen.go(this.screens.create);
        }
      }
    },
    formData: {
      action: {
        postCancel: () => this.screen.back(),
        postUpdate: () => {
          this.screen.back();
          this.revertBussPointForm();
          Notifier.success(XeLbl(XeLabel.SAVED_SUCCESSFULLY));
        }
      }
    }
  }, BussPoint.new({
    company: this.myCompany
  }));
  currentBussPointInTable = (): BussPoint => this.bussPointTable.formData.share.entity;

  searchLocationText: any;
  cancelBussPoint = () => {
    this.screen.go(this.screens.bussPoints);
  }

  constructor(private locationService: LocationService) {
    super();
  }

  locations$: Observable<Location[]>;
  private searchLocationTerm = new Subject<string>();

  ngOnInit(): void {
    this.locations$ = this.searchLocationTerm.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((term: string) => this.locationService.searchLocation(term))
    );
  }

  displayLocation(location: Location) {
    if (!location) {
      return '';
    }
    EntityUtil.cache(location, [
      {
        fieldName: 'parent',
        fieldClassName: 'Location',
        entityClassName: 'Location',
        children: [{
          fieldName: 'parent',
          fieldClassName: 'Location',
          entityClassName: 'Location',
        }]
      }
    ]);
    return `${location.locationName}${location.parent ? ', ' + location.parent.locationName : ''}${location.parent?.parent ? ', ' + location.parent.parent.locationName : ''}`;
  }

  searchLocation() {
    if (!StringUtil.isBlank(this.searchLocationText)) {
      this.searchLocationTerm.next(this.searchLocationText);
    }
  }

  selectLocation(location: any) {
    this.searchLocationText = undefined;
    this.currentBussPointInTable().location = location;
    this.currentBussPointInTable().locationId = location.locationId;
  }

  backToBussPointList() {
    this.revertBussPointForm();
    this.screen.go(this.screens.bussPoints);
  }

  private updateNewBussPointForm() {
    this.bussPointTable.formData.display.bare = true;
  }

  private revertBussPointForm() {
    this.bussPointTable.formData.display.bare = false;
  }
}
