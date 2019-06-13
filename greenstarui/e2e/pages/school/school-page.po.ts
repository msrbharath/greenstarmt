import { by, element } from 'protractor';

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

    public setSearchSchoolParam() {
        element(by.id('state')).sendKeys('TAMIL NADU');
        element(by.id('district')).sendKeys('COIMBATORE');        
    }

}