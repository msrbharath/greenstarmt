import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'ngbd-modal-content',
  template: `
    <div class="modal-header">
      <h4 class="modal-title">{{modalheadertext}}</h4>
      <button type="button" class="close" aria-label="Close" (click)="activeModal.dismiss('Cross click')">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
      <p>{{modalmessage}}!</p>
    </div>
  `
})
export class SchoolMessageModalComponent {
  @Input() modalmessage;
  @Input() modalheadertext;
  constructor(public activeModal: NgbActiveModal) {}
}