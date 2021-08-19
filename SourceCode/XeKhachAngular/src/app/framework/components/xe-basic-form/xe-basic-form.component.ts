import {AfterViewInit, Component, Input, OnDestroy, ViewChild} from '@angular/core';
import {CommonUpdateService} from "../../../business/service/common-update.service";
import {Notifier} from "../../notify/notify.service";
import {XeLabel} from "../../../business/i18n";
import {HttpErrorResponse} from "@angular/common/http";
import {EntityField, XeFormData} from "../../model/XeFormData";
import {FormAbstract} from "../../model/form.abstract";
import {XeEntity} from "../../../business/entities/XeEntity";
import {EntityUtil} from "../../util/entity.util";
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
  @Input("readMode") _readMode;
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

  get readMode() {
    return this._readMode === '' || this._readMode === true;
  }

  get editMode() {
    return !this.readMode;
  }

  get hasDialog() {
    return this.dialog !== undefined;
  }


  oriHideBody;

  get isHideBody() {
    return this._hideBody === '' || this._hideBody;
  }

  hideHeader = false;

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


  handlers = [
    {
      name: "basicFormInfo",
      processor: (formFields) => {
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
          return CommonUpdateService.instance.update<any>(formFields, this.formData.entityIdentifier.clazz);
        } else {
          return CommonUpdateService.instance.insert<any>(formFields, this.formData.entityIdentifier.clazz);
        }
      },
      success: {
        call: (entity) => {
          const isUpdate = this.isIdValid;
          const isPersist = !this.isIdValid;
          this.formData.fields.filter(field => field.clearOnSuccess).forEach(field => this.formData.share.entity[field.name] = undefined);
          if (isUpdate && this.formData.action?.postUpdate) {
            this.formData.action?.postUpdate(entity);
          }
          if (isPersist) {
            if (this.formData.action?.postPersist)this.formData.action?.postPersist(entity);
            if (!!this.postPersist) this.postPersist(entity, isPersist);
          }
          this.updateIdValidStatus();
          this.backupShareEntity(entity);
        }
      }
    }
  ];

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

  updateIdValidStatus() {
    Object.assign(this.idsIncludeOnSubmit(), this.formData.share.entity);
    setTimeout(() => this._isIdValid = this.entityUtil.isIdValid(this.formData.share.entity, this.formData.entityIdentifier), 0);
  }

  entityUtil = EntityUtil;

  backupEntity = {};

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
    this.profileOwnerEntity.profileImageUrl = entity.profileImageUrl;
    this.backupSelfEntity();
  }

  newEntity() {
    this.backupShareEntity(EntityUtil.newByEntityDefine(this.formData.entityIdentifier));
  }

  get profileOwnerEntity() {
    return this.entityUtil.getFieldOwnerEntity(this.formData.share.entity, this.formData.header.profileImage);
  }

  get profileOwnerMainId() {
    return this.entityUtil.getFieldOwnerMainId(this.formData.header.profileImage, this.formData.entityIdentifier, this.formData.share.entity);
  }

  @ViewChild("form") submitForm: XeFormComponent;
  onProfileImageChange = (file: File) => {
    if (!this.isIdValid) {
      return;
    }
    const formData = new FormData();
    Object.keys(this.profileOwnerMainId).forEach(key => {
      formData.append(key, this.formData.share.entity[key]);
    });

    formData.append("profileImage", file);
    this.subscriptions.push(CommonUpdateService.instance.updateProfileImage(formData, this.formData.entityIdentifier.clazz).subscribe(
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
    const idFromEntity = this.entityUtil.getAllPossibleId(this.formData.share.entity, this.formData.entityIdentifier);
    const idFromCriteria = this.entityUtil.getAllPossibleId(this.formData.entityIdentifier.entity, this.formData.entityIdentifier);
    return Object.assign(idFromCriteria, idFromEntity);
  }

  turnBack() {
    this.screen.go(this.screens.form);
    setTimeout(() => {
      this.assignCtrlForForms();
    }, 0);
  }

  screens = {
    form: 'form',
    deleteEntity: 'delete'
  };
  screen = new XeScreen({home: this.screens.form});
  get columnNumber() {
    return this.formData.display?.columnNumber ? 12 / this.formData.display.columnNumber : 12;
  }

  deleteCurrentEntity() {
    this.subscriptions.push(CommonUpdateService.instance.delete(this.formData.share.entity, this.formData.entityIdentifier.clazz).subscribe(
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

}
