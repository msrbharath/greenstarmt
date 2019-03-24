/**
 * ${ExcelTemplateWriteHelper}
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDataTableVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDataVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceDayVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceHeaderVO;
import com.cognizant.outreach.microservices.perfdata.vo.PerformanceRowVO;

/**
 * ExcelTemplateWriteHelper class to handle generate the performance bulk upload excel template. 
 * 
 * @author Panneer
 */

@Component
public class ExcelTemplateWriteHelper {

	public static final String EXCEL_INTRODUCTION_SHEETNAME = "Introduction";
	public static final String EXCEL_DATA_SHEETNAME = "Performance Measurable Data";

	/**
	 * Method to generate the performance bulk upload template excel sheet 
	 * 
	 * @param performanceDataTableVO
	 * @return
	 * @throws IOException
	 */
	public byte[] getExcelTemplateFile(PerformanceDataTableVO performanceDataTableVO) throws IOException {

		// Create Work book
		XSSFWorkbook workbook = createWorkBook();

		// Create Introduction sheet
		createIntroductionSheet(workbook, performanceDataTableVO);
		
		// Create Data Sheet
		createDataSheet(workbook, performanceDataTableVO);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		workbook.write(bos);
		
		return bos.toByteArray();
	}

	/**
	 * Method to create workbook.
	 * 
	 * @return XSSFWorkbook
	 */
	private XSSFWorkbook createWorkBook() {
		return new XSSFWorkbook();
	}

	/**
	 * Method to generate the performance bulk upload introduction sheet.
	 * 
	 * @param workbook
	 * @param performanceDataTableVO
	 */
	private void createIntroductionSheet(XSSFWorkbook workbook, PerformanceDataTableVO performanceDataTableVO) {

		XSSFSheet sheet = workbook.createSheet(EXCEL_INTRODUCTION_SHEETNAME);

		Row row = sheet.createRow(1);
		Cell headerCell = row.createCell(2);
		headerCell.setCellValue("Introduction");
		headerCell.setCellStyle(getIntroductionHeaderCellStyle(workbook));
		CellRangeAddress cellMerge = new CellRangeAddress(1, 1, 2, 3);
		sheet.addMergedRegion(cellMerge);
		setBorder(cellMerge, headerCell, sheet, workbook);
		
		// School Label & Value
		row = sheet.createRow(4);
		headerCell = row.createCell(2);
		sheet.setColumnWidth(2, 4000);
		headerCell.setCellValue("School Name");
		headerCell.setCellStyle(getIntroductionCellStyle(workbook));
		setCellBorder(headerCell);
		
		headerCell = row.createCell(3);
		sheet.setColumnWidth(3, 15000);
		headerCell.setCellValue(performanceDataTableVO.getSchoolName());
		headerCell.setCellStyle(getIntroductionCellStyle(workbook));
		setCellBorder(headerCell);
		
		row = sheet.createRow(5);
		headerCell = row.createCell(2);
		
		headerCell.setCellValue("School ID");
		headerCell.setCellStyle(getIntroductionCellStyle(workbook));
		setCellBorder(headerCell);
		
		headerCell = row.createCell(3);
		headerCell.setCellStyle(getIntroductionValueItemsCellStyle(workbook));
		headerCell.setCellValue(String.valueOf(performanceDataTableVO.getSchoolId()));		
		headerCell.setCellStyle(getIntroductionCellStyle(workbook));
		setCellBorder(headerCell);
		
		// Section Label & Value
		row = sheet.createRow(6);
		headerCell = row.createCell(2);		
		headerCell.setCellValue("Class & Section");
		headerCell.setCellStyle(getIntroductionCellStyle(workbook));
		setCellBorder(headerCell);
		
		headerCell = row.createCell(3);
		headerCell.setCellStyle(getIntroductionValueItemsCellStyle(workbook));
		headerCell.setCellValue(String.valueOf(performanceDataTableVO.getClassName()));
		headerCell.setCellStyle(getIntroductionCellStyle(workbook));
		setCellBorder(headerCell);
		
		// Class Label & Value
		row = sheet.createRow(7);
		headerCell = row.createCell(2);		
		headerCell.setCellValue("Class ID");
		headerCell.setCellStyle(getIntroductionCellStyle(workbook));
		setCellBorder(headerCell);
		
		headerCell = row.createCell(3);
		headerCell.setCellStyle(getIntroductionValueItemsCellStyle(workbook));
		headerCell.setCellValue(String.valueOf(performanceDataTableVO.getClassId()));
		headerCell.setCellStyle(getIntroductionCellStyle(workbook));
		setCellBorder(headerCell);
		
		// Month Label & Value
		row = sheet.createRow(8);
		headerCell = row.createCell(2);		
		headerCell.setCellValue("Month");
		headerCell.setCellStyle(getIntroductionCellStyle(workbook));
		setCellBorder(headerCell);
		
		headerCell = row.createCell(3);
		headerCell.setCellStyle(getIntroductionValueItemsCellStyle(workbook));
		headerCell.setCellValue(performanceDataTableVO.getMonthName());
		headerCell.setCellStyle(getIntroductionCellStyle(workbook));
		setCellBorder(headerCell);
		
		// Note Label
		row = sheet.createRow(12);
		headerCell = row.createCell(2);		
		headerCell.setCellValue("Note:");
		headerCell.setCellStyle(getIntroductionCellStyle(workbook));
		cellMerge = new CellRangeAddress(12, 12, 2, 6);
		sheet.addMergedRegion(cellMerge);
		setBorder(cellMerge, headerCell, sheet, workbook);
		
		// Note Values - 1
		row = sheet.createRow(14);
		headerCell = row.createCell(2);		
		headerCell.setCellValue("Please do not change/remove any value in/from this sheet");
		headerCell.setCellStyle(getIntroductionValueItemsCellStyle(workbook));
		cellMerge = new CellRangeAddress(14, 14, 2, 6);
		sheet.addMergedRegion(cellMerge);
		setBorder(cellMerge, headerCell, sheet, workbook);
		
		// Note Values - 2
		row = sheet.createRow(15);
		headerCell = row.createCell(2);		
		headerCell.setCellValue("Please do not change/remove any header value in/from \"Performance Measurable Data\" sheet");
		headerCell.setCellStyle(getIntroductionValueItemsCellStyle(workbook));
		cellMerge = new CellRangeAddress(15, 15, 2, 6);
		sheet.addMergedRegion(cellMerge);
		setBorder(cellMerge, headerCell, sheet, workbook);
		
		// Note Values - 3
		row = sheet.createRow(16);
		headerCell = row.createCell(2);		
		headerCell.setCellValue("Please do not change/remove any roll no and student name in/from \"Performance Measurable Data\" sheet");
		headerCell.setCellStyle(getIntroductionValueItemsCellStyle(workbook));
		cellMerge = new CellRangeAddress(16, 16, 2, 6);
		sheet.addMergedRegion(cellMerge);
		setBorder(cellMerge, headerCell, sheet, workbook);
		
		// Note Values - 4
		row = sheet.createRow(17);
		headerCell = row.createCell(2);		
		headerCell.setCellValue("User can edit the performance parameter value with either \"0\" or \"1\"");
		headerCell.setCellStyle(getIntroductionValueItemsCellStyle(workbook));
		cellMerge = new CellRangeAddress(17, 17, 2, 6);
		sheet.addMergedRegion(cellMerge);
		setBorder(cellMerge, headerCell, sheet, workbook);
	}

