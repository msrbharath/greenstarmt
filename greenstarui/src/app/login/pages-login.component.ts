import { Component } from '@angular/core';

@Component({
  selector: 'ngx-pages',
  template: `
    <ngx-login-layout>
      <router-outlet></router-outlet>
    </ngx-login-layout>
  `
})
export class PagesLoginComponent {

}
