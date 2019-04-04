import { Component, OnInit } from "@angular/core";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { saveAs as tempSaveAs } from 'file-saver';
import { IStudentSearchData } from "./student.interface";
import { StudentService } from "./student.service";

@Component({
    selector: 'ngx-modal',
    templateUrl: './student-bulk-upload.component.modal.html'
})
export class StudentBulkUploadModalComponent implements OnInit {

    public uploadFile: File;
    public schoolId: number;
    public classId: number;
    public fileName: string;

    public isDisableButton: boolean;
    public isShowSuccessMsg: boolean;
    public isShowErrorMsg: boolean;

    public errorMsg: string;
    public isSpinner: boolean;

    constructor(
        private activeModal: NgbActiveModal,
        private studentService: StudentService
    ) { }

    ngOnInit(): void {
        this.uploadFile = null;
        this.fileName = '';
        this.isDisableButton = true;
        this.isShowSuccessMsg = false;
        this.isShowErrorMsg = false;
        this.errorMsg = '';
    }

    private resetUploadParam() {
        this.uploadFile = null;
        this.fileName = '';
        this.isDisableButton = true;
        this.isShowSuccessMsg = false;
        this.isShowErrorMsg = false;
        this.errorMsg = '';
    }

    public handleFileInput(files: FileList) {
        this.resetUploadParam();
        if (this.isValidFile(files.item(0))) {
            this.uploadFile = files.item(0);
            this.fileName = files.item(0).name;
            this.isDisableButton = false;
            this.isShowSuccessMsg = false;
        } else {
            this.isShowErrorMsg = true;
            this.errorMsg = 'Please upload valid excel file downloaded via download template(without renaming) for the school selected in search!';
        }
    }

    //To validate excel file
    private isValidFile(excelFile: File): boolean {
        if (excelFile.name.split('.').pop() != 'xlsx') {
            return false;
        }
        if (excelFile.name.indexOf('Bulk_Upload_Student_' + this.schoolId + '_' ) != 0) {
            return false;
        }
        return true;
    }

    public downloadBulkUploadTemplate(): void {
        let searchParam: IStudentSearchData = new IStudentSearchData();
        searchParam.schoolId = this.schoolId;
        searchParam.classId = this.classId;
        this.isSpinner = true;
        this.studentService.getStudentDataTemplate(searchParam).subscribe(
            (response) => {
                var blob = new Blob([response], { type: 'application/octet-stream' });
                tempSaveAs(blob, "Bulk_Upload_Student_" + this.schoolId + "_" + new Date() + ".xlsx");
                this.isSpinner = false;
            },
            error => {
                this.isSpinner = false;
                console.log("Http Server error", error);
            }
        );
    }

    public uploadStudentBulkData(): void {
        this.isDisableButton = true;
        this.isShowSuccessMsg = false;

        if (this.uploadFile != null) {
            const formData = new FormData();
            formData.append('file', this.uploadFile);
            formData.append('userId', localStorage.getItem('userId'));
            formData.append('schoolId', this.schoolId + '');

            this.isSpinner = true;
            this.studentService.bulkUploadStudentData(formData).subscribe(
                (response) => {
                    this.isSpinner = false;
                    if (response == 'Bulk Uplaod Successful!') {
                        this.isShowSuccessMsg = true;
                        this.isDisableButton = true;
                    } else {
                        this.uploadFile = null;
                        this.fileName = '';
                        this.isDisableButton = true;
                        this.isShowErrorMsg = true;
                        this.errorMsg = response;
                    }
                },
                error => {
                    this.isSpinner = false;
                    console.log("Http Server error", error);
                }
            );
        } else {
            this.isShowErrorMsg = true;
            this.errorMsg = 'Please upload file!';
        }
    }

    public closeModal(): void {
        this.activeModal.close();
    }

}
