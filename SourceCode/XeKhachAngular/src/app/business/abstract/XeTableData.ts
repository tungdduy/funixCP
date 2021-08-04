import {EntityField, XeFormData} from "./XeFormData";
import {TemplateRef} from "@angular/core";

export interface TableColumn {
  hiddenClass?: 'hidden-phone' | 'hidden-tablet';
  type: 'role' | 'avatar' | 'string' | 'money' | 'link' | 'boldString' | 'avatarRole' | 'boldStringRole' | 'avatarString';
  field: EntityField;
  link?: string;
}

export interface ManualColumn {
  field: EntityField;
  template: () => TemplateRef<any>;
}

export declare interface XeTableData {
  formData: XeFormData;
  table: {
    filterCondition?: (entity: any) => boolean
    basicColumns: TableColumn[],
    manualColumns?: ManualColumn[]
  };
}


