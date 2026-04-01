import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageResponse, TrainFilter, TrainRegularity } from '../models/train.model';

@Injectable({ providedIn: 'root' })
export class TrainService {

  private readonly baseUrl = '/stats';
  constructor(private http: HttpClient) {}
  getRanking(filters: TrainFilter, page: number, size = 20): Observable<PageResponse<TrainRegularity>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
    if (filters.trainType)  params = params.set('trainType', filters.trainType);
    if (filters.dataSetType) params = params.set('dataSetType', filters.dataSetType);
    if (filters.label)      params = params.set('label', filters.label);
    if (filters.from)       params = params.set('from', filters.from);

    return this.http.get<PageResponse<TrainRegularity>>(`${this.baseUrl}/ranking`, { params });
  }

  getChartData(label: string, from: string, to: string): Observable<PageResponse<TrainRegularity>> {
    const params = new HttpParams()
      .set('label', label)
      .set('from', from)
      .set('to', to)
      .set('page', '0')
      .set('size', '200')

    return this.http.get<PageResponse<TrainRegularity>>(`${this.baseUrl}/filterby`, { params });
  }
}
