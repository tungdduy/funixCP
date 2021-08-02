import {XeFormData} from "./XeFormData";

interface TableColumn {
  hiddenClass?: 'hidden-phone' | 'hidden-tablet';
  type: 'role' | 'avatar' | 'string' | 'money' | 'link' | 'boldString' | 'avatarRole' | 'boldStringRole' | 'avatarString';
  name: string;
  link?: string;
}

export declare interface XeTableData {
  share?: {entity: any};
  className: string;
  idColumns?: {};
  table: {
    basicColumns: TableColumn[]
  };
  formData: XeFormData;
}


