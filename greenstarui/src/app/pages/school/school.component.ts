import { Component, OnInit } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LocalDataSource } from 'ng2-smart-table';
import { IState } from '../common/common.interface';
import { CommonService } from '../common/common.service';
import { SchoolMessageModalComponent } from './school-message.modal.component';
import { SchoolData } from './school.data';
import { ISchoolDetail } from './school.interface';
import { SchoolService } from './school.service';

@Component({
  selector: 'nb-dialog',
  styleUrls: ['./school.component.scss'],
  templateUrl: './school.component.html'
})
export class SchoolComponent implements OnInit {

  public title: string;
  public action: string
  public schoolId: number;

  public schoolDetail: ISchoolDetail = new ISchoolDetail();

  public stateList: IState[];
  public districtList: string[];

  // class table setting
  public classDetail: LocalDataSource = new LocalDataSource();
  public classTableSetting: any;

  // performance param table setting
  public perfParamDynamicDetail: LocalDataSource = new LocalDataSource();
  public perfParamDynamicSetting: any;

  // school holiday table setting
  public schoolHolidayDetail: LocalDataSource = new LocalDataSource();
  public schoolHolidaySetting: any;

  // weekend working day table setting
  public schoolWeekendWorkDetail: LocalDataSource = new LocalDataSource();
  public schoolWeekendWorkSetting: any = SchoolData.getSchoolWeekendWorkingSetting(this.action);

  constructor(private commonService: CommonService,
    public activeModal: NgbActiveModal,
    private modalService: NgbModal,
    private schoolService: SchoolService) {
  }

  ngOnInit(): void {
    this.loadTableSettings();
    if (this.action === 'create') {
      this.schoolDetail = SchoolData.createSchoolDetailObject();
      this.perfParamDynamicDetail.load(this.schoolDetail.perfParamList);
    } else if (this.action === 'edit' || this.action === 'view') {
      // retrieve and load school data
      this.retrieveSchool();
    }
  }

  private loadTableSettings() {
    this.classTableSetting = SchoolData.getClassTableSetting(this.action);
    this.perfParamDynamicSetting = SchoolData.getPerfParamTableSetting(this.action);
    this.schoolHolidaySetting = SchoolData.getSchoolHolidaySetting(this.action);
    this.schoolWeekendWorkSetting = SchoolData.getSchoolWeekendWorkingSetting(this.action);
  }

  private retrieveSchool() {
    this.schoolService.retrieveSchool(this.schoolId).subscribe(
      (response) => {
        this.schoolDetail = response;
        // Load datasource with the data from server
        if (this.schoolDetail.classList == null) {
          this.classDetail.load([]);
        } else {
          this.classDetail.load(this.schoolDetail.classList);
        }
        this.perfParamDynamicDetail.load(this.schoolDetail.perfParamList);

        if (this.schoolDetail.holidays == null) {
          this.schoolHolidayDetail.load([]);
        } else {
          this.schoolHolidayDetail.load(this.schoolDetail.holidays);
        }

        if (this.schoolDetail.weekendWorkingDays == null) {
          this.schoolWeekendWorkDetail.load([]);
        } else {
          this.schoolWeekendWorkDetail.load(this.schoolDetail.weekendWorkingDays);
        }
        //Load district list
        this.onStateChange();
      },
      error => {
        this.openModal('Error Occured', "Error occured while retrieving school");
        console.log("Http Server error", error);
      }
    );
  }

  // On change of state set corresponding district to the district dropdown
  public onStateChange() {
    if (this.schoolDetail.state == '--Select State--') {
      this.districtList = [];
    } else {
      this.stateList.forEach((state) => {
        if (state.stateName == this.schoolDetail.state) {
          this.districtList = state.districts;
        }
      });
    }
  }
  public onChangeTab(event) { }

  public onClassAdd(event): void {
    if (this.schoolDetail.classList == null || this.schoolDetail.classList.length == 0) {
      this.setClassList(event);
    } else {
      let i = 1;
      this.schoolDetail.classList.forEach((clazzDetail) => {
        // If school and section already exist then no need to add 
        if (clazzDetail.className == event.newData.className &&
          clazzDetail.sectionName == event.newData.sectionName) {
          this.openModal('Validation Message', 'Already class available with same name and section');
          event.confirm.reject();
          return;
        }
        if (this.schoolDetail.classList.length == i) {
          this.setClassList(event);
        }
        i++;
      });
    }
  }

  private setClassList(event): void {
    // If any of the feilds are left blank 
    if (event.newData.className == null || event.newData.className == '' ||
      event.newData.sectionName == null || event.newData.sectionName == '') {
      this.openModal('Validation Message', 'Both the fieilds are mandatory to add a class!');
      event.confirm.reject();
    } else {
      this.schoolDetail.classList = event.source.data;
      event.confirm.resolve();
    }
  }

  public onClassEdit(event): void {
    // If any of the feilds are left blank 
    if (event.newData.className == null || event.newData.className == '' ||
      event.newData.sectionName == null || event.newData.sectionName == '') {
      this.openModal('Validation Message', 'Both the fieilds are mandatory to edit a class!');
      event.confirm.reject();
    } else {
      this.schoolDetail.classList = event.source.data;
      event.confirm.resolve();
    }
  }

  public onClassDeleteConfirm(event): void {
    // Only can delete the newly added class. Exisitng class cannot be deleted due to data loss
    if (event.data.id == undefined) {
      if (window.confirm('Are you sure you want to delete class?')) {
        this.schoolDetail.classList = event.source.data;
        //remove un saved class, persisted class won't allow to delete
        for (let i = 0; i < this.schoolDetail.classList.length; i++) {
          let clazz = this.schoolDetail.classList[i];
          if (clazz.className === event.data.className
            && clazz.sectionName === event.data.sectionName) {
            this.schoolDetail.classList.splice(i, 1);
          }
        }
        event.confirm.resolve();
      } else {
        event.confirm.reject();
      }
    } else {
      this.openModal('Message', 'Existing class in DB cannot be deleted it will erase entire performance data for the class. You can rename it!');
      event.confirm.reject();
    }
  }

  public onParameterEdit(event): void {
    // If any of the feilds are left blank 
    if (event.newData.paramTitle == null || event.newData.paramTitle == '' ||
      event.newData.paramDesc == null || event.newData.paramDesc == '') {
      this.openModal('Validation Message', 'All fieilds are mandatory to add a performance parameter!');
      event.confirm.reject();
    } else {
      this.schoolDetail.perfParamList = event.source.data;
      event.confirm.resolve();
    }
  }

  public onCreateForHoliday(event): void {
    // If any of the feilds are left blank 
    if (event.newData.fromDate == null || event.newData.fromDate == '' ||
      event.newData.toDate == null || event.newData.toDate == '' ||
      event.newData.description == null || event.newData.description == '') {
      this.openModal('Validation Message', 'All fieilds are mandatory to add a holiday!');
      event.confirm.reject();
    } else {
      this.schoolDetail.holidays = event.source.data;
      event.confirm.resolve();
    }
  }

  public onEditForHoliday(event): void {
    this.schoolDetail.holidays = event.source.data;
    event.confirm.resolve();
  }

  public onDeleteForHoliday(event): void {
    if (window.confirm('Are you sure you want to delete?')) {
      this.schoolDetail.holidays = event.source.data;
      //remove the holiday
      for (let i = 0; i < this.schoolDetail.holidays.length; i++) {
        let holiday = this.schoolDetail.holidays[i];
        if (holiday.id === event.data.id
          && holiday.fromDate === event.data.fromDate
          && holiday.toDate === event.data.toDate &&
          holiday.description === event.data.description) {
          this.schoolDetail.holidays.splice(i, 1);
        }
      }
      event.confirm.resolve();
    } else {
      event.confirm.reject();
    }
  }

  public onEditForWeekendWorking(event): void {
    this.schoolDetail.weekendWorkingDays = event.source.data;
    event.confirm.resolve();
  }

  public onCreateForWeekendWorking(event): void {
    // If any of the feilds are left blank 
    if (event.newData.workingDate == null || event.newData.workingDate == '' ||
      event.newData.reason == null || event.newData.reason == '') {
      this.openModal('Validation Message', 'Both the fieilds are mandatory to add a weekend working day!');
      event.confirm.reject();
    } else {
      this.schoolDetail.weekendWorkingDays = event.source.data;
      event.confirm.resolve();
    }
  }

  public onDeleteForWeekendWorking(event): void {
    if (window.confirm('Are you sure you want to delete?')) {
      this.schoolDetail.weekendWorkingDays = event.source.data;
      for (let i = 0; i < this.schoolDetail.weekendWorkingDays.length; i++) {
        let workingDay = this.schoolDetail.weekendWorkingDays[i];
        if (workingDay.id === event.data.id
          && workingDay.reason === event.data.reason
          && workingDay.workingDate === event.data.workingDate) {
          this.schoolDetail.weekendWorkingDays.splice(i, 1);
        }
      }
      event.confirm.resolve();
    } else {
      event.confirm.reject();
    }
  }

  public onSubmitChanges(): void {
    let errorMessage = this.validateSubmit();
    if (errorMessage != '') {
      this.openModal('Validation Error', errorMessage);
    } else {
      this.schoolDetail.userId = localStorage.getItem('userId');
      this.schoolDetail.action = this.action;
      this.schoolService.submitSchool(this.schoolDetail).subscribe(
        (response) => {
          if (this.action == 'create') {
            this.openModal('Message', 'School information saved successfully');
          } else {
            //Edit flow
            this.openModal('Message', 'School information updated successfully');
          }
          this.schoolDetail = response;
        },
        error => {
          if (this.action == 'create') {
            this.openModal('Error Occured', "Error occured while saving school");
          } else {
            //Edit flow
            this.openModal('Error Occured', "Error occured while updating school");
          }
          console.log("Http Server error", error);
        }
      );
    }
  }

  closeModal() {
    this.activeModal.close();
  }

  private openModal(modalheadertext, modalmessage) {
    const modalRef = this.modalService.open(SchoolMessageModalComponent);
    modalRef.componentInstance.modalmessage = modalmessage;
    modalRef.componentInstance.modalheadertext = modalheadertext;
  }

  private validateSubmit() {
    let errorField: string[] = [];
    let errorString = '';
    if (this.schoolDetail.cityName == null || this.schoolDetail.cityName == '') {
      errorField.push("cityName");
    }
    if (this.schoolDetail.schoolName == null || this.schoolDetail.schoolName == '') {
      errorField.push("schoolName");
    }
    if (this.schoolDetail.district == null || this.schoolDetail.district == '--Select District--') {
      errorField.push("district");
    }
    if (this.schoolDetail.state == null || this.schoolDetail.state == '--Select State--') {
      errorField.push("state");
    }
    if (this.schoolDetail.address == null || this.schoolDetail.address == '') {
      errorField.push("address");
    }
    if (this.schoolDetail.classList.length == 0) {
      errorField.push("Class");
    }
    if (this.schoolDetail.holidays.length == 0) {
      errorField.push("Holidays");
    }
    if (errorField.length > 0) {
      errorString = "Please provide data for "
      errorField.forEach((errormsg) => {
        errorString = errorString + errormsg + ",";
      });
    }
    return errorString;
  }

}
