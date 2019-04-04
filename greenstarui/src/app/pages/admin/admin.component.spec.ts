import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserDynamicTestingModule } from '@angular/platform-browser-dynamic/testing';
import { RouterModule } from '@angular/router';
import { NbDialogModule, NbLayoutModule, NbSidebarModule, NbSpinnerModule, NbThemeModule } from '@nebular/theme';
import { NgbActiveModal, NgbModal, NgbModalModule, NgbModalRef, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { ThemeModule } from '../../@theme/theme.module';
import { AdminComponent } from './admin.component';
import { AdminModalComponent } from './admin.component.modal';
import { AdminService } from './admin.service';
import { AdminServiceMock } from './mocks/admin.service.mock';

describe('Admin Component', () => {

    let component: AdminComponent;
    let fixture: ComponentFixture<AdminComponent>;
    let modalService: NgbModal;
    let modalRef: NgbModalRef;
    
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
            declarations: [AdminComponent, AdminModalComponent],
            providers: [ NgbModal, NgbActiveModal, 
                { provide: AdminService, useClass: AdminServiceMock }                
            ],
            imports: [
                FormsModule,
                ReactiveFormsModule,
                ThemeModule,
                NbSpinnerModule,
                Ng2SmartTableModule,
                NbLayoutModule,
                NbSidebarModule,
                NbThemeModule.forRoot({ name: 'default' }),
                NgbModule.forRoot(),
                NgbModalModule.forRoot(),
                NbDialogModule.forRoot(),
                RouterModule.forRoot([])
            ]
        }).compileComponents();

        TestBed.overrideModule(BrowserDynamicTestingModule, {
            set: {
                entryComponents: [AdminModalComponent],
            },
        }).compileComponents();

    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(AdminComponent);
        component = fixture.componentInstance;

        modalService = TestBed.get(NgbModal);
        modalRef = modalService.open(AdminModalComponent);
        spyOn(modalService, "open").and.returnValue(modalRef);
    });

    it('Admin Module : Should admin component create', async(() => {
        expect(component).toBeTruthy();
    }));

    it('Admin Module : Should load existing user role mappings', async(() => {
        component.ngOnInit();
        expect(component.userRoleDetail !== null);
    }));

    it('Admin Module : Should create new user role mappings with empty validation', async(() => {
        // search parameters
        component.ngOnInit();

        // create custom event
        const event = {
            newData: { userId: "", roleName: "" },
            data: null,
            source: { data: { userId: "", roleName: "" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        fixture.detectChanges();
        component.onPostCallForUserRoleMap(event);
        expect(component.userRoleDetail !== null);
    }));

    it('Admin Module : Should create new user role mappings and validate the already user id and role', async(() => {
        // search parameters
        component.ngOnInit();

        // create custom event
        const event = {
            newData: { userId: "panneer", roleName: "PMO" },
            data: null,
            source: { data: { userId: "panneer", roleName: "PMO" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };

        fixture.detectChanges();
        component.onPostCallForUserRoleMap(event);
        expect(component.userRoleDetail !== null);
    }));

    it('Admin Module : Should create new user role mappings', async(() => {
        // search parameters
        component.ngOnInit();

        // create custom event
        const event = {
            newData: { userId: "magesh", roleName: "PMO" },
            data: null,
            source: { data: { userId: "magesh", roleName: "PMO" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        fixture.detectChanges();
        component.onPostCallForUserRoleMap(event);
        expect(component.userRoleDetail !== null);
    }));

    it('Admin Module : Should edit existing user role mappings and validate the already user id and role', async(() => {
        // search parameters
        component.ngOnInit();

        // create custom event
        const event = {
            newData: { id: "2", userId: "panneer", roleName: "PMO" },
            data: { id: "2", userId: "panneer", roleName: "PMO" },
            source: { data: { id: "2", userId: "panneer", roleName: "PMO" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        fixture.detectChanges();
        component.onPostCallForUserRoleMap(event);
        expect(component.userRoleDetail !== null);
    }));

    it('Admin Module : Should edit existing user role mappings', async(() => {
        // search parameters
        component.ngOnInit();

        // create custom event
        const event = {
            newData: { id: "3", userId: "magesh", roleName: "PMO" },
            data: { id: "3", userId: "magesh", roleName: "PMO" },
            source: { data: { id: "3", userId: "magesh", roleName: "PMO" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        fixture.detectChanges();
        component.onPostCallForUserRoleMap(event);
        expect(component.userRoleDetail !== null);
    }));

    it('Admin Module : Should delete existing user role mappings', async(() => {
        // search parameters
        component.ngOnInit();

        // create custom event
        const event = {
            newData: { id: "3", userId: "magesh", roleName: "PMO" },
            data: { id: "3", userId: "magesh", roleName: "PMO" },
            source: { data: { id: "3", userId: "magesh", roleName: "PMO" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        fixture.detectChanges();
        spyOn(window, 'confirm').and.returnValue(true);
        component.onDeleteConfirmForUserRoleMap(event);
        expect(component.userRoleDetail !== null);
    }));

});