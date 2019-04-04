import { Component, OnInit } from "@angular/core";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { PerformanceDataService } from "./performance-data.service";

@Component({
    selector: 'ngx-modal',
    templateUrl: './performance-data-upload.component.modal.html'
})
export class PerformanceDataUploadModalComponent implements OnInit {

    public uploadFile: File = null;
    public fileName: string = '';
    public isDisableButton: boolean = true;
    public isShowSuccessMsg: boolean = false;
    public isShowErrorMsg: boolean = false;
    public errorMessage: string = '';
    public errorMessages = [];
    // loading drop down values on selection
    public isSpinner = false;
    
    constructor(
        private activeModal: NgbActiveModal,
        private performanceDataService: PerformanceDataService
    ) { }

    public ngOnInit(): void {
        this.resetUploadParam();
    }

    private resetUploadParam() {
        this.uploadFile = null;
        this.fileName = '';
        this.isDisableButton = true;
        this.isShowSuccessMsg = false;
        this.isShowErrorMsg = false;
        this.errorMessage = '';
        this.errorMessages = [];
    }

    public handleFileInput(files: FileList) {        
        this.resetUploadParam();
        if(files.item(0).name.split('.').pop() === 'xlsx') {
            this.uploadFile = files.item(0);
            this.fileName = files.item(0).name;
            this.isDisableButton = false;
            this.isShowSuccessMsg = false;
        } else {
            this.isShowErrorMsg = true;
            this.errorMessage = 'Please upload valid file in .xlsx file format';
        }
    }

    public uploadBulkData(): void {
        this.isDisableButton = true;
        this.isShowSuccessMsg = false;

        if (this.uploadFile != null) {
            const formData = new FormData();
            formData.append('file', this.uploadFile);
            formData.append('userId', localStorage.getItem('userId'));

            this.isSpinner = true;
            this.performanceDataService.uploadBulkPerformanceData(formData).subscribe(
                (response) => {
                    this.isSpinner = false;
                    if(response.message == null ) {
                        this.isShowErrorMsg = true;
                        this.errorMessage = '';
                        this.errorMessages = response.result;
                        this.isDisableButton = false;
                    } else {
                        this.uploadFile = null;
                        this.fileName = '';
                        this.isDisableButton = true;
                        this.isShowSuccessMsg = true;
                        this.isShowErrorMsg = false;
                        this.errorMessage = '';
                    }
                },
                error => {
                    this.isSpinner = false;
                    console.log("Http Server error", error);
                }
            );
        } else {
            this.isShowErrorMsg = true;
            this.errorMessage = 'Please upload valid file in .xlsx file format';
        }
    }

    public closeModal(): void {
        this.activeModal.close();
    }

}
