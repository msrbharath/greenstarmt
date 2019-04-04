import { Injectable } from '@angular/core';
import { IPerformanceStarData,ISearchPerformanceStarData } from "../performance-star.interface";
import { Observable } from "rxjs/Rx";

@Injectable()
export class PerformanceStarGenerateServiceMock {
    constructor() { }

    getPerformanceStar(searchPerformanceStarData: ISearchPerformanceStarData) {
        let mockData: any;
        if(searchPerformanceStarData.schoolId != 1){
            //Data for valid scenario
            mockData = {"paramOne":"Attendance","paramTwo":"HomeWork","paramThree":"Discipline","paramOneMonthColorCodes":["#7beded","#7CFC00","#7CFC00","#7CFC00","#7beded","#7beded","#7CFC00","#7CFC00","#7CFC00","#7CFC00","#7CFC00","#7beded","#7beded","#7CFC00","#7CFC00","#7CFC00","#7CFC00","#7CFC00","#7beded","#7beded","#7CFC00","#7CFC00","#7CFC00","#7CFC00","#7CFC00","#7beded","#7beded","#7CFC00","#7CFC00","#7CFC00","#7CFC00"],"paramTwoMonthColorCodes":["#7beded","#7CFC00","#7CFC00","#7CFC00","#7beded","#7beded","#7CFC00","#7CFC00","#7CFC00","#7CFC00","#7CFC00","#7beded","#7beded","#7CFC00","#7CFC00","#7CFC00","#7CFC00","#7CFC00","#7beded","#7beded","#7CFC00","#7CFC00","#7CFC00","#7CFC00","#7CFC00","#7beded","#7beded","#7CFC00","#7CFC00","#7CFC00","#7CFC00"],"paramThreeMonthColorCodes":["#7beded","#7CFC00","#7CFC00","#7CFC00","#7beded","#7beded","#7CFC00","#7CFC00","#7CFC00","#7CFC00","#7CFC00","#7beded","#7beded","#7CFC00","#7CFC00","#7CFC00","#7CFC00","#7CFC00","#7beded","#7beded","#7CFC00","#7CFC00","#7CFC00","#7CFC00","#7CFC00","#7beded","#7beded","#7CFC00","#7CFC00","#7CFC00","#7CFC00"]};
        }else{
            // Data for non existing school empty star data
            mockData = {"paramOne":"","paramTwo":"","paramThree":"","paramOneMonthColorCodes":null,"paramTwoMonthColorCodes":null,"paramThreeMonthColorCodes":null};
        }
        return Observable.of(mockData);
    }

}