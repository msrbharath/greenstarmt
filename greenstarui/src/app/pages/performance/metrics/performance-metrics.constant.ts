import { IPerformanceHeader, IPerformanceDataTable, IPerformanceData, IPerformanceRow, IPerformanceDay, IPerformanceMetricsWeek, IPerformanceMetricsRow, IPerformanceMetricsDay, IPerformanceMetricsData, IPerformanceMetricsDataTable, IClassWiseMetricsDataTable, IClassWiseHeader, IClassWiseMetricsRow, ITeamWiseMetricsDataTable, ITeamWiseHeader, ITeamWiseMetricsRow, IEncouragingMetricsDataTable, IEncouragingMetrics, ISection, IAverageRow } from "./performance-metrics.interface";

export class PerformanceStaticData {
    public static monthList: Array<any> = [
        { value: 1, label: 'January' },
        { value: 2, label: 'February' },
        { value: 3, label: 'March' },
        { value: 4, label: 'April' },
        { value: 5, label: 'May' },
        { value: 6, label: 'June' },
        { value: 7, label: 'July' },
        { value: 8, label: 'August' },
        { value: 9, label: 'September' },
        { value: 10, label: 'October' },
        { value: 11, label: 'November' },
        { value: 12, label: 'December' }
      ];
}
