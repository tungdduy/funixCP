import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {CommonUpdateService} from "../../../business/service/common-update.service";
import {ManualColumn, TableColumn, XeTableData} from "../../model/XeTableData";
import {NbDialogService} from "@nebular/theme";
import {SelectionModel} from "@angular/cdk/collections";
import {Notifier} from "../../notify/notify.service";
import {XeLabel} from "../../../business/i18n";
import {HttpErrorResponse} from "@angular/common/http";
import {RoleUtil} from "../../util/role.util";
import {XeSubscriber} from "../../model/XeSubscriber";
import {XeEntity} from "../../../business/entities/XeEntity";
import {EntityField, EntityIdentifier} from "../../model/XeFormData";
import {XeScreen} from "../xe-nav/xe-nav.component";
import {EntityUtil} from "../../util/EntityUtil";
import {Subject} from "rxjs";
import {debounceTime, distinctUntilChanged, switchMap} from "rxjs/operators";
import {ObjectUtil} from "../../util/object.util";
import {EditOnRow} from "../../model/EnumStatus";
import {Xe} from "../../model/Xe";

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
  selection: SelectionModel<E>;
  viewing: SelectionModel<E>;
  isSelected = true;
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
  searchTerm = new Subject<any>();
  @ViewChild("inputTemplate") inputTemplateColumn;
  @ViewChild("string") stringColumn;
  @ViewChild("avatarString") avatarStringColumn;
  @ViewChild("avatar") avatarColumn;
  @ViewChild("boldString") boldStringColumn;
  @ViewChild("boldStringRole") boldStringRoleColumn;
  @ViewChild("role") roleColumn;
  @ViewChild("iconOption") iconOptionColumn;
  tempLastFieldName: string;
  pageIndex: any;

  constructor(private commonService: CommonUpdateService,
              private dialogService: NbDialogService) {
    super();
  }

  get hasRowSelected() {
    return this.selection?.selected?.length > 0;
  }

  get editOnRow() {
    return this.tableData.table.action.editOnRow || EditOnRow.disabled;
  }

  get entityMeta() {
    return this.tableData.formData.entityIdentifier.clazz.meta;
  }

  get entityIdentifier() {
    return this.tableData.external.parent.tableData.formData.entityIdentifier;
  }

  ngOnInit(): void {
    this.initTable();
  }

  initTable() {
    this.initFullScreenForm();
    this.initColumns();
    this.initSelectionAnsShare();
    setTimeout(() => {
      this.initData();
    }, 0);
    console.log(this.editOnRow);
  }

  public updateTableData(result: E[]) {
    console.time('cache table entity');
    EntityUtil.cache(result, this.entityMeta);

    console.log("table result to update: ", result);
    const mainIdName = this.entityMeta.mainIdName;
    const filter = (entity) => {
      const tableCondition = this.tableData.table.action.filters?.filterSingle ? this.tableData.table.action.filters.filterSingle(entity) : true;
      if (!tableCondition) return tableCondition;
      if (this.tableData?.external?.parent && this.tableData.external.parent.tableData.formData.entityIdentifier.clazz.meta.capName === this.entityMeta.capName) {
        return !this.tableData.external.parent.tableData.formData.share.tableEntities
          .map(parent => parent[mainIdName]).includes(entity[mainIdName]);
      }
      return true;
    };
    result = result.filter(e => filter(e));
    if (this.tableData.table.action?.filters?.filterArray) {
      result = this.tableData.table.action.filters.filterArray(result);
    }
    console.timeEnd('cache table entity');
    console.time('update table');
    this.updateTableSource(result);
    console.timeEnd('update table');
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
    if (this.tableData.table.mode.lazyData) {
      if (filterValue.trim().length > 0) {
        this.searchTerm.next(filterValue);
      } else {
        this.updateTableData([]);
      }
    } else {
      this.filterDataInTable(filterValue);
    }
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
      const mainIdName = this.entityMeta.mainIdName;
      if (entity[mainIdName] === data[i][mainIdName]) {
        return data.splice(i, 1);
      }
    }
  }

  dialogEditEntity() {
    if (this.tableData.table.mode?.readonly) return;
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

    this.subscriptions.push(this.commonService.deleteAll(this.selection.selected, this.entityMeta).subscribe(
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

  getColumn(column: TableColumn) {
    if (column.field?.template) return this.inputTemplateColumn;
    if (column.type === 'string') return this.stringColumn;
    if (column.type === 'avatar') return this.avatarColumn;
    if (column.type === 'role') return this.roleColumn;
    if (column.type === 'boldString') return this.boldStringColumn;
    if (column.type === 'boldStringRole') return this.boldStringRoleColumn;
    if (column.type === 'avatarString') return this.avatarStringColumn;
    if (column.type === 'iconOption') return this.iconOptionColumn;

    return this.stringColumn;
  }

  getRoleIcons(role: string) {
    if (!role) return [];
    return role.split(",").map(s => RoleUtil.roleInfo[s]).filter(s => s !== undefined);
  }

  asCol(tableColumn: any): TableColumn {
    return tableColumn as TableColumn;
  }

  toggleRow(entity: E) {
    this.prepareEntityOnSelect(entity);
    this.viewing.clear();
    if (this.selection.isSelected(entity)) {
      this.selection.clear();
      this.postDeSelect(entity);
    } else {
      this.selection.clear();
      this.selection.select(entity);
      this.postSelect(entity);
    }
  }

  toggleRowWithoutAction(entity: E) {
    this.prepareEntityOnSelect(entity);
    let selectedIdx = 0;
    this.tableSource.data.forEach((e, idx) => {
      if (e[this.entityMeta.mainIdName] === entity[this.entityMeta.mainIdName]) {
        selectedIdx = idx;
      }
    });
    const pageIndex = Math.floor(selectedIdx / this.paginator.pageSize);
    while (this.paginator.pageIndex !== pageIndex) {
      if (pageIndex > this.paginator.pageIndex) {
        this.paginator.nextPage();
      } else {
        this.paginator.previousPage();
      }
    }
    if (this.viewing.isSelected(entity)) {
      this.viewing.clear();
      return false;
    } else {
      this.viewing.clear();
      this.viewing.select(entity);
      return true;
    }
  }

  onSelectColumn(entity: E, tableColumn: TableColumn = null) {
    this.prepareEntityOnSelect(entity);
    if (tableColumn?.field.template?.isTableOrder) return;
    if (tableColumn?.action?.screen) {
      this.tableData?.xeScreen.go(tableColumn.action?.screen);
    } else if (tableColumn?.action?.onSelect) {
      tableColumn.action.onSelect(entity);
    } else if (this.tableData.display.toggleOne) {
      this.toggleRow(entity);
    } else if (this.tableData?.table?.action?.postSelect) {
      this.postSelect(entity);
    } else if (!this.tableData.table.mode.readonly) {
      this.dialogEditEntity();
    }
  }

  postSelect(entity: E) {
    if (this.tableData?.table?.action?.postSelect) {
      this.tableData.table.action.postSelect(entity);
      return true;
    }
    return false;
  }

  postDeSelect(entity: E) {
    if (this.tableData?.table?.action?.postDeSelect) {
      this.tableData.table.action.postDeSelect(entity);
    }
  }

  addSelectedToParent() {
    const share = this.tableData.formData.share;
    const clazz = this.tableData.formData.entityIdentifier.clazz;

    const parentEntity = this.entityIdentifier.entity;
    const parentIdentifier = this.entityIdentifier;
    const syncFields = this.tableData.external.parent.syncFieldsPreCreate;
    const selectedEntities = share.selection.selected;
    const entities = selectedEntities.map(entity => {
      const criteria = Object.assign({}, parentEntity);
      const newEntity = EntityUtil.getAllPossibleId(criteria, parentIdentifier);
      newEntity[clazz.meta.mainIdName] = entity[clazz.meta.mainIdName];
      if (syncFields) {
        syncFields.forEach(sync => {
          newEntity[sync.childName] = this.entityUtil.getOriginFieldValue(parentEntity, parentIdentifier.clazz.meta, sync.parentField);
        });
      }
      console.log(newEntity);
      return newEntity;
    });
    CommonUpdateService.instance.insertMulti<E>(entities, parentIdentifier.clazz.meta).subscribe(
      returnedEntities => {
        const returnedIds = returnedEntities.map(be => be[clazz.meta.mainIdName]);
        share.selection.clear();
        share.tableSource.data = share.tableSource.data.filter(e => !returnedIds.includes(e[clazz.meta.mainIdName]));
        Notifier.success(XeLabel.SAVED_SUCCESSFULLY);
      },
      (error: HttpErrorResponse) => {
        console.log(error);
        Notifier.httpErrorResponse(error);
      }
    );
  }

  bringUp(entity: E, field: EntityField, columnIndex: number) {
    const rowIndex = this.getRowIndexBase1(columnIndex) - 1;
    const prevEntity = this.tableSource.data[rowIndex - 1];
    if (prevEntity) {
      this.swapThenSort(prevEntity, entity, field.name);
    }
  }

  swapThenSort(aboveSwap: E, belowSwap: E, fieldName: string) {
    const swapOrder = aboveSwap[fieldName];
    const clazz = this.tableData.formData.entityIdentifier.clazz;
    aboveSwap[fieldName] = belowSwap[fieldName];
    belowSwap[fieldName] = swapOrder;
    this.tableData.formData.share.tableSource.data = this.tableData.formData.share.tableSource.data.sort((above, below) => above[fieldName] - below[fieldName]);
    Xe.update([aboveSwap, belowSwap], clazz.meta);
  }

  bringDown(entity: E, field: EntityField, columnIndex: any) {
    const rowIndex = this.getRowIndexBase1(columnIndex) - 1;
    const nextEntity = this.tableSource.data[rowIndex + 1];
    if (nextEntity) {
      this.swapThenSort(nextEntity, entity, field.name);
    }
  }

  getRowIndexBase1(columnIndex: any): number {
    return (!this.paginator?.pageIndex) ? columnIndex + 1
      : this.paginator?.pageIndex === 0
        ? columnIndex + 1
        : 1 + columnIndex + this.paginator?.pageIndex * this.paginator?.pageSize;
  }

  isFirstRow(columnIndex: number) {
    return this.getRowIndexBase1(columnIndex) === 1;
  }

  isLastRow(columnIndex: any) {
    return this.getRowIndexBase1(columnIndex) === this.tableSource.data?.length;
  }

  updateOnRow() {
    Xe.update$(this.tableSource.data, this.entityMeta).subscribe(arrayResult => {
      this.updateTableData(arrayResult as any);
      if (this.editOnRow.hasOnClick) {
        this.editOnRow.toEditingNo();
      }
      Notifier.success(XeLabel.SAVED_SUCCESSFULLY);
    });
  }

  getLastFieldName = () => this.tempLastFieldName;

  getLastEntity(entity: any, tableColumn: any) {
    const col = tableColumn as TableColumn;
    const ef = this.entityUtil
      .getEntityWithField(entity, this.entityMeta,
        col.field);
    this.tempLastFieldName = ef.lastFieldName;
    return ef.entity;
  }

  getInlineValue(entity: any, column: TableColumn) {
    return this.entityUtil.valueAsInlineString(entity, this.entityMeta, column.field);
  }

  private initSelectionAnsShare() {
    const initialSelection = [];
    const allowMultiSelect = !this.tableData.table.mode?.selectOneOnly;
    this.selection = new SelectionModel<any>(allowMultiSelect, initialSelection);
    this.viewing = new SelectionModel<any>(allowMultiSelect, initialSelection);
    this.tableData.formData.share.selection = this.selection;
    this.tableData.formData.share.tableComponent = this;
  }

  private initData() {
    const previousData = this.tableData.formData.share?.tableEntities;
    let dataEmpty = true;
    if (previousData?.length > 0) {
      this.updateTableData(previousData);
      dataEmpty = false;
    }
    if (this.tableData.table.mode.lazyData) {
      this.tableData.table.action.triggerUpdate = (term) => this.searchTerm.next(term);
      if (dataEmpty) this.initLazyData();
    } else if (this.tableData.table.customData) {
      const data = this.tableData.table.customData();
      this.updateTableData(data);
      this.tableData.table.mode.readonly = true;
    } else if (this.tableData.table?.mode?.customObservable) {
      this.subscriptions.push(this.tableData.table.mode.customObservable.subscribe(
        (result: E[]) => {
          this.updateTableData(result);
        }
      ));
    } else {
      this.subscriptions.push(this.commonService.findByEntityIdentifier<E>(this.tableData.formData.entityIdentifier).subscribe(
        (result: E[]) => {
          this.updateTableData(result);
        }
      ));
    }
  }

  private initColumns() {
    this.displayedColumns = this.tableData.table.basicColumns.filter(c => c !== undefined).map(c => c.field.name);
    this.tableData.table.manualColumns?.map(c => c.field.name).every(columnName => this.displayedColumns.push(columnName));
    if (!this.tableData.table.mode.hideSelectColumn) {
      this.displayedColumns.push("select");
    }
  }

  private initFullScreenForm() {
    if (this.tableData?.display?.fullScreenForm) {
      this.tableData.formData.display.cancelBtn = "close";
      const currentPostCancel = this.tableData.formData.action.postCancel;
      this.tableData.formData.action.postCancel = (entity) => {
        if (currentPostCancel) currentPostCancel(entity);
        this.screen.back();
      };
    }
  }

  private initLazyData() {
    if (this.tableData.table.mode.lazyData) {
      this.searchTerm.pipe(
        debounceTime(300),
        distinctUntilChanged(),
        switchMap((term) => {
          if (this.tableData?.table?.mode?.lazyData) {
            return this.tableData.table.mode.lazyData(term);
          } else {
            if (ObjectUtil.isObject(term)) {
              return term.triggerLazySearch;
            }
          }
        })
      ).subscribe(data => this.updateTableData(data as any as E[]));
    }
  }

  private filterDataInTable(filterValue: string) {
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
    return String(this.entityUtil.valueAsInlineString(data, this.entityMeta, column.field)).toLowerCase().includes(value);
  }

  private updateTableSource(result: E[]) {
    this.tableData.formData.share.tableSource = this.tableSource;
    this.tableData.formData.share.tableEntities = result;
    this.tableSource = new MatTableDataSource<any>(result);
    this.tableData.formData.share.tableSource = this.tableSource;
    this.tableSource.paginator = this.paginator;
    this.tableSource.sort = this.sort;
    this.tableSource.sortingDataAccessor = (item, property) => {
      return this.entityUtil.valueAsInlineString(item, this.entityMeta, {name: property});
    };
  }

  private prepareEntityOnSelect(entity: E) {
    this.tableData.formData.share.entity = entity;
    const thisIdent = this.tableData.formData.entityIdentifier;
    if (this.tableData.external?.updateCriteriaTableOnSelect) {
      this.tableData.external.updateCriteriaTableOnSelect().forEach(table => {
        const targetIdent = table.formData.entityIdentifier;
        this.updateCriteria(targetIdent, thisIdent, entity);
      });
    }
  }

  private updateCriteria(targetIdent: EntityIdentifier<any>, thisIdent: EntityIdentifier<E>, entity: E) {
    const targetEntity = targetIdent.entity;
    if (targetIdent.clazz.meta.pkMetas().map(meta => meta.camelName).includes(thisIdent.clazz.meta.camelName)) {
      targetEntity[thisIdent.clazz.meta.camelName] = entity;
      targetEntity[thisIdent.clazz.meta.mainIdName] = entity[thisIdent.clazz.meta.mainIdName];
    } else {
      let thisFieldId;
      let targetFieldId;
      thisIdent.idFields.forEach(field => {
        thisFieldId = this.entityUtil.getLastFieldName(field);
        targetIdent.idFields.forEach(targetField => {
          targetFieldId = this.entityUtil.getLastFieldName(targetField);
          if (thisFieldId === targetFieldId) {
            targetEntity[targetFieldId] = this.entityUtil.getOriginFieldValue(entity, thisIdent.clazz.meta, field);
            targetEntity[this.entityUtil.getLastSubEntityName(targetField)] = targetEntity[targetFieldId];
          }
        });
      });
    }
  }

}
