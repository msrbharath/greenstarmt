import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { saveAs as tempSaveAs } from 'file-saver';
import { LocalDataSource } from 'ng2-smart-table';
import { RoleService } from '../common/role.service';
import { SchoolMessageModalComponent } from '../school/school-message.modal.component';
import { StudentBulkUploadModalComponent } from './student-bulk-upload.component.modal';
import { IClassSectionDetail, ISchoolDetail, ISchoolTeamCount, IStudentSearchData } from './student.interface';
import { StudentService } from './student.service';

@Component({
  selector: 'ngx-student',
  styleUrls: ['./student.component.scss'],
  templateUrl: './student-list.component.html',
})
export class StudentListComponent implements OnInit {

  public schoolList: ISchoolDetail[];

  public isSearch: boolean;
  public isBulkUploadRequestNotValid: boolean = false;

  public studentSearchData: IStudentSearchData = new IStudentSearchData();
  // Contains list of class to display in dropdown
  public classSectionList: IClassSectionDetail[] = [];
  // Contains all details of class
  public classSectionDetail: IClassSectionDetail = new IClassSectionDetail();

  public loadingDropdown: boolean = false;
  public loadingStudents: boolean = false;
  public isSearchDataNotValid: boolean = false;
  public isExportNotValid: boolean = false;

  // class table setting
  public studentSource: LocalDataSource = new LocalDataSource();
  public tableSetting: any;

  // Team table setting
  public teamNameTableSource: LocalDataSource = new LocalDataSource();
  public teamNameTableSetting: any = this.teamTableSettings();

  constructor(
    private modalService: NgbModal,
    private studentService: StudentService,
    public roleService: RoleService) {
  }

  ngOnInit(): void {

    if (this.roleService.isGrantedRole(['Admin', 'PMO'])) {
      this.tableSetting = this.studentTableSetting();
    } else {
      this.tableSetting = this.studentTableSettingRestricted();
    }

    this.loadSchoolDropDown();
    this.studentSearchData.schoolId = 0;
    this.studentSearchData.classId = 0;
    this.isSearch = false;
    this.studentSource.load(this.classSectionDetail.studentList);
  }

  private loadSchoolDropDown() {
    this.studentService.getSchools().subscribe(
      (response) => {
        this.schoolList = response;
      },
      error => {
        console.log("Http Server error", error);
      },
    );
  }

  public onChangeClass() {
    this.isSearchDataNotValid = false;
    this.isSearch = false;
  }

  public onChangeSchoolChange() {
    this.isSearchDataNotValid = false;
    this.isBulkUploadRequestNotValid = false;
    this.isExportNotValid = false;
    this.isSearch = false;
    if (this.studentSearchData.schoolId == 0) {
      this.classSectionList = [];
      this.studentSearchData.classId = 0;
    } else {
      this.loadingDropdown = true;
      let schoolDetail: ISchoolDetail = new ISchoolDetail();
      schoolDetail.id = this.studentSearchData.schoolId;
      this.studentService.getClassList(schoolDetail).subscribe(
        (response) => {
          this.classSectionList = response;
          this.loadingDropdown = false;
        },
        error => {
          console.log("Http Server error", error);
        },
      );
    }
  }

  // On click of class edit
  public onClassEdit() {
    if (this.studentSearchData.schoolId == 0 ||
      this.studentSearchData.classId == 0) {
      this.isSearchDataNotValid = true;
    } else {
      this.loadingStudents = true;
      let classDetail: IClassSectionDetail = new IClassSectionDetail();
      classDetail.id = this.studentSearchData.classId;
      classDetail.schoolId = this.studentSearchData.schoolId;
      this.studentService.getClassDetail(classDetail).subscribe(
        (response) => {
          this.isSearch = true;
          this.classSectionDetail = response;
          this.studentSource.load(this.classSectionDetail.studentList);
          this.teamNameTableSource.load(this.classSectionDetail.schoolTeamList);
          this.loadingStudents = false;
        },
        error => {
          this.isSearch = false;
          console.log("Http Server error", error);
        },
      );
    }
  }

  // on click of export 
  public exportStudents() {
    if (this.studentSearchData.schoolId == 0) {
      this.isExportNotValid = true;
    } else {
      this.loadingDropdown = true;
      this.studentService.exportStudents(this.studentSearchData).subscribe(
        (response) => {
          var blob = new Blob([response], { type: 'application/octet-stream' });
          tempSaveAs(blob, "Student_Exports_" + this.studentSearchData.schoolId + "_" + new Date() + ".xlsx");
          this.loadingDropdown = false;
        },
        error => {
          this.loadingDropdown = false;
          console.log("Http Server error", error);
        }
      );
    }
  }

  // On submit after updating the students
  public onStudentSubmit() {
    this.classSectionDetail.userId = localStorage.getItem('userId');
    this.classSectionDetail.schoolId = this.studentSearchData.schoolId;
    let validationError = this.validateTeamName();
    // If error exist do not proceed open the error model
    if (validationError != '') {
      this.openModal("Error", validationError);
      return;
    }
    this.loadingStudents = true;
    this.studentService.saveOrUpdateStudent(this.classSectionDetail).subscribe(
      (response) => {
        this.classSectionDetail = response;
        this.studentSource.load(this.classSectionDetail.studentList);
        this.teamNameTableSource.load(this.classSectionDetail.schoolTeamList);
        this.loadingStudents = false;
        this.openModal('Message', 'Students updated successfully!');
      },
      error => {
        console.log("Http Server error", error);
        this.openModal('Error Message', 'Error occured while updating students!');
      },
    );
  }

