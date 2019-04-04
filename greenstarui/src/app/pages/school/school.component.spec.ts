import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserDynamicTestingModule } from '@angular/platform-browser-dynamic/testing';
import { RouterModule } from '@angular/router';
import { NbDialogModule, NbSpinnerModule, NbStepperModule } from '@nebular/theme';
import { NgbActiveModal, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { ThemeModule } from '../../@theme/theme.module';
import { CommonService } from '../common/common.service';
import { CommonMockService } from '../common/mock/common.service.mock';
import { SchoolServiceMock } from './mocks/school.service.mock';
import { SchoolMessageModalComponent } from './school-message.modal.component';
import { SchoolComponent } from './school.component';
import { SchoolData } from './school.data';
import { SchoolService } from './school.service';

describe('School Component', () => {

    let component: SchoolComponent;
    let fixture: ComponentFixture<SchoolComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
            declarations: [SchoolComponent, SchoolMessageModalComponent],
            providers: [NgbActiveModal,
                { provide: SchoolService, useClass: SchoolServiceMock },
                { provide: CommonService, useClass: CommonMockService }
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
                entryComponents: [SchoolMessageModalComponent],
            },
        }).compileComponents();

    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(SchoolComponent);
        component = fixture.componentInstance;
    });

    it('Should school component create', async(() => {
        expect(component).toBeTruthy();
    }));

    it('Should create new school and class details', async(() => {
        component.title = 'Add School Detail';
        component.action = 'create';
        component.stateList = component.stateList = SchoolData.getStates();
        component.ngOnInit();
        expect(component.perfParamDynamicDetail !== null);
    }));

    it('Should load the district, without selecting state dropdown', () => {
        component.title = 'Add School Detail';
        component.action = 'create';
        component.stateList = component.stateList = SchoolData.getStates();
        component.ngOnInit();

        component.onStateChange();
        expect(component.districtList.length > 0);
    });

    it('Should create new school and class details without entering mandatory information', async(() => {
        component.title = 'Add School Detail';
        component.action = 'create';
        component.stateList = component.stateList = SchoolData.getStates();
        component.ngOnInit();

        component.onSubmitChanges();
        expect(component.schoolDetail !== null);
    }));

    it('Should edit existing school and class details', async(() => {
        component.title = 'Edit School Detail';
        component.schoolId = 42;
        component.action = 'edit';
        component.stateList = component.stateList = SchoolData.getStates();
        component.ngOnInit();
        expect(component.perfParamDynamicDetail !== null);
    }));

    it('Should load existing school with empty class, holidays and weekend working days', async(() => {
        component.title = 'Edit School Detail';
        component.schoolId = 53;
        component.action = 'edit';
        component.stateList = component.stateList = SchoolData.getStates();
        component.ngOnInit();
        expect(component.perfParamDynamicDetail !== null);
    }));

    it('Should edit and update the existing school details', () => {
        component.title = 'Edit School Detail';
        component.schoolId = 42;
        component.action = 'edit';
        component.stateList = SchoolData.getStates();
        component.ngOnInit();

        component.onSubmitChanges();
        expect(component.schoolDetail !== null);
    });

    it('Should create new class wiothout class detail', () => {
        component.ngOnInit();
        const event = {
            newData: { className: "", sectionName: "" },
            source: { data: { className: "", sectionName: "" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        component.schoolDetail = JSON.parse('{"id":313,"userId":null,"action":null,"schoolName":"Coimbatore Government Hr Sec School","district":"COIMBATORE","state":"TAMIL NADU","cityName":"COIMBATORE","address":"COIMBATORE","classList":[],"holidays":[{"id":3161,"fromDate":"2019-01-01","toDate":"2019-01-01","description":"New Year’s Day"}]}');
        component.onClassAdd(event);
        expect(component.schoolDetail.classList.length == 2);
    });

    it('Should create new class to existing empty class list', () => {
        component.ngOnInit();
        const event = {
            newData: { className: "I", sectionName: "A" },
            source: { data: { className: "I", sectionName: "A" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        component.schoolDetail = JSON.parse('{"id":313,"userId":null,"action":null,"schoolName":"Coimbatore Government Hr Sec School","district":"COIMBATORE","state":"TAMIL NADU","cityName":"COIMBATORE","address":"COIMBATORE","classList":[],"holidays":[{"id":3161,"fromDate":"2019-01-01","toDate":"2019-01-01","description":"New Year’s Day"}]}');
        component.onClassAdd(event);
        expect(component.schoolDetail.classList.length == 2);
    });

    it('Should not add existing class', () => {
        component.ngOnInit();
        const event = {
            newData: { className: "I", sectionName: "A" },
            source: { data: { className: "I", sectionName: "A" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        component.schoolDetail = JSON.parse('{"id":313,"userId":null,"action":null,"schoolName":"Coimbatore Government Hr Sec School","district":"COIMBATORE","state":"TAMIL NADU","cityName":"COIMBATORE","address":"COIMBATORE","classList":[{"id":487,"className":"I","sectionName":"A"},{"id":488,"className":"I","sectionName":"B"}],"holidays":[{"id":3161,"fromDate":"2019-01-01","toDate":"2019-01-01","description":"New Year’s Day"}]}');
        component.onClassAdd(event);
        expect(component.schoolDetail.classList.length == 2);
    });

    it('Should edit the existing class without mandatory fields', () => {
        component.ngOnInit();
        const event = {
            newData: { className: "", sectionName: "" },
            source: { data: { className: "", sectionName: "" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        component.schoolDetail = JSON.parse('{"id":313,"userId":null,"action":null,"schoolName":"Coimbatore Government Hr Sec School","district":"COIMBATORE","state":"TAMIL NADU","cityName":"COIMBATORE","address":"COIMBATORE","classList":[{"id":487,"className":"I","sectionName":"A"},{"id":488,"className":"I","sectionName":"B"}],"holidays":[{"id":3161,"fromDate":"2019-01-01","toDate":"2019-01-01","description":"New Year’s Day"}]}');
        component.onClassEdit(event);
        expect(component.schoolDetail.classList.length == 2);
    });

    it('Should edit the existing class', () => {
        component.ngOnInit();
        const event = {
            newData: { className: "I", sectionName: "A" },
            source: { data: { className: "I", sectionName: "A" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        component.schoolDetail = JSON.parse('{"id":313,"userId":null,"action":null,"schoolName":"Coimbatore Government Hr Sec School","district":"COIMBATORE","state":"TAMIL NADU","cityName":"COIMBATORE","address":"COIMBATORE","classList":[{"id":487,"className":"I","sectionName":"A"},{"id":488,"className":"I","sectionName":"B"}],"holidays":[{"id":3161,"fromDate":"2019-01-01","toDate":"2019-01-01","description":"New Year’s Day"}]}');
        component.onClassEdit(event);
        expect(component.schoolDetail.classList.length == 2);
    });

    it('Should delete the existing class', () => {
        component.ngOnInit();
        const event = {
            newData: { id: "487", className: "I", sectionName: "A" },
            data: { id: undefined, className: "I", sectionName: "A" },
            source: { data: { id: "487", className: "I", sectionName: "A" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        component.schoolDetail = JSON.parse('{"id":313,"userId":null,"action":null,"schoolName":"Coimbatore Government Hr Sec School","district":"COIMBATORE","state":"TAMIL NADU","cityName":"COIMBATORE","address":"COIMBATORE","classList":[{"id":487,"className":"I","sectionName":"A"},{"id":488,"className":"I","sectionName":"B"}],"holidays":[{"id":3161,"fromDate":"2019-01-01","toDate":"2019-01-01","description":"New Year’s Day"}]}');

        spyOn(window, 'confirm').and.returnValue(true);
        component.onClassDeleteConfirm(event);
        expect(component.schoolDetail.classList.length == 1);
    });

    it('Should edit the existing parameter data', () => {
        component.ngOnInit();
        const event = {
            newData: { id: "487", paramTitle: "Attendance", paramDesc: "Attendance" },
            source: { data: { id: "487", paramTitle: "Attendance", paramDesc: "Attendance" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        component.schoolDetail = JSON.parse('{"id":313,"userId":null,"action":null,"schoolName":"Coimbatore Government Hr Sec School","district":"COIMBATORE","state":"TAMIL NADU","cityName":"COIMBATORE","address":"COIMBATORE","classList":[{"id":487,"className":"I","sectionName":"A"},{"id":488,"className":"I","sectionName":"B"}],"perfParamList":[{"id":487,"paramTitle":"Attendance","paramDesc":"Attendance"},{"id":488,"paramTitle":"HomeWork","paramDesc":"HomeWork"},{"id":489,"paramTitle":"Discipline","paramDesc":"Discipline"}],"holidays":[{"id":3161,"fromDate":"2019-01-01","toDate":"2019-01-01","description":"New Year’s Day"}]}');
        component.onParameterEdit(event);
        expect(component.schoolDetail.classList.length == 2);
    });

    it('Should edit the existing parameter data without mandatory fields', () => {
        component.ngOnInit();
        const event = {
            newData: { paramTitle: "", paramDesc: "" },
            source: { data: { paramTitle: "", paramDesc: "" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        component.schoolDetail = JSON.parse('{"id":313,"userId":null,"action":null,"schoolName":"Coimbatore Government Hr Sec School","district":"COIMBATORE","state":"TAMIL NADU","cityName":"COIMBATORE","address":"COIMBATORE","classList":[{"id":487,"className":"I","sectionName":"A"},{"id":488,"className":"I","sectionName":"B"}],"perfParamList":[{"id":487,"paramTitle":"Attendance","paramDesc":"Attendance"},{"id":488,"paramTitle":"HomeWork","paramDesc":"HomeWork"},{"id":489,"paramTitle":"Discipline","paramDesc":"Discipline"}],"holidays":[{"id":3161,"fromDate":"2019-01-01","toDate":"2019-01-01","description":"New Year’s Day"}]}');
        component.onParameterEdit(event);
        expect(component.schoolDetail.classList.length == 2);
    });

    it('Should create new holiday', () => {
        component.ngOnInit();
        const event = {
            newData: { fromDate: "2019-12-25", toDate: "2019-12-25", description: "Christmas day" },
            source: { data: { romDate: "2019-12-25", toDate: "2019-12-25", description: "Christmas day" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        component.schoolDetail = JSON.parse('{"id":313,"userId":null,"action":null,"schoolName":"Coimbatore Government Hr Sec School","district":"COIMBATORE","state":"TAMIL NADU","cityName":"COIMBATORE","address":"COIMBATORE","classList":[{"id":487,"className":"I","sectionName":"A"},{"id":488,"className":"I","sectionName":"B"}],"perfParamList":[{"id":487,"paramTitle":"Attendance","paramDesc":"Attendance"},{"id":488,"paramTitle":"HomeWork","paramDesc":"HomeWork"},{"id":489,"paramTitle":"Discipline","paramDesc":"Discipline"}],"holidays":[{"id":3161,"fromDate":"2019-01-01","toDate":"2019-01-01","description":"New Year’s Day"}]}');
        component.onCreateForHoliday(event);
        expect(component.schoolDetail.classList.length == 2);
    });

    it('Should edit the existing holiday', () => {
        component.ngOnInit();
        const event = {
            newData: { id: "3161", fromDate: "2019-01-01", toDate: "2019-01-01", description: "New Year’s Day" },
            source: { data: { id: "3161", fromDate: "2019-01-01", toDate: "2019-01-01", description: "New Year’s Day" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        component.schoolDetail = JSON.parse('{"id":313,"userId":null,"action":null,"schoolName":"Coimbatore Government Hr Sec School","district":"COIMBATORE","state":"TAMIL NADU","cityName":"COIMBATORE","address":"COIMBATORE","classList":[{"id":487,"className":"I","sectionName":"A"},{"id":488,"className":"I","sectionName":"B"}],"perfParamList":[{"id":487,"paramTitle":"Attendance","paramDesc":"Attendance"},{"id":488,"paramTitle":"HomeWork","paramDesc":"HomeWork"},{"id":489,"paramTitle":"Discipline","paramDesc":"Discipline"}],"holidays":[{"id":3161,"fromDate":"2019-01-01","toDate":"2019-01-01","description":"New Year’s Day"}]}');
        component.onEditForHoliday(event);
        expect(component.schoolDetail.classList.length == 1);
    });

    it('Should delete the existing holiday', () => {
        component.ngOnInit();
        const event = {
            newData: { id: "3161", fromDate: "2019-01-01", toDate: "2019-01-01", description: "New Year’s Day" },
            source: { data: { id: "3161", fromDate: "2019-01-01", toDate: "2019-01-01", description: "New Year’s Day" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        component.schoolDetail = JSON.parse('{"id":313,"userId":null,"action":null,"schoolName":"Coimbatore Government Hr Sec School","district":"COIMBATORE","state":"TAMIL NADU","cityName":"COIMBATORE","address":"COIMBATORE","classList":[{"id":487,"className":"I","sectionName":"A"},{"id":488,"className":"I","sectionName":"B"}],"perfParamList":[{"id":487,"paramTitle":"Attendance","paramDesc":"Attendance"},{"id":488,"paramTitle":"HomeWork","paramDesc":"HomeWork"},{"id":489,"paramTitle":"Discipline","paramDesc":"Discipline"}],"holidays":[{"id":3161,"fromDate":"2019-01-01","toDate":"2019-01-01","description":"New Year’s Day"}, {"id":3162,"fromDate":"2019-12-25","toDate":"2019-12-25","description":"Christmas day"}]}');
        spyOn(window, 'confirm').and.returnValue(true);
        component.onDeleteForHoliday(event);
        expect(component.schoolDetail.classList.length == 1);
    });

    it('Should create new weekend working day', () => {
        component.ngOnInit();
        const event = {
            newData: { id: "3161", workingDate: "2019-01-01", reason: "Neet specail class" },
            source: { data: { id: "3161", workingDate: "2019-01-01", reason: "Neet specail class" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        component.schoolDetail = JSON.parse('{"id":313,"userId":null,"action":null,"schoolName":"Coimbatore Government Hr Sec School","district":"COIMBATORE","state":"TAMIL NADU","cityName":"COIMBATORE","address":"COIMBATORE","classList":[{"id":487,"className":"I","sectionName":"A"},{"id":488,"className":"I","sectionName":"B"}],"perfParamList":[{"id":487,"paramTitle":"Attendance","paramDesc":"Attendance"},{"id":488,"paramTitle":"HomeWork","paramDesc":"HomeWork"},{"id":489,"paramTitle":"Discipline","paramDesc":"Discipline"}],"holidays":[{"id":3161,"fromDate":"2019-01-01","toDate":"2019-01-01","description":"New Year’s Day"}, {"id":3162,"fromDate":"2019-12-25","toDate":"2019-12-25","description":"Christmas day"}], "weekendWorkingDays":[{"id":487,"workingDate":"2019-12-25","reason":"Special Class"},{"id":488,"workingDate":"2019-12-26","reason":"Neet Class"}]}');
        component.onCreateForWeekendWorking(event);
        expect(component.schoolDetail.classList.length == 3);
    });

    it('Should edit the existing weekend working day', () => {
        component.ngOnInit();
        const event = {
            newData: { id: "3161", workingDate: "2019-01-01", reason: "Neet specail class" },
            source: { data: { id: "3161", workingDate: "2019-01-01", reason: "Neet specail class" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        component.schoolDetail = JSON.parse('{"id":313,"userId":null,"action":null,"schoolName":"Coimbatore Government Hr Sec School","district":"COIMBATORE","state":"TAMIL NADU","cityName":"COIMBATORE","address":"COIMBATORE","classList":[{"id":487,"className":"I","sectionName":"A"},{"id":488,"className":"I","sectionName":"B"}],"perfParamList":[{"id":487,"paramTitle":"Attendance","paramDesc":"Attendance"},{"id":488,"paramTitle":"HomeWork","paramDesc":"HomeWork"},{"id":489,"paramTitle":"Discipline","paramDesc":"Discipline"}],"holidays":[{"id":3161,"fromDate":"2019-01-01","toDate":"2019-01-01","description":"New Year’s Day"}, {"id":3162,"fromDate":"2019-12-25","toDate":"2019-12-25","description":"Christmas day"}], "weekendWorkingDays":[{"id":487,"workingDate":"2019-12-25","reason":"Special Class"},{"id":488,"workingDate":"2019-12-26","reason":"Neet Class"}]}');
        component.onEditForWeekendWorking(event);
        expect(component.schoolDetail.classList.length == 2);
    });

    it('Should delete the existing weekend working day', () => {
        component.ngOnInit();
        const event = {
            newData: { id: "488", workingDate: "2019-12-26", reason: "Neet Class" },
            source: { data: { id: "488", workingDate: "2019-12-26", reason: "Neet Class" } },
            confirm: {
                resolve: function resolveFunction() {
                },
                reject: function resolveFunction() {
                }
            }
        };
        component.schoolDetail = JSON.parse('{"id":313,"userId":null,"action":null,"schoolName":"Coimbatore Government Hr Sec School","district":"COIMBATORE","state":"TAMIL NADU","cityName":"COIMBATORE","address":"COIMBATORE","classList":[{"id":487,"className":"I","sectionName":"A"},{"id":488,"className":"I","sectionName":"B"}],"perfParamList":[{"id":487,"paramTitle":"Attendance","paramDesc":"Attendance"},{"id":488,"paramTitle":"HomeWork","paramDesc":"HomeWork"},{"id":489,"paramTitle":"Discipline","paramDesc":"Discipline"}],"holidays":[{"id":3161,"fromDate":"2019-01-01","toDate":"2019-01-01","description":"New Year’s Day"}, {"id":3162,"fromDate":"2019-12-25","toDate":"2019-12-25","description":"Christmas day"}], "weekendWorkingDays":[{"id":487,"workingDate":"2019-12-25","reason":"Special Class"},{"id":488,"workingDate":"2019-12-26","reason":"Neet Class"}]}');
        spyOn(window, 'confirm').and.returnValue(true);
        component.onDeleteForWeekendWorking(event);
        expect(component.schoolDetail.classList.length == 1);
    });

});
