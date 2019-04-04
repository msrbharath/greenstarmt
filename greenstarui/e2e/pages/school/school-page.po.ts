import { browser, by, element, promise, ElementFinder, ElementArrayFinder } from 'protractor';

export class SchoolPage {

    public getSchoolNavigationButton(): void {
        element(by.xpath("//a[@href='/greenstarui/pages/school']")).click();
    }

    public addSchoolDialogbox(): void {
        element(by.id('addSchool')).click();
    }

    public getSchoolPopupTitleText() {
        return element(by.id('schoolTitle')).getText();
    }

}