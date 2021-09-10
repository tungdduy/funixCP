import {XeLabel, XeLbl} from "../../business/i18n";
import {Directive} from "@angular/core";
import {BussSchemeMode, InputMode, InputTemplate, LabelMode} from "./EnumStatus";
import {AdminComponent} from "../../business/pages/admin/admin.component";
import {EntityUtil} from "../util/EntityUtil";
import {Xe} from "./Xe";
import {AuthUtil} from "../auth/auth.util";

@Directive()
export class AbstractXe extends Xe {
  xeLabel = XeLabel;
  xeInputMode = InputMode;
  xeInputTemplate = InputTemplate;
  entityUtil = EntityUtil;
  bussSchemeMode = BussSchemeMode;
  xeLbl = XeLbl;
  auth = AuthUtil.instance;
  labelMode = LabelMode;

  constructor() {
    super();
    AbstractXe.instance = this;
  }

  static instance;

  get adminContainer() {
    return AdminComponent.instance();
  }


}
