// Holds the data used to display star
export interface IPerformanceStarData {
    paramOne: string;
    paramTwo: string;
    paramThree: string;
    paramOneMonthColorCodes: string[];
    paramTwoMonthColorCodes: string[];
    paramThreeMonthColorCodes: string[];
}

export interface ISearchPerformanceStarData {
    calcType: string;
    schoolId: number;
    studentId: number;
    classId: number;
    teamName: string;
    month: number
}

export class ISchoolDetail {
    public id: number;
    public schoolName: string;
    public userId: string;
    public action: string;
    public district: string;
    public state: string;
    public cityName: string;
    public address: string;
    public schoolTeamList: string[];
    public classList: IClassSectionDetail[];
    public perfParamList: IPerformanceParam[];
    public holidays: IHoliday[];
    public weekendWorkingDays: IWeekendWorkingDay[];
}

export class IClassSectionDetail {
    public id: number;
    public userId: number;
    public schoolId:number;
    public className: string;
    public teamName: string;
    public sectionName: string;
    public classAndSectionName: string;
    public studentList: IStudent[];
    public teamList: string[];
    public schoolTeamList: string[];
}

export class IStudent {
    public id: number;
    public associationId: number;
    public classId: number;
    public teamName: string;
    public rollId: string;
    public studentName: string;
}


export class IPerformanceParam {
    public id:number;
    public paramTitle: string;
    public paramDesc: string;
}


export class IHoliday {
    public id:number;
    public fromDate: string;
    public toDate: string;
    public description: string;
}

export class IWeekendWorkingDay {
    public id:number;
    public workingDate: string;
    public reason: string;
}
