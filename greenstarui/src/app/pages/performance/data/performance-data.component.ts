import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { saveAs as tempSaveAs } from 'file-saver';
import { ValidatorUtil } from '../../util/validator-util';
import { IClassSectionDetail, ISchoolDetail } from '../star/performance-star.interface';
import { PerformanceStarService } from '../star/performance-star.service';
import { PerformanceDataSuccessModalComponent } from './performance-data-success.component.modal';
import { PerformanceDataUploadModalComponent } from './performance-data-upload.component.modal';
import { IPerformanceData, IPerformanceDataTable, IPerformanceHeader, ISearchPerformanceData } from './performance-data.interface';
import { PerformanceDataService } from './performance-data.service';

@Component({
    selector: 'ngx-performance',
    styleUrls: ['./performance-data.component.scss'],
    templateUrl: './performance-data.component.html',
})
export class PerformanceDataComponent implements OnInit {

    public performanceSource: IPerformanceDataTable = {} as IPerformanceDataTable;

    public isShowPerformanceMetricTable: boolean = false;
    public isPerformanceChkboxEnabled: boolean = false;
    public isPerformanceEditEnabled: boolean = false;
    public isPerformanceAddEnabled: boolean = false;
    public isSearchDataNotValid: boolean = false;
    public isSpinner: boolean = false;
    public action: string = 'update';
    public searchDataErrorMsg: string = '';

    public schoolList: ISchoolDetail[];
    public classList: IClassSectionDetail[];
    public weekDays = new Map<String, String>();

    public perfDataForm: FormGroup;

    constructor(
        private modalService: NgbModal,
        private formBuilder: FormBuilder,
        private performanceDataService: PerformanceDataService,
        private performanceStarService: PerformanceStarService) {
    }

    ngOnInit(): void {
        this.initializeForm();
        this.loadSchoolDetails();
    }

    private initializeForm(): void {
        this.perfDataForm = this.formBuilder.group({
            schoolId: ['', Validators.required],
            classId: ['', Validators.required],
            month: ['', Validators.required],
            week: ['', Validators.required]
        });
        this.performanceSource = {} as IPerformanceDataTable;
        this.isShowPerformanceMetricTable = false;
        this.isPerformanceChkboxEnabled = false;
        this.isPerformanceEditEnabled = false;
        this.isPerformanceAddEnabled = false;
    }

    public resetPerformanceSearch(): void {
        this.initializeForm();
        this.isSearchDataNotValid = false;
        this.searchDataErrorMsg = '';
    }

    public loadExistingPerformanceData(searchPerformanceData: ISearchPerformanceData): void {
        this.isSpinner = true;
        this.performanceDataService.getExistingPerformanceMetricDatas(searchPerformanceData).subscribe(
            (response) => {
                this.isSpinner = false;
                this.performanceSource = response.result;
                if (this.performanceSource != null) {
                    this.isShowPerformanceMetricTable = true;
                    this.isPerformanceEditEnabled = true;
                    this.isPerformanceChkboxEnabled = false;
                    this.isPerformanceAddEnabled = false;
                    if (this.performanceSource.performanceRows.length <= 0) {
                        this.isPerformanceAddEnabled = true;
                        this.isPerformanceChkboxEnabled = false;
                        this.isPerformanceEditEnabled = false;
                    }
                } else {
                    this.isShowPerformanceMetricTable = false;
                    this.isPerformanceChkboxEnabled = false;
                    this.isPerformanceEditEnabled = false;
                    this.isPerformanceAddEnabled = false;
                }
            },
            error => {
                this.isSpinner = false;
                console.log("Http Server error", error);
            }
        );
    }

    public loadCreatePerformanceData(searchPerformanceData: ISearchPerformanceData): void {
        this.isSpinner = true;
        this.performanceDataService.getCreatePerformanceMetricDatas(searchPerformanceData).subscribe(
            (response) => {
                this.isSpinner = false;
                this.performanceSource = response.result;
                if (this.performanceSource != null) {
                    this.isShowPerformanceMetricTable = true;
                    this.isPerformanceEditEnabled = true;
                    this.isPerformanceChkboxEnabled = false;
                    this.isPerformanceAddEnabled = false;
                    if (this.performanceSource.performanceRows.length <= 0) {
                        this.isPerformanceAddEnabled = true;
                        this.isPerformanceChkboxEnabled = false;
                        this.isPerformanceEditEnabled = false;
                    }
                } else {
                    this.isShowPerformanceMetricTable = false;
                    this.isPerformanceChkboxEnabled = false;
                    this.isPerformanceEditEnabled = false;
                    this.isPerformanceAddEnabled = false;
                }
            },
            error => {
                this.isSpinner = false;
                console.log("Http Server error", error);
            }
        );
    }

