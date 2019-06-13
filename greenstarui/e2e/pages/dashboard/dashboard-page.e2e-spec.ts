import { browser, by, element } from 'protractor';
import { DashboardPage } from '../dashboard/dashboard-page.po';
import { LoginPage } from '../login/login-page.po';

describe('Green Star Application - e2e testing', () => {

    const login = new LoginPage();
    const dashboard = new DashboardPage();
    
    // login & dashboard module - start
    /*
    it('When user trying to login with wrong credentials he should stay on “login” page and see error notification', () => {
        const credentias = {
            username: 'test',
            password: 'test'
        };
        login.fillCredentials(credentias);
        browser.sleep(5000);
        expect(dashboard.getPageTitleText()).toEqual('Login');      
        expect(login.getErrorMessage()).toEqual('Invalid User Id and Password.');
    });    
    */

    it('When login is successful — he should redirect to default “Dashboard” page', () => {        
        login.navigateToLogin();
        login.fillCredentials();        
        browser.sleep(5000);
        browser.ignoreSynchronization = true;
        expect(dashboard.getPageTitleText()).toEqual('Schools using Greenstar Application every month');
    });
    // // login & dashboard module - end
    
    // school module - start
    it('When user click on school menu and it navigated to school screen.', () => {
        dashboard.navigateToSchoolScreen();
        browser.sleep(3000);
        expect(browser.driver.getCurrentUrl()).toContain('/school');
    });

    it('When user try to search the school details', () => {        
        dashboard.setSearchSchoolParam();
        element(by.id('searchSchool')).click();
        browser.sleep(5000);
        expect(browser.driver.getCurrentUrl()).toContain('/school');
    });
    // school module - end

    // student module - start
    it('When user click on student menu and it navigated to student screen.', () => {
        dashboard.navigateToStudentScreen();
        browser.sleep(3000);
        expect(browser.driver.getCurrentUrl()).toContain('/student');
    });

    it('When user try to search the student details', () => {
        dashboard.setSearchStudentParam();
        element(by.id('searchStudentBtn')).click();
        browser.sleep(5000);
        expect(browser.driver.getCurrentUrl()).toContain('/student');
    });

    it('When user try to export the searchable student details', () => {
        element(by.id('exportbtn')).click();
        browser.sleep(5000);
        expect(browser.driver.getCurrentUrl()).toContain('/student');
    });

    // student module - end

    // performance data module - start
    it('When user click on performance data menu and it navigated to performance data screen.', () => {
        dashboard.navigateToPerfDataScreen();
        browser.sleep(3000);
        expect(browser.driver.getCurrentUrl()).toContain('/performancedata');
    });

    it('When user try to search the performance details', () => {
        dashboard.setSearchPerformanceDataParam();
        element(by.id('searchPerfDataBtn')).click();
        browser.sleep(5000);
        expect(browser.driver.getCurrentUrl()).toContain('/performancedata');
    });

    it('When user try to download the performance template', () => {        
        element(by.id('downloadTemplateBtn')).click();
        browser.sleep(25000);
        expect(browser.driver.getCurrentUrl()).toContain('/performancedata');
    });  
    // performance data module - end

    // performance star module - start
    it('When user click on performance star menu and it navigated to performance star screen.', () => {
        dashboard.navigateToPerfStarScreen();
        browser.sleep(3000);
        expect(browser.driver.getCurrentUrl()).toContain('/performancestar');
    });

    it('When user try to generate the star for school level', () => {
        dashboard.setSearchPerformanceStarParam();
        element(by.id('generateStarBtn')).click();
        browser.sleep(5000);
        expect(browser.driver.getCurrentUrl()).toContain('/performancestar');
    });
    
    it('When user try to download the generated star for school level', () => {        
        element(by.id('downloadGeneratedStarBtn')).click();
        browser.sleep(5000);
        expect(browser.driver.getCurrentUrl()).toContain('/performancestar');
    });
    // performance star module - end

    // performance metric module - start
    it('When user click on performance metric menu and it navigated to performance metric screen-Individual Performance Metric Tab.', () => {
        dashboard.navigateToPerfMetricsScreen();
        browser.sleep(3000);
        expect(browser.driver.getCurrentUrl()).toContain('/performancemetrics');
    });

    it('When user try to search the performance metric details', () => {
        dashboard.setSearchPerformanceMetricParamForIndividual();
        element(by.id('viewMetricBtn')).click();
        browser.sleep(5000);
        expect(browser.driver.getCurrentUrl()).toContain('/performancemetrics');
    });

    it('When user try to export the performance metric details', () => {        
        element(by.id('exportPerfIndMetricBtn')).click();
        browser.sleep(10000);
        expect(browser.driver.getCurrentUrl()).toContain('/performancemetrics');
    });

    /*
    it('When user click on Class Wise Performance Metric Tab.', () => {
        element(by.css('.class-wise-metrics-tab')).click();
        browser.sleep(5000);
        expect(browser.driver.getCurrentUrl()).toContain('/performancemetrics');
    });

    it('When user click on Team Wise Performance Metric Tab.', () => {
        element(by.css('.team-wise-metrics-tab')).click();
        browser.sleep(5000);
        expect(browser.driver.getCurrentUrl()).toContain('/performancemetrics');
    });

    it('When user click on Encouraging Performance Metric Tab.', () => {
        element(by.css('.encouraging-metrics-tab')).click();
        browser.sleep(5000);
        expect(browser.driver.getCurrentUrl()).toContain('/performancemetrics');
    });
    */
    // performance metric module - end

    // admin module - start
    it('When user click on admin menu and it navigated to admin screen.', () => {
        dashboard.navigateToAdminScreen();
        browser.sleep(5000);
        expect(browser.driver.getCurrentUrl()).toContain('/admin');
    });       
    // admin module - end

    // login module - start
    it('When user click on logout menu and it navigated to login screen.', () => {
        dashboard.navigateToLoginScreen();
        browser.sleep(5000);
        expect(browser.driver.getCurrentUrl()).toContain('/login');
    });       
    // login module - end

});
