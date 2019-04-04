import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { ISchoolDetail, ISchoolSearchData } from '../school.interface';

@Injectable()
export class SchoolServiceMock {

    constructor() { }

    public getSchoolsForSearch(schoolSearchData: ISchoolSearchData): Observable<any> {
        const mockData = [{ "id": 42, "userId": null, "action": null, "schoolName": "Coimbatore Government Hr Sec School", "district": "COIMBATORE", "state": "TAMIL NADU", "cityName": "COIMBATORE", "address": "COIMBATORE", "schoolTeamList": null, "classList": null, "holidays": null, "weekendWorkingDays": null, "perfParamList": null }, { "id": 43, "userId": null, "action": null, "schoolName": "Annur Government Hr Sec School", "district": "COIMBATORE", "state": "TAMIL NADU", "cityName": "ANNUR", "address": "ANNUR", "schoolTeamList": null, "classList": null, "holidays": null, "weekendWorkingDays": null, "perfParamList": null }, { "id": 50, "userId": null, "action": null, "schoolName": "SSVM Matriculation school", "district": "COIMBATORE", "state": "TAMIL NADU", "cityName": "Mettupalayam", "address": "Alangombu (Post),\nMettupalayam - 641 302.\nCoimbatore (Dt.), Tamil Nadu", "schoolTeamList": null, "classList": null, "holidays": null, "weekendWorkingDays": null, "perfParamList": null }];
        return Observable.of(mockData);
    }

    public submitSchool(schoolDetail: ISchoolDetail): Observable<any> {
        const mockData = '';
        return Observable.of(mockData);
    }

    public retrieveSchool(schoolId: number): Observable<any> {
        let mockData: any = "";
        if(schoolId === 42) {
            mockData = { "id": 42, "userId": null, "action": null, "schoolName": "Coimbatore Government Hr Sec School", "district": "COIMBATORE", "state": "TAMIL NADU", "cityName": "COIMBATORE", "address": "COIMBATORE", "schoolTeamList": null, "classList": [{ "id": 37, "className": "I", "sectionName": "A", "userId": null, "classAndSectionName": "I-A", "studentList": null, "teamList": null, "schoolId": 0, "teamName": null, "schoolTeamList": null }, { "id": 38, "className": "I", "sectionName": "B", "userId": null, "classAndSectionName": "I-B", "studentList": null, "teamList": null, "schoolId": 0, "teamName": null, "schoolTeamList": null }], "holidays": [{ "id": 211, "fromDate": "2019-01-01", "toDate": "2019-01-01", "description": "New Year’s Day" }, { "id": 212, "fromDate": "2019-03-04", "toDate": "2019-03-04", "description": "Maha Shivaratri" }, { "id": 213, "fromDate": "2019-03-21", "toDate": "2019-03-21", "description": "Holi" }, { "id": 214, "fromDate": "2019-06-05", "toDate": "2019-06-05", "description": "Idul Fitr" }, { "id": 215, "fromDate": "2019-08-15", "toDate": "2019-08-15", "description": "Independence Day" }, { "id": 216, "fromDate": "2019-09-10", "toDate": "2019-09-10", "description": "Muharram" }, { "id": 217, "fromDate": "2019-10-02", "toDate": "2019-10-02", "description": "Gandhi Jayanti" }, { "id": 218, "fromDate": "2019-10-08", "toDate": "2019-10-08", "description": "Durga Puja and Dussehra" }, { "id": 219, "fromDate": "2019-10-28", "toDate": "2019-10-28", "description": "Diwali Holiday" }, { "id": 220, "fromDate": "2019-11-12", "toDate": "2019-11-12", "description": "Guru Nanak Jayanti" }, { "id": 221, "fromDate": "2019-12-25", "toDate": "2019-12-25", "description": "Christmas Day" }, { "id": 222, "fromDate": "2019-04-01", "toDate": "2019-05-31", "description": "Summer Holidays" }, { "id": 223, "fromDate": "2019-02-06", "toDate": "2019-02-07", "description": "State level inter school tournament" }], "weekendWorkingDays": [{ "id": 35, "workingDate": "2019-02-09", "reason": "Compoff for inter school meet" }, { "id": 36, "workingDate": "2019-02-16", "reason": "Compoff for inter school meet" }], "perfParamList": [{ "id": 52, "paramTitle": "Attendance", "paramDesc": "Attendance" }, { "id": 53, "paramTitle": "HomeWork", "paramDesc": "Home Work" }, { "id": 54, "paramTitle": "Discipline", "paramDesc": "Discipline" }] };
        } else {
            mockData = { "id": 53, "userId": null, "action": null, "schoolName": "Coimbatore Government Hr Sec School", "district": "COIMBATORE", "state": "TAMIL NADU", "cityName": "COIMBATORE", "address": "COIMBATORE", "schoolTeamList": null, "classList": null, "holidays": null, "weekendWorkingDays": null, "perfParamList": [{ "id": 52, "paramTitle": "Attendance", "paramDesc": "Attendance" }, { "id": 53, "paramTitle": "HomeWork", "paramDesc": "Home Work" }, { "id": 54, "paramTitle": "Discipline", "paramDesc": "Discipline" }] };
        }
        return Observable.of(mockData);
    }

}