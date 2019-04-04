import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from "rxjs";
import { ISearchPerformanceStarData } from "./performance-star.interface";

import { environment } from '../../../../environments/environment';

const API_URL: string = environment.API_URL+'/api/perfdata';

@Injectable()
export class PerformanceGenerateStarService {

    private headerValue: HttpHeaders = new HttpHeaders({ 'Accept': 'application/json' });

    constructor(private http: HttpClient) {
    }

    public getPerformanceStar(searchPerformanceStarData: ISearchPerformanceStarData): Observable<any> {
        return this.http.post(API_URL+'/perfstar/generateStar',searchPerformanceStarData,{ headers: this.headerValue });
    }

    private handleError(error: Response | any): any {
        console.log('API Service :: Handle Error' + error);
        return Observable.throw(error);
    }

}
