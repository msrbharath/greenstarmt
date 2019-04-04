import { Component, OnInit } from '@angular/core';
import * as html2canvas from 'html2canvas';
import * as jspdf from 'jspdf';
import { PerformanceGenerateStarService } from './performance-star.generate.service';
import { IClassSectionDetail, IPerformanceStarData, ISchoolDetail, ISearchPerformanceStarData, IStudent } from "./performance-star.interface";
import { PerformanceStarService } from './performance-star.service';

@Component({
    selector: 'ngx-performance',
    styleUrls: ['./performance-star.component.scss'],
    templateUrl: './performance-star.component.html',
})
export class PerformanceStarComponent implements OnInit {

    // Enable indicators for search inputs
    public isSchoolViewable = false;
    public isClassViewable = false;
    public isMonthViewable = false;
    public isTeamViewable = false;
    public isNameViewable = false;

    //Value holders for search input used to display the value in star
    public schoolName = "";
    public classAndSectionName = "";
    public teamName = "";
    public studentName = "";
    public month = "";

    //Const month array
    public monthArray: any[] = [{ "id": 0, "value": "--Select--" }, { "id": 1, "value": "Jan" }, { "id": 2, "value": "Feb" }, { "id": 3, "value": "Mar" }, { "id": 4, "value": "Apr" }, { "id": 5, "value": "May" }, { "id": 6, "value": "Jun" }, { "id": 7, "value": "Jul" }, { "id": 8, "value": "Aug" }, { "id": 9, "value": "Sep" }, { "id": 10, "value": "Oct" }, { "id": 11, "value": "Nov" }, { "id": 12, "value": "Dec" }];

    // Loading indicator used on printing pdf and loading star
    public loading = false;

    // loading drop down values on selection
    public loadingDropdown = false;

    //To enable validation error
    public isSearchDataNotValid = false;

    // To disable or enable the star dispaly nb card
    public isDataAvailable = false;
    public isNoPerfData = false;

    // Holds the value to be displayed in dropdown with the information from backend
    public schoolList: ISchoolDetail[];
    public classList: IClassSectionDetail[];
    public studentList: IStudent[];
    public teamList: string[];

    public searchPerformanceStarData: ISearchPerformanceStarData = {} as ISearchPerformanceStarData;

    public performanceStarData: IPerformanceStarData = {} as IPerformanceStarData;

    constructor(private performanceStarService: PerformanceStarService,
        private performanceGenerateStarService: PerformanceGenerateStarService) {
    }

    ngOnInit(): void {
        this.searchPerformanceStarData.calcType = "Select";
        this.searchPerformanceStarData.classId = 0;
        this.searchPerformanceStarData.month = 0;
        this.searchPerformanceStarData.schoolId = 0;
        this.searchPerformanceStarData.studentId = 0;
        this.searchPerformanceStarData.teamName = "Select";

        //Hide the star div
        this.isDataAvailable = false;
        this.isNoPerfData = false;
    }
    /**
     * Method to generate performance start based on the search criteria 
     * */
    public generatePerformanceStar() {
        if (!this.validateSearch()) {
            this.loading = true;
            // Clear the existing data 
            this.performanceStarData = {} as IPerformanceStarData;
            this.performanceGenerateStarService.getPerformanceStar(this.searchPerformanceStarData).subscribe(
                (response) => {
                    this.performanceStarData = response;
                    if (this.performanceStarData.paramOneMonthColorCodes == null) {
                        this.isDataAvailable = false;
                        this.isNoPerfData = true;
                    } else if (this.performanceStarData.paramOneMonthColorCodes.length > 0) {
                        this.isDataAvailable = true;
                        this.isNoPerfData = false;
                    }
                    this.loading = false;
                },
                error => {
                    console.log("Http Server error", error);
                    this.loading = false;
                }
            );
        }
        this.isSearchDataNotValid = this.validateSearch();
    }


