export interface TrainRegularity {
  id: number;
  date: string;
  trainType: string;
  dataSetType: string;
  label: string;
  punctualityRate: number;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
  last: boolean;
  first: boolean;
  empty: boolean;
}

export interface TrainFilter {
  trainType?: string;
  dataSetType?: string;
  label?: string;
  from?: string;
}
