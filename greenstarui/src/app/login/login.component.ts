import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserIdleService } from 'angular-user-idle';
import { LoginService } from './login.service';
import { SessionoutDialogModalComponent } from './sessionout.modal.component';

@Component({
  selector: 'ngx-login-pages',
  styleUrls: ['./login.component.scss'],
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit {

  public loginForm: FormGroup;
  public isShowErrorMsg: boolean = false;
  public isSpinner: boolean = false;

  public ngOnInit(): void {
    localStorage.clear();
    this.loginForm = this.formBuilder.group({
      userId: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  constructor(private router: Router,
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private userIdle: UserIdleService,
    private modalService: NgbModal) {
  }

  public doLoginAuth(): void {
    localStorage.clear();
    this.isShowErrorMsg = false;
    if (this.loginForm.valid) {
      this.isSpinner = true;      
      const user = { "username": this.loginForm.getRawValue().userId, "password": this.loginForm.getRawValue().password };
      
      this.loginService.userLogin(user).subscribe(
        (response) => {
          if (response !== null && response !== '') {
            // Initilize the sessionout.
            this.initializeSessionManagement();

            let menuNames = null;
            const roleName = this.getUIDisplayRole(response.roleName);
            if (roleName === 'Admin') {
              menuNames = 'Dashboard~School~Student~Performance Data~Performance Star~Performance Metrics~Admin~Logout';
            } else if (roleName === 'PMO') {
              menuNames = 'Dashboard~School~Student~Performance Data~Performance Star~Performance Metrics~Logout';
            } else if (roleName === 'Event POC') {
              menuNames = 'School~Student~Performance Data~Performance Star~Performance Metrics~Logout';
            } else {
              console.log('No matches found');
            }
            console.log('roleName', roleName);
            console.log('apiToken', response.apiToken);
            console.log('uiMenuList', menuNames);

            localStorage.setItem('roleName', roleName);
            localStorage.setItem('apiToken', response.apiToken);
            localStorage.setItem('uiMenuList', menuNames);
            localStorage.setItem('userId', this.loginForm.getRawValue().userId);

            let seconds: any = Math.round(new Date().getTime() / 1000);
            localStorage.setItem('lastUpdatedTime', seconds);

            if (roleName === 'Event POC') {
              this.router.navigate(['greenstarui/pages/school']);
            } else {
              this.router.navigate(['greenstarui/pages/dashboard']);
            }
          } else {
            this.isSpinner = false;
            this.isShowErrorMsg = true;
          }
        },
        error => {
          this.isSpinner = false;
          this.isShowErrorMsg = true;
          console.log("Http Server error", error);
        }
      );
    } else {
      this.isSpinner = false;
      this.isShowErrorMsg = true;
    }

  }

  private getUIDisplayRole(securityRoleName: string): string {
    if (securityRoleName.indexOf('ADMIN') > 0) {
      return 'Admin';
    } else if (securityRoleName.indexOf('PMO') > 0) {
      return 'PMO';
    } else if (securityRoleName.indexOf('POC') > 0) {
      return 'Event POC';
    }
  }

  private initializeSessionManagement() {
    // Start watching for user inactivity.
    this.userIdle.startWatching();

    // Start watching when user idle is starting.
    this.userIdle.onTimerStart().subscribe(count => console.log(count));

    // Start watch when time is up.
    this.userIdle.onTimeout().subscribe(() => {
      console.log('Session Time is up!');
      const activeModal = this.modalService.open(SessionoutDialogModalComponent, { size: 'lg', container: 'nb-layout' });
      activeModal.componentInstance.modalContent = 'User session idle timeout reached! Click continue to extend your session';
    });
  }

}