    private loadSchoolDetails(): void {
        this.isSpinner = true;
        this.performanceStarService.getSchools().subscribe(
            (response) => {
                this.schoolList = response;
                this.isSpinner = false;
            },
            error => {
                console.log("Http Server error", error);
                this.isSpinner = false;
            },

        );
    }

    public loadClassDetailsBySchool(): void {
        if (this.perfDataForm.getRawValue().schoolId != 0) {
            this.isSpinner = true;
            let schoolDetail: ISchoolDetail = {} as ISchoolDetail;
            schoolDetail.id = this.perfDataForm.getRawValue().schoolId;
            this.performanceStarService.getClassList(schoolDetail).subscribe(
                (response) => {
                    this.classList = response;
                    this.isSpinner = false;
                },
                error => {
                    console.log("Http Server error", error);
                    this.isSpinner = false;
                }
            );
        }
    }

    public populateWeekWorkingDays(): void {

        this.isSearchDataNotValid = false;
        this.searchDataErrorMsg = '';

        if (!ValidatorUtil.isEmpty(this.perfDataForm.getRawValue().schoolId)
            && !ValidatorUtil.isEmpty(this.perfDataForm.getRawValue().classId)
            && !ValidatorUtil.isEmpty(this.perfDataForm.getRawValue().month)) {

            this.isSpinner = true;
            let searchPerformanceData: ISearchPerformanceData = this.getSearchParamObject();

            this.performanceDataService.getWeekDaysByMonth(searchPerformanceData).subscribe(
                (response) => {
                    this.weekDays = response.result;
                    this.isSpinner = false;
                },
                error => {
                    console.log("Http Server error", error);
                    this.isSpinner = false;
                }
            );
        } else {
            ValidatorUtil.validateAllFormFields(this.perfDataForm);
            this.isSearchDataNotValid = true;
            this.searchDataErrorMsg = 'All fields are mandatory!';
            this.weekDays = new Map<String, String>();
        }
    }

    public openBulkUploadMmodal(): void {
        const activeModal = this.modalService.open(PerformanceDataUploadModalComponent, { size: 'lg', container: 'nb-layout' });
    }

    public downloadTemplate(): void {
        this.isSearchDataNotValid = false;
        this.searchDataErrorMsg = '';
        if (!ValidatorUtil.isEmpty(this.perfDataForm.getRawValue().schoolId)
            && !ValidatorUtil.isEmpty(this.perfDataForm.getRawValue().classId)
            && !ValidatorUtil.isEmpty(this.perfDataForm.getRawValue().month)) {

            let searchPerformanceData: ISearchPerformanceData = this.getSearchParamObject();

            let fileName = searchPerformanceData.schoolName
                + '_' + searchPerformanceData.className
                + '_' + searchPerformanceData.month
                + '.xlsx';

            this.isSpinner = true;
            this.performanceDataService.getPerformanceDataTemplate(searchPerformanceData).subscribe(
                (response) => {
                    this.isSpinner = false;
                    var blob = new Blob([response], { type: 'application/octet-stream' });
                    tempSaveAs(blob, fileName);
                },
                error => {
                    this.isSpinner = false;
                    console.log("Http Server error", error);
                }
            );
        } else {
            ValidatorUtil.validateAllFormFields(this.perfDataForm);
            this.isSearchDataNotValid = true;
            this.searchDataErrorMsg = 'Please select school, class and month';
        }
    }

    public searchPerformanceData(): void {
        this.isSearchDataNotValid = false;
        this.searchDataErrorMsg = '';
        if (this.perfDataForm.valid) {
            let searchPerformanceData: ISearchPerformanceData = this.getSearchParamObject();
            this.loadExistingPerformanceData(searchPerformanceData);
        } else {
            ValidatorUtil.validateAllFormFields(this.perfDataForm);
            this.isSearchDataNotValid = true;
            this.searchDataErrorMsg = 'All fields are mandatory!';
        }
    }

    public addPerformanceData(): void {
        this.action = 'create';
        this.isSearchDataNotValid = false;
        this.searchDataErrorMsg = '';
        if (this.perfDataForm.valid) {
            let searchPerformanceData: ISearchPerformanceData = this.getSearchParamObject();
            this.loadCreatePerformanceData(searchPerformanceData);
        } else {
            ValidatorUtil.validateAllFormFields(this.perfDataForm);
            this.isSearchDataNotValid = true;
            this.searchDataErrorMsg = 'All fields are mandatory!';
        }
    }

    public editPerformanceData(): void {
        this.isPerformanceChkboxEnabled = true;
    }

