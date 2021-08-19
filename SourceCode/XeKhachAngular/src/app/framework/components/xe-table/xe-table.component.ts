import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {CommonUpdateService} from "../../../business/service/common-update.service";
import {EntityUtil} from "../../util/entity.util";
import {ManualColumn, TableColumn, XeTableData} from "../../model/XeTableData";
import {NbDialogService} from "@nebular/theme";
import {SelectionModel} from "@angular/cdk/collections";
import {Notifier} from "../../notify/notify.service";
import {XeLabel, XeLbl} from "../../../business/i18n";
import {HttpErrorResponse} from "@angular/common/http";
import {RoleUtil} from "../../util/role.util";
import {XeSubscriber} from "../../model/XeSubscriber";
import {XeEntity} from "../../../business/entities/XeEntity";
import {EntityIdentifier} from "../../model/XeFormData";
import {XeScreen} from "../xe-nav/xe-nav.component";

@Component({
  selector: 'xe-table',
  templateUrl: './xe-table.component.html',
  styleUrls: ['./xe-table.component.scss']
})
export class XeTableComponent<E extends XeEntity> extends XeSubscriber implements OnInit {
  @Input() tableData: XeTableData<E>;
  displayedColumns: string[] = [];
  tableSource: MatTableDataSource<E>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild("basicFormDialogWrapper") basicFormDialogWrapper;
  @ViewChild("deleteDialogWrapper") deleteDialog;
  selection: SelectionModel<any>;
  isSelected = true;

  entityUtil = EntityUtil;

  screens = {
    basicForm: "basicForm",
    table: "table",
    delete: "delete"
  };
  screen = new XeScreen({
    home: this.screens.table, preGo: () => {
      if (this.screen.is(this.screens.basicForm)) {
        this.tableData.formData?.share?.xeBasicForm?.restoreShareEntity();
      }
    }
  });

  constructor(private commonService: CommonUpdateService,
              private dialogService: NbDialogService) {
    super();
  }

  get hasRowSelected() {
    return this.selection?.selected?.length > 0;
  }

  ngOnInit(): void {

    if (this.tableData.display.fullScreenForm) {
      this.tableData.formData.display.cancelBtn = "close";
      const currentPostCancel = this.tableData.formData.action.postCancel;
      this.tableData.formData.action.postCancel = (entity) => {
        if (currentPostCancel) currentPostCancel(entity);
        this.screen.back();
      };
    }

    this.displayedColumns = this.tableData.table.basicColumns.filter(c => c !== undefined).map(c => c.field.name);

    this.tableData.table.manualColumns?.map(c => c.field.name).every(columnName => this.displayedColumns.push(columnName));
    this.displayedColumns.push("select");

    this.subscriptions.push(this.commonService.findByEntityIdentifier<E>(this.tableData.formData.entityIdentifier).subscribe(
      (result: E[]) => {
        result = EntityUtil.cachePk(this.tableData.formData.entityIdentifier.clazz, result, this.tableData.table?.action?.filterCondition);
        this.updateTableSource(result);
      }
    ));

    const initialSelection = [];
    const allowMultiSelect = true;
    this.selection = new SelectionModel<any>(allowMultiSelect, initialSelection);
    this.tableData.formData.share.selection = this.selection;
  }

  filterTableColumn(entity, column: TableColumn, value) {
    const columnCheck = this.isValueContains(entity, column, value);
    if (columnCheck || !column.subColumns) {
      return columnCheck;
    } else {
      for (const subColumn of column.subColumns) {
        if (this.isValueContains(entity, subColumn, value)) {
          return true;
        }
      }
    }
    return false;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.tableSource.filterPredicate = (data, inputValue) => {
      const value = inputValue.trim().toLowerCase();
      const manual = this.tableData.table.manualColumns?.findIndex(column => {
        return this.isValueContains(data, column, value);
      }) > -1;
      if (manual) return true;
      const basicFilter = this.tableData.table.basicColumns?.filter(column => {
        return this.filterTableColumn(data, column, value);
      });
      return basicFilter && basicFilter.length > 0;
    };
    this.tableSource.filter = filterValue.trim().toLowerCase();
    if (this.tableSource.paginator) {
      this.tableSource.paginator.firstPage();
    }
  }

  private isValueContains(data, column: ManualColumn | TableColumn, value: string) {
    return String(this.entityUtil.getReadableFieldValue(data, column.field)).toLowerCase().includes(value);
  }

  postPersist = (entity) => {
    this.tableSource.data.unshift(entity);
    this.tableSource.data = this.tableSource.data;
    this.tableData.formData.share.entity = this.tableSource.data[0];
  }

  postRemove = (entity) => {
    this.removeEntityFromArray(entity, this.tableSource.data);
    this.removeEntityFromArray(entity, this.selection.selected);
    this.tableSource.data = this.tableSource.data;
  }

  removeEntityFromArray(entity, data: any[]) {
    for (let i = 0; i < data.length; i++) {
      const mainIdName = this.tableData.formData.entityIdentifier.clazz.mainIdName;
      if (entity[mainIdName] === data[i][mainIdName]) {
        return data.splice(i, 1);
      }
    }
  }

  dialogEditEntity() {
    if (this.tableData.formData?.control?.readMode) return;
    this.openBasicFormDialog();
  }

  newEntityDialog() {
    this.tableData.formData.share.entity = this.entityUtil.newByEntityDefine(this.tableData.formData.entityIdentifier);
    this.openBasicFormDialog();
  }

