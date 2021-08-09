import {AfterViewInit, Component} from '@angular/core';
import {XeTableData} from "../../../../framework/model/XeTableData";
import {BussType, BussTypeUtil} from "../../../entities/BussType";
import {Buss} from "../../../entities/Buss";
import {XeSubscriber} from "../../../../framework/model/XeSubscriber";
import {Company} from "../../../entities/Company";
import {CommonUpdateService} from "../../../service/common-update.service";
import {Notifier} from "../../../../framework/notify/notify.service";
import {SelectItem} from "../../../../framework/model/SelectItem";
import {XeScreen} from "../../../../framework/components/xe-nav/xe-nav.component";
import {BasicBussScheme} from "../../../../framework/components/basic-buss-scheme/basic-buss-scheme.component";

@Component({
  selector: 'xe-buss-type',
  styleUrls: ['buss-type.component.scss'],
  templateUrl: 'buss-type.component.html',
})
export class BussTypeComponent extends XeSubscriber implements AfterViewInit {

  screens = {
    bussTypeList: 'bussTypeList',
    bussScheme: 'bussScheme',
    busses: 'busses'
  };
  screen = new XeScreen(this.screens.bussTypeList, 'pencil-ruler', () => `${this.currentBussType.bussTypeName} (${this.currentBussType.bussTypeCode})`);
  currentBussType: BussType;

  viewScheme = (bussOrType: BussType | Buss) => {
    if ("bussId" in bussOrType) {
      this.currentBussType = bussOrType.bussType;
    } else {
      this.currentBussType = bussOrType;
    }
    this.screen.go(this.screens.bussScheme);
  }

  viewBusses = (bussType) => {
    this.currentBussType = bussType;
    this.screen.go(this.screens.busses);
  }

  bussTypeTable: XeTableData = {
    table: {
      basicColumns: [
        {field: {name: 'profileImageUrl'}, type: "avatar"},
        {field: {name: 'bussTypeCode'}, type: "boldStringRole"},
        {
          field: {name: 'bussTypeName'}, type: "boldStringRole",
          subColumns: [
            {field: {name: 'bussTypeDesc', css: 'd-block'}, type: "string"}
          ]
        },

        {field: {name: 'totalSeats'}, type: "iconOption", rowIcon: {iconAfter: "couch"}, action: this.viewScheme},
        {field: {name: 'totalBusses'}, type: "iconOption", rowIcon: {iconAfter: "bus"}, action: this.viewBusses},
      ],
    },
    formData: {
      entityIdentifier: {
        className: "BussType",
        idFields: () => [
          {name: "bussTypeId", value: 0}
        ],
      },
      share: {entity: BussType},
      header: {
        profileImage: {name: 'profileImageUrl'},
        titleField: {name: 'bussTypeName'},
        descField: {name: 'bussTypeDesc'},
      },
      fields: [
        {name: "bussTypeCode", required: true, selectOneMenu: () => BussTypeUtil.bussTypeCodeSelectItems},
        {name: "bussTypeName", required: true},
        {name: "bussTypeDesc", required: true},
        {name: "totalSeats", required: true},
      ]
    }
  };

  companySelectItems: SelectItem<Company>[];
  bussTable: XeTableData = {
    xeScreen: this.screen,
    table: {
      basicColumns: [
        {
          field: {name: 'company.companyName'}, type: "boldStringRole",
          subColumns: [
            {field: {name: 'company.companyDesc', css: 'd-block'}, type: "string"}
          ]
        },
        {
          field: {name: 'bussLicense'}, type: "boldStringRole",
          subColumns: [
            {field: {name: 'bussDesc', css: 'd-block'}, type: "string"}
          ]
        },

      ],
    },
    formData: {
      entityIdentifier: {
        className: "Buss",
        idFields: () => [
          {name: "bussTypeId", value: this.currentBussType.bussTypeId},
          {name: "bussId", value: 0},
          {name: "companyId", value: 0}
        ],
      },
      share: {entity: Buss},
      header: {
        profileImage: {name: 'bussType.profileImageUrl'},
        titleField: {name: 'bussLicense'},
        descField: {name: 'bussDesc'},
      },
      fields: [
        {name: "bussLicense", required: true},
        {name: "bussDesc", required: true},
        {
          name: "companyId",
          newOnly: true,
          required: true,
          lblKey: 'selectCompany',
          selectOneMenu: () => this.companySelectItems
        },
      ]
    }
  };
  bussScheme: BasicBussScheme = {
    bussType: () => this.currentBussType,
    turnBackAction: () => this.screen.back()
  };

  ngAfterViewInit(): void {
    this.subscriptions.push(
      CommonUpdateService.instance.getAll<Company>(Company.identifier.idForSearchAll, "Company").subscribe(
        companies => this.companySelectItems = companies.map(c => new SelectItem<Company>(c.companyName, c.companyId)),
        error => Notifier.httpErrorResponse(error)
      )
    );
  }

}