  // Check if the team name is not part of other class and each team should have 3 to 5 students
  private validateTeamName(): string {
    let errorMessage = '';
    //Selected class
    let className = this.getClassSectionName();
    // Container to hold the current team details list
    let classTeamList: ISchoolTeamCount[] = [];
    this.classSectionDetail.studentList.forEach(student => {
      this.classSectionDetail.schoolTeamList.forEach(schoolTeam => {
        if (schoolTeam.classSectionName != className && schoolTeam.teamName == student.teamName) {
          errorMessage = "The team name " + student.teamName + " already taken by class :: " + schoolTeam.classSectionName;
          return false;
        }
      });

      // Get the current class team and check if it already has count 5
      let isTeamAlreadyAdded = false;
      classTeamList.forEach(classTeam => {
        if (student.teamName == classTeam.teamName) {
          isTeamAlreadyAdded = true;
          if (classTeam.studentCount == 5) {
            errorMessage = "The team name " + student.teamName + " has more than 5 students";
            return false;
          } else {
            classTeam.studentCount = classTeam.studentCount + 1;
          }
        }
      });
      // If this team is not present in the current team list add to the list
      if (!isTeamAlreadyAdded) {
        let teamCount: ISchoolTeamCount = new ISchoolTeamCount();
        teamCount.studentCount = 1;
        teamCount.teamName = student.teamName;
        classTeamList.push(teamCount);
      }
    });
    // Finally check if the count is between 3 and 5
    classTeamList.forEach(classTeam => {
      let classTeamCount = classTeam.studentCount;
      if (classTeamCount < 3 || classTeamCount > 5) {
        errorMessage = "The team name " + classTeam.teamName + " count is not between 3 to 5!"
        return false;
      }
    });
    return errorMessage;
  }

  // get the class and section name of the selected class in dropdown
  private getClassSectionName(): string {
    let classSectionName = '';
    this.classSectionList.forEach(classSection => {
      if (classSection.id == this.studentSearchData.classId) {
        classSectionName = classSection.classAndSectionName;
        return false;
      }
    });
    return classSectionName;
  }

  public openBulkUploadDialog(): void {
    if (this.studentSearchData.schoolId == 0) {
      this.isBulkUploadRequestNotValid = true;
    } else {
      const activeModal = this.modalService.open(StudentBulkUploadModalComponent, { size: 'lg', container: 'nb-layout' });
      activeModal.componentInstance.schoolId = this.studentSearchData.schoolId;
      activeModal.componentInstance.classId = this.studentSearchData.classId;
    }
  }

  public onStudentCreate(event): void {
    // If any of the feilds are left blank 
    if (event.newData.teamName == null || event.newData.teamName == '' ||
      event.newData.studentName == null || event.newData.studentName == '') {
      this.openModal('Validation Message', 'All fieilds are mandatory to add a Student!');
      event.confirm.reject();
    } else {
      this.classSectionDetail.studentList = event.source.data;
      event.confirm.resolve();
    }
  }

  public onStudentEdit(event): void {
    if (event.newData.teamName == null || event.newData.teamName == '' ||
      event.newData.studentName == null || event.newData.studentName == '') {
      this.openModal('Validation Message', 'All fieilds are mandatory to edit a Student!');
      event.confirm.reject();
    } else {
      this.classSectionDetail.studentList = event.source.data;
      event.confirm.resolve();
    }
  }

  public onStudentDeleteConfirm(event): void {
    if (window.confirm('Are you sure you want to delete? All the preformance data for the student will also get deleted!')) {
      this.classSectionDetail.studentList = event.source.data;
      for (let i = 0; i < this.classSectionDetail.studentList.length; i++) {
        let student = this.classSectionDetail.studentList[i];
        if (student.studentName === event.data.studentName
          && student.teamName === event.data.teamName) {
          this.classSectionDetail.studentList.splice(i, 1);
        }
      }
      event.confirm.resolve();
    } else {
      event.confirm.reject();
    }
  }

  private studentTableSetting() {
    let settings: any = {
      add: {
        addButtonContent: '<i class="nb-plus"></i>',
        createButtonContent: '<i class="nb-checkmark"></i>',
        cancelButtonContent: '<i class="nb-close"></i>',
        confirmCreate: true
      },
      edit: {
        editButtonContent: '<i class="nb-edit"></i>',
        saveButtonContent: '<i class="nb-checkmark"></i>',
        cancelButtonContent: '<i class="nb-close"></i>',
        confirmSave: true
      },
      delete: {
        deleteButtonContent: '<i class="nb-trash"></i>',
        confirmDelete: true
      },
      pager: { display: true, perPage: 10 },
      columns: {
        studentName: {
          title: 'Student Name',
          type: 'string'
        },
        teamName: {
          title: 'Team Name',
          type: 'string'
        }
      }
    };
    return settings;
  }

  private studentTableSettingRestricted() {
    let settings: any = {
      actions: { add: false, edit: false, delete: false },
      pager: { display: true, perPage: 10 },
      columns: {
        studentName: {
          title: 'Student Name',
          type: 'string'
        },
        teamName: {
          title: 'Team Name',
          type: 'string'
        }
      }
    };
    return settings;
  }

  private teamTableSettings() {
    let settings: any = {
      actions: { add: false, edit: false, delete: false },
      pager: { display: true, perPage: 10 },
      columns: {
        classSectionName: {
          title: 'Class',
          type: 'string'
        },
        teamName: {
          title: 'Team',
          type: 'string'
        },
        studentCount: {
          title: 'Student Count',
          type: 'string'
        }
      }
    };
    return settings;
  }

  private openModal(modalheadertext, modalmessage) {
    const modalRef = this.modalService.open(SchoolMessageModalComponent);
    modalRef.componentInstance.modalmessage = modalmessage;
    modalRef.componentInstance.modalheadertext = modalheadertext;
  }

}