	/**
	 * Method to generate the performance bulk upload data sheet.
	 * Including student and measurable parameter information.
	 * 
	 * @param workbook
	 * @param performanceDataTableVO
	 */
	private void createDataSheet(XSSFWorkbook workbook, PerformanceDataTableVO performanceDataTableVO) {

		XSSFSheet sheet = workbook.createSheet(EXCEL_DATA_SHEETNAME);

		Row row = sheet.createRow(0);
		Row row1 = sheet.createRow(1);
		
		// Roll No Header
		Cell headerRollNoCell = row.createCell(0);
		headerRollNoCell.setCellValue("Roll No");
		headerRollNoCell.setCellStyle(getHeaderCellStyle(workbook));
		CellRangeAddress cellMerge = new CellRangeAddress(0, 1, 0, 0);
		sheet.addMergedRegion(cellMerge);
		setBorder(cellMerge, headerRollNoCell, sheet, workbook);
		
		// Student Name Header
		Cell headerNameCell = row.createCell(1);
		headerNameCell.setCellValue("Student Name");
		headerNameCell.setCellStyle(getHeaderCellStyle(workbook));
		cellMerge = new CellRangeAddress(0, 1, 1, 1);
		sheet.addMergedRegion(cellMerge);
		setBorder(cellMerge, headerNameCell, sheet, workbook);
		
		// Dynamic Main Header
		int dynamicColumnStart = 2;
		for(PerformanceHeaderVO performanceHeaderVO : performanceDataTableVO.getHeaders()) {
			Cell headerCell = row.createCell(dynamicColumnStart);
			headerCell.setCellValue(performanceHeaderVO.getTitle());
			headerCell.setCellStyle(getHeaderCellStyle(workbook));
			
			cellMerge = new CellRangeAddress(0, 0, dynamicColumnStart, (dynamicColumnStart+(performanceDataTableVO.getTotalSubTitle()-1)));
			sheet.addMergedRegion(cellMerge);
			setBorder(cellMerge, headerCell, sheet, workbook);
			
			for(PerformanceHeaderVO subPerformanceHeaderVO : performanceHeaderVO.getSubTitleList()) {
				
				Cell headerCell1 = row1.createCell(dynamicColumnStart);
				headerCell1.setCellValue(subPerformanceHeaderVO.getTitle());
				headerCell1.setCellStyle(getHeaderCellStyle(workbook));
				
				setBorder(new CellRangeAddress(1, 1, dynamicColumnStart, dynamicColumnStart), headerCell1, sheet, workbook);
				
				dynamicColumnStart++;
			}
		}
		
		// Row data
		int rowStartIndex = 2;
		for(PerformanceRowVO performanceRowVO : performanceDataTableVO.getPerformanceRows()) {
			
			Row dataRow = sheet.createRow(rowStartIndex);
			
			// Student roll number
			Cell dataCell = dataRow.createCell(0);
			dataCell.setCellValue(performanceRowVO.getRollId());
			setBorder(new CellRangeAddress(rowStartIndex, rowStartIndex, 0, 0), dataCell, sheet, workbook);
			
			// Student name
			dataCell = dataRow.createCell(1);
			dataCell.setCellValue(performanceRowVO.getStudentName());
			setBorder(new CellRangeAddress(rowStartIndex, rowStartIndex, 1, 1), dataCell, sheet, workbook);
			
			// Student performance measurable parameter value. by default to set value as '1'
			int dynamicDataColumnStart = 2;
			for(PerformanceDayVO performanceDayVO : performanceRowVO.getPerformanceDays()) {
				for(PerformanceDataVO performanceDataVO : performanceDayVO.getPerformanceData()) {
					dataCell = dataRow.createCell(dynamicDataColumnStart);
					dataCell.setCellValue("1");
					setBorder(new CellRangeAddress(rowStartIndex, rowStartIndex, dynamicDataColumnStart, dynamicDataColumnStart), dataCell, sheet, workbook);
					setDataCellStyle(dataCell);
					dynamicDataColumnStart++;
				}
			}			
			rowStartIndex++;
		}		
	}
	
