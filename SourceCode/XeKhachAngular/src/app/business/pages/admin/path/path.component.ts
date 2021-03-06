import {Component} from '@angular/core';
import {Path} from "../../../entities/Path";
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {PathPoint} from "../../../entities/PathPoint";
import {XeScreen} from "../../../../framework/components/xe-nav/xe-nav.component";
import {FormAbstract} from "../../../../framework/model/form.abstract";

@Component({
  selector: 'xe-path',
  styles: [],
  templateUrl: 'path.component.html',
})
export class PathComponent extends FormAbstract {

  screens = {
    path: 'path',
    pathPoints: 'pathPoints',
  };
  screen = new XeScreen({
    home: this.screens.path,
    homeIcon: 'road',
    homeTitle: () => this.pathTable?.formData?.share?.entity?.pathName
  });

  pathTable = Path.tableData({
      xeScreen: this.screen,
      external: {
        updateCriteriaTableOnSelect: () => [this.pathPointTable]
      },
      table: {
        mode: {
          hideSelectColumn: !this.auth.hasBussAdmin,
          readonly: !this.auth.hasBussAdmin
        },
        selectBasicColumns: ['pathName', 'pathDesc'],
        basicColumns: [
          {field: {name: 'totalPathPoints'}, action: {screen: this.screens.pathPoints}}
        ]
      },
    },
    Path.new({company: AuthUtil.instance.user?.employee?.company}));

  pathPointTable = PathPoint.tableData({
    xeScreen: this.screen,
    table: {
      mode: {
        newToBottom: true,
        hideSelectColumn: !this.auth.hasBussAdmin,
        readonly: !this.auth.hasBussAdmin
      },
      selectBasicColumns: [
        'pointOrder', 'pointName', 'location'
      ]
    }
  });

}
