import {Component, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {CommonUpdateService} from "../../../business/service/common-update.service";
import {EntityUtil} from "../../util/entity.util";
import {TableColumn, XeTableData} from "../../../business/abstract/XeTableData";
import {NbDialogService} from "@nebular/theme";
import {SelectionModel} from "@angular/cdk/collections";
import {Notifier} from "../../notify/notify.service";
import {XeLabel} from "../../../business/i18n";
import {HttpErrorResponse} from "@angular/common/http";
import {RoleUtil} from "../../util/role.util";
import {XeSubscriber} from "../../../business/abstract/XeSubscriber";

@Component({
  selector: 'xe-table',
  templateUrl: './xe-table.component.html',
  styleUrls: ['./xe-table.component.scss']
})
export class XeTableComponent extends XeSubscriber implements OnInit {

  @Input() tableData: XeTableData;
  displayedColumns: string[] = [];
  tableSource: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild("basicFormDirect") basicFormDialog;
  @ViewChild("deleteDirect") deleteDialog;
  selection: SelectionModel<any>;
  isSelected = true;

  entityUtil = EntityUtil;

  constructor(private commonService: CommonUpdateService,
              private dialogService: NbDialogService) {
    super();
  }

  get hasRowSelected() {
    return this.selection.selected.length > 0;
  }

  ngOnInit(): void {

    Object.assign(this.displayedColumns, this.tableData.table.basicColumns.map(c => c.field.name));
    this.tableData.table.manualColumns?.map(c => c.field.name).every(columnName => this.displayedColumns.push(columnName));
    this.displayedColumns.push("select");

    this.subscriptions.push(this.commonService.getAll<any>(this.entityUtil.getIdFromIdentifier(this.tableData.formData.entityIdentifier), this.tableData.formData.entityIdentifier.className).subscribe(
      (result) => {
        if (this.tableData.table.filterCondition) {
          result = result.filter(this.tableData.table.filterCondition);
        }
        this.updateTableSource(result);
      }
    ));

    const initialSelection = [];
    const allowMultiSelect = true;
    this.selection = new SelectionModel<any>(allowMultiSelect, initialSelection);
    this.tableData.formData.share.selection = this.selection;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.tableSource.filter = filterValue.trim().toLowerCase();
    if (this.tableSource.paginator) {
      this.tableSource.paginator.firstPage();
    }
  }

  submitEntitySuccess = (entity, isNew: boolean) => {
    if (isNew) {
      this.tableSource.data.unshift(entity);
      this.tableSource.data = this.tableSource.data;
      this.tableData.formData.share.entity = this.tableSource.data[0];
    }
  }

  deleteEntitySuccess = (entity) => {
    this.removeEntityFromArray(entity, this.tableSource.data);
    this.removeEntityFromArray(entity, this.selection.selected);
    this.tableSource.data = this.tableSource.data;
  }

  removeEntityFromArray(entity, data: any[]) {
    for (let i = 0; i < data.length; i++) {
      const e = data[i];
      if (this.entityUtil.isMatchingId(this.tableData.formData.entityIdentifier, e, entity)) {
        return data.splice(i, 1);
      }
    }
  }

  editEntityDialog(entity: any) {
    if (this.tableData.formData.readonly) return;
    this.tableData.formData.share.entity = entity;
    this.openBasicFormDialog();
  }

  newEntityDialog() {
    this.tableData.formData.share.entity = this.entityUtil.newByEntityDefine(this.tableData.formData.entityIdentifier);
    this.openBasicFormDialog();
  }
  openBasicFormDialog() {
    this.dialogService.open(this.basicFormDialog).onClose.subscribe(() => {
      this.tableData.formData?.share?.xeBasicForm?.restoreShareEntity();
    });
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
    this.subscriptions.push(this.commonService.deleteAll(this.selection.selected, this.tableData.formData.entityIdentifier.className).subscribe(
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
  getColumn(name: string) {
    if (name === 'string') return this.stringColumn;
    if (name === 'avatar') return this.avatarColumn;
    if (name === 'role') return this.roleColumn;
    if (name === 'boldString') return this.boldStringColumn;
    if (name === 'boldStringRole') return this.boldStringRoleColumn;
    if (name === 'avatarString') return this.avatarStringColumn;
    return this.stringColumn;
  }

  getRoleIcons(role: string) {
    if (!role) return [];
    return role.split(",").map(s => RoleUtil.roleIcon[s]).filter(s => s !== undefined);
  }

  asTableColumn(tableColumn: any): TableColumn {
    return tableColumn as TableColumn;
  }

  private updateTableSource(result: any[]) {
    this.tableData.formData.share.tableSource = this.tableSource;
    this.tableData.formData.share.entities = result;
    this.tableSource = new MatTableDataSource<any>(result);
    this.tableData.formData.share.tableSource = this.tableSource;
    this.tableSource.paginator = this.paginator;
    this.tableSource.sort = this.sort;
    this.tableSource.sortingDataAccessor = (item, property) => {
      return this.entityUtil.getFieldValue(item, {name: property}).toLowerCase();
    };
  }
}
