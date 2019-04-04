import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';

@Injectable()
export class LoginServiceMock {

    constructor() { }

    public userLogin(formData: any): Observable<any> {

        let mockData: any = '';
        if (formData.username === 'panneer') {
            mockData = {"roleName":"ROLE_ADMIN","apiToken":"eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNTUzNzc3NTg1LCJzdWIiOiJncmVlbnN0YXIiLCJpc3MiOiJzeXN0ZW0ifQ.4-cuAGFFarFgHImU0odqQSbfETM5n70qaR7ceKUB6fY","uiMenuList":["dashboard","school","student","performance_metrics","performance_star","performance_data","admin"],"userId":"panneer"};
        } else if (formData.username === 'magesh') {
            mockData = {"roleName":"ROLE_PMO","apiToken":"eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNTUzNzc3NTg1LCJzdWIiOiJncmVlbnN0YXIiLCJpc3MiOiJzeXN0ZW0ifQ.4-cuAGFFarFgHImU0odqQSbfETM5n70qaR7ceKUB6fY","uiMenuList":["dashboard","school","student","performance_metrics","performance_star","performance_data","admin"],"userId":"magesh"};
        } else if (formData.username === 'bharath') {
            mockData = {"roleName":"ROLE_EVENT_POC","apiToken":"eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNTUzNzc3NTg1LCJzdWIiOiJncmVlbnN0YXIiLCJpc3MiOiJzeXN0ZW0ifQ.4-cuAGFFarFgHImU0odqQSbfETM5n70qaR7ceKUB6fY","uiMenuList":["dashboard","school","student","performance_metrics","performance_star","performance_data","admin"],"userId":"bharath"};            
        } else {
            mockData = '';
        }
        return Observable.of(mockData);
    }

}
