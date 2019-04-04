import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from '../../../environments/environment';
import { IAdminDetail } from "./admin.interface";

const API_URL: string = environment.API_URL + '/api/admin';

@Injectable()
export class AdminService {

    private headerValue: HttpHeaders = new HttpHeaders({ 'Accept': 'application/json' });

    constructor(private http: HttpClient) {
    }

    public getUserRoleMappings(): Observable<any> {
        return this.http.get(API_URL + '/userrolemappings', { headers: this.headerValue });
    }

    public saveUserRoleMapping(adminDetail: IAdminDetail): Observable<any> {
        return this.http.post(API_URL + '/saveuserrole', adminDetail, { headers: this.headerValue });
    }

    public updateUserRoleMapping(adminDetail: IAdminDetail): Observable<any> {
        return this.http.post(API_URL + '/updateuserrole', adminDetail, { headers: this.headerValue });
    }

    public deleteUserRoleMapping(adminDetail: IAdminDetail): Observable<any> {
        return this.http.post(API_URL + '/deleteuserrole', adminDetail, { headers: this.headerValue });
    }

    private handleError(error: Response | any): any {
        console.log('API Service :: Handle Error' + error);
        return Observable.throw(error);
    }

}
