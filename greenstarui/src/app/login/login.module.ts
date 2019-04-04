import { NgModule } from '@angular/core';

import { LoginComponent } from './login.component';
import { Routes } from '@angular/router';

import { RouterModule } from '@angular/router';
import { LoginRoutingModule } from './login.routing.module';
import { PagesLoginComponent } from './pages-login.component';
import { ThemeModule } from '../@theme/theme.module';
import { LoginService } from './login.service';
import { NbSpinnerModule } from '@nebular/theme';
import { SessionoutDialogModalComponent } from './sessionout.modal.component';

const LOGIN_PAGES_COMPONENTS = [
  PagesLoginComponent, LoginComponent, SessionoutDialogModalComponent
];

@NgModule({
  imports: [
    ThemeModule,
    LoginRoutingModule,
    NbSpinnerModule
  ],
  declarations: [
    ...LOGIN_PAGES_COMPONENTS,
  ],
  providers: [
    LoginService
  ],
  entryComponents: [
    SessionoutDialogModalComponent
  ],
})
export class LoginModule { }
