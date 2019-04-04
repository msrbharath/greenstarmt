import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from "rxjs";
import { ISchoolDetail, IClassSectionDetail } from "./performance-star.interface";

import { environment } from '../../../../environments/environment';

const API_URL: string = environment.API_URL+'/api';

@Injectable()
export class PerformanceStarService {

    private headerValue: HttpHeaders = new HttpHeaders({ 'Accept': 'application/json' });

    constructor(private http: HttpClient) {
    }

    public getSchools(): Observable<any> {
        return this.http.get(API_URL+'/school/getSchools', { headers: this.headerValue });
    }

    public getClassList(school: ISchoolDetail): Observable<any> {
        return this.http.post(API_URL+'/school/getClassList',school,{ headers: this.headerValue });
    }

    public getClassDetail(classInfo: IClassSectionDetail): Observable<any> {
        return this.http.post(API_URL+'/school/getClassDetail',classInfo,{ headers: this.headerValue });
    }

    private handleError(error: Response | any): any {
        console.log('API Service :: Handle Error' + error);
        return Observable.throw(error);
    }

}
