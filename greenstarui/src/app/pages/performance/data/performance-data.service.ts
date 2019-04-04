import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from '../../../../environments/environment';
import { IPerformanceDataTable, ISearchPerformanceData } from "./performance-data.interface";

const API_URL: string = environment.API_URL+'/api';

@Injectable()
export class PerformanceDataService {

    private headerValue: HttpHeaders = new HttpHeaders({ 'Accept': 'application/json' });

    constructor(private http: HttpClient) {
    }

    public getExistingPerformanceMetricDatas(searchPerformanceData: ISearchPerformanceData): Observable<any> {
        return this.http.post(API_URL+'/perfdata/existingmetricdatas', searchPerformanceData, { headers: this.headerValue });
    }

    public getCreatePerformanceMetricDatas(searchPerformanceData: ISearchPerformanceData): Observable<any> {
        return this.http.post(API_URL+'/perfdata/createmetricdatas', searchPerformanceData, { headers: this.headerValue });
    }

    public savePerformanceMetricDatas(performanceDataTable: IPerformanceDataTable): Observable<any> {
        return this.http.post(API_URL+'/perfdata/savemetricdatas', performanceDataTable, { headers: this.headerValue });
    }

    public updatePerformanceMetricDatas(performanceDataTable: IPerformanceDataTable): Observable<any> {
        return this.http.post(API_URL+'/perfdata/updatemetricdatas', performanceDataTable, { headers: this.headerValue });
    }

    public getPerformanceDataTemplate(searchPerformanceData: ISearchPerformanceData): Observable<any> {
        return this.http.post(API_URL + '/perfdata/downloadtemplate', searchPerformanceData, { responseType: 'blob' });
    }

    public uploadBulkPerformanceData(formData: FormData): Observable<any> {
        return this.http.post(API_URL + '/perfdata/uploadbulkdata', formData, { headers: this.headerValue });
    }

    public getWeekDaysByMonth(searchPerformanceData: ISearchPerformanceData): Observable<any> {
        return this.http.post(API_URL + '/perfdata/weekdayes', searchPerformanceData, { headers: this.headerValue });
    }

    private handleError(error: Response | any): any {
        console.log('API Service :: Handle Error' + error);
        return Observable.throw(error);
    }

}
