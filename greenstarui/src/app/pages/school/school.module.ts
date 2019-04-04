import { NgModule } from '@angular/core';
import { NbDialogModule } from '@nebular/theme';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { SmartTableDatePickerComponent } from '../../@theme/components/smart-table-date-picker-component/smart-table-date-picker.components';
import { ThemeModule } from '../../@theme/theme.module';
import { RoleService } from '../common/role.service';
import { SchoolListComponent } from './school-list.component';
import { SchoolMessageModalComponent } from './school-message.modal.component';
import { SchoolComponent } from './school.component';
import { SchoolService } from './school.service';

@NgModule({
  imports: [
    ThemeModule,
    Ng2SmartTableModule,
    NbDialogModule.forRoot()
  ],
  declarations: [
    SchoolListComponent,
    SchoolComponent,
    SchoolMessageModalComponent
  ],
  entryComponents: [
    SchoolComponent,
    SmartTableDatePickerComponent,
    SchoolMessageModalComponent
  ],
  providers: [
    RoleService, SchoolService
  ]
})
export class SchoolModule { }
