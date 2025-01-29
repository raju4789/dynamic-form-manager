import {
    UseFiltersInstanceProps,
    UseFiltersOptions,
    UseGlobalFiltersInstanceProps,
    UseGlobalFiltersOptions,
    UseSortByInstanceProps,
    UseSortByOptions,
    UseSortByColumnProps,
    TableInstance,
    ColumnInstance,
    HeaderGroup,
  } from 'react-table';
  
  declare module 'react-table' {
    export interface TableOptions<D extends object>
      extends UseFiltersOptions<D>,
        UseGlobalFiltersOptions<D>,
        UseSortByOptions<D> {}
  
    export interface TableInstance<D extends object = {}>
      extends UseFiltersInstanceProps<D>,
        UseGlobalFiltersInstanceProps<D>,
        UseSortByInstanceProps<D> {}
  
    export interface ColumnInstance<D extends object = {}>
      extends UseSortByColumnProps<D> {}
  
    export interface HeaderGroup<D extends object = {}>
      extends ColumnInstance<D> {}
  }