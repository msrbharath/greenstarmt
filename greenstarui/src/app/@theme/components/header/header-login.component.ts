import { Component, Input, OnInit } from '@angular/core';

import { NbMenuService, NbSidebarService } from '@nebular/theme';
import { UserService } from '../../../@core/data/users.service';
import { AnalyticsService } from '../../../@core/utils/analytics.service';

@Component({
  selector: 'ngx-header-login',
  styleUrls: ['./header.component.scss'],
  templateUrl: './header-login.component.html',
})
export class HeaderLoginComponent implements OnInit {

  constructor() {
  }

  ngOnInit() {
  }

}
