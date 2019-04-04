import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NbDialogModule, NbSpinnerModule, NbStepperModule } from '@nebular/theme';
import { NgbActiveModal, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { ThemeModule } from '../../@theme/theme.module';
import { StudentServiceMock } from './mock/student.service.mock';
import { StudentBulkUploadModalComponent } from './student-bulk-upload.component.modal';
import { StudentService } from './student.service';

describe('Student Bulk Upload Component', () => {

    let component: StudentBulkUploadModalComponent;
    let fixture: ComponentFixture<StudentBulkUploadModalComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
            declarations: [StudentBulkUploadModalComponent],
            providers: [NgbActiveModal,
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
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(StudentBulkUploadModalComponent);
        component = fixture.componentInstance;
    });

    it('Student Bulk Upload : Should component create', async(() => {
        component.ngOnInit();
        expect(component).toBeTruthy();
    }));


    it('Student Bulk Upload : Should handle file input', async(() => {
        component.ngOnInit();

        const blob = new Blob([""], { type: "application/octet-stream" });
        blob["lastModifiedDate"] = "";
        blob["name"] = "student_data.xlsx";
        const file = <File>blob;
        const fileList = {
            0: file,
            length: 1,
            item: (index: number) => file
        };
        component.handleFileInput(fileList);
        expect(component.isShowErrorMsg == false);
    }));


    it('Student Bulk Upload : Should upload input file', async(() => {
        component.ngOnInit();
        component.schoolId = 42;
        component.uploadFile = new File([""], "student_data.xlsx", { type: 'application/octet-stream' });

        component.uploadStudentBulkData();

        expect(component.isShowSuccessMsg == true);
    }));

    it('Student Bulk Upload : Should download the template file', () => {
        component.ngOnInit();
        component.schoolId = 42;
        component.classId = 32;

        component.downloadBulkUploadTemplate();

        expect(component.isSpinner == false);
    });

    it('Student Bulk Upload : Should close the bulk upload poup modal', async(() => {
        component.ngOnInit();
        component.closeModal();

        expect(component).toBeTruthy();
    }));

});
