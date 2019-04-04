import { browser, by, element, promise, ElementFinder, ElementArrayFinder } from 'protractor';

export class LoginPage {
    
    private credentias = {
        username: 'panneer',
        password: 'panneer'
    };

    public fillCredentials(credentias: any = this.credentias) {
        element(by.id('userId')).sendKeys(credentias.username);
        element(by.id('password')).sendKeys(credentias.password);
        element(by.css('.btn-primary')).click();
    }

    public navigateToLogin(): promise.Promise<any> {
        return browser.get('/greenstarui/login');
    }

    public getPageTitleText() {
        return element(by.id('loginHeader')).getText();
    }

    public getErrorMessage() {
        return element(by.css('.errormsg')).getText();
    }

}