  openBasicFormDialog() {
    if (this.tableData.display?.fullScreenForm) {
      this.screen.go(this.screens.basicForm);
    } else {
      this.dialogService.open(this.basicFormDialogWrapper).onClose.subscribe(() => {
        this.tableData.formData?.share?.xeBasicForm?.restoreShareEntity();
      });
    }
  }

  openDeleteDialog() {
    this.dialogService.open(this.deleteDialog);
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.tableSource.data.length;
    return numSelected === numRows;
  }

  toggleSelectAll() {
    if (this.isAllSelected()) {
      this.selection.clear();
    } else {
      this.selection.clear();
      this.tableSource.data.forEach(row => this.selection.select(row));
    }
  }

  deleteSelected() {

    this.subscriptions.push(this.commonService.deleteAll(this.selection.selected, this.tableData.formData.entityIdentifier.clazz).subscribe(
      () => {
        this.selection.selected.forEach(entity => {
          this.removeEntityFromArray(entity, this.tableSource.data);
        });
        this.selection.clear();
        this.tableSource.data = this.tableSource.data;
        Notifier.success(XeLabel.DELETE_SUCCESS);
      },
      (error: HttpErrorResponse) => {
        Notifier.httpErrorResponse(error);
      }
    ));
  }


  @ViewChild("string") stringColumn;
  @ViewChild("avatarString") avatarStringColumn;
  @ViewChild("avatar") avatarColumn;
  @ViewChild("boldString") boldStringColumn;
  @ViewChild("boldStringRole") boldStringRoleColumn;
  @ViewChild("role") roleColumn;
  @ViewChild("iconOption") iconOptionColumn;

  getColumn(name: string) {
    if (name === 'string') return this.stringColumn;
    if (name === 'avatar') return this.avatarColumn;
    if (name === 'role') return this.roleColumn;
    if (name === 'boldString') return this.boldStringColumn;
    if (name === 'boldStringRole') return this.boldStringRoleColumn;
    if (name === 'avatarString') return this.avatarStringColumn;
    if (name === 'iconOption') return this.iconOptionColumn;

    return this.stringColumn;
  }

  getRoleIcons(role: string) {
    if (!role) return [];
    return role.split(",").map(s => RoleUtil.roleInfo[s]).filter(s => s !== undefined);
  }

  asCol(tableColumn: any): TableColumn {
    return tableColumn as TableColumn;
  }

  private updateTableSource(result: E[]) {
    this.tableData.formData.share.tableSource = this.tableSource;
    this.tableData.formData.share.tableEntities = result;
    this.tableSource = new MatTableDataSource<any>(result);
    this.tableData.formData.share.tableSource = this.tableSource;
    this.tableSource.paginator = this.paginator;
    this.tableSource.sort = this.sort;
    this.tableSource.sortingDataAccessor = (item, property) => {
      return this.entityUtil.getReadableFieldValue(item, {name: property});
    };
  }

  onSelect(entity: E, tableColumn: TableColumn) {
    this.tableData.formData.share.entity = entity;
    const thisIdent = this.tableData.formData.entityIdentifier;
    if (this.tableData.external?.updateCriteriaTableOnSelect) {
      this.tableData.external.updateCriteriaTableOnSelect().forEach(table => {
        const targetIdent = table.formData.entityIdentifier;
        this.updateCriteria(targetIdent, thisIdent, entity);
      });
    }
    if (tableColumn.action?.screen) {
      this.tableData?.xeScreen.go(tableColumn.action?.screen);
    } else if (tableColumn.action?.onSelect) {
      tableColumn.action.onSelect(entity);
    } else {
      this.dialogEditEntity();
    }
  }

  private updateCriteria(targetIdent: EntityIdentifier<any>, thisIdent: EntityIdentifier<E>, entity: E) {
    const targetEntity = targetIdent.entity;
    if (targetIdent.clazz.pkMapFieldNames.includes(thisIdent.clazz.camelName)) {
      targetEntity[thisIdent.clazz.camelName] = entity;
      targetEntity[thisIdent.clazz.mainIdName] = entity[thisIdent.clazz.mainIdName];
    } else {
      targetIdent.clazz.pkMapFieldNames.forEach(pk => {
        if (entity[pk]) {
          targetEntity[pk] = entity[pk];
          targetEntity[pk + "Id"] = entity[pk + "Id"];
        }
      });
    }
  }

  addSelectedToParent() {
    const share = this.tableData.formData.share;
    const clazz = this.tableData.formData.entityIdentifier.clazz;

    const parentCriteria = this.tableData.external.parent.formData.entityIdentifier.entity;
    const parentIdentifier = this.tableData.external.parent.formData.entityIdentifier;

    const selectedEntities = share.selection.selected;
    const entities = selectedEntities.map(entity => {
      const newEntity = Object.assign({}, parentCriteria);
      const newEntityIds = EntityUtil.getAllPossibleId(newEntity, parentIdentifier);
      newEntityIds[clazz.mainIdName] = entity[clazz.mainIdName];
      return newEntityIds;
    });
    CommonUpdateService.instance.insertMulti<E>(entities, parentIdentifier.clazz).subscribe(
      returnedEntities => {
        const returnedIds = returnedEntities.map(be => be[clazz.mainIdName]);
        share.selection.clear();
        share.tableSource.data = share.tableSource.data.filter(e => !returnedIds.includes(e[clazz.mainIdName]));
        Notifier.success(XeLabel.SAVED_SUCCESSFULLY);
      },
      (error: HttpErrorResponse) => {
        console.log(error);
        Notifier.httpErrorResponse(error);
      }
    );
  }
}
