import { NgModule } from '@angular/core';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { NbDialogModule } from '@nebular/theme';

import { ThemeModule } from '../../@theme/theme.module';
import { SmartTableDatePickerComponent } from '../../@theme/components/smart-table-date-picker-component/smart-table-date-picker.components';
import { AdminComponent } from './admin.component';
import { AdminService } from './admin.service';
import { AdminModalComponent } from './admin.component.modal';

@NgModule({
  imports: [
    ThemeModule,
    Ng2SmartTableModule,
    NbDialogModule.forRoot()
  ],
  declarations: [
    AdminComponent,
    AdminModalComponent
  ],
  entryComponents: [
    SmartTableDatePickerComponent,
    AdminModalComponent
  ],
  providers: [
    AdminService
  ]
})
export class AdminModule { }
