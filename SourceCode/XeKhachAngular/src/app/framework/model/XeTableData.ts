import {EntityField, ShareFormData, XeFormData} from "./XeFormData";
import {TemplateRef} from "@angular/core";
import {XeScreen} from "../components/xe-nav/xe-nav.component";
import {XeEntity} from "../../business/entities/XeEntity";
import {Observable} from "rxjs";
import {EditOnRow} from "./EnumStatus";

export interface IconOption {
  iconOnly?: string;
  iconPre?: string;
  iconAfter?: string;
}

export interface EntityFilter {
  filterArray?: (entities: any[]) => any[];
  filterSingle?: (entity) => boolean;
}

export interface TableColumn {
  hiddenClass?: 'hidden-phone' | 'hidden-tablet' | 'd-none';
  type?: 'role' | 'avatar' | 'string' | 'money' | 'link' | 'boldString' | 'avatarRole' | 'boldStringRole' | 'avatarString' | 'iconOption';
  field?: EntityField;
  subColumns?: TableColumn[];
  display?: {
    header?: {
      title?: string;
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
    parent?: {
      tableData: XeTableData<any>;
      syncFieldsPreCreate?: { childName: string, parentField: EntityField }[];
    }
    updateToShareEntityField?: {
      share?: ShareFormData<any>;
      fieldName?: any;
    }
  };
  display?: {
    toggleOne?: boolean;
    fullScreenForm?: boolean;
  };
  formData?: XeFormData<E>;
  xeScreen?: XeScreen;
  table?: {
    customData?: () => E[],
    mode?: {
      newToBottom?: boolean;
      denyNew?: boolean;
      hideSearchBox?: boolean;
      lazyData?: (term: string) => Observable<E[]>,
      customObservable?: Observable<E[]>,
      selectOneOnly?: boolean,
      readonly?: boolean,
      hideSelectColumn?: boolean
    },
    action?: {
      preBack?: () => any;
      postUpdate?: (arrayResult: E[]) => any;
      editOnRow?: EditOnRow,
      postSelect?: (entity) => any,
      postDeSelect?: (entity: E) => any,
      onClickBtnCreate?: () => any,
      filters?: EntityFilter,
      triggerUpdate?: (term) => any;
    }
    selectBasicColumns?: any[],
    basicColumns?: TableColumn[],
    manualColumns?: ManualColumn[]
  };

  static fullFill(tableData: XeTableData<any>) {
    if (!tableData.table) tableData.table = {};
    if (!tableData.table.action) tableData.table.action = {};
    if (!tableData.table.mode) tableData.table.mode = {};
    if (!tableData.table.basicColumns) tableData.table.basicColumns = [];
    if (!tableData.display) tableData.display = {fullScreenForm: false};
    if (!tableData.formData) tableData.formData = {};
    XeFormData.fullFill(tableData.formData);
  }
}


