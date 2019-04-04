import { NgModule } from '@angular/core';
import { NbDialogModule, NbSpinnerModule, NbStepperModule } from '@nebular/theme';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { ThemeModule } from '../../@theme/theme.module';
import { CommonService } from '../common/common.service';
import { DashboardService } from '../dashboard/dashboard.service';
import { PerformanceDataSuccessModalComponent } from './data/performance-data-success.component.modal';
import { PerformanceDataUploadModalComponent } from './data/performance-data-upload.component.modal';
import { PerformanceDataComponent } from './data/performance-data.component';
import { PerformanceDataService } from './data/performance-data.service';
import { PerformanceMetricsComponent } from './metrics/performance-metrics.component';
import { PerformanceMetricsService } from './metrics/performance-metrics.service';
import { GreenstarComponent } from './star/greenstar/greenstar.component';
import { PerformanceStarComponent } from './star/performance-star.component';
import { PerformanceGenerateStarService } from './star/performance-star.generate.service';
import { PerformanceStarService } from './star/performance-star.service';

@NgModule({
  imports: [
    ThemeModule,
    Ng2SmartTableModule,
    NbStepperModule,
    NbSpinnerModule,
    NbDialogModule.forRoot()
  ],
  declarations: [
    PerformanceDataComponent,
    PerformanceMetricsComponent,
    PerformanceStarComponent,
    PerformanceDataUploadModalComponent,
    GreenstarComponent,
    PerformanceDataSuccessModalComponent
  ],
  entryComponents: [
    PerformanceDataUploadModalComponent,
    GreenstarComponent,
    PerformanceDataSuccessModalComponent
  ],
  providers: [
    PerformanceDataService,
    PerformanceMetricsService,
    PerformanceStarService,
    PerformanceGenerateStarService,
    CommonService,
    DashboardService
  ]
})
export class PerformanceModule { }
