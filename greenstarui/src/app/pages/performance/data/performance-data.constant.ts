import { IClassWiseHeader, IClassWiseMetricsDataTable, IClassWiseMetricsRow, IPerformanceData, IPerformanceDataTable, IPerformanceDay, IPerformanceHeader, IPerformanceMetricsData, IPerformanceMetricsDataTable, IPerformanceMetricsDay, IPerformanceMetricsRow, IPerformanceMetricsWeek, IPerformanceRow, ITeamWiseHeader, ITeamWiseMetricsDataTable, ITeamWiseMetricsRow } from "./performance-data.interface";

export class PerformanceStaticData {

    public static performanceSource: IPerformanceDataTable = {} as IPerformanceDataTable;
    public static performanceMetricsSource: IPerformanceMetricsDataTable = {} as IPerformanceMetricsDataTable;
    public static classwiseSource: IClassWiseMetricsDataTable = {} as IClassWiseMetricsDataTable;
    public static teamwiseSource: ITeamWiseMetricsDataTable = {} as ITeamWiseMetricsDataTable;

    public static getTestTableContent(): IPerformanceDataTable {

        // table dynamic headers - start
        let dynamicHeaders: IPerformanceHeader[] = [
            { title: '18-Feb-2019', alais: '18-Feb-2019', checkValue: false, subTitleList: this.getSubTitle() },
            { title: '19-Feb-2019', alais: '19-Feb-2019', checkValue: false, subTitleList: this.getSubTitle() },
            { title: '20-Feb-2019', alais: '20-Feb-2019', checkValue: false, subTitleList: this.getSubTitle() },
            { title: '21-Feb-2019', alais: '21-Feb-2019', checkValue: false, subTitleList: this.getSubTitle() },
            { title: '22-Feb-2019', alais: '22-Feb-2019', checkValue: false, subTitleList: this.getSubTitle() }
        ];

        this.performanceSource.headers = dynamicHeaders;
        // table dynamic headers - end

        this.performanceSource.performanceRows = this.getPerformanceContents();

        return this.performanceSource;
    }


    public static getPerformanceMetricsTableContent(): IPerformanceMetricsDataTable {

        // table dynamic headers - start
        let dynamicHeaders: IPerformanceHeader[] = [
            { title: '18-Feb-2019', alais: "", checkValue: false, subTitleList: this.getSubTitle() },
            { title: '19-Feb-2019', alais: "", checkValue: false, subTitleList: this.getSubTitle() },
            { title: '20-Feb-2019', alais: "", checkValue: false, subTitleList: this.getSubTitle() },
            { title: '21-Feb-2019', alais: "", checkValue: false, subTitleList: this.getSubTitle() },
            { title: '22-Feb-2019', alais: "", checkValue: false, subTitleList: this.getSubTitle() },
            { title: 'Week Summary', alais: "", checkValue: false, subTitleList: this.getSubTitle() }
        ];

        this.performanceMetricsSource.headers = dynamicHeaders;
        // table dynamic headers - end

        this.performanceMetricsSource.performanceRows = this.getPerformanceMetricsContents();
        return this.performanceMetricsSource;
    }

    public static getSubTitle(): IPerformanceHeader[] {

        let dynamicTitle: IPerformanceHeader[] = [
            { title: 'HomeWork', alais: 'HW', checkValue: false, subTitleList: [] },
            { title: 'Disciplain', alais: 'DS', checkValue: false, subTitleList: [] },
            { title: 'Attendance', alais: 'AT', checkValue: false, subTitleList: [] }
        ];
        return dynamicTitle;
    }

    public static getPerformanceContents(): IPerformanceRow[] {

        let performanceRowArray: IPerformanceRow[] = [];

        performanceRowArray.push(this.getPerformanceRowObject1());
        performanceRowArray.push(this.getPerformanceRowObject2());

        return performanceRowArray;
    }

    public static getPerformanceRowObject1(): IPerformanceRow {

        let performanceRow: IPerformanceRow = {} as IPerformanceRow;
        performanceRow.rollId = "LKG01";
        performanceRow.studentName = "Panneer";
        performanceRow.performanceDays = this.getPerformanceDays();

        return performanceRow;
    }

