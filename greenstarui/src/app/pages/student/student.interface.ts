
export class IStudentSearchData {
    public schoolId: number;
    public classId: number;
}

export class ISchoolDetail {
    public id: number;
    public schoolName: string;
    public teamMap: Map<string,number>;
}

export class IClassSectionDetail {
    public id: number;
    public userId: string;
    public schoolId: number;
    public className: string;
    public sectionName: string;
    public classAndSectionName: string;
    public studentList: IStudentDetail[] = [];
    public teamList: string[];
    public schoolTeamList: ISchoolTeamCount[];
}

export class IStudentDetail {
    public id: number;
    public associationId: number;
    public classId: number;
    public teamName: string;
    public rollId: string;
    public studentName: string;  
}

export class ISchoolTeamCount{
    public teamName: string;
    public studentCount: number;
    public classId:number;
	public classSectionName: string;
}

