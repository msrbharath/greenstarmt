import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserDynamicTestingModule } from '@angular/platform-browser-dynamic/testing';
import { RouterModule } from '@angular/router';
import { NbDialogModule, NbSpinnerModule, NbStepperModule } from '@nebular/theme';
import { NgbActiveModal, NgbModal, NgbModalModule, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { ThemeModule } from '../../@theme/theme.module';
import { RoleService } from '../common/role.service';
import { SchoolMessageModalComponent } from '../school/school-message.modal.component';
import { StudentServiceMock } from './mock/student.service.mock';
import { StudentBulkUploadModalComponent } from './student-bulk-upload.component.modal';
import { StudentListComponent } from './student-list.component';
import { StudentService } from './student.service';

describe('Student List Component', () => {

    let component: StudentListComponent;
    let fixture: ComponentFixture<StudentListComponent>;
    let modalService: NgbModal;
    let modalRef: NgbModalRef;
    
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
            declarations: [StudentListComponent, StudentBulkUploadModalComponent, SchoolMessageModalComponent],
            providers: [NgbActiveModal, RoleService,
                { provide: StudentService, useClass: StudentServiceMock }
            ],
            imports: [
                FormsModule,
                ReactiveFormsModule,
                ThemeModule,
                NbStepperModule,
                NbSpinnerModule,
                Ng2SmartTableModule,
                NgbModalModule,
                NbDialogModule.forRoot(),
                RouterModule.forRoot([])
            ]
        }).compileComponents();

        TestBed.overrideModule(BrowserDynamicTestingModule, {
            set: {
                entryComponents: [StudentBulkUploadModalComponent, SchoolMessageModalComponent],
            },
        }).compileComponents().then(()=> {
            fixture = TestBed.createComponent(StudentListComponent);
            component = fixture.componentInstance;

            modalService = TestBed.get(NgbModal);
            modalRef = modalService.open(StudentBulkUploadModalComponent);
            spyOn(modalService, "open").and.returnValue(modalRef);
        });

    }));

    it('Should student component create', async(() => {
        expect(component).toBeTruthy();
    }));

    it('Should initialize and load school details search student screen', async(() => {
        component.ngOnInit();
        expect(component.schoolList.length > 0);
    }));

    it('Should load the class details, while selecting school', async(() => {
        component.ngOnInit();
        component.studentSearchData.schoolId = 42;
        component.onChangeSchoolChange();
        expect(component.classSectionList.length > 0);
    }));

    it('Should load the class details, while selecting school', async(() => {
        component.ngOnInit();
        component.studentSearchData.schoolId = 42;
        component.onChangeSchoolChange();
        expect(component.classSectionList.length > 0);
    }));

    it('Should on change the state, while selecting class', async(() => {
        component.ngOnInit();
        component.studentSearchData.schoolId = 42;
        component.onChangeSchoolChange();

        component.studentSearchData.classId = 37;
        component.onChangeClass();
        expect(component.isSearchDataNotValid === false);
    }));

    it('Should validate empty for school and class, while user click on search button', async(() => {
        component.ngOnInit();
        component.onClassEdit();
        expect(component.isSearchDataNotValid === true);
    }));

    it('Should validate the school and class, while user click on search button', async(() => {
        component.ngOnInit();
        component.studentSearchData.schoolId = 42;
        component.onChangeSchoolChange();

        component.studentSearchData.classId = 37;
        component.onClassEdit();
        expect(component.classSectionDetail.studentList.length > 0);
        expect(component.classSectionDetail.schoolTeamList.length > 0);
    }));

    it('Should create new student without student name and team name', () => {
        // search parameters
        component.ngOnInit();
        component.studentSearchData.schoolId = 42;
        component.onChangeSchoolChange();
        component.studentSearchData.classId = 37;
        // search the student details
        component.onClassEdit();

        // create custom event
        const event = {
            newData: { studentName: "", teamName: "" },
            source: { data: { studentName: "", teamName: "" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        component.onStudentCreate(event);
        expect(component.classSectionDetail.studentList.length > 0);
    });

    it('Should create new student', () => {
        // search parameters
        component.ngOnInit();
        component.studentSearchData.schoolId = 42;
        component.onChangeSchoolChange();
        component.studentSearchData.classId = 37;
        // search the student details
        component.onClassEdit();

        // create custom event
        const event = {
            newData: { studentName: "Panneer", teamName: "CSK" },
            source: { data: { studentName: "Panneer", teamName: "CSK" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        component.onStudentCreate(event);
        expect(component.classSectionDetail.studentList.length > 0);
    });

    it('Should edit the existing student detail without student name and team name', () => {
        // search parameters
        component.ngOnInit();
        component.studentSearchData.schoolId = 42;
        component.onChangeSchoolChange();
        component.studentSearchData.classId = 37;
        // search the student details
        component.onClassEdit();

        // create custom event
        const event = {
            newData: { studentName: "", teamName: "" },
            source: { data: { studentName: "", teamName: "" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        component.onStudentEdit(event);
        expect(component.classSectionDetail.studentList.length > 0);
    });

    it('Should edit the existing student detail', () => {
        // search parameters
        component.ngOnInit();
        component.studentSearchData.schoolId = 42;
        component.onChangeSchoolChange();
        component.studentSearchData.classId = 37;
        // search the student details
        component.onClassEdit();

        // create custom event
        const event = {
            newData: { id: "333", studentName: "Akil", teamName: "Kalaam" },
            source: { data: { id: "333", studentName: "Akil", teamName: "Kalaam" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        component.onStudentEdit(event);
        expect(component.classSectionDetail.studentList.length > 0);
    });

    it('Should delete the existing student detail', () => {
        // search parameters
        component.ngOnInit();
        component.studentSearchData.schoolId = 42;
        component.onChangeSchoolChange();
        component.studentSearchData.classId = 37;
        // search the student details
        component.onClassEdit();

        // create custom event
        const event = {
            newData: { id: "333", studentName: "Akil", teamName: "Kalaam" },
            source: { data: { id: "333", studentName: "Akil", teamName: "Kalaam" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        spyOn(window, 'confirm').and.returnValue(true);
        component.onStudentDeleteConfirm(event);
        expect(component.classSectionDetail.studentList.length > 0);
    });

    it('Should update editable student details, while user click on update students button', async(() => {
        component.ngOnInit();
        component.studentSearchData.schoolId = 42;
        component.onChangeSchoolChange();

        component.studentSearchData.classId = 37;
        // search the student details
        component.onClassEdit();
        component.onStudentSubmit();
        expect(component.classSectionDetail.studentList.length > 0);
        expect(component.classSectionDetail.schoolTeamList.length > 0);
    }));

    it('Should open student bulk upload without selecting school, while user click on bulk upload button', async(() => {
        component.ngOnInit();

        component.openBulkUploadDialog();
        expect(component.isBulkUploadRequestNotValid == true);
    }));
    
    it('Should open student bulk upload, while user click on bulk upload button', async(() => {
        component.ngOnInit();
        component.studentSearchData.schoolId = 42;
        
        component.openBulkUploadDialog();
        expect(component.isBulkUploadRequestNotValid == false);        
    }));
    
});