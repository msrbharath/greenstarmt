package com.cognizant.outreach.microservices.perfdata.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

@Component
public class ExcelTemplateWriteHelper {

	public static final String EXCEL_FILENAME = "ComponentTrackerDetails";
	public static final String EXCEL_INTRODUCTION_SHEETNAME = "Introduction";
	public static final String EXCEL_DATA_SHEETNAME = "Performance Measurable data";

	public byte[] getExcelTemplateFile(PerformanceDataTableVO performanceDataTableVO) throws IOException {

		// Create Work book
		XSSFWorkbook workbook = createWorkBook();

		// Create Introduction sheet
		createIntroductionSheet(workbook);
		
		// Create Data Sheet
		createDataSheet(workbook, performanceDataTableVO);
		
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			workbook.write(bos);
		} finally {
			bos.close();
		}
		byte[] bytes = bos.toByteArray();
		
		Path path = Paths.get("C:\\Users\\Admin\\Desktop\\GreenStarAppDoc\\sample.xlsx");
		Files.write(path, bytes);
		System.out.println("END");
		
		return bytes;
	}

	private XSSFWorkbook createWorkBook() {
		return new XSSFWorkbook();
	}

	private void createIntroductionSheet(XSSFWorkbook workbook) {

		XSSFSheet sheet = workbook.createSheet(EXCEL_INTRODUCTION_SHEETNAME);

		Row row = sheet.createRow(5);

		Cell headerCell = row.createCell(5);
		
		headerCell.setCellValue("Introduction");	
	}

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
		headerNameCell.setCellValue("Name");
		headerNameCell.setCellStyle(getHeaderCellStyle(workbook));
		cellMerge = new CellRangeAddress(0, 1, 1, 1);
		sheet.addMergedRegion(cellMerge);
		setBorder(cellMerge, headerNameCell, sheet, workbook);
		
		// Main Header
		int subTitleTot = 2;
		int dynamicColumnStart = 2;
		for(PerformanceHeaderVO performanceHeaderVO : performanceDataTableVO.getHeaders()) {
			Cell headerCell = row.createCell(dynamicColumnStart);
			headerCell.setCellValue(performanceHeaderVO.getTitle());
			headerCell.setCellStyle(getHeaderCellStyle(workbook));
			
			cellMerge = new CellRangeAddress(0, 0, dynamicColumnStart, (dynamicColumnStart+(subTitleTot-1)));
			sheet.addMergedRegion(cellMerge);
			setBorder(cellMerge, headerCell, sheet, workbook);
			
			for(PerformanceHeaderVO subPerformanceHeaderVO : performanceHeaderVO.getSubTitleList()) {
				
				Cell headerCell1 = row1.createCell(dynamicColumnStart);
				headerCell1.setCellValue(subPerformanceHeaderVO.getTitle());
				headerCell1.setCellStyle(getHeaderCellStyle(workbook));
				
				setBorder(new CellRangeAddress(1, 1, dynamicColumnStart, dynamicColumnStart), headerCell1, sheet, workbook);
				
				// setCellWidth(sheet, dynamicColumnStart, subPerformanceHeaderVO.getTitle().length());
				
				dynamicColumnStart++;
			}
		}
		
		// Row data
		int rowStartIndex = 2;
		for(PerformanceRowVO performanceRowVO : performanceDataTableVO.getPerformanceRows()) {
			
			Row dataRow = sheet.createRow(rowStartIndex);
			
			Cell dataCell = dataRow.createCell(0);
			dataCell.setCellValue(performanceRowVO.getRollId());
			setBorder(new CellRangeAddress(rowStartIndex, rowStartIndex, 0, 0), dataCell, sheet, workbook);
			
			dataCell = dataRow.createCell(1);
			dataCell.setCellValue(performanceRowVO.getStudentName());
			setBorder(new CellRangeAddress(rowStartIndex, rowStartIndex, 1, 1), dataCell, sheet, workbook);
			
			int dynamicDataColumnStart = 2;
			for(PerformanceDayVO performanceDayVO : performanceRowVO.getPerformanceDays()) {
				for(PerformanceDataVO performanceDataVO : performanceDayVO.getPerformanceData()) {
					dataCell = dataRow.createCell(dynamicDataColumnStart);
					dataCell.setCellValue("");
					setBorder(new CellRangeAddress(rowStartIndex, rowStartIndex, dynamicDataColumnStart, dynamicDataColumnStart), dataCell, sheet, workbook);
					setDataCellStyle(dataCell);
					dynamicDataColumnStart++;
				}
			}			
			rowStartIndex++;
		}
		
		System.out.println("BorderStyle.THIN.ordinal() :: "+BorderStyle.THIN.ordinal());
	}
	
	private static void setBorder(CellRangeAddress region, Cell cell, XSSFSheet sheet, XSSFWorkbook workbook) {		
		RegionUtil.setBorderBottom(1, region, sheet, workbook);
		RegionUtil.setBorderTop(1, region, sheet, workbook);
		RegionUtil.setBorderLeft(1, region, sheet, workbook);
		RegionUtil.setBorderRight(1, region, sheet, workbook);
				
		CellStyle cellStyle = cell.getCellStyle();
		cellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
	}
	
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
	
	private static void setCellWidth(XSSFSheet sheet, int columnNo, int width) {
		sheet.setColumnWidth(columnNo, width);
	}
	
	private static CellStyle setDataCellStyle(Cell cell) {

        CellStyle cellStyle = cell.getCellStyle();

        cellStyle.setAlignment((short)HorizontalAlignment.CENTER.ordinal());
        cellStyle.setVerticalAlignment((short)VerticalAlignment.CENTER.ordinal());

        return cellStyle;
	}
	
	public static void main(String[] args) throws IOException {

		PerformanceDataTableVO performanceDataTableVO = new PerformanceDataTableVO();

		byte[] bytes = new ExcelTemplateWriteHelper().getExcelTemplateFile(null);

		Path path = Paths.get("C:\\Users\\Admin\\Desktop\\GreenStarAppDoc\\sample.xlsx");
		Files.write(path, bytes);

		System.out.println("END");

	}

}
