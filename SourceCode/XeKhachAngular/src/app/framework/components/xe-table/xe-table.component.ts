import {Component, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {CommonUpdateService} from "../../../business/service/common-update.service";
import {EntityUtil} from "../../util/entity.util";
import {XeTableData} from "../../../business/abstract/XeTableData";
import {NbDialogService} from "@nebular/theme";
import {XeEntity} from "../../../business/model/xe-entity";
import {SelectionModel} from "@angular/cdk/collections";
import {Notifier} from "../../notify/notify.service";
import {XeLabel} from "../../../business/i18n";
import {HttpErrorResponse} from "@angular/common/http";
import {RoleUtil} from "../../util/role.util";

@Component({
  selector: 'xe-table',
  templateUrl: './xe-table.component.html',
  styleUrls: ['./xe-table.component.scss']
})
export class XeTableComponent implements OnInit, OnDestroy {

  @Input() tableData: XeTableData;
  displayedColumns: string[] = [];
  dataSource: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild("basicFormDirect") basicFormDialog;
  @ViewChild("deleteDirect") deleteDialog;
  selection: SelectionModel<any>;
  isSelected = true;

  constructor(private commonService: CommonUpdateService,
              private dialogService: NbDialogService) {
  }

  get hasRowSelected() {
    return this.selection.selected.length > 0;
  }

  ngOnInit(): void {
    this.tableData.formData.share = this.tableData.share;
    this.tableData.formData.className = this.tableData.className;
    this.tableData.formData.idColumns = this.tableData.idColumns;
    Object.assign(this.displayedColumns, this.tableData.table.basicColumns.map(c => c.name));
    this.displayedColumns.push("select");
    this.subscriptions.push(this.commonService.getAll<any>(this.tableData.idColumns, this.tableData.className).subscribe(
      (result) => {
        console.log(result);
        this.dataSource = new MatTableDataSource<any>(result);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      }
    ));

    const initialSelection = [];
    const allowMultiSelect = true;
    this.selection = new SelectionModel<any>(allowMultiSelect, initialSelection);
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  submitEntitySuccess = (entity, isNew: boolean) => {
    if (isNew) {
      this.dataSource.data.unshift(entity);
      this.dataSource.data = this.dataSource.data;
      this.tableData.share.entity = this.dataSource.data[0];
    }
  }

  deleteEntitySuccess = (entity) => {
    this.removeEntityFromArray(entity, this.dataSource.data);
    this.removeEntityFromArray(entity, this.selection.selected);
    this.dataSource.data = this.dataSource.data;
  }

  removeEntityFromArray(entity, data: any[]) {
    for (let i = 0; i < data.length; i++) {
      const e = data[i];
      if (XeEntity.isMatchingId(this.tableData.idColumns, e, entity)) {
        return data.splice(i, 1);
      }
    }
  }

  editDialog(entity: any) {
    this.tableData.share.entity = entity;
    this.dialogService.open(this.basicFormDialog);
  }

  newDialog() {
    this.tableData.share.entity = EntityUtil.newByName(this.tableData.className);
    this.dialogService.open(this.basicFormDialog);
  }

  openDeleteDialog() {
    this.dialogService.open(this.deleteDialog);
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  toggleSelectAll() {
    if (this.isAllSelected()) {
      this.selection.clear();
    } else {
      this.selection.clear();
      this.dataSource.data.forEach(row => this.selection.select(row));
    }
  }

  subscriptions = [];
  deleteSelected() {
    this.subscriptions.push(this.commonService.deleteAll(this.selection.selected, this.tableData.className).subscribe(
      () => {
        this.selection.selected.forEach(entity => {
          this.removeEntityFromArray(entity, this.dataSource.data);
        });
        this.selection.clear();
        this.dataSource.data = this.dataSource.data;
        Notifier.success(XeLabel.DELETE_SUCCESS);
      },
      (error: HttpErrorResponse) => {
        Notifier.httpErrorResponse(error);
      }
    ));
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
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

  getRoleIcons(role: []) {
    return role.map(s => RoleUtil.roleIcon[s]).filter(s => s !== undefined);
  }
}
