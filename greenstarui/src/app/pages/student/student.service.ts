import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from '../../../environments/environment';
import { IClassSectionDetail, ISchoolDetail, IStudentSearchData } from "./student.interface";


const API_URL: string = environment.API_URL+'/api';

@Injectable()
export class StudentService {

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

    public saveOrUpdateStudent(classInfo: IClassSectionDetail): Observable<any> {
        return this.http.post(API_URL + '/school/saveclassstudents', classInfo, { headers: this.headerValue });
    }

    public getStudentDataTemplate(searchStudentData: IStudentSearchData): Observable<any> {
        return this.http.post(API_URL + '/school/student/downloadtemplate', searchStudentData, { responseType: 'blob' });
    }

    public exportStudents(searchStudentData: IStudentSearchData): Observable<any> {
        return this.http.post(API_URL + '/school/student/exportstudents', searchStudentData, { responseType: 'blob' });
    }

    public bulkUploadStudentData(formData: FormData): Observable<any> {
        return this.http.post(API_URL + '/school/student/uploadbulkdata', formData, { headers: this.headerValue });
    }

    private handleError(error: Response | any): any {
        console.log('API Service :: Handle Error' + error);
        return Observable.throw(error);
    }

}
