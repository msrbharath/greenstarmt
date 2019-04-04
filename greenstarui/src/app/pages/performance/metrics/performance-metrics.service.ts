import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from "rxjs";
import { ISearchPerformanceMetrics } from "./performance-metrics.interface";
import { environment } from '../../../../environments/environment';

const API_URL: string = environment.API_URL+'/api/perfdata';

@Injectable()
export class PerformanceMetricsService {

    private headerValue: HttpHeaders = new HttpHeaders({ 'Accept': 'application/json' });

    constructor(private http: HttpClient) {
    }

    public getWeekDaysByMonth(searchPerformanceMetrics: ISearchPerformanceMetrics): Observable<any> {
        return this.http.post(API_URL + '/weekdayes', searchPerformanceMetrics, { headers: this.headerValue });
    }

    public getIndividualPerformanceMetrics(searchPerformanceMetrics: ISearchPerformanceMetrics): Observable<any> {
        return this.http.post(API_URL+'/existingmetricdatas', searchPerformanceMetrics, { headers: this.headerValue });
    }

    public getTeamwisePerformanceMetrics(searchPerformanceMetrics: ISearchPerformanceMetrics): Observable<any> {
        return this.http.post(API_URL+'/perfmetrics/teamwise', searchPerformanceMetrics, { headers: this.headerValue });
    }

    public getClasswisePerformanceMetrics(searchPerformanceMetrics: ISearchPerformanceMetrics): Observable<any> {
        return this.http.post(API_URL+'/perfmetrics/classwise', searchPerformanceMetrics, { headers: this.headerValue });
    }

    public getEncouragingPerformanceMetrics(searchPerformanceMetrics: ISearchPerformanceMetrics): Observable<any> {
        return this.http.post(API_URL+'/perfmetrics/encouraging', searchPerformanceMetrics, { headers: this.headerValue });
    }
}
