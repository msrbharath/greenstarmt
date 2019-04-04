import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { IAdminDetail } from '../admin.interface';

@Injectable()
export class AdminServiceMock {

    constructor() { }

    public getUserRoleMappings(): Observable<any> {
        const mockData = [{"id":2,"userId":"panneer","roleName":"Admin","loggedUserId":null},{"id":3,"userId":"magesh","roleName":"PMO","loggedUserId":null},{"id":4,"userId":"bharath","roleName":"Event POC","loggedUserId":null},{"id":5,"userId":"siva","roleName":"Event POC","loggedUserId":null}];
        return Observable.of(mockData);
    }

    public saveUserRoleMapping(adminDetail: IAdminDetail): Observable<any> {
        let mockData = 'SUCCESS';
        if (adminDetail.userId === 'panneer') {
            mockData = 'EXIST';
        }
        return Observable.of(mockData);
    }

    public updateUserRoleMapping(adminDetail: IAdminDetail): Observable<any> {
        let mockData = 'SUCCESS';
        if (adminDetail.userId === 'panneer') {
            mockData = 'EXIST';
        }
        return Observable.of(mockData);
    }

    public deleteUserRoleMapping(adminDetail: IAdminDetail): Observable<any> {
        const mockData = 'SUCCESS';
        return Observable.of(mockData);
    }

}