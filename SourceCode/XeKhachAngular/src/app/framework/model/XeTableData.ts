import {EntityField, XeFormData} from "./XeFormData";
import {TemplateRef} from "@angular/core";
import {XeScreen} from "../components/xe-nav/xe-nav.component";

export interface IconOption {
  iconOnly?: string;
  iconPre?: string;
  iconAfter?: string;
}

export interface TableColumn {
  hiddenClass?: 'hidden-phone' | 'hidden-tablet' | 'd-none';
  inline?: boolean;
  type: 'role' | 'avatar' | 'string' | 'money' | 'link' | 'boldString' | 'avatarRole' | 'boldStringRole' | 'avatarString' | 'iconOption';
  field: EntityField;
  subColumns?: TableColumn[];
  icon?: IconOption;
  rowIcon?: IconOption;
  link?: string;
  action?: (entity: any) => void;
}

export interface ManualColumn {
  field: EntityField;
  template: () => TemplateRef<any>;
}

export declare interface XeTableData {
  formData: XeFormData;
  xeScreen?: XeScreen;
  table: {
    filterCondition?: (entity: any) => boolean,
    basicColumns: TableColumn[],
    manualColumns?: ManualColumn[]
  };
}