    public static getPerformanceRowObject2(): IPerformanceRow {

        let performanceRow: IPerformanceRow = {} as IPerformanceRow;
        performanceRow.rollId = "LKG02";
        performanceRow.studentName = "Magesh";
        performanceRow.performanceDays = this.getPerformanceDays();

        return performanceRow;
    }

    public static getPerformanceDays(): IPerformanceDay[] {

        let performanceDayArray: IPerformanceDay[] = [];

        let performanceDay1: IPerformanceDay = {} as IPerformanceDay;
        performanceDay1.dateValue = "18-Feb-2019";
        performanceDay1.performanceData = this.getPerformanceData();
        performanceDayArray.push(performanceDay1);

        let performanceDay2: IPerformanceDay = {} as IPerformanceDay;
        performanceDay2.dateValue = "19-Feb-2019";
        performanceDay2.performanceData = this.getPerformanceData();
        performanceDayArray.push(performanceDay2);

        let performanceDay3: IPerformanceDay = {} as IPerformanceDay;
        performanceDay3.dateValue = "20-Feb-2019";
        performanceDay3.performanceData = this.getPerformanceData();
        performanceDayArray.push(performanceDay3);

        let performanceDay4: IPerformanceDay = {} as IPerformanceDay;
        performanceDay4.dateValue = "21-Feb-2019";
        performanceDay4.performanceData = this.getPerformanceData();
        performanceDayArray.push(performanceDay4);

        let performanceDay5: IPerformanceDay = {} as IPerformanceDay;
        performanceDay5.dateValue = "22-Feb-2019";
        performanceDay5.performanceData = this.getPerformanceData();
        performanceDayArray.push(performanceDay5);

        return performanceDayArray;
    }

    public static getPerformanceData(): IPerformanceData[] {

        let performanceDataArray: IPerformanceData[] = [];

        let performanceData1: IPerformanceData = {} as IPerformanceData;
        performanceData1.key = "HomeWork";
        performanceData1.value = true;
        performanceDataArray.push(performanceData1);

        let performanceData2: IPerformanceData = {} as IPerformanceData;
        performanceData2.key = "Disciplain";
        performanceData2.value = false;
        performanceDataArray.push(performanceData2);

        let performanceData3: IPerformanceData = {} as IPerformanceData;
        performanceData3.key = "Attendance";
        performanceData3.value = true;
        performanceDataArray.push(performanceData3);

        return performanceDataArray;
    }

    public static getPerformanceMetricsContents(): IPerformanceMetricsRow[] {

        let performanceMetricsRowArray: IPerformanceMetricsRow[] = [];

        performanceMetricsRowArray.push(this.getPerformanceMetricsRowObject1());
        performanceMetricsRowArray.push(this.getPerformanceMetricsRowObject2());

        return performanceMetricsRowArray;
    }

    public static getPerformanceMetricsRowObject1(): IPerformanceMetricsRow {

        let performanceMetricsRow: IPerformanceMetricsRow = {} as IPerformanceMetricsRow;
        performanceMetricsRow.rollId = "LKG01";
        performanceMetricsRow.name = "Panneer";
        performanceMetricsRow.performanceMetricsDays = this.getPerformanceMetricsDays();

        return performanceMetricsRow;
    }

    public static getPerformanceMetricsRowObject2(): IPerformanceMetricsRow {

        let performanceRow: IPerformanceMetricsRow = {} as IPerformanceMetricsRow;
        performanceRow.rollId = "LKG02";
        performanceRow.name = "Magesh";
        performanceRow.performanceMetricsDays = this.getPerformanceMetricsDays();

        return performanceRow;
    }

