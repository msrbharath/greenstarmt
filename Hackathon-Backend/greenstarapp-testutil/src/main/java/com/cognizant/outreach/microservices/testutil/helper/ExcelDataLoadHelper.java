package com.cognizant.outreach.microservices.testutil.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Load param value data from excel
 * 
 * @author Magesh
 */
@Component
public class ExcelDataLoadHelper {
	
	protected Logger logger = LoggerFactory.getLogger(ExcelDataLoadHelper.class);

	public Map<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, Integer>>>> 
			extractParamValueDataFromExcel(File inputFile) throws IOException {
        logger.debug("Retreiving  Data {} {}", inputFile.getName() , ".....");
		FileInputStream excelFile = new FileInputStream(inputFile);
		Workbook workbook = new XSSFWorkbook(excelFile);
		// Holds <monthname<Studentname,<parametername,<day,value>>>>
		Map<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, Integer>>>> monthWiseValues = new LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, Integer>>>>();

		for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
			Sheet datatypeSheet = workbook.getSheetAt(sheetIndex);
			String sheetMonthName = datatypeSheet.getSheetName().trim();
			logger.debug("Name of the Month ==> {}" , sheetMonthName);
			Iterator<Row> iterator = datatypeSheet.iterator();

			// Holds <Studentname,<parametername,<day,value>>>
			LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, Integer>>> studentsMonthlyValues = new LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, Integer>>>();

			List<Integer> daysForMonth = new ArrayList<Integer>();

			LinkedHashMap<String, LinkedHashMap<Integer, Integer>> attendanceStudentMap = new LinkedHashMap<String, LinkedHashMap<Integer, Integer>>();
			LinkedHashMap<String, LinkedHashMap<Integer, Integer>> homeworkStudentMap = new LinkedHashMap<String, LinkedHashMap<Integer, Integer>>();
			LinkedHashMap<String, LinkedHashMap<Integer, Integer>> disciplineStudentMap = new LinkedHashMap<String, LinkedHashMap<Integer, Integer>>();

			// Get the days of the month configured for in the excel header
			Row currentRow = iterator.next();
			if (currentRow.getRowNum() == 0) {
				for (int i = 1; i < currentRow.getLastCellNum(); i++) {
					Cell currentCell = currentRow.getCell(i);
					daysForMonth.add((int) currentCell.getNumericCellValue());
				}
			}

			while (iterator.hasNext()) {
				currentRow = iterator.next();
				// Get the data for attendance
				if (currentRow.getRowNum() >= 2 && currentRow.getRowNum() <= 11) {
					int dayNumberIndex = 0;
					Cell firstCell = currentRow.getCell(0);
					String studentName = firstCell.getStringCellValue();
					LinkedHashMap<Integer, Integer> studentAttendanceDayAndValueMap = new LinkedHashMap<Integer, Integer>();
					for (int i = 1; i < currentRow.getLastCellNum(); i++) {
						Cell currentCell = currentRow.getCell(i);
						studentAttendanceDayAndValueMap.put(daysForMonth.get(dayNumberIndex),
								(int) currentCell.getNumericCellValue());
						dayNumberIndex++;
					}
					attendanceStudentMap.put(studentName, studentAttendanceDayAndValueMap);
				}

				// Get the data for Homework
				if (currentRow.getRowNum() >= 13 && currentRow.getRowNum() <= 22) {
					int dayNumberIndex = 0;
					Cell firstCell = currentRow.getCell(0);
					String studentName = firstCell.getStringCellValue();
					LinkedHashMap<Integer, Integer> studentAttendanceValueMap = new LinkedHashMap<Integer, Integer>();
					for (int i = 1; i < currentRow.getLastCellNum(); i++) {
						Cell currentCell = currentRow.getCell(i);
						studentAttendanceValueMap.put(daysForMonth.get(dayNumberIndex),
								(int) currentCell.getNumericCellValue());
						dayNumberIndex++;
					}
					homeworkStudentMap.put(studentName, studentAttendanceValueMap);
				}

				// Get the data for Discipline
				if (currentRow.getRowNum() >= 24 && currentRow.getRowNum() <= 33) {
					int dayNumberIndex = 0;
					Cell firstCell = currentRow.getCell(0);
					String studentName = firstCell.getStringCellValue();
					LinkedHashMap<Integer, Integer> studentDisciplineValueMap = new LinkedHashMap<Integer, Integer>();
					for (int i = 1; i < currentRow.getLastCellNum(); i++) {
						Cell currentCell = currentRow.getCell(i);
						studentDisciplineValueMap.put(daysForMonth.get(dayNumberIndex),
								(int) currentCell.getNumericCellValue());
						dayNumberIndex++;
					}
					disciplineStudentMap.put(studentName.trim(), studentDisciplineValueMap);
				}
			}
			studentsMonthlyValues.put("Attendance", attendanceStudentMap);
			studentsMonthlyValues.put("HomeWork", homeworkStudentMap);
			studentsMonthlyValues.put("Discipline", disciplineStudentMap);
			monthWiseValues.put(sheetMonthName, studentsMonthlyValues);
		}

		// To check the with standalone
		/*for (Map.Entry<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Integer, Integer>>>> entry : monthWiseValues
				.entrySet()) {
			logger.debug(("Month ==> " + entry.getKey());
			for (Map.Entry<String, LinkedHashMap<String, LinkedHashMap<Integer, Integer>>> entry0 : entry.getValue()
					.entrySet()) {
				logger.debug(("Parameter Type ==> " + entry0.getKey());
				for (Map.Entry<String, LinkedHashMap<Integer, Integer>> entry1 : entry0.getValue().entrySet()) {
					logger.debug(("Student Name ==> " + entry1.getKey());
					StringBuffer buffer = new StringBuffer();
					for (Map.Entry<Integer, Integer> entry2 : entry1.getValue().entrySet()) {
						buffer.append(entry2.getKey()).append(" # ").append(entry2.getValue()).append("||");
					}
					logger.debug((buffer.toString());
				}
			}
		}*/
		logger.debug("Retreiving  Data completed!");
		return monthWiseValues;
	}
}
