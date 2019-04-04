import { browser, by, element, promise, ElementFinder, ElementArrayFinder } from 'protractor';

export class DashboardPage {

    public getPageTitleText() {
        return element(by.id('dashboardTitle')).getText();
    }

    public navigateToDashboardScreen(): void {
        element(by.xpath("//a[@href='/greenstarui/pages/dashboard']")).click();
    }

    public navigateToSchoolScreen(): void {
        element(by.xpath("//a[@href='/greenstarui/pages/school']")).click();
    }

    public navigateToStudentScreen(): void {
        element(by.xpath("//a[@href='/greenstarui/pages/student']")).click();
    }

    public navigateToPerfDataScreen(): void {
        element(by.xpath("//a[@href='/greenstarui/pages/performancedata']")).click();
    }

    public navigateToPerfStarScreen(): void {
        element(by.xpath("//a[@href='/greenstarui/pages/performancestar']")).click();
    }

    public navigateToPerfMetricsScreen(): void {
        element(by.xpath("//a[@href='/greenstarui/pages/performancemetrics']")).click();
    }

    public navigateToAdminScreen(): void {
        element(by.xpath("//a[@href='/greenstarui/pages/admin']")).click();
    }

}