import { browser } from 'protractor';

import { protractor } from 'protractor/built/ptor';
import { DashboardPage } from '../dashboard/dashboard-page.po';
import { LoginPage } from '../login/login-page.po';
import { SchoolPage } from './school-page.po';

describe('School Page', () => {
    const page = new SchoolPage();
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
        
    });

    it('When user click on school menu and it nvigated to school page.', () => {
        page.getSchoolNavigationButton();        
        expect(browser.driver.getCurrentUrl()).toContain('/school');
    });

    it('When user try to add new school to click add school button, show popup dialog', () => {
        page.addSchoolDialogbox();        
        expect(browser.driver.getCurrentUrl()).toContain('/school');
    });


});