import {XeLabel, XeLbl} from "../../business/i18n";
import {Directive} from "@angular/core";
import {BussSchemeMode, InputMode, InputTemplate} from "./EnumStatus";
import {AdminComponent} from "../../business/pages/admin/admin.component";
import {EntityUtil} from "../util/EntityUtil";
import {Xe} from "./Xe";

@Directive()
export class AbstractXe extends Xe {
  xeLabel = XeLabel;
  xeInputMode = InputMode;
  xeInputTemplate = InputTemplate;
  entityUtil = EntityUtil;
  bussSchemeMode = BussSchemeMode;

  private static _instance;
  static get instance(): AbstractXe {
    return AbstractXe._instance;
  }

  constructor() {
    super();
    AbstractXe._instance = this;
  }
  xeLbl = XeLbl;

  get adminContainer() {
    return AdminComponent.instance();
  }


}
