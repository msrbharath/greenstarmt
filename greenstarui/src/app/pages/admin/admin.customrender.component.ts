import { Component, Input, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { ISchoolDetail } from '../student/student.interface';
import { ViewCell, Cell, DefaultEditor, Editor } from 'ng2-smart-table';

@Component({
  template: `<div *ngIf="isEventPOC"><ng-multiselect-dropdown
  [placeholder]="'Select Schools'"
  [data]="dropdownList"
  [(ngModel)]="selectedItems"
  [settings]="dropdownSettings"
  (onSelect)="onItemSelect($event)"
  (onSelectAll)="onSelectAll($event)"
  (onDeSelect)="onDeSelect($event)"
  (onDropdownClose)="onDropdownClose($event)"
>
</ng-multiselect-dropdown> </div>`,
})
export class CustomRenderComponent extends DefaultEditor implements ViewCell, OnInit, AfterViewInit {

  @ViewChild('name') name: ElementRef;
  @ViewChild('url') url: ElementRef;
  @ViewChild('htmlValue') htmlValue: ElementRef;

  renderValue: string;

  @Input() value: string;
  @Input() rowData: any;

  public schoolList: ISchoolDetail[] = [];

  public isEventPOC: boolean = false;

  selectedItems = [];

  schoolIds = [];
  schoolNames = [];
  dropdownSettings = {};
  originalValue = '';
  
  finalSelection = [];
  
  dropdownList = [];

  constructor() {
    super();
  }

   ngOnInit() {
    let selectedRow = this.cell.getRow();
    // Display school selection only for event poc
    if (selectedRow.getData().roleName == "Event POC") {
      this.isEventPOC = true;
      this.dropdownList = JSON.parse(localStorage.getItem('schools'));

      this.dropdownSettings = {
        singleSelection: false,
        idField: 'id',
        textField: 'schoolName',
        selectAllText: 'Select All',
        unSelectAllText: 'UnSelect All',
        itemsShowLimit: 1,
        allowSearchFilter: true
      };

      const schoolIds = selectedRow.getData().schoolIds.split(',');

      for (let i = 0; i < schoolIds.length; i++) {
        this.dropdownList.forEach(school => {
          if (school.id + '' == schoolIds[i]) {
            this.selectedItems.push(school);
            this.schoolIds.push(school.id);
            this.schoolNames.push(school.schoolName);
          }
        });
      }
      this.finalSelection = this.selectedItems;
    }
  }

  ngAfterViewInit() {
   /** if (this.cell.newValue !== '') {
      this.name.nativeElement.value = this.getUrlName();
      this.url.nativeElement.value = this.getUrlHref();
    }*/ 
  }

  updateValue() {
    const href = this.url.nativeElement.value;
    const name = this.name.nativeElement.value;
    this.cell.newValue = `<a href='${href}'>${name}</a>`;
  }

  getUrlName(): string {
    return this.htmlValue.nativeElement.innerText;
  }

  getUrlHref(): string {
    return this.htmlValue.nativeElement.querySelector('a').getAttribute('href');
  }

  onItemSelect(item: any) {
    console.log(item);
    this.finalSelection.push(item);
  }
  onSelectAll(items: any) {
    console.log(items);
  }
  onDeSelect(item: any) {
    console.log(item);
    this.finalSelection = [];
    for (let i = 0; i < this.selectedItems.length; i++) {
       if(this.selectedItems[i].id != item.id){
        this.finalSelection.push(this.selectedItems[i]);
       }
    }
  }
  onDropdownClose(){
    console.log('closed!!!');
  }
}