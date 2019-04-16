import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LocalDataSource } from 'ng2-smart-table';
import { ValidatorUtil } from '../util/validator-util';
import { AdminModalComponent } from './admin.component.modal';
import { AdminData } from './admin.data';
import { IAdminDetail } from './admin.interface';
import { AdminService } from './admin.service';
import { StudentService } from '../student/student.service';
import {ISchoolDetail} from '../student/student.interface';

@Component({
  selector: 'ngx-dashboard',
  templateUrl: './admin.component.html',
})
export class AdminComponent implements OnInit {

  // user role table setting
  public userRoleDetail: LocalDataSource = new LocalDataSource();
  public userRoleTableSetting: any = AdminData.getUserRoleMappingTableSetting();
  public isSpinner: boolean = false;
  public schoolList: ISchoolDetail[]=[];

  constructor(
    private modalService: NgbModal,
    private adminService: AdminService,
    private studentService: StudentService) {
  }

  ngOnInit(): void {
    this.loadUserRoleMappings();
    this.loadSchoolList();
  }

  private loadSchoolList() {
    this.studentService.getSchools().subscribe(
      (response) => {
        this.schoolList = response;
        localStorage.setItem('schools',JSON.stringify(this.schoolList));
      },
      error => {
        console.log("Http Server error", error);
      },
    );
  }

  private loadUserRoleMappings(): void {
    this.adminService.getUserRoleMappings().subscribe(
      (response) => {
        this.userRoleDetail.load(response);
        this.isSpinner = false;
      },
      error => {
        console.log("Http Server error", error);
        this.isSpinner = false;
      }
    );
  }

  private validateUserRole(event: any): Boolean {
    let isValid: boolean = true;
    if (ValidatorUtil.isEmpty(event.newData.userId) || ValidatorUtil.isEmpty(event.newData.roleName)) {
      isValid = false;
    }
    return isValid;
  }

  public onPostCallForUserRoleMap(event: any): void {

    let isValid = this.validateUserRole(event);

    if (isValid) {

      let adminDetail: IAdminDetail = {} as IAdminDetail;
      adminDetail.loggedUserId = localStorage.getItem('userId');

      if (typeof event.data === 'undefined' || event.data === null) {
        adminDetail.userId = event.newData.userId;
        adminDetail.roleName = event.newData.roleName;

        this.isSpinner = true;
        this.adminService.saveUserRoleMapping(adminDetail).subscribe(
          (response) => {
            this.isSpinner = false;
            if ('SUCCESS' === response) {
              event.confirm.resolve();
              const activeModal = this.modalService.open(AdminModalComponent, { size: 'sm', container: 'nb-layout' });
              activeModal.componentInstance.modalContent = 'User Role Mapping saved successfully!..';
            } else {
              event.confirm.reject();
              const activeModal = this.modalService.open(AdminModalComponent, { size: 'sm', container: 'nb-layout' });
              activeModal.componentInstance.modalContent = 'User Role Mapping already exist!..';
            }
            this.loadUserRoleMappings();
          },
          error => {
            console.log("Http Server error", error);
            event.confirm.reject();
            this.isSpinner = false;
          }
        );
      } else {
        adminDetail.id = event.newData.id;
        adminDetail.userId = event.newData.userId;
        adminDetail.roleName = event.newData.roleName;

        this.isSpinner = true;
        this.adminService.updateUserRoleMapping(adminDetail).subscribe(
          (response) => {
            this.isSpinner = false;
            if ('SUCCESS' === response) {
              event.confirm.resolve();
              const activeModal = this.modalService.open(AdminModalComponent, { size: 'sm', container: 'nb-layout' });
              activeModal.componentInstance.modalContent = 'User Role Mapping updated successfully!..';
            } else {
              event.confirm.reject();
              const activeModal = this.modalService.open(AdminModalComponent, { size: 'sm', container: 'nb-layout' });
              activeModal.componentInstance.modalContent = 'User Role Mapping already exist!..';
            }
            this.loadUserRoleMappings();
          },
          error => {
            console.log("Http Server error", error);
            event.confirm.reject();
            this.isSpinner = false;
          }
        );
      }
    } else {
      const activeModal = this.modalService.open(AdminModalComponent, { size: 'sm', container: 'nb-layout' });
      activeModal.componentInstance.modalContent = 'Please enter user id and select role group';
    }
  }

  public onDeleteConfirmForUserRoleMap(event): void {
    if (window.confirm('Are you sure you want to delete?')) {
      let adminDetail: IAdminDetail = {} as IAdminDetail;
      adminDetail.loggedUserId = localStorage.getItem('userId');
      adminDetail.id = event.data.id;
      adminDetail.userId = event.data.userId;
      adminDetail.roleName = event.data.roleName;

      this.isSpinner = true;
      this.adminService.deleteUserRoleMapping(adminDetail).subscribe(
        (response) => {
          this.isSpinner = false;
          if ('SUCCESS' === response) {
            event.confirm.resolve();
            const activeModal = this.modalService.open(AdminModalComponent, { size: 'sm', container: 'nb-layout' });
            activeModal.componentInstance.modalContent = 'User Role Mapping deleted successfully!..';
          }
          this.loadUserRoleMappings();
        },
        error => {
          console.log("Http Server error", error);
          event.confirm.reject();
          this.isSpinner = false;
        }
      );
      event.confirm.resolve();
    } else {
      event.confirm.reject();
    }
  }

}
