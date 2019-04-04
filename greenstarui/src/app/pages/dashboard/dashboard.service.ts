import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from '../../../environments/environment';

const API_URL: string = environment.API_URL + '/api/perfdata';

@Injectable()
export class DashboardService {

    private headerValue: HttpHeaders = new HttpHeaders({ 'Accept': 'application/json' });

    constructor(private http: HttpClient) {
    }

    public getTotalNoOfSchools(): Observable<any> {
        return this.http.post(API_URL + '/perfmetrics/dashboard/totalschools', { headers: this.headerValue });
    }

    public getSchoolByMonthMetrics(): Observable<any> {
        return this.http.post(API_URL + '/perfmetrics/dashboard/schoolbymonth', { headers: this.headerValue });
    }

    public getTopPerformingSchools(): Observable<any> {
        return this.http.post(API_URL + '/perfmetrics/dashboard/topschools', { headers: this.headerValue });
    }

    public getTopPerformingVolunteers(): Observable<any> {
        return this.http.post(API_URL + '/perfmetrics/dashboard/topvolunteers', { headers: this.headerValue });
    }

    private handleError(error: Response | any): any {
        console.log('API Service :: Handle Error' + error);
        return Observable.throw(error);
    }

}