    public static getPerformanceMetricsDays(): IPerformanceMetricsDay[] {

        let performanceDayArray: IPerformanceMetricsDay[] = [];

        let performanceDay1: IPerformanceMetricsDay = {} as IPerformanceMetricsDay;
        performanceDay1.dateValue = "18-Feb-2019";
        performanceDay1.performanceDatas = this.getPerformanceMetricsData();
        performanceDayArray.push(performanceDay1);

        let performanceDay2: IPerformanceMetricsDay = {} as IPerformanceMetricsDay;
        performanceDay2.dateValue = "19-Feb-2019";
        performanceDay2.performanceDatas = this.getPerformanceMetricsData();
        performanceDayArray.push(performanceDay2);

        let performanceDay3: IPerformanceMetricsDay = {} as IPerformanceMetricsDay;
        performanceDay3.dateValue = "20-Feb-2019";
        performanceDay3.performanceDatas = this.getPerformanceMetricsData();
        performanceDayArray.push(performanceDay3);

        let performanceDay4: IPerformanceMetricsDay = {} as IPerformanceMetricsDay;
        performanceDay4.dateValue = "21-Feb-2019";
        performanceDay4.performanceDatas = this.getPerformanceMetricsData();
        performanceDayArray.push(performanceDay4);

        let performanceDay5: IPerformanceMetricsDay = {} as IPerformanceMetricsDay;
        performanceDay5.dateValue = "22-Feb-2019";
        performanceDay5.performanceDatas = this.getPerformanceMetricsData();
        performanceDayArray.push(performanceDay5);

        let performanceWeek: IPerformanceMetricsWeek = {} as IPerformanceMetricsWeek;
        performanceWeek.weekName = "Week 1";
        performanceDay5.performanceDatas = this.getPerformanceMetricsData();
        performanceDayArray.push(performanceDay5);

        return performanceDayArray;
    }

    public static getPerformanceMetricsData(): IPerformanceMetricsData[] {

        let performanceMetricsDataArray: IPerformanceMetricsData[] = [];

        let performanceData1: IPerformanceMetricsData = {} as IPerformanceMetricsData;
        performanceData1.key = "HomeWork";
        performanceData1.value = 2;
        performanceMetricsDataArray.push(performanceData1);

        let performanceData2: IPerformanceMetricsData = {} as IPerformanceMetricsData;
        performanceData2.key = "Discipline";
        performanceData2.value = 0;
        performanceMetricsDataArray.push(performanceData2);

        let performanceData3: IPerformanceMetricsData = {} as IPerformanceMetricsData;
        performanceData3.key = "Attendance";
        performanceData3.value = 2;
        performanceMetricsDataArray.push(performanceData3);

        return performanceMetricsDataArray;
    }

    public static getClassWiseTableContent(): IClassWiseMetricsDataTable {

        // class wise dynamic headers - start
        let classwiseHeaders: IClassWiseHeader[] = [
            { title: 'Class', alias: 'Class' },
            { title: 'Attendance', alias: 'Attendance' },
            { title: 'Discipline', alias: 'Discipline' },
            { title: 'Homework', alias: 'Homework' },
            { title: 'Total', alias: 'Total' }
        ];

        this.classwiseSource.headers = classwiseHeaders;
        // table dynamic headers - end

        this.classwiseSource.performanceRows = this.getClasswiseMetricsContents();

        return this.classwiseSource;
    }

    public static getClasswiseMetricsContents(): IClassWiseMetricsRow[] {
        let classwiseMetricsRowArray: IClassWiseMetricsRow[] = [];

        classwiseMetricsRowArray.push(this.getClasswiseMetricsRowObject1());
        classwiseMetricsRowArray.push(this.getClasswiseMetricsRowObject2());
        classwiseMetricsRowArray.push(this.getClasswiseMetricsRowObject3());
        classwiseMetricsRowArray.push(this.getClasswiseMetricsRowObject4());

        return classwiseMetricsRowArray;
    }

    public static getClasswiseMetricsRowObject1(): IClassWiseMetricsRow {

        let classwiseMetricsData: IClassWiseMetricsRow = {} as IClassWiseMetricsRow;

        classwiseMetricsData.class = "5th-A";
        classwiseMetricsData.attendance = 122;
        classwiseMetricsData.homework = 123;
        classwiseMetricsData.discipline = 142;
        classwiseMetricsData.total = 141;

        return classwiseMetricsData;
    }

