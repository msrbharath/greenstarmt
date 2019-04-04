import { browser } from 'protractor';
import { LoginPage } from './login-page.po';
import { protractor } from 'protractor/built/ptor';
import { DashboardPage } from '../dashboard/dashboard-page.po';

describe(' Login Page', () => {
    const page = new LoginPage();
    const dashboard = new DashboardPage();

    beforeEach(() => {

        var origFn = browser.driver.controlFlow().execute;
        browser.driver.controlFlow().execute = function () {
            var args = arguments;
            // queue 100 ms wait
            origFn.call(browser.driver.controlFlow(), function () {
                return protractor.promise.delayed(200);   // here we can adjust the execution speed
            });
            return origFn.apply(browser.driver.controlFlow(), args);
        };

        page.navigateToLogin();
    });
    /*
    it('When user trying to login with wrong credentials he should stay on “login” page and see error notification', () => {
        const credentias = {
            username: 'test',
            password: 'test'
        };
        page.fillCredentials(credentias);
        expect(page.getPageTitleText()).toEqual('Login');      
        expect(page.getErrorMessage()).toEqual('Invalid User Id and Password.');
    });
    */
    it('When login is successful — he should redirect to default “Dashboard” page', () => {
        page.fillCredentials();
        expect(dashboard.getPageTitleText()).toEqual('Schools using Greenstar Application every month');      
    });



});