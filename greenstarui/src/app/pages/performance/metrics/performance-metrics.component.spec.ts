import { async, ComponentFixture, TestBed, flushMicrotasks, tick, fakeAsync } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { NbDialogModule, NbSpinnerModule, NbStepperModule, NbTooltipModule, NbTooltipDirective } from '@nebular/theme';
import { ThemeModule } from '../../../@theme/theme.module';
import { PerformanceStarServiceMock } from '../star/mocks/performance-star.service.mock';
import { PerformanceStarService } from '../star/performance-star.service';
import { PerformanceMetricsServiceMock } from './mocks/performance-metrics.service.mock';
import { PerformanceMetricsComponent } from './performance-metrics.component';
import { PerformanceMetricsService } from './performance-metrics.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


// To test the performance metrics component 
describe('PerformanceMetricsComponent', () => {
  let originalTimeout;
  let component: PerformanceMetricsComponent;
  let fixture: ComponentFixture<PerformanceMetricsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [PerformanceMetricsComponent],
      providers: [{ provide: PerformanceStarService, useClass: PerformanceStarServiceMock },
        { provide: PerformanceMetricsService, useClass: PerformanceMetricsServiceMock }],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        ThemeModule,
        NbStepperModule,
        NbSpinnerModule,
        NbTooltipModule,
        NbDialogModule.forRoot(),
        RouterModule.forRoot([])
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PerformanceMetricsComponent);
    component = fixture.componentInstance;
    originalTimeout = jasmine.DEFAULT_TIMEOUT_INTERVAL;
    jasmine.DEFAULT_TIMEOUT_INTERVAL = 30000;
  });

  afterEach(function () {
      jasmine.DEFAULT_TIMEOUT_INTERVAL = originalTimeout;
  });
  
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display error on individual metrics search fields empty', fakeAsync(() => {    
      component.ngOnInit();
      fixture.detectChanges();
      component.viewIndividualPerformanceMetrics();
      tick(2000);
      flushMicrotasks();
      fixture.detectChanges();      
      expect(component.isSearchDataNotValid === true);
  }));

  
  it('should display error on classwise metrics search fields empty', fakeAsync(() => {
    
      component.ngOnInit();
      fixture.autoDetectChanges();
      component.viewClasswisePerformanceMetrics();
      fixture.detectChanges();
      tick(2000);
      flushMicrotasks();
      expect(component.isClasswiseSearchDataNotValid === true);
  }));

  it('should display error on teamwise metrics search fields empty', fakeAsync(() => {
   
      component.ngOnInit();
      fixture.detectChanges();
      component.viewTeamwisePerformanceMetrics();
      tick(2000);
      flushMicrotasks();
      fixture.detectChanges();
      expect(component.isTeamwiseSearchDataNotValid === true);
  }));

  it('should display error on encouraging metrics search fields empty', fakeAsync(() => {
    
      component.ngOnInit();
      fixture.detectChanges();
      component.viewEncouragingPerformanceMetrics();
      tick(2000);
      flushMicrotasks();
      fixture.detectChanges();
      expect(component.isEncouragingSearchDataNotValid === true);
  }));

   it('should populate class dropdown on school change in Indvidual metrics screen', fakeAsync(() => {
      component.ngOnInit();
      fixture.detectChanges();
      component.perfMetricsForm.controls['schoolId'].setValue(313);
      component.loadIndividualClassDetailsBySchool();
      tick(2000);
      flushMicrotasks();
      fixture.detectChanges();
      expect(component.classList.length > 0);
  }));

  it('should populate class dropdown on school change in classwiese metrics screen', fakeAsync(() => {
      component.ngOnInit();
      fixture.detectChanges();
      component.classPerfMetricsForm.controls['schoolId'].setValue(313);
      component.loadClasswiseClassDetailsBySchool();
      tick(2000);
      flushMicrotasks();
      fixture.detectChanges();
      expect(component.classList.length > 0);
  }));

  it('should populate class dropdown on school change in teamwise metrics screen', fakeAsync(() => {
      component.ngOnInit();
      fixture.detectChanges();
      component.teamPerfMetricsForm.controls['schoolId'].setValue(313);
      component.loadTeamwiseClassDetailsBySchool();
      tick(2000);
      flushMicrotasks();
      fixture.detectChanges();
      expect(component.classList.length > 0);
  }));

  it('should populate class dropdown on school change in encouraging metrics screen', fakeAsync(() => {
      component.ngOnInit(); 
      fixture.detectChanges();
      component.encouragingPerfMetricsForm.controls['schoolId'].setValue(313);
      component.loadEncouragingClassDetailsBySchool();
      tick(2000);
      flushMicrotasks();
      fixture.detectChanges();
      expect(component.classList.length > 0);
  }));
  
  /*
  it('should populate populate individual metrics on clicking the view individual metrics', async(() => {
      component.ngOnInit();
      fixture.detectChanges();
      component.perfMetricsForm.controls['schoolId'].setValue(313);
      component.perfMetricsForm.controls['classId'].setValue(487);
      component.perfMetricsForm.controls['month'].setValue(1);
      component.perfMetricsForm.controls['week'].setValue(1);
      fixture.detectChanges();
      component.viewIndividualPerformanceMetrics();      
      component.viewClasswisePerformanceMetrics();
      component.viewTeamwisePerformanceMetrics();
      component.viewEncouragingPerformanceMetrics();
      fixture.detectChanges();
      expect(component.performanceMetricsSource != null);      
  }));
  /*
  it('should populate populate classwise metrics on clicking the view classwise metrics', fakeAsync(() => {
      component.ngOnInit();
      fixture.detectChanges();
      component.classPerfMetricsForm.controls['schoolId'].setValue(313);
      component.classPerfMetricsForm.controls['classId'].setValue(487);
      fixture.detectChanges();
      component.viewClasswisePerformanceMetrics();
      tick(2000);
      flushMicrotasks();
      fixture.detectChanges();
      expect(component.classWiseMetricsSource != null );
  }));
  it('should populate populate teamwise metrics on clicking the view teamwise metrics', fakeAsync(() => {
      component.ngOnInit();
      fixture.detectChanges();
      component.teamPerfMetricsForm.controls['schoolId'].setValue(313);
      component.teamPerfMetricsForm.controls['classId'].setValue(487);
      component.teamPerfMetricsForm.controls['month'].setValue(1);
      component.teamPerfMetricsForm.controls['week'].setValue(1);
      fixture.detectChanges();
      component.viewTeamwisePerformanceMetrics();
      tick(2000);
      flushMicrotasks();
      fixture.detectChanges();
      expect(component.teamWiseMetricsSource != null );
  }));
  it('should populate populate encouraging metrics on clicking the compare metrics', fakeAsync(() => {
      component.ngOnInit();
      fixture.detectChanges();
      component.encouragingPerfMetricsForm.controls['schoolId'].setValue(313);
      component.encouragingPerfMetricsForm.controls['classId'].setValue(487);
      component.encouragingPerfMetricsForm.controls['month1'].setValue(1);
      component.encouragingPerfMetricsForm.controls['month2'].setValue(2);
      fixture.detectChanges();
      component.viewEncouragingPerformanceMetrics();
      tick(2000);
      flushMicrotasks();
      fixture.detectChanges();
      expect(component.encouragingMetricsSource != null);
  }));

  it('Should load the week dropdown without search param', fakeAsync(() => {
      component.ngOnInit();
      component.populateWeekWorkingDays();
      tick(2000);
      flushMicrotasks();
      fixture.detectChanges();
      expect(component.weekDays.size <= 0);
  }));

  /*
  it('Should load the week dropdown with search param', fakeAsync(() => {
    component.ngOnInit();
    component.perfMetricsForm.controls['schoolId'].setValue(313);
    component.perfMetricsForm.controls['classId'].setValue(487);
    component.perfMetricsForm.controls['month'].setValue(1);
    component.populateWeekWorkingDays();
    tick(2000);
    flushMicrotasks();
    fixture.detectChanges();
    expect(component.weekDays.size >= 0);
}));
*/


});
