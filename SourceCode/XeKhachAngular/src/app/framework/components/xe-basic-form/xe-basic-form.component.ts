import {AfterViewInit, Component, Input, OnDestroy, ViewChild} from '@angular/core';
import {CommonUpdateService} from "../../../business/service/common-update.service";
import {Notifier} from "../../notify/notify.service";
import {XeLabel} from "../../../business/i18n";
import {HttpErrorResponse} from "@angular/common/http";
import {EntityField, XeFormData} from "../../model/XeFormData";
import {FormAbstract} from "../../model/form.abstract";
import {ClassMeta, XeEntity} from "../../../business/entities/XeEntity";
import {EntityUtil} from "../../util/EntityUtil";
import {XeFormComponent} from "../xe-form/xe-form.component";
import {State} from "../../model/message.model";
import {ObjectUtil} from "../../util/object.util";
import {XeScreen} from "../xe-nav/xe-nav.component";

@Component({
  selector: 'xe-basic-form',
  templateUrl: './xe-basic-form.component.html',
  styleUrls: ['./xe-basic-form.component.scss']
})
export class XeBasicFormComponent<E extends XeEntity> extends FormAbstract implements OnDestroy, AfterViewInit {

  @Input("dialog") dialog: { close() };
  @Input("postPersist") postPersist: (data, isPersist) => void;
  @Input("postRemove") postRemove: (data) => void;
  @Input("formData") formData: XeFormData<E>;
  @Input("hideBody") _hideBody;
  oriHideBody;
  hideHeader = false;
  handlers = [
    {
      name: "basicFormInfo",
      processor: (formFields) => {
        const entityMapFields = this.formData.entityIdentifier.clazz.mapFields;
        Object.keys(formFields).forEach(key => {
          const mapField: ClassMeta = entityMapFields[key];
          if (ObjectUtil.isObject(mapField) && ObjectUtil.isObject(formFields[key])) {
            formFields[key] = formFields[key][mapField.mainIdName];
          }
        });
        if (this.formData.action?.preSubmit) {
          const validator = this.formData.action?.preSubmit(this.formData.share.entity);
          if (!validator.isSuccess())
            return validator;
          if (validator.data) {
            Object.keys(validator.data).forEach(key => {
              formFields[key] = validator.data[key];
            });
          }
        }
        if (this.isIdValid) {
          return CommonUpdateService.instance.update<any>(formFields, this.formData.entityIdentifier.clazz.meta);
        } else {
          return CommonUpdateService.instance.insert<any>(formFields, this.formData.entityIdentifier.clazz.meta);
        }
      },
      success: {
        call: (entity) => {
          const isUpdate = this.isIdValid;
          const isPersist = !this.isIdValid;
          this.entityUtil.cacheThenFill(entity, this.formData.entityIdentifier.clazz.meta);
          this.formData.fields.filter(field => field.clearOnSuccess).forEach(field => this.formData.share.entity[field.name] = undefined);
          if (isUpdate && this.formData.action?.postUpdate) {
            this.formData.action?.postUpdate(entity);
          }
          if (isPersist) {
            if (this.formData.action?.postPersist) this.formData.action?.postPersist(entity);
            if (!!this.postPersist) this.postPersist(entity, isPersist);
          }
          this.updateIdValidStatus();
          this.backupShareEntity(entity);
        }
      }
    }
  ];
  backupEntity = {};
  @ViewChild("form") submitForm: XeFormComponent;
  screens = {
    form: 'form',
    deleteEntity: 'delete'
  };
  screen = new XeScreen({home: this.screens.form});

  @Input("readMode") _readMode;

  get readMode() {
    return this._readMode === '' || this._readMode === true;
  }

  get formEntity() {
    return this.formData.share?.entity;
  }

  get formMeta() {
    return this.formData.entityIdentifier?.clazz?.meta;
  }

  get formProfileField() {
    return this.formData.header?.profileImage;
  }

  get editMode() {
    return !this.readMode;
  }

  get hasDialog() {
    return this.dialog !== undefined;
  }

  get isHideBody() {
    return this._hideBody === '' || this._hideBody;
  }

  private _isIdValid: boolean;

  get isIdValid(): boolean {
    return this._isIdValid;
  }

  get isUpdate(): boolean {
    return this._isIdValid;
  }

  get isCreate(): boolean {
    return !this.isUpdate;
  }

