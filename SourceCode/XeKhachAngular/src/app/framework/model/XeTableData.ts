import {EntityField, XeFormData} from "./XeFormData";
import {TemplateRef} from "@angular/core";
import {XeScreen} from "../components/xe-nav/xe-nav.component";
import {XeEntity} from "../../business/entities/XeEntity";

export interface IconOption {
  iconOnly?: string;
  iconPre?: string;
  iconAfter?: string;
}

export interface TableColumn {
  hiddenClass?: 'hidden-phone' | 'hidden-tablet' | 'd-none';
  type?: 'role' | 'avatar' | 'string' | 'money' | 'link' | 'boldString' | 'avatarRole' | 'boldStringRole' | 'avatarString' | 'iconOption';
  field?: EntityField;
  subColumns?: TableColumn[];
  display?: {
    header?: {
      css?: string;
      silence?: boolean;
      icon?: IconOption;
      inline?: boolean;
    };
    row?: {
      css?: string;
      icon?: IconOption;
      inline?: boolean;
    };
  };
  action?: {
    link?: string;
    onSelect?: (entity: any) => void;
    screen?: string;
  };
}

export interface ManualColumn {
  field: EntityField;
  template: () => TemplateRef<any>;
}

export class XeTableData<E extends XeEntity> {
  external?: {
    updateCriteriaTableOnSelect?: () => XeTableData<any>[];
    lookUpScreen?: string;
    parent?: XeTableData<any>;
  };
  display?: {
    fullScreenForm: boolean;
  };
  formData?: XeFormData<E>;
  xeScreen?: XeScreen;
  table?: {
    action?: {
      manualCreate?: () => any,
      filterCondition?: (entity: any) => boolean
    }
    basicColumns?: TableColumn[],
    manualColumns?: ManualColumn[]
  };

  static fullFill(tableData: XeTableData<any>) {
    if (!tableData.table) tableData.table = {};
    if (!tableData.table.action) tableData.table.action = {};
    if (!tableData.table.basicColumns) tableData.table.basicColumns = [];
    if (!tableData.display) tableData.display = {fullScreenForm: false};
    if (!tableData.formData.share) tableData.formData.share = {};
    XeFormData.fullFill(tableData.formData);
  }
}


