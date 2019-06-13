import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from '../../environments/environment';

const API_URL: string = environment.API_URL+'/api';

@Injectable()
export class LoginService {

    private headerValue: HttpHeaders = new HttpHeaders({ 'Accept': 'application/json' })

    constructor(private http: HttpClient) {
    }

    public userLogin(formData: any): Observable<any> {
        return this.http.post(API_URL + '/security/login', formData, { headers: this.headerValue });
    }

    public getAssignedSchools(userId: string): Observable<any> {
        return this.http.post(API_URL + '/admin/getassignedschools', userId, { headers: this.headerValue });
    }
}