  get profile(): { ownerId: number, ownerMeta: ClassMeta, owner: any } {
    const ef = this.entityUtil.getEntityWithField(this.formData.share.entity, this.formData.entityIdentifier.clazz.meta, this.formData.header.profileImage);
    return {
      ownerId: ef.entity[ef.fieldMeta?.mainIdName] ? ef.entity[ef.fieldMeta?.mainIdName] : this.formData.share.entity[this.formData.entityIdentifier.clazz.meta.mainIdName],
      ownerMeta: ef.fieldMeta ? ef.fieldMeta : this.formData.entityIdentifier.clazz.meta,
      owner: ef.entity ? ef.entity : this.formData.share.entity
    };
  }

  onEdit = () => {
    this.updateIdValidStatus();
    this._readMode = false;
    this.updateBody();
    this.submitForm.unMute();
    if (this.formData.action?.preEdit) this.formData?.action?.preEdit(this.formData.share.entity);
  }

  onCancel = () => {
    this._readMode = true;
    this.dialog?.close();
    this.resetHideBody();
    this.submitForm.reset();
    this.submitForm.mute();
    if (this.formData.action?.postCancel) this.formData.action?.postCancel(this.formData.share.entity);
  }

  updateBody() {
    if (this.isHideBody) {
      this.hideHeader = true;
      this._hideBody = false;
    }
  }

  resetHideBody() {
    this.hideHeader = false;
    this._hideBody = this.oriHideBody;
  }

  filteredField(): EntityField[] {
    return this.formData?.fields?.filter(f => !!f);
  }

  updateIdValidStatus() {
    Object.assign(this.idsIncludeOnSubmit(), this.formData.share.entity);
    setTimeout(() => this._isIdValid = this.entityUtil.isIdValid(this.formData.share.entity, this.formData.entityIdentifier), 0);
  }

  backupShareEntity(entity) {
    this.formData.share.entity = entity;
    this.backupEntity = ObjectUtil.eraserAndDeepCopyForRestore(entity, {});
  }

  backupSelfEntity() {
    this.backupEntity = ObjectUtil.eraserAndDeepCopyForRestore(this.formData.share.entity, {});
  }

  restoreShareEntity() {
    ObjectUtil.eraserAndDeepCopyForRestore(this.backupEntity, this.formData.share.entity);
  }

  ngAfterViewInit() {
    super.ngAfterViewInit();
    this.oriHideBody = this._hideBody;
    this.formData.share.xeBasicForm = this;
    this.formData.share.xeForm = this.submitForm;
    this.updateIdValidStatus();
    this.backupSelfEntity();
  }

  updateProfileImage(entity: XeEntity) {
    entity.profileImageUrl = entity.profileImageUrl + "?r=" + Math.random().toString(36).substring(7);
    this.profile.owner.profileImageUrl = entity.profileImageUrl;
    this.backupSelfEntity();
  }

  newEntity() {
    this.backupShareEntity(EntityUtil.newByEntityDefine(this.formData.entityIdentifier));
  }

  onProfileImageChange = (file: File) => {
    if (!this.isIdValid) {
      return;
    }
    const formData = new FormData();
    formData.append(this.profile.ownerMeta.mainIdName, String(this.profile.ownerId));

    formData.append("profileImage", file);
    this.subscriptions.push(CommonUpdateService.instance.updateProfileImage(formData, this.profile.ownerMeta).subscribe(
      (entity) => {
        this.updateProfileImage(entity);
        if (this.formData.action?.postUpdateProfile) {
          this.formData.action?.postUpdateProfile(entity);
        }
        this.submitForm.notify(XeLabel.SAVED_SUCCESSFULLY, State.success);
      },
      (error: HttpErrorResponse) => {
        Notifier.httpErrorResponse(error);
      }
    ));
  }

  idsIncludeOnSubmit = (): {} => {
    return this.entityUtil.getAllPossibleId(this.formData.share.entity, this.formData.entityIdentifier);
  }

  turnBack() {
    this.screen.go(this.screens.form);
    setTimeout(() => {
      this.assignCtrlForForms();
    }, 0);
  }

  colSpan(field: EntityField) {
    const colSpan = field.colSpan ? field.colSpan : 1;
    return this.formData.display?.columnNumber
      ? 12 / this.formData.display.columnNumber * colSpan
      : 12;
  }

  deleteCurrentEntity() {
    this.subscriptions.push(CommonUpdateService.instance.delete(this.formData.share.entity, this.formData.entityIdentifier.clazz.meta).subscribe(
      () => {
        if (!!this.postRemove) {
          this.postRemove(this.formData.share.entity);
        }
        if (this.formData.action?.postDelete) {
          this.formData.action?.postDelete(this.formData.share.entity);
        }
        Notifier.success(XeLabel.DELETE_SUCCESS);
      },
      (error: HttpErrorResponse) => {
        Notifier.httpErrorResponse(error);
      }
    ));
  }

  asXeForm(form: any): XeFormComponent {
    return form as XeFormComponent;
  }
}