	/**
	 * Method to set excel sheet range level cell border.
	 * 
	 * @param region
	 * @param cell
	 * @param sheet
	 * @param workbook
	 */
	private static void setBorder(CellRangeAddress region, Cell cell, XSSFSheet sheet, XSSFWorkbook workbook) {
		
		RegionUtil.setBorderBottom(1, region, sheet, workbook);
		RegionUtil.setBorderTop(1, region, sheet, workbook);
		RegionUtil.setBorderLeft(1, region, sheet, workbook);
		RegionUtil.setBorderRight(1, region, sheet, workbook);
				
		CellStyle cellStyle = cell.getCellStyle();
		cellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
	}
	
	/**
	 * Method to set excel sheet individual cell border.
	 *  
	 * @param cell
	 */
	private static void setCellBorder(Cell cell) {
		CellStyle cellStyle = cell.getCellStyle();
		cellStyle.setBorderBottom((short) BorderStyle.THIN.ordinal());
		cellStyle.setBorderLeft((short) BorderStyle.THIN.ordinal());
		cellStyle.setBorderRight((short) BorderStyle.THIN.ordinal());
		cellStyle.setBorderTop((short) BorderStyle.THIN.ordinal());
	}
	
	/**
	 * Method to set header cell style.
	 * 
	 * @param workBook
	 * @return
	 */
	private static CellStyle getHeaderCellStyle(Workbook workBook) {

        CellStyle cellStyle = workBook.createCellStyle();

        // To Set Font Style
        Font font = workBook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setAlignment((short)HorizontalAlignment.CENTER.ordinal());
        cellStyle.setVerticalAlignment((short)VerticalAlignment.CENTER.ordinal());
        
        return cellStyle;
	}
	
	/**
	 * Method to set value cell style and alignment for introduction sheet.
	 * 
	 * @param workBook
	 * @return cellStyle
	 */
	private static CellStyle getIntroductionCellStyle(Workbook workBook) {

        CellStyle cellStyle = workBook.createCellStyle();

        // To Set Font Style
        Font font = workBook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setAlignment((short)HorizontalAlignment.LEFT.ordinal());
        cellStyle.setVerticalAlignment((short)VerticalAlignment.CENTER.ordinal());
        
        return cellStyle;
	}
	
	/**
	 * Method to set header cell style and alignment for introduction sheet.
	 * 
	 * @param workBook
	 * @return cellStyle
	 */
	private static CellStyle getIntroductionHeaderCellStyle(Workbook workBook) {

        CellStyle cellStyle = workBook.createCellStyle();

        // To Set Font Style
        Font font = workBook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setAlignment((short)HorizontalAlignment.CENTER.ordinal());
        cellStyle.setVerticalAlignment((short)VerticalAlignment.CENTER.ordinal());
        
        return cellStyle;
	}
	
	/**
	 * Method to set value items cell style and alignment for introduction sheet.
	 * 
	 * @param workBook
	 * @return cellStyle
	 */
	private static CellStyle getIntroductionValueItemsCellStyle(Workbook workBook) {

        CellStyle cellStyle = workBook.createCellStyle();

        // To Set Font Style
        Font font = workBook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setColor(IndexedColors.BLACK.getIndex());
        cellStyle.setFont(font);

        cellStyle.setAlignment((short)HorizontalAlignment.LEFT.ordinal());
        cellStyle.setVerticalAlignment((short)VerticalAlignment.CENTER.ordinal());
        
        return cellStyle;
	}
		
	/**
	 * Method to set cell style for data cell.
	 * 
	 * @param cell
	 * @return cellStyle
	 */
	private static CellStyle setDataCellStyle(Cell cell) {

        CellStyle cellStyle = cell.getCellStyle();

        cellStyle.setAlignment((short)HorizontalAlignment.CENTER.ordinal());
        cellStyle.setVerticalAlignment((short)VerticalAlignment.CENTER.ordinal());

        return cellStyle;
	}
	
}
