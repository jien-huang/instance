import { DataSource } from '@angular/cdk/collections';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { map } from 'rxjs/operators';
import { Observable, of as observableOf, merge } from 'rxjs';

// TODO: Replace this with your own data model type
export interface ConfigurationTableItem {
  key: string;
  value: string;
}

// TODO: replace this with real data from your application
const EXAMPLE_DATA: ConfigurationTableItem[] = [
  {value: "key", key: 'Hydrogen'},
  {value: "key", key: 'Helium'},
  {value: "key", key: 'Lithium'},
  {value: "key", key: 'Beryllium'},
  {value: "key", key: 'Boron'},
  {value: "key", key: 'Carbon'},
  {value: "key", key: 'Nitrogen'},
  {value: "key", key: 'Oxygen'},
  {value: "key", key: 'Fluorine'},
  {value: "key", key: 'Neon'},
  {value: "key", key: 'Sodium'},
  {value: "key", key: 'Magnesium'},
  {value: "key", key: 'Aluminum'},
  {value: "key", key: 'Silicon'},
  {value: "key", key: 'Phosphorus'},
  {value: "key", key: 'Sulfur'},
  {value: "key", key: 'Chlorine'},
  {value: "key", key: 'Argon'},
  {value: "key", key: 'Potassium'},
  {value: "key", key: 'Calcium'},
];

/**
 * Data source for the ScriptsTable view. This class should
 * encapsulate all logic for fetching and manipulating the displayed data
 * (including sorting, pagination, and filtering).
 */
export class ConfigurationTableDataSource extends MatTableDataSource<ConfigurationTableItem> {
  data: ConfigurationTableItem[] = EXAMPLE_DATA;

  constructor(public paginator: MatPaginator, public sort: MatSort) {
    super();
  }


  /**
   *  Called when the table is being destroyed. Use this function, to clean up
   * any open connections or free any held resources that were set up during connect.
   */
  disconnect() {}

  /**
   * Paginate the data (client-side). If you're using server-side pagination,
   * this would be replaced by requesting the appropriate data from the server.
   */
  private getPagedData(data: ConfigurationTableItem[]) {
    const startIndex = this.paginator.pageIndex * this.paginator.pageSize;
    return data.splice(startIndex, this.paginator.pageSize);
  }

  /**
   * Sort the data (client-side). If you're using server-side sorting,
   * this would be replaced by requesting the appropriate data from the server.
   */
  private getSortedData(data: ConfigurationTableItem[]) {
    if (!this.sort.active || this.sort.direction === '') {
      return data;
    }

    return data.sort((a, b) => {
      const isAsc = this.sort.direction === 'asc';
      switch (this.sort.active) {
        case 'value': return compare(a.value, b.value, isAsc);
        case 'key': return compare(+a.key, +b.key, isAsc);
        default: return 0;
      }
    });
  }
}

/** Simple sort comparator for example ID/Name columns (for client-side sorting). */
function compare(a, b, isAsc) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