    public submitPerformanceData(): void {

        this.performanceSource.userId = localStorage.getItem('userId');

        if (this.action === 'create') {
            this.isSpinner = true;
            this.performanceDataService.savePerformanceMetricDatas(this.performanceSource).subscribe(
                (response) => {
                    this.isSpinner = false;
                    if (response.status === 200 && response.message === 'SUCCESS') {
                        const activeModal = this.modalService.open(PerformanceDataSuccessModalComponent, { size: 'sm', container: 'nb-layout' });
                        activeModal.componentInstance.modalContent = 'Performance Meatric data saved successfully!..';
                    }
                },
                error => {
                    this.isSpinner = false;
                    console.log("Http Server error", error);
                }
            );
        } else {
            this.isSpinner = true;
            this.performanceDataService.updatePerformanceMetricDatas(this.performanceSource).subscribe(
                (response) => {
                    this.isSpinner = false;
                    if (response.status === 200 && response.message === 'SUCCESS') {
                        const activeModal = this.modalService.open(PerformanceDataSuccessModalComponent, { size: 'sm', container: 'nb-layout' });
                        activeModal.componentInstance.modalContent = 'Performance Meatric data saved successfully!..';
                    }
                },
                error => {
                    this.isSpinner = false;
                    console.log("Http Server error", error);
                }
            );
        }
        this.action = 'update';
        this.isPerformanceChkboxEnabled = false;
        this.isPerformanceAddEnabled = false;
    }

    public isFieldValid(field: string): boolean {
        this.isSearchDataNotValid = false;
        return ValidatorUtil.isFieldValid(this.perfDataForm, field);
    }

    public displayFieldCss(field: string): Object {
        return ValidatorUtil.displayFieldCss(this.perfDataForm, field);
    }

    public checkCellPerformanceDataStatus(checkValue: boolean, performanceData: IPerformanceData): void {
        performanceData.value = checkValue;
    }

    public checkPerformanceParamWise(checkValue: boolean, headerObj: IPerformanceHeader, subTitle: IPerformanceHeader): void {
        this.performanceSource.performanceRows.forEach(
            (performanceRow) => {
                performanceRow.performanceDays.forEach(
                    (performanceDay) => {
                        if (performanceDay.dateValue === headerObj.title) {
                            performanceDay.performanceData.forEach(
                                (performanceData) => {
                                    if (performanceData.key === subTitle.title) {
                                        performanceData.value = checkValue;
                                    }
                                });
                        }
                    });
            });
    }

    public checkPerformanceDayWise(checkValue: boolean, headerObj: IPerformanceHeader): void {
        this.performanceSource.performanceRows.forEach(
            (performanceRow) => {
                performanceRow.performanceDays.forEach(
                    (performanceDay) => {
                        if (performanceDay.dateValue === headerObj.title) {
                            performanceDay.performanceData.forEach(
                                (performanceData) => {
                                    performanceData.value = checkValue;
                                });
                        }
                    });
            });

        // check header checkbox
        this.performanceSource.headers.forEach(
            (performanceHeader) => {
                if (performanceHeader.title === headerObj.title) {
                    performanceHeader.subTitleList.forEach(
                        (performanceHeader) => {
                            //if (performanceHeader.title === headerObj.title) {
                            performanceHeader.checkValue = checkValue;
                            //}
                        });
                }
            });
    }

    public checkAllPerformance(checkValue: boolean): void {

        // check all performance parameters
        this.performanceSource.performanceRows.forEach(
            (performanceRow) => {
                performanceRow.performanceDays.forEach(
                    (performanceDay) => {
                        performanceDay.performanceData.forEach(
                            (performanceData) => {
                                performanceData.value = checkValue;
                            });
                    });
            });

        // check header checkbox
        this.performanceSource.headers.forEach(
            (performanceHeader) => {
                performanceHeader.checkValue = checkValue;
                performanceHeader.subTitleList.forEach(
                    (performanceHeader) => {
                        performanceHeader.checkValue = checkValue;
                    });
            });
    }

    private getSearchParamObject(): ISearchPerformanceData {

        let searchPerformanceData: ISearchPerformanceData = {} as ISearchPerformanceData;
        searchPerformanceData.schoolId = this.perfDataForm.getRawValue().schoolId;
        searchPerformanceData.classId = this.perfDataForm.getRawValue().classId;
        searchPerformanceData.month = this.perfDataForm.getRawValue().month;
        searchPerformanceData.week = this.perfDataForm.getRawValue().week;

        searchPerformanceData.schoolName = this.getSchoolName(this.perfDataForm.getRawValue().schoolId);
        searchPerformanceData.className = this.getClassName(this.perfDataForm.getRawValue().classId);

        return searchPerformanceData;
    }

    private getSchoolName(schoolId: number): string {

        let schooName = '';
        this.schoolList.forEach(school => {
            if (school.id == schoolId) {
                schooName = school.schoolName;
            }
        });
        return schooName;
    }

    private getClassName(classId: number): string {

        let className = '';
        this.classList.forEach(classz => {
            if (classz.id == classId) {
                className = classz.className;
            }
        });
        return className;
    }

}
