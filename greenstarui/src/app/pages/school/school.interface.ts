export class ISchoolDetail {

    public id: number;
    public schoolName: string;
    public address: string;
    public cityName: string;
    public state: string;
    public district: string;

    public userId: string;
    public action: string;
    // class 
    public classList: IClass[];

    // perofrmance parameter
    public perfParamType: string
    public perfParamList: IPerformanceParam[];

    // holiday information
    public holidays: IHoliday[];

    // weekend working day information
    public weekendWorkingDays: IWeekendWorkingDay[];
}

export class ISchoolSearchData {
    public stateName: string;
    public district: string;
}

export class IClass {
    public id: number;
    public className: string;
    public sectionName: string;
}

export class IPerformanceParam {
    public id: number;
    public paramTitle: string;
    public paramDesc: string;
}

export class IHoliday {
    public id: number;
    public fromDate: string;
    public toDate: string;
    public description: string;
}

export class IWeekendWorkingDay {
    public id: number;
    public workingDate: string;
    public reason: string;
}
