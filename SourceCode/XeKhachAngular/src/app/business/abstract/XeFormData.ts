interface EntityField {
  name: string;
  required?: boolean;
  hidden?: boolean;
  newOnly?: boolean;
}

export declare interface XeFormData {
  share?: {entity: any};
  className?: string;
  idColumns?: {};
  grid?: boolean;
  header?: {
    titleKey?: string,
    descKey?: string,
  };
  fields: EntityField[];
  onAvatarChange?: (entity) => void;
  onSuccess?: (entity) => void;
}

