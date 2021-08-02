import {Component} from '@angular/core';
import {Company} from "../../../model/company";
import {FormAbstract} from "../../../abstract/form.abstract";
import { XeTableData } from '../../../abstract/XeTableData';

@Component({
  selector: 'xe-company-manager',
  styleUrls: ['company-manager.component.scss'],
  templateUrl: 'company-manager.component.html',
})
export class CompanyManagerComponent extends FormAbstract {

  companyTable: XeTableData = {
    share: {entity: Company},
    className: "Company",
    idColumns: {companyId: 0},
    table: {
      basicColumns: [
        {name: 'companyName', type: "avatarString"},
        {name: 'companyDesc', type: "string"}
      ],
    },
    formData: {
      share: {entity: Company},
      header: {
        titleKey: 'companyName',
        descKey: 'companyDesc',
      },
      fields: [
        {name: "companyName", required: false},
        {name: "companyDesc", required: false},
      ]
    }
  };

}
