import {XeLabel, XeLbl} from "../../business/i18n";
import {Directive} from "@angular/core";
import {BussSchemeMode, InputMode, InputTemplate} from "./EnumStatus";
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

  constructor() {
    super();
    AbstractXe._instance = this;
  }

  private static _instance;

  static get instance(): AbstractXe {
    return AbstractXe._instance;
  }

  get adminContainer() {
    return AdminComponent.instance();
  }


}