    public static getClasswiseMetricsRowObject2(): IClassWiseMetricsRow {

        let classwiseMetricsData: IClassWiseMetricsRow = {} as IClassWiseMetricsRow;

        classwiseMetricsData.class = "5th-B";
        classwiseMetricsData.attendance = 122;
        classwiseMetricsData.homework = 123;
        classwiseMetricsData.discipline = 142;
        classwiseMetricsData.total = 142;

        return classwiseMetricsData;
    }
    public static getClasswiseMetricsRowObject3(): IClassWiseMetricsRow {
        
        let classwiseMetricsData: IClassWiseMetricsRow = {} as IClassWiseMetricsRow;

        classwiseMetricsData.class = "5th-C";
        classwiseMetricsData.attendance = 122;
        classwiseMetricsData.homework = 123;
        classwiseMetricsData.discipline = 142;
        classwiseMetricsData.total = 143;

        return classwiseMetricsData;
    }
    public static getClasswiseMetricsRowObject4(): IClassWiseMetricsRow {

        let classwiseMetricsData: IClassWiseMetricsRow = {} as IClassWiseMetricsRow;

        classwiseMetricsData.class = "5th-D";
        classwiseMetricsData.attendance = 122;
        classwiseMetricsData.homework = 123;
        classwiseMetricsData.discipline = 142;
        classwiseMetricsData.total = 144;

        return classwiseMetricsData;
    }

    public static getTeamWiseTableContent(): ITeamWiseMetricsDataTable {

        // class wise dynamic headers - start
        let teamwiseHeaders: ITeamWiseHeader[] = [
            { title: 'Team', alias: 'Team' },
            { title: 'Attendance', alias: 'Attendance' },
            { title: 'Discipline', alias: 'Discipline' },
            { title: 'Homework', alias: 'Homework' },
            { title: 'Total', alias: 'Total' }
        ];

        this.teamwiseSource.headers = teamwiseHeaders;
        // table dynamic headers - end

        this.teamwiseSource.performanceRows = this.getTeamwiseMetricsContents();

        return this.teamwiseSource;
    }
    public static getTeamwiseMetricsContents(): ITeamWiseMetricsRow[] {
        let teamwiseMetricsRowArray: ITeamWiseMetricsRow[] = [];

        teamwiseMetricsRowArray.push(this.getTeamwiseMetricsRowObject1());
        teamwiseMetricsRowArray.push(this.getTeamwiseMetricsRowObject2());
        teamwiseMetricsRowArray.push(this.getTeamwiseMetricsRowObject3());
        teamwiseMetricsRowArray.push(this.getTeamwiseMetricsRowObject4());
        return teamwiseMetricsRowArray;
    }

    public static getTeamwiseMetricsRowObject1(): ITeamWiseMetricsRow {
        let teamwiseMetricsData: ITeamWiseMetricsRow = {} as ITeamWiseMetricsRow;

        teamwiseMetricsData.team = "Kattabomman";
        teamwiseMetricsData.attendance = 122;
        teamwiseMetricsData.homework = 123;
        teamwiseMetricsData.discipline = 142;
        teamwiseMetricsData.total = 141;

        return teamwiseMetricsData;
    }

    public static getTeamwiseMetricsRowObject2(): ITeamWiseMetricsRow {
        let teamwiseMetricsData: ITeamWiseMetricsRow = {} as ITeamWiseMetricsRow;

        teamwiseMetricsData.team = "Avvaiyar";
        teamwiseMetricsData.attendance = 122;
        teamwiseMetricsData.homework = 123;
        teamwiseMetricsData.discipline = 142;
        teamwiseMetricsData.total = 146;

        return teamwiseMetricsData;
    }
    public static getTeamwiseMetricsRowObject3(): ITeamWiseMetricsRow {
        let teamwiseMetricsData: ITeamWiseMetricsRow = {} as ITeamWiseMetricsRow;

        teamwiseMetricsData.team = "Marutham";
        teamwiseMetricsData.attendance = 122;
        teamwiseMetricsData.homework = 123;
        teamwiseMetricsData.discipline = 142;
        teamwiseMetricsData.total = 143;

        return teamwiseMetricsData;
    }
    public static getTeamwiseMetricsRowObject4(): ITeamWiseMetricsRow {
        let teamwiseMetricsData: ITeamWiseMetricsRow = {} as ITeamWiseMetricsRow;

        teamwiseMetricsData.team = "Paalai";
        teamwiseMetricsData.attendance = 122;
        teamwiseMetricsData.homework = 123;
        teamwiseMetricsData.discipline = 142;
        teamwiseMetricsData.total = 144;

        return teamwiseMetricsData;
    }

}
