import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PerformanceStarComponent } from './performance-star.component';
import { ThemeModule } from '../../../@theme/theme.module';
import { NbDialogModule, NbStepperModule, NbSpinnerModule } from '@nebular/theme';
import { PerformanceStarService } from './performance-star.service';
import { GreenstarComponent } from '../star/greenstar/greenstar.component'
import { PerformanceGenerateStarService } from './performance-star.generate.service';
import { PerformanceStarServiceMock } from './mocks/performance-star.service.mock';
import { PerformanceStarGenerateServiceMock } from './mocks/performance-star.generate.service.mock';
import { By } from '@angular/platform-browser';

// To test the green star component 
describe('GreenstarComponent', () => {
  let component: PerformanceStarComponent;
  let fixture: ComponentFixture<PerformanceStarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [PerformanceStarComponent, GreenstarComponent],
      providers: [{ provide: PerformanceStarService, useClass: PerformanceStarServiceMock },
      { provide: PerformanceGenerateStarService, useClass: PerformanceStarGenerateServiceMock }],
      imports: [
        ThemeModule,
        NbStepperModule,
        NbSpinnerModule,
        NbDialogModule.forRoot()
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PerformanceStarComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display error on individual empty', () => {
    component.ngOnInit();
    fixture.detectChanges();
    component.generatePerformanceStar();
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('span').textContent).toContain('All fields are mandatory to generate star!');
  });

  it('should display team dropdown on team calctype', () => {
    component.ngOnInit();
    fixture.detectChanges();
    component.searchPerformanceStarData.calcType = 'Team';
    component.onChangeCalcType("Team");
    fixture.detectChanges();
    expect(fixture.debugElement.query(By.css('#tname')).name == "tname");
  });

  it('should display class dropdown on class calctype', () => {
    component.ngOnInit();
    fixture.detectChanges();
    component.searchPerformanceStarData.calcType = 'Class';
    component.onChangeCalcType("Class");
    fixture.detectChanges();
    expect(fixture.debugElement.query(By.css('#className')).name == "className");
  });

  it('should set the team value on team change', () => {
    component.ngOnInit();
    component.searchPerformanceStarData.calcType = 'Team';
    component.onChangeCalcType("Team");
    component.searchPerformanceStarData.schoolId = 313;
    component.onChangeSchoolChange();
    component.searchPerformanceStarData.classId = 487;
    component.onChangeClassChange();
    component.searchPerformanceStarData.teamName = 'paalai';
    component.onChangeTeamChange();
    expect(component.teamName == 'paalai');
  });

  it('should create drop downs, its values, generate star and print', () => {
    component.ngOnInit();
    fixture.detectChanges();
    component.searchPerformanceStarData.calcType = 'Individual';
    component.onChangeCalcType("Individual");
    fixture.detectChanges();
    // Set autodetect is tru so that setting values will get reflected in UI then and there
    //Check data for school dropdown is populated
    expect(component.schoolList.length > 0);
    // Check the school dropdown is populated in UI
    expect(fixture.debugElement.query(By.css('#school')).name == "school");
    //Set the school dropdown value
    component.searchPerformanceStarData.schoolId = 313;
    //Trigger school dropdown change
    component.onChangeSchoolChange();
    fixture.detectChanges();
    //Check if the class dropdown data is populated
    expect(component.classList.length > 0);
    //Check if class dropdown is populated in UI
    expect(fixture.debugElement.query(By.css('#className')).name == "className");
    //Set the class id
    component.searchPerformanceStarData.classId = 487;
    //Trigger class dropdown change
    component.onChangeClassChange();
    fixture.detectChanges();
    // Check if student dropdown is rendered
    expect(fixture.debugElement.query(By.css('#sname')).name == "sname");
    // Set the student id
    component.searchPerformanceStarData.studentId = 4841;
    //Trigger student onchange
    component.onChangeStudentChange();
    fixture.detectChanges();
    //Check if month dropdown is rendered
    expect(fixture.debugElement.query(By.css('#month')).name == "month");
    //Set month id
    component.searchPerformanceStarData.month = 1;
    //Trigger student onchange
    component.onChangeMonthChange();
    fixture.detectChanges();
    component.generatePerformanceStar();
    fixture.detectChanges();
    // Check star data is populated
    expect(component.performanceStarData.paramOneMonthColorCodes.length > 0);
    // Check if star rendered
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h6').textContent).toContain('School: Coimbatore Government Hr Sec School');

    component.printStar();
  });


  it('Empty star data on non existing school id', () => {
    component.ngOnInit();
    component.searchPerformanceStarData.calcType = 'Individual';
    component.onChangeCalcType("Individual");
    component.searchPerformanceStarData.schoolId = 1;
    component.onChangeSchoolChange();
    component.searchPerformanceStarData.classId = 487;
    component.onChangeClassChange();
    component.searchPerformanceStarData.studentId = 4841;
    component.onChangeStudentChange();
    component.searchPerformanceStarData.month = 1;
    component.onChangeMonthChange();
    component.generatePerformanceStar();
    expect(component.performanceStarData.paramOneMonthColorCodes == null);
  });

  it('should display error on any of the dropdown is select', () => {
    component.ngOnInit();
    component.searchPerformanceStarData.calcType = 'Team';
    component.onChangeCalcType("Team");
    component.searchPerformanceStarData.schoolId = 313;
    component.onChangeSchoolChange();
    component.searchPerformanceStarData.classId = 487;
    component.onChangeClassChange();
    component.searchPerformanceStarData.teamName = 'Select';
    component.onChangeTeamChange();
    component.generatePerformanceStar();
    fixture.detectChanges();
    let compiled = fixture.debugElement.nativeElement;
    // If team is select then display error
    expect(compiled.querySelector('span').textContent).toContain('All fields are mandatory to generate star!');
    component.searchPerformanceStarData.teamName = 'paalai';
    component.onChangeTeamChange();
    component.generatePerformanceStar();
    fixture.detectChanges();
    compiled = fixture.debugElement.nativeElement;
    // If month is select then display error
    expect(compiled.querySelector('span').textContent).toContain('All fields are mandatory to generate star!');

    //Search error on individual and school is empty
    component.ngOnInit();
    component.searchPerformanceStarData.calcType = 'Individual';
    component.onChangeCalcType("Individual");
    component.searchPerformanceStarData.schoolId = 0;
    component.generatePerformanceStar();
    fixture.detectChanges();
    compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('span').textContent).toContain('All fields are mandatory to generate star!');

    //Search error on class and school is empty
    component.ngOnInit();
    component.searchPerformanceStarData.calcType = 'Class';
    component.onChangeCalcType("Class");
    component.searchPerformanceStarData.schoolId = 0;
    component.generatePerformanceStar();
    fixture.detectChanges();
    compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('span').textContent).toContain('All fields are mandatory to generate star!');

    //Search error on class and class is empty
    component.ngOnInit();
    component.searchPerformanceStarData.calcType = 'Class';
    component.onChangeCalcType("Class");
    component.searchPerformanceStarData.schoolId = 313;
    component.searchPerformanceStarData.classId = 0;
    component.generatePerformanceStar();
    fixture.detectChanges();
    compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('span').textContent).toContain('All fields are mandatory to generate star!');

    //Search error on class and month is empty
    component.ngOnInit();
    component.searchPerformanceStarData.calcType = 'Class';
    component.onChangeCalcType("Class");
    component.searchPerformanceStarData.schoolId = 313;
    component.searchPerformanceStarData.classId = 487;
    component.searchPerformanceStarData.month = 0;
    component.generatePerformanceStar();
    fixture.detectChanges();
    compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('span').textContent).toContain('All fields are mandatory to generate star!');

    //Search error on School and school is empty
    component.ngOnInit();
    component.searchPerformanceStarData.calcType = 'School';
    component.onChangeCalcType("School");
    component.searchPerformanceStarData.schoolId = 0;
    component.generatePerformanceStar();
    fixture.detectChanges();
    compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('span').textContent).toContain('All fields are mandatory to generate star!');

    //Search error on School and month is empty
    component.ngOnInit();
    component.searchPerformanceStarData.calcType = 'School';
    component.onChangeCalcType("School");
    component.searchPerformanceStarData.schoolId = 313;
    component.searchPerformanceStarData.month = 0;
    component.generatePerformanceStar();
    fixture.detectChanges();
    compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('span').textContent).toContain('All fields are mandatory to generate star!');

  });

  it('should display school dropdown on school calctype', () => {
    component.ngOnInit();
    fixture.detectChanges();
    component.searchPerformanceStarData.calcType = 'School';
    component.onChangeCalcType("School");
    fixture.detectChanges();
    expect(fixture.debugElement.query(By.css('#school')).name == "school");
  });
});
