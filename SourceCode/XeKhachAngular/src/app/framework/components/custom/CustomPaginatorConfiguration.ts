import {MatPaginatorIntl} from "@angular/material/paginator";
import {XeLbl} from "../../../business/i18n";

export function CustomPaginator() {
  const customPaginatorIntl = new MatPaginatorIntl();

  customPaginatorIntl.itemsPerPageLabel = XeLbl("ITEMS_PER_PAGE") + ":";
  customPaginatorIntl.getRangeLabel = (page: number, pageSize: number, length: number) => {
    return `${page * pageSize + 1}-${Math.min(page * pageSize + pageSize, length)}/${length}`;
  };

  return customPaginatorIntl;
}
