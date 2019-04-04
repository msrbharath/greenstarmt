import { NgModule } from '@angular/core';
import { ExtraOptions, RouterModule, Routes } from '@angular/router';

const parentRoutes: Routes = [
  {
    path: 'greenstarui',
    children: [
      { path: 'login', loadChildren: 'app/login/login.module#LoginModule' },
      { path: 'logout', loadChildren: 'app/login/login.module#LoginModule' },
      { path: 'pages', loadChildren: 'app/pages/pages.module#PagesModule' },
    ]
  },
  { path: '', redirectTo: 'greenstarui/login', pathMatch: 'full' },
];

const config: ExtraOptions = {
  //useHash: true,
  enableTracing: true
};

@NgModule({
  imports: [RouterModule.forRoot(parentRoutes, config)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