    /**
    * Method to print the star for each param on each page, to change the 
    * svg size while printing the svg size is set to the required size and
    * reset back to support responsiveness
    * */
    public printStar() {
        this.loading = true;
        let starSVGS = document.getElementsByClassName("svgClass");
        //console.log("Star Svg's count ==> " + starSVGS.length);
        //console.log("starSVGS[0] ==> " + starSVGS[0]);

        // Set the height and width of the star so that the svg will render properly in pdf
        starSVGS[0].setAttribute("width", "500");
        starSVGS[0].setAttribute("height", "500");
        //Dynamically choose the parent since the second step content won't be enabled on the first rendering
        let paramContent = document.getElementsByName("starDisplayContent")[0];

        //console.log("Content to be printed ==> " + paramContent.id);

        html2canvas(paramContent, { logging: true }).then(canvas => {
            const paramStar = canvas.toDataURL('image/png')
            let pdf = new jspdf('p', 'mm', 'a4');
            pdf.addImage(paramStar, 'PNG', 20, 50, 0, 0);// change x and y coordinates to print star in centre
            pdf.save('PerformanceStar.pdf'); // Generated PDF
            // Reset the height and width of svg to be responsive
            starSVGS[0].setAttribute("width", "100%");
            starSVGS[0].setAttribute("height", "100%");
            this.loading = false;
            // console.log("Star Printed!");
        });
    }

    // Method to enable disable input based on the type selection
    public onChangeCalcType(selectedValue: string) {
        // Reset other dropdowns
        this.searchPerformanceStarData.schoolId = 0;
        this.searchPerformanceStarData.classId = 0;
        this.searchPerformanceStarData.month = 0;
        this.searchPerformanceStarData.studentId = 0;
        this.searchPerformanceStarData.teamName = "Select";
        // Populate school list
        if (this.searchPerformanceStarData.calcType != 'Select') {
            this.loadingDropdown = true;
            this.performanceStarService.getSchools().subscribe(
                (response) => {
                    this.schoolList = response;
                    this.enableSearchComponents(selectedValue);
                    this.loadingDropdown = false;
                },
                error => {
                    console.log("Http Server error", error);
                    this.loadingDropdown = false;
                },

            );
        }
        //Reset error message
        this.isSearchDataNotValid = false;

        //Hide the star div
        this.isDataAvailable = false;
        this.isNoPerfData = false;
    }

    // Method to enable disable input based on the type selection
    public onChangeSchoolChange() {

        this.schoolName = this.getSchoolSelectedValue(this.searchPerformanceStarData.schoolId);
        // Reset other dropdowns
        this.searchPerformanceStarData.classId = 0;
        this.searchPerformanceStarData.month = 0;
        this.searchPerformanceStarData.studentId = 0;
        this.searchPerformanceStarData.teamName = "Select";
        //Reset the dropdown values
        this.classList = [];
        this.studentList = [];
        this.teamList = [];
        // Populate class details
        if (this.searchPerformanceStarData.schoolId != 0) {
            this.loadingDropdown = true;
            let schoolDetail: ISchoolDetail = {} as ISchoolDetail;
            schoolDetail.id = this.searchPerformanceStarData.schoolId;
            this.performanceStarService.getClassList(schoolDetail).subscribe(
                (response) => {
                    this.classList = response;
                    this.loadingDropdown = false;
                },
                error => {
                    console.log("Http Server error", error);
                    this.loadingDropdown = false;
                }
            );
        }
        //Reset error message
        this.isSearchDataNotValid = false;

        //Hide the star div
        this.isDataAvailable = false;
        this.isNoPerfData = false;
    }

    private getSchoolSelectedValue(schoolId: number): string {
        if (schoolId == 0) {
            return "--Select--"
        }
        for (let i = 0; i < this.schoolList.length; i++) {
            if (schoolId == this.schoolList[i].id) {
                return this.schoolList[i].schoolName;
            }
        }
    }

    public onChangeClassChange() {
        this.classAndSectionName = this.getClassSelectedValue(this.searchPerformanceStarData.classId);
        // Reset other dropdowns
        this.searchPerformanceStarData.month = 0;
        this.searchPerformanceStarData.studentId = 0;
        this.searchPerformanceStarData.teamName = "Select";

        // Populate student and team details
        if (this.searchPerformanceStarData.classId != 0) {
            this.loadingDropdown = true;
            let classDetail: IClassSectionDetail = {} as IClassSectionDetail;
            classDetail.id = this.searchPerformanceStarData.classId;
            this.performanceStarService.getClassDetail(classDetail).subscribe(
                (response) => {
                    //Reset the dropdown values
                    this.teamList = [];
                    this.studentList = response.studentList;
                    this.teamList = response.teamList;
                    this.loadingDropdown = false;
                },
                error => {
                    console.log("Http Server error", error);
                    this.loadingDropdown = false;
                }
            );
        }


        //Reset error message
        this.isSearchDataNotValid = false;

        //Hide the star div
        this.isDataAvailable = false;
        this.isNoPerfData = false;
    }

