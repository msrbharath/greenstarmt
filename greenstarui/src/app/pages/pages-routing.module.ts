import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { PagesComponent } from './pages.component';
import { PerformanceDataComponent } from './performance/data/performance-data.component';
import { PerformanceMetricsComponent } from './performance/metrics/performance-metrics.component';
import { PerformanceStarComponent } from './performance/star/performance-star.component';
import { SchoolListComponent } from './school/school-list.component';
import { StudentListComponent } from './student/student-list.component';
import { AuthGuardService } from './util/auth-guard.service';

export const PAGE_ROUTES: Routes = [
  {
    path: '', component: PagesComponent,
    children: [
      { path: '', component: DashboardComponent },
      { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuardService] },
      { path: 'school', component: SchoolListComponent, canActivate: [AuthGuardService] },
      { path: 'student', component: StudentListComponent, canActivate: [AuthGuardService] },
      { path: 'performancedata', component: PerformanceDataComponent, canActivate: [AuthGuardService] },
      { path: 'performancemetrics', component: PerformanceMetricsComponent, canActivate: [AuthGuardService] },
      { path: 'performancestar', component: PerformanceStarComponent, canActivate: [AuthGuardService] },
      { path: 'admin', component: AdminComponent, canActivate: [AuthGuardService] }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(PAGE_ROUTES)],
  exports: [RouterModule],
})
export class PagesRoutingModule {
}
