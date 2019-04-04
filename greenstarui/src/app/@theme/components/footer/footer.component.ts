import { Component } from '@angular/core';

@Component({
  selector: 'ngx-footer',
  styleUrls: ['./footer.component.scss'],
  template: `
    <span class="created-by">Cognizant &copy; 2019</span>
    <div>      
      <img src="../../assets/images/cognizant1.png" width="180" height="50">
    </div>
  `,
})
export class FooterComponent {
}
