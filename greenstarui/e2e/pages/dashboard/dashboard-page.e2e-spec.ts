import { browser } from 'protractor';
import { protractor } from 'protractor/built/ptor';
import { DashboardPage } from '../dashboard/dashboard-page.po';
import { LoginPage } from '../login/login-page.po';

describe('Dashboard Page', () => {

    const login = new LoginPage();
    const dashboard = new DashboardPage();

    beforeEach(() => {

        var origFn = browser.driver.controlFlow().execute;
        browser.driver.controlFlow().execute = function () {
            var args = arguments;
            // queue 100 ms wait
            origFn.call(browser.driver.controlFlow(), function () {
                return protractor.promise.delayed(20);   // here we can adjust the execution speed
            });
            return origFn.apply(browser.driver.controlFlow(), args);
        };
    });

    it('When login is successful — he should redirect to default “Dashboard” page', () => {
        login.navigateToLogin();
        login.fillCredentials();
        expect(dashboard.getPageTitleText()).toEqual('Schools using Greenstar Application every month');
    });

    it('When user click on school menu and it nvigated to school screen.', () => {
        dashboard.navigateToSchoolScreen();
        expect(browser.driver.getCurrentUrl()).toContain('/school');
    });

    it('When user click on student menu and it nvigated to student screen.', () => {
        dashboard.navigateToStudentScreen();
        expect(browser.driver.getCurrentUrl()).toContain('/student');
    });

    it('When user click on performance data menu and it nvigated to performance data screen.', () => {
        dashboard.navigateToPerfDataScreen();
        expect(browser.driver.getCurrentUrl()).toContain('/performancedata');
    });

    it('When user click on performance star menu and it nvigated to performance star screen.', () => {
        dashboard.navigateToPerfStarScreen();
        expect(browser.driver.getCurrentUrl()).toContain('/performancestar');
    });

    it('When user click on performance metrics menu and it nvigated to performance metrics screen.', () => {
        dashboard.navigateToPerfMetricsScreen();
        expect(browser.driver.getCurrentUrl()).toContain('/performancemetrics');
    });

    it('When user click on admin menu and it nvigated to admin screen.', () => {
        dashboard.navigateToAdminScreen();
        expect(browser.driver.getCurrentUrl()).toContain('/performancemetrics');
    });

    it('When user click on dashboard menu and it nvigated to dashboard screen.', () => {
        dashboard.navigateToDashboardScreen();
        expect(browser.driver.getCurrentUrl()).toContain('/dashboard');
    });

});