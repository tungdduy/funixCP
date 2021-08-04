import {AfterViewInit, Component, Input, OnDestroy, ViewChild} from '@angular/core';
import {CommonUpdateService} from "../../../business/service/common-update.service";
import {Notifier} from "../../notify/notify.service";
import {XeLabel} from "../../../business/i18n";
import {HttpErrorResponse} from "@angular/common/http";
import {XeFormData} from "../../../business/abstract/XeFormData";
import {FormAbstract} from "../../../business/abstract/form.abstract";
import {XeEntity} from "../../../business/entities/xe-entity";
import {EntityUtil} from "../../util/entity.util";
import {XeFormComponent} from "../xe-form/xe-form.component";
import {State} from "../../model/message.model";
import {ObjectUtil} from "../../util/object.util";

@Component({
  selector: 'xe-basic-form',
  templateUrl: './xe-basic-form.component.html',
  styleUrls: ['./xe-basic-form.component.scss']
})
export class XeBasicFormComponent extends FormAbstract implements OnDestroy, AfterViewInit {

  @Input("dialog") dialog: {close()};
  @Input("onSuccess") onSuccess: (data, isNew) => void;
  @Input("onDelete") onDelete: (data) => void;
  @Input("formData") formData: XeFormData;
  subscriptions = [];
  handlers = [
    {
      name: "basicFormInfo",
      processor: (entity) => {
        if (this.isIdValid) {
          return this.commonUpdateService.update<any>(entity, this.formData.entityIdentifier.className);
        } else {
          return this.commonUpdateService.insert<any>(entity, this.formData.entityIdentifier.className);
        }
      },
      success: {
        call: (entity) => {
          this.formData.fields.filter(field => field.clearOnSuccess).forEach(field => this.formData.share.entity[field.name] = undefined);
          if (this.formData.onSuccess) {
            this.formData.onSuccess(entity);
          }
          if (!!this.onSuccess) {
            this.onSuccess(entity, !this.isIdValid);
          }
          this.backupShareEntity(entity);
        }
      }
    }
  ];
  entityUtil = EntityUtil;

  backupEntity = {};
  backupShareEntity(entity) {
    this.formData.share.entity = entity;
    this.backupEntity = ObjectUtil.deepCopy(entity, {});
  }
  backupSelfEntity() {
    this.backupEntity = ObjectUtil.deepCopy(this.formData.share.entity, {});
  }

  restoreShareEntity() {
    ObjectUtil.deepCopy(this.backupEntity, this.formData.share.entity);
  }

  ngAfterViewInit() {
    super.ngAfterViewInit();
    this.formData.share.xeBasicForm = this;
    this.formData.share.xeForm = this.submitForm;
    this.backupSelfEntity();
  }

  constructor(private commonUpdateService: CommonUpdateService) {
    super();
  }

  get hasRef() {
    return this.dialog !== undefined;
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
  get profileOwnerClassName() {
    return this.entityUtil.getFieldOwnerClassName(this.formData.header.profileImage, this.formData.entityIdentifier);
  }

  @ViewChild("submitContent") submitForm: XeFormComponent;
  onProfileImageChange = (file: File) => {
    if (!this.isIdValid) {
      return;
    }
    const formData = new FormData();
    Object.keys(this.profileOwnerMainId).forEach(key => {
      formData.append(key, this.formData.share.entity[key]);
    });

    formData.append("profileImage", file);
    this.subscriptions.push(this.commonUpdateService.updateProfileImage(formData, this.profileOwnerClassName).subscribe(
      (entity) => {
        this.updateProfileImage(entity);
        if (this.formData.onAvatarChange) {
          this.formData.onAvatarChange(entity);
        }
        this.submitForm.notify(XeLabel.SAVED_SUCCESSFULLY, State.success);
      },
      (error: HttpErrorResponse) => {
        Notifier.httpErrorResponse(error);
      }
    ));
  }

  entityIds = () => {
    return this.entityUtil.fetchAndFlatAllPossibleId(this.formData.share.entity, this.formData.entityIdentifier);
  }
  deleting = false;

  get isIdValid(): boolean {
    return this.entityUtil.isIdValid(this.formData.share.entity, this.formData.entityIdentifier);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  turnBack() {
    this.deleting = false;
    setTimeout(() => {
      this.assignCtrlForForms();
    }, 0);
  }

  deleteCurrentEntity() {
    this.subscriptions.push(this.commonUpdateService.delete(this.formData.share.entity, this.formData.entityIdentifier.className).subscribe(
      () => {
        if (!!this.onDelete) {
          this.onDelete(this.formData.share.entity);
        }
        Notifier.success(XeLabel.DELETE_SUCCESS);
      },
      (error: HttpErrorResponse) => {
        Notifier.httpErrorResponse(error);
      }
    ));
  }

}
