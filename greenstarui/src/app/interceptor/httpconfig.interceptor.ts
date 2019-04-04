import { HttpErrorResponse, HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { ErrorDialogModalComponent } from '../error-dialog/errordialog.modal.component';

@Injectable()
export class HttpConfigInterceptor implements HttpInterceptor {

    private allowedStatusCodes: number[] = [200, 201, 202, 203, 204, 205, 206, 207, 208, 226];

    constructor(private modalService: NgbModal, private router: Router) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        let newRequest = request;
        //If not login url then add headers
        if (request.url.indexOf('api/security/login') == -1) {

            if (localStorage.getItem('apiToken') === undefined ||
                localStorage.getItem('apiToken') == '') {
                const activeModal = this.modalService.open(ErrorDialogModalComponent, { size: 'lg', container: 'nb-layout' });
                activeModal.componentInstance.modalContent = 'Session expired or invalid.Login again';
                this.router.navigate(['/login']);
            }

            // clone the request to add the api authentication key to header.
            newRequest = request.clone({
                headers: new HttpHeaders({
                    'apitoken': localStorage.getItem('apiToken'),
                    'userid': localStorage.getItem('userId')
                })
            });
        }

        // Forward the request
        return next.handle(newRequest).pipe(
            map((event: HttpEvent<any>) => {

                if (event instanceof HttpResponse) {
                    // if block to check the http allowed status code and show the error message popup
                    if (event.status == 504) {
                        const activeModal = this.modalService.open(ErrorDialogModalComponent, { size: 'lg', container: 'nb-layout' });
                        activeModal.componentInstance.modalContent = 'Gateway timeout, Please retry after sometime!';
                    } else if (event.status == 401) {
                        const activeModal = this.modalService.open(ErrorDialogModalComponent, { size: 'lg', container: 'nb-layout' });
                        activeModal.componentInstance.modalContent = 'Session expired or invalid.Login again';
                        this.router.navigate(['/login']);
                        // Invalidate local storage
                        localStorage.clear();
                    } else if (!this.allowedStatusCodes.includes(event.status)) {
                        const activeModal = this.modalService.open(ErrorDialogModalComponent, { size: 'lg', container: 'nb-layout' });
                        activeModal.componentInstance.modalContent = 'Error Occured, Please try again later';
                    }
                }
                return event;
            }),
            // catch error block to check error and show to error popup alert.
            catchError((error: HttpErrorResponse) => {
                let data = {};
                console.log('Error ::', JSON.stringify(error));
                if (error.status == 504) {
                    const activeModal = this.modalService.open(ErrorDialogModalComponent, { size: 'lg', container: 'nb-layout' });
                    activeModal.componentInstance.modalContent = 'Session timeout, Please retry after sometime!';
                    return throwError(error);
                }
                if (error.status == 403) {
                    const activeModal = this.modalService.open(ErrorDialogModalComponent, { size: 'lg', container: 'nb-layout' });
                    activeModal.componentInstance.modalContent = 'User not authorized to access this link!';
                    return throwError(error);
                }
                // 401 occurs only in case of login with wrong credentials
                if (error.status != 401) {
                    data = {
                        reason: error && error.error.reason ? error.error.reason : '',
                        status: error.status
                    };
                    const activeModal = this.modalService.open(ErrorDialogModalComponent, { size: 'lg', container: 'nb-layout' });
                    activeModal.componentInstance.modalContent = 'Error Occured, Please try again later';
                }
                return throwError(error);
            }));
    }
}
