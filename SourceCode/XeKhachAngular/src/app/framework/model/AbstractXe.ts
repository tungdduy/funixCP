import {XeLabel, XeLbl} from "../../business/i18n";
import {Directive} from "@angular/core";
import {BussSchemeMode, InputMode, InputTemplate} from "./EnumStatus";
import {AdminComponent} from "../../business/pages/admin/admin.component";
import {EntityUtil} from "../util/EntityUtil";

@Directive()
export class AbstractXe {
  xeLabel = XeLabel;
  xeInputMode = InputMode;
  xeInputTemplate = InputTemplate;
  entityUtil = EntityUtil;
  bussSchemeMode = BussSchemeMode;
  xeLbl = XeLbl;
  get adminContainer() {
    return AdminComponent.instance();
  }
}
