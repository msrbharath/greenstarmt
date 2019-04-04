import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { ISchoolSearchData, ISchoolDetail } from '../../school/school.interface';
import { IClassSectionDetail } from '../../performance/star/performance-star.interface';
import { IStudentSearchData } from '../student.interface';

@Injectable()
export class StudentServiceMock {

    constructor() { }

    public getSchools(): Observable<any> {
        const mockData = [{"id":42,"userId":null,"action":null,"schoolName":"Coimbatore Government Hr Sec School","district":null,"state":null,"cityName":null,"address":null,"schoolTeamList":null,"classList":null,"holidays":null,"weekendWorkingDays":null,"perfParamList":null},{"id":43,"userId":null,"action":null,"schoolName":"Annur Government Hr Sec School","district":null,"state":null,"cityName":null,"address":null,"schoolTeamList":null,"classList":null,"holidays":null,"weekendWorkingDays":null,"perfParamList":null},{"id":44,"userId":null,"action":null,"schoolName":"Anthiyur Government Hr Sec School","district":null,"state":null,"cityName":null,"address":null,"schoolTeamList":null,"classList":null,"holidays":null,"weekendWorkingDays":null,"perfParamList":null},{"id":45,"userId":null,"action":null,"schoolName":"Bhavani Government Hr Sec School","district":null,"state":null,"cityName":null,"address":null,"schoolTeamList":null,"classList":null,"holidays":null,"weekendWorkingDays":null,"perfParamList":null},{"id":46,"userId":null,"action":null,"schoolName":"Alwar Government Senior Sec School","district":null,"state":null,"cityName":null,"address":null,"schoolTeamList":null,"classList":null,"holidays":null,"weekendWorkingDays":null,"perfParamList":null},{"id":47,"userId":null,"action":null,"schoolName":"Bhiwadi Government Hr Sec School","district":null,"state":null,"cityName":null,"address":null,"schoolTeamList":null,"classList":null,"holidays":null,"weekendWorkingDays":null,"perfParamList":null},{"id":48,"userId":null,"action":null,"schoolName":"Baran Government Senior Secondary School","district":null,"state":null,"cityName":null,"address":null,"schoolTeamList":null,"classList":null,"holidays":null,"weekendWorkingDays":null,"perfParamList":null},{"id":49,"userId":null,"action":null,"schoolName":"Samraniya Government Hr Sec School","district":null,"state":null,"cityName":null,"address":null,"schoolTeamList":null,"classList":null,"holidays":null,"weekendWorkingDays":null,"perfParamList":null},{"id":50,"userId":null,"action":null,"schoolName":"SSVM Matriculation school","district":null,"state":null,"cityName":null,"address":null,"schoolTeamList":null,"classList":null,"holidays":null,"weekendWorkingDays":null,"perfParamList":null}];
        return Observable.of(mockData);
    }

    public getClassList(school: ISchoolDetail): Observable<any> {
        const mockData = [{"id":37,"className":"I","sectionName":"A","userId":null,"classAndSectionName":"I-A","studentList":null,"teamList":null,"schoolId":0,"teamName":null,"schoolTeamList":null},{"id":38,"className":"I","sectionName":"B","userId":null,"classAndSectionName":"I-B","studentList":null,"teamList":null,"schoolId":0,"teamName":null,"schoolTeamList":null}];
        return Observable.of(mockData);
    }

    public getClassDetail(classInfo: IClassSectionDetail): Observable<any> {
        const mockData = {"id":37,"className":"I","sectionName":"A","userId":null,"classAndSectionName":null,"studentList":[{"id":333,"studentName":"Akil","associationId":333,"classId":37,"teamName":"Kalaam","rollId":"IA333"},{"id":334,"studentName":"Jishnuprasad","associationId":334,"classId":37,"teamName":"Kalaam","rollId":"IA334"},{"id":335,"studentName":"Aadhi","associationId":335,"classId":37,"teamName":"Kalaam","rollId":"IA335"},{"id":336,"studentName":"Neethi","associationId":336,"classId":37,"teamName":"Kalaam","rollId":"IA336"},{"id":337,"studentName":"Deepthi","associationId":337,"classId":37,"teamName":"Kalaam","rollId":"IA337"},{"id":338,"studentName":"Aarav","associationId":338,"classId":37,"teamName":"Teresa","rollId":"IA338"},{"id":339,"studentName":"Prathiksha","associationId":339,"classId":37,"teamName":"Teresa","rollId":"IA339"},{"id":340,"studentName":"Aaradhanaa","associationId":340,"classId":37,"teamName":"Teresa","rollId":"IA340"},{"id":341,"studentName":"Paul","associationId":341,"classId":37,"teamName":"Teresa","rollId":"IA341"},{"id":342,"studentName":"Musthafa","associationId":342,"classId":37,"teamName":"Teresa","rollId":"IA342"}],"teamList":["Kalaam","Teresa"],"schoolId":0,"teamName":null,"schoolTeamList":[{"teamName":"Teresa","studentCount":10,"classId":37,"classSectionName":"I-A"},{"teamName":"Kalaam","studentCount":10,"classId":37,"classSectionName":"I-A"}]};
        return Observable.of(mockData);
    }

    public saveOrUpdateStudent(classInfo: IClassSectionDetail): Observable<any> {
        const mockData = {"id":37,"className":"I","sectionName":"A","userId":null,"classAndSectionName":null,"studentList":[{"id":333,"studentName":"Akil","associationId":333,"classId":37,"teamName":"Kalaam","rollId":"IA333"},{"id":334,"studentName":"Jishnuprasad","associationId":334,"classId":37,"teamName":"Kalaam","rollId":"IA334"},{"id":335,"studentName":"Aadhi","associationId":335,"classId":37,"teamName":"Kalaam","rollId":"IA335"},{"id":336,"studentName":"Neethi","associationId":336,"classId":37,"teamName":"Kalaam","rollId":"IA336"},{"id":337,"studentName":"Deepthi","associationId":337,"classId":37,"teamName":"Kalaam","rollId":"IA337"},{"id":338,"studentName":"Aarav","associationId":338,"classId":37,"teamName":"Teresa","rollId":"IA338"},{"id":339,"studentName":"Prathiksha","associationId":339,"classId":37,"teamName":"Teresa","rollId":"IA339"},{"id":340,"studentName":"Aaradhanaa","associationId":340,"classId":37,"teamName":"Teresa","rollId":"IA340"},{"id":341,"studentName":"Paul","associationId":341,"classId":37,"teamName":"Teresa","rollId":"IA341"},{"id":342,"studentName":"Musthafa","associationId":342,"classId":37,"teamName":"Teresa","rollId":"IA342"}],"teamList":["Kalaam","Teresa"],"schoolId":0,"teamName":null,"schoolTeamList":[{"teamName":"Teresa","studentCount":10,"classId":37,"classSectionName":"I-A"},{"teamName":"Kalaam","studentCount":10,"classId":37,"classSectionName":"I-A"}]};
        return Observable.of(mockData);
    }

    public getStudentDataTemplate(searchStudentData: IStudentSearchData): Observable<any> {
        const mockData = "";
        return Observable.of(mockData);
    }

    public exportStudents(searchStudentData: IStudentSearchData): Observable<any> {
        const mockData = "";
        return Observable.of(mockData);
    }

    public bulkUploadStudentData(formData: FormData): Observable<any> {
        const mockData = "";
        return Observable.of(mockData);
    }

}