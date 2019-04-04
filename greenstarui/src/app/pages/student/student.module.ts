import { NgModule } from '@angular/core';
import { NbDialogModule } from '@nebular/theme';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { ThemeModule } from '../../@theme/theme.module';
import { RoleService } from '../common/role.service';
import { StudentBulkUploadModalComponent } from './student-bulk-upload.component.modal';
import { StudentListComponent } from './student-list.component';
import { StudentService } from './student.service';

@NgModule({
  imports: [
    ThemeModule,
    Ng2SmartTableModule,
    NbDialogModule.forRoot()
  ],
  declarations: [
    StudentListComponent,
    StudentBulkUploadModalComponent
  ],
  entryComponents: [
    StudentBulkUploadModalComponent
  ],
  providers: [
    RoleService, StudentService
  ]
})
export class StudentModule { }
