import {Component, Input, OnDestroy, ViewChild} from '@angular/core';
import {CommonUpdateService} from "../../../business/service/common-update.service";
import {Notifier} from "../../notify/notify.service";
import {XeLabel} from "../../../business/i18n";
import {HttpErrorResponse} from "@angular/common/http";
import {XeFormData} from "../../../business/abstract/XeFormData";
import {FormAbstract} from "../../../business/abstract/form.abstract";
import {XeEntity} from "../../../business/model/xe-entity";
import {EntityUtil} from "../../util/entity.util";
import {XeFormComponent} from "../xe-form/xe-form.component";
import {State} from "../../model/message.model";

@Component({
  selector: 'xe-basic-form',
  templateUrl: './xe-basic-form.component.html',
  styleUrls: ['./xe-basic-form.component.scss']
})
export class XeBasicFormComponent extends FormAbstract implements OnDestroy {

  @Input("ref") ref: {close()};
  @Input("onSuccess") onSuccess: (data, isNew) => void;
  @Input("onDelete") onDelete: (data) => void;
  @Input("formData") formData: XeFormData;
  subscriptions = [];
  handlers = [
    {
      name: "basicFormInfo",
      processor: (entity) => {
        if (this.isIdValid) {
          return this.commonUpdateService.update<any>(entity, this.formData.className);
        } else {
          return this.commonUpdateService.insert<any>(entity, this.formData.className);
        }
      },
      success: {
        call: (entity) => {
          if (this.formData.onSuccess) {
            this.formData.onSuccess(entity);
          }
          if (!!this.onSuccess) {
            this.onSuccess(entity, !this.isIdValid);
          }
          Object.assign(this.formData.share.entity, entity);
        }
      }
    }
  ];

  constructor(private commonUpdateService: CommonUpdateService) {
    super();
  }

  get hasRef() {
    return this.ref !== undefined;
  }

  updateProfileImage(entity: XeEntity) {
    entity.profileImageUrl = entity.profileImageUrl + "?r=" + Math.random().toString(36).substring(7);
    this.formData.share.entity.profileImageUrl = entity.profileImageUrl;
  }

  newEntity() {
    this.formData.share.entity = EntityUtil.newByName(this.formData.className);
  }

  @ViewChild("submitContent") submitForm: XeFormComponent;
  onProfileImageChange = (file: File) => {
    if (!this.isIdValid) {
      this.submitForm.notify("Thêm bản ghi trước khi đổi ảnh!", State.danger);
      return;
    }
    const formData = new FormData();
    Object.keys(this.formData.idColumns).forEach(key => {
      formData.append(key, this.formData.share.entity[key]);
    });
    formData.append("profileImage", file);
    this.subscriptions.push(this.commonUpdateService.updateProfileImage(formData, this.formData.className).subscribe(
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
    const ids = {};
    Object.keys(this.formData.idColumns).forEach((key) => {
      ids[key] = this.formData.share.entity[key];
    });
    return  ids;
  }
  deleting = false;

  get isIdValid(): boolean {
    return XeEntity.isIdValid(this.formData.share.entity, this.formData.idColumns);
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
    this.subscriptions.push(this.commonUpdateService.delete(this.formData.share.entity, this.formData.className).subscribe(
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