    private getClassSelectedValue(classId: number): string {
        if (classId == 0) {
            return "--Select--"
        }
        for (let i = 0; i < this.classList.length; i++) {
            if (classId == this.classList[i].id) {
                return this.classList[i].classAndSectionName;
            }
        }
    }

    public onChangeStudentChange() {
        this.studentName = this.getStudentSelectedValue(this.searchPerformanceStarData.studentId);
        // Reset other dropdowns
        this.searchPerformanceStarData.month = 0;
        this.searchPerformanceStarData.teamName = "Select";
        //Reset error message
        this.isSearchDataNotValid = false;

        //Hide the star div
        this.isDataAvailable = false;
        this.isNoPerfData = false;
    }

    private getStudentSelectedValue(studentId: number): string {
        if (studentId == 0) {
            return "--Select--"
        }
        for (let i = 0; i < this.studentList.length; i++) {
            if (studentId == this.studentList[i].id) {
                return this.studentList[i].studentName;
            }
        }
    }

    public onChangeTeamChange() {
        this.teamName = this.searchPerformanceStarData.teamName;
        // Reset other dropdowns
        this.searchPerformanceStarData.month = 0;
        this.searchPerformanceStarData.studentId = 0;
        //Reset error message
        this.isSearchDataNotValid = false;

        //Hide the star div
        this.isDataAvailable = false;
        this.isNoPerfData = false;
    }

    public onChangeMonthChange() {
        this.month = this.monthArray[this.searchPerformanceStarData.month].value;
        //Reset error message
        this.isSearchDataNotValid = false;

        //Hide the star div
        this.isDataAvailable = false;
        this.isNoPerfData = false;
    }

    private enableSearchComponents(selectedValue: string) {
        if (selectedValue == "Individual") {
            this.isSchoolViewable = true;
            this.isClassViewable = true;
            this.isMonthViewable = true;
            this.isNameViewable = true;
            this.isTeamViewable = false;
        } else if (selectedValue == "Team") {
            this.isSchoolViewable = true;
            this.isClassViewable = true;
            this.isMonthViewable = true;
            this.isNameViewable = false;
            this.isTeamViewable = true;
        } else if (selectedValue == "Class") {
            this.isSchoolViewable = true;
            this.isClassViewable = true;
            this.isMonthViewable = true;
            this.isNameViewable = false;
            this.isTeamViewable = false;
        } else if (selectedValue == "School") {
            this.isSchoolViewable = true;
            this.isMonthViewable = true;
            this.isClassViewable = false;
            this.isNameViewable = false;
            this.isTeamViewable = false;
        }
        //Reset the dropdown values
        this.searchPerformanceStarData.classId = 0;
        this.searchPerformanceStarData.month = 0;
        this.searchPerformanceStarData.schoolId = 0;
        this.searchPerformanceStarData.studentId = 0;
        this.searchPerformanceStarData.teamName = "Select";

        //Reset the dropdown values
        this.classList = [];
        this.studentList = [];
        this.teamList = [];
    }

    private validateSearch(): boolean {
        let isSearchNotValid = false;
        if (this.searchPerformanceStarData.calcType == "Individual") {
            if (this.searchPerformanceStarData.schoolId == 0 ||
                this.searchPerformanceStarData.classId == 0 ||
                this.searchPerformanceStarData.studentId == 0 ||
                this.searchPerformanceStarData.month == 0) {
                isSearchNotValid = true;
            }
        } else if (this.searchPerformanceStarData.calcType == "Team") {
            if (this.searchPerformanceStarData.schoolId == 0 ||
                this.searchPerformanceStarData.classId == 0 ||
                this.searchPerformanceStarData.month == 0 ||
                this.searchPerformanceStarData.teamName == "Select") {
                isSearchNotValid = true;
            }
        } else if (this.searchPerformanceStarData.calcType == "Class") {
            if (this.searchPerformanceStarData.schoolId == 0 ||
                this.searchPerformanceStarData.classId == 0 ||
                this.searchPerformanceStarData.month == 0) {
                isSearchNotValid = true;
            }
        } else if (this.searchPerformanceStarData.calcType == "School") {
            if (this.searchPerformanceStarData.schoolId == 0 ||
                this.searchPerformanceStarData.month == 0) {
                isSearchNotValid = true;
            }
        } else if (this.searchPerformanceStarData.calcType == "Select") {
            isSearchNotValid = true;
        }
        return isSearchNotValid;
    }
}
