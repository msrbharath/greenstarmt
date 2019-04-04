import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { UserIdleService } from "angular-user-idle";

@Component({
  selector: 'ngx-modal',
  template: `
    <div class="modal-header">
      <span>Alert</span>
      <button class="close" aria-label="Close" (click)="closeModal()">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
      {{ modalContent }}
    </div>
    <div class="modal-footer">
      <button class="btn btn-md btn-primary" (click)="sessionContinue()">Continue</button>
      <button class="btn btn-md btn-primary" (click)="closeModal()">Login Again</button>
    </div>
  `,
})
export class SessionoutDialogModalComponent {

  public modalContent: string;

  constructor(private activeModal: NgbActiveModal, private router: Router, private userIdle: UserIdleService) { }

  public closeModal(): void {
    localStorage.clear();    
    this.activeModal.close();
    this.router.navigate(['']);
  }

  public sessionContinue(): void {
    this.userIdle.resetTimer();
    this.activeModal.close();
  }

}