import { Component, OnInit } from '@angular/core';
import { DashboardService } from './dashboard.service';

@Component({
  selector: 'ngx-dashboard',
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent implements OnInit {

  public totalNoOfSchools: String;

  view: any[] = [800, 400];
  // options for the chart
  showXAxis = true;
  showYAxis = true;
  gradient = false;
  showLegend = true;
  showXAxisLabel = true;
  xAxisLabelForSchoolMonth = 'Month';
  yAxisLabelForSchoolMonth = 'Number of Schools using Greenstar application';
  xAxisLabel = 'Outreach Performers';
  showYAxisLabel = true;
  yAxisLabel = 'Contributions';
  timeline = true;

  colorScheme = {
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA', '#ADD8E6']
  };

  // line, area
  autoScale = true;

  //pie
  showLabels = true;
  explodeSlices = false;
  doughnut = false;
  public schoolByMonthSingle: Array<any>;
  public topSchoolByMonthSingle: Array<any>;
  public topPerformingVolunteersSingle: Array<any>;

  constructor(
    private dashboardService: DashboardService) {
    this.view = [innerWidth / 1.3, 400];
  }

  ngOnInit(): void {
    this.getTotalNoOfSchools();
    this.getSchoolByMonthMetrics();
    this.getTopPerformingSchools();
    this.getTopPerformingVolunteers();
  }

  onResize(event) {
    this.view = [event.target.innerWidth / 1.35, 400];
  }

  private getTotalNoOfSchools(): void {
    this.dashboardService.getTotalNoOfSchools().subscribe((response) => {
      this.totalNoOfSchools = response.result;
    }, error => {
      console.log("Http Server error", error);
    });
  }

  private getTopPerformingSchools(): void {
    this.dashboardService.getTopPerformingSchools().subscribe((response) => {
      this.topSchoolByMonthSingle = response.result;
    }, error => {
      console.log("Http Server error", error);
    });
  }

  private getTopPerformingVolunteers(): void {
    this.dashboardService.getTopPerformingVolunteers().subscribe((response) => {
      this.topPerformingVolunteersSingle = response.result;
    }, error => {
      console.log("Http Server error", error);
    });
  }

  private getSchoolByMonthMetrics() {
    this.dashboardService.getSchoolByMonthMetrics().subscribe((response) => {
      this.schoolByMonthSingle = response.result;
    }, error => {
      console.log("Http Server error", error);
    });
  }
}
