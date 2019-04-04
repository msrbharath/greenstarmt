import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login.component';
import { PagesLoginComponent } from './pages-login.component';

const LOGIN_ROUTES: Routes = [{
    path: '',
    component: PagesLoginComponent,
    children: [
        { path: '', component: LoginComponent },
        { path: 'login', component: LoginComponent }
    ],
}];

@NgModule({
    imports: [
        RouterModule.forChild(LOGIN_ROUTES),
    ],
    exports: [
        RouterModule
    ],
})
export class LoginRoutingModule {

}
