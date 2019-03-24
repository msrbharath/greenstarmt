/**
 * ${TemplateValidator}
 *
 *  2019 Cognizant Technology Solutions. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of Cognizant Technology
 *  Solutions("Confidential Information").  You shall not disclose or use Confidential
 *  Information without the express written agreement of Cognizant Technology Solutions.
 *  Modification Log:
 *  -----------------
 *  Date                   Author           Description
 *  18/Feb/2019            Panneer        	Developed base code structure
 *  ---------------------------------------------------------------------------
 */
package com.cognizant.outreach.microservices.perfdata.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cognizant.outreach.entity.MeasurableParam;
import com.cognizant.outreach.microservices.perfdata.vo.TemplateError;

/**
 * TemplateValidator class to handle validation for performance bulk upload template. 
 * 
 * @author Panneer
 */

@Component
public class TemplateValidator {
	
	/**
	 * Method to validate the uploaded template introduction sheet.
	 * @param workbook
	 * @return errorMessages
	 */
	public List<TemplateError> validateTemplateSrchParam(Workbook workbook) {
		
		List<TemplateError> errorMessages = new ArrayList<>();
		
		Sheet sheet = workbook.getSheetAt(0);
		
		if(StringUtils.isEmpty(readCellData(sheet.getRow(5).getCell(3)))) {
			errorMessages.add(new TemplateError((sheet.getRow(5).getRowNum()+1), "Sheet-0", "School should not be empty"));
		}
		
		if(StringUtils.isEmpty(readCellData(sheet.getRow(7).getCell(3)))) {
			errorMessages.add(new TemplateError((sheet.getRow(6).getRowNum()+1), "Sheet-0", "Class should not be empty"));
		}
		
		if(StringUtils.isEmpty(readCellData(sheet.getRow(8).getCell(3)))) {
			errorMessages.add(new TemplateError((sheet.getRow(8).getRowNum()+1), "Sheet-0", "Month should not be empty"));
		}
				
		return errorMessages;
	}
	
	/**
	 * Method to validate the performance bulk upload templates row and cell value.
	 * 
	 * @param workbook
	 * @param rollNoList
	 * @param measurableParamMap
	 * @param weekDays
	 * @return errorMessages
	 * @throws Exception 
	 */
	public List<TemplateError> validateTemplateFile(Workbook workbook, List<String> rollNoList, Map<String, MeasurableParam> measurableParamMap, List<String> weekDays) throws Exception {

		List<TemplateError> errorMessages = new ArrayList<>();

		try {
			Sheet sheet = workbook.getSheetAt(1);

			Row dayHeaderRow = sheet.getRow(0);
			Row paramHeaderRow = sheet.getRow(1);

			int paramSize = measurableParamMap.size();
			
			// Validation for header (week dates)
			int columnIndex = 2;
			do {
				String cellTitle = readCellData(dayHeaderRow.getCell(columnIndex));

				if (StringUtils.isEmpty(cellTitle)) {
					errorMessages.add(new TemplateError((dayHeaderRow.getRowNum()+1), cellTitle, "Date in header should not be empty"));
				} else if (!weekDays.contains(cellTitle)) {
					errorMessages.add(new TemplateError((dayHeaderRow.getRowNum()+1), cellTitle, "Date in header is not associated with the expected range"));
				}
				columnIndex += paramSize;
			} while (columnIndex < dayHeaderRow.getLastCellNum());

			// Validation for sub header (Measurable parameter)
			columnIndex = 2;
			do {
				String cellSubTitle = readCellData(paramHeaderRow.getCell(columnIndex));

				if (StringUtils.isEmpty(cellSubTitle)) {
					errorMessages.add(new TemplateError((paramHeaderRow.getRowNum()+1), cellSubTitle, "Performance parameter should not be empty"));
				} else if (!measurableParamMap.containsKey(cellSubTitle)) {
					errorMessages.add(new TemplateError((paramHeaderRow.getRowNum()+1), cellSubTitle, "Performance parameter is not aligned with the expected school parameters"));
				}
				columnIndex++;
			} while (columnIndex < paramHeaderRow.getLastCellNum());

			// Validation for measurable parameter value.
			for (int rowIndex = 2; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row dataRow = sheet.getRow(rowIndex);

				// Roll No validation
				if (StringUtils.isEmpty(dataRow.getCell(0).getStringCellValue())) {
					errorMessages.add(new TemplateError((dataRow.getRowNum()+1), dataRow.getCell(0).getStringCellValue(), "Roll number should not be empty"));
				} else if (!rollNoList.contains(dataRow.getCell(0).getStringCellValue())) {
					errorMessages.add(new TemplateError((dataRow.getRowNum()+1), dataRow.getCell(0).getStringCellValue(), "Invalid roll number"));
				}

				// Student Name validation
				if (StringUtils.isEmpty(dataRow.getCell(1).getStringCellValue())) {
					errorMessages.add(new TemplateError((dataRow.getRowNum()+1), dataRow.getCell(1).getStringCellValue(), "Student name should not be empty"));
				}

				for (int dataColumnIndex = 2; dataColumnIndex < dataRow.getLastCellNum(); dataColumnIndex++) {
					
					String paramValue = readParamCellData(dataRow.getCell(dataColumnIndex));

					if (null == paramValue || "".equals(paramValue)) {
						errorMessages.add(new TemplateError((dataRow.getRowNum()+1), paramValue, "Performance parameter value should not be empty"));
					} else if (!"0".equals(paramValue) && !"1".equals(paramValue)) {
						errorMessages.add(new TemplateError((dataRow.getRowNum()+1), paramValue, "Invalid performance parameter value"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return errorMessages;
	}

	/**
	 * Method to read cell data.
	 * 
	 * @param cell
	 * @return cellValue
	 */
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

	/**
	 * Method to read parameter cell data.
	 * 
	 * @param cell
	 * @return cellValue
	 */
	private String readParamCellData(Cell cell) {
		String cellValue = "";
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			cellValue = cell.getStringCellValue();
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			Long value = (long) cell.getNumericCellValue();
			cellValue = String.valueOf(value);
		}
		return cellValue;
	}

}
