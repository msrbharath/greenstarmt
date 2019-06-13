import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { LoginComponent } from './login.component';
import { LoginService } from './login.service';
import { LoginServiceMock } from './mock/login.service.mock';

describe('Login Component', () => {

    let component: LoginComponent;
    let fixture: ComponentFixture<LoginComponent>;
    let router: Router;
    
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
            declarations: [LoginComponent],
            providers: [{ provide: LoginService, useClass: LoginServiceMock }],
            imports: [
                FormsModule,
                ReactiveFormsModule,                
                RouterTestingModule.withRoutes([])
            ]
        }).compileComponents().then(() => {
            fixture = TestBed.createComponent(LoginComponent);
            component = fixture.componentInstance;
            router = TestBed.get(Router)
        });

    }));

    it('Login Module: Should component create', async(() => {
        expect(component).toBeTruthy();
    }));

    it('Login Module: Should initialize the login form component create', async(() => {
        component.ngOnInit();
        expect(component).toBeTruthy();
    }));

    it('Login Module: Should login user with invalid form validation', async(() => {
        component.ngOnInit();
        component.doLoginAuth();

        expect(component.isShowErrorMsg === true);
    }));

    it('Login Module: Should login Admin User', () => {
        component.ngOnInit();

        component.loginForm.controls['userId'].setValue('panneer');
        component.loginForm.controls['password'].setValue('panneer');

        let navigateSpy = spyOn(router, 'navigate');
        component.doLoginAuth();
        expect(navigateSpy).toHaveBeenCalledWith(['greenstarui/pages/dashboard']);
        expect(localStorage.getItem('userId') === 'panneer');
        expect(localStorage.getItem('roleName') === 'Admin');
        expect(localStorage.getItem('apiToken') !== null);
    });

    it('Login Module: Should login PMO User', () => {
        component.ngOnInit();

        component.loginForm.controls['userId'].setValue('magesh');
        component.loginForm.controls['password'].setValue('magesh');

        let navigateSpy = spyOn(router, 'navigate');
        component.doLoginAuth();

        expect(navigateSpy).toHaveBeenCalledWith(['greenstarui/pages/dashboard']);
        expect(localStorage.getItem('userId') === 'magesh');
        expect(localStorage.getItem('roleName') === 'PMO');
        expect(localStorage.getItem('apiToken') !== null);
    });

    /*
    it('Login Module: Should login Event POC User', () => {
        component.ngOnInit();

        component.loginForm.controls['userId'].setValue('bharath');
        component.loginForm.controls['password'].setValue('bharath');

        let navigateSpy = spyOn(router, 'navigate');
        component.doLoginAuth();
        expect(navigateSpy).toHaveBeenCalledWith(['greenstarui/pages/school']);
        expect(localStorage.getItem('userId') === 'bharath');
        expect(localStorage.getItem('roleName') === 'Event POC');
        expect(localStorage.getItem('apiToken') !== null);
    });
    */
   
    it('Login Module: Should login Invalid user', () => {
        component.ngOnInit();

        component.loginForm.controls['userId'].setValue('senthil');
        component.loginForm.controls['password'].setValue('senthil');

        component.doLoginAuth();
        expect(component.isShowErrorMsg === true);
    });

});
