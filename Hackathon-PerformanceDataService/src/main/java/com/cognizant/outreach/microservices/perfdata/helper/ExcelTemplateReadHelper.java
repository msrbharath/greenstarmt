package com.cognizant.outreach.microservices.perfdata.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.cognizant.outreach.microservices.perfdata.vo.SearchPerformanceData;
import com.cognizant.outreach.util.DateUtil;

@Component
public class ExcelTemplateReadHelper {

	public SearchPerformanceData getSearchParamFromTemplate(Workbook workbook) throws IOException {
		
		SearchPerformanceData searchPerformanceData = new SearchPerformanceData();
		try {
			Sheet sheet = workbook.getSheetAt(0);
			searchPerformanceData.setSchoolName(readCellData(sheet.getRow(4).getCell(3)));
			searchPerformanceData.setSchoolId(Long.valueOf(readCellData(sheet.getRow(5).getCell(3))));
			searchPerformanceData.setClassName(readCellData(sheet.getRow(6).getCell(3)));
			searchPerformanceData.setClassId(Long.valueOf(readCellData(sheet.getRow(7).getCell(3))));
			searchPerformanceData.setMonth(DateUtil.MonthValue.getMonthValue(readCellData(sheet.getRow(8).getCell(3))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return searchPerformanceData;
	}

	public Map<String, Map<String, String>> getExcelTemplateData(Workbook workbook) {
		
		Map<String, Map<String, String>> excelMap = new HashMap<>();
		
		try {
			Sheet sheet = workbook.getSheetAt(1);

			Row dayHeaderRow = sheet.getRow(0);
			Row paramHeaderRow = sheet.getRow(1);

			for (int rowIndex = 2; rowIndex <= sheet.getLastRowNum(); rowIndex++) {

				Map<String, String> rowDataMap = new HashMap<>();
				Row dataRow = sheet.getRow(rowIndex);

				String cellTitle = "";
				for (int columnIndex = 2; columnIndex < dataRow.getLastCellNum(); columnIndex++) {
					String param = "";
					
					String cellSubTitle = readCellData(paramHeaderRow.getCell(columnIndex));
					if (null != readCellData(dayHeaderRow.getCell(columnIndex)) 
							&& "" != readCellData(dayHeaderRow.getCell(columnIndex))) {
						cellTitle = readCellData(dayHeaderRow.getCell(columnIndex));
						param = cellTitle + cellSubTitle;
					} else {
						param = cellTitle + cellSubTitle;
					}
					rowDataMap.put(param, readParamCellData(dataRow.getCell(columnIndex)));
				}
				excelMap.put(readCellData(dataRow.getCell(0)), rowDataMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return excelMap;
	}

	private String readCellData(Cell cell) {
		String cellValue = "";
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			cellValue = cell.getStringCellValue();
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			Long value = (long) cell.getNumericCellValue();
			cellValue = String.valueOf(value);
		}
		return cellValue;
	}
	
	private String readParamCellData(Cell cell) {
		String cellValue = "0";
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			cellValue = cell.getStringCellValue();
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			Long value = (long) cell.getNumericCellValue();
			cellValue = String.valueOf(value);
		}
		return cellValue;
	}

}
