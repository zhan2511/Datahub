package org.example.datahub.file;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class ExcelMergerService {

    /**
     * Merges multiple Excel files into a single Excel file.
     *
     * @param templateFilePath The path of the template Excel file.
     * @param submissionFilePaths The list of paths of the submission Excel files.
     * @param outputFilePath The path of the output Excel file.
     * @throws Exception If any error occurs during the merging process.
     */
    public void mergeExcelFiles(
        String templateFilePath,
        List<String> submissionFilePaths,
        String outputFilePath
    ) throws Exception {

        FileInputStream templateFis = new FileInputStream(templateFilePath);
        XSSFWorkbook templateWb = new XSSFWorkbook(templateFis);
        XSSFSheet templateSheet = templateWb.getSheetAt(0);

        XSSFWorkbook outputWb = new XSSFWorkbook();
        XSSFSheet outputSheet = outputWb.createSheet(templateSheet.getSheetName());

        copyRow(templateSheet, outputSheet, templateSheet.getRow(0), 0);

        int outputRowIndex = 1;

        for (String filePath : submissionFilePaths) {

            FileInputStream fis = new FileInputStream(filePath);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);

            int rows = sheet.getPhysicalNumberOfRows();

            for (int i = 1; i < rows; i++) {
                Row srcRow = sheet.getRow(i);
                if (srcRow == null) continue;

                Row destRow = outputSheet.createRow(outputRowIndex);

                copyRow(templateSheet, outputSheet, srcRow, outputRowIndex);

                outputRowIndex++;
            }

            wb.close();
            fis.close();
        }

        FileOutputStream fos = new FileOutputStream(outputFilePath);
        outputWb.write(fos);
        fos.close();

        templateWb.close();
        templateFis.close();
    }

    private static void copyRow(XSSFSheet templateSheet, XSSFSheet outputSheet,
                                Row srcRow, int destRowNum) {

        Row templateRow = templateSheet.getRow(0);
        Row destRow = outputSheet.getRow(destRowNum);
        if (destRow == null) destRow = outputSheet.createRow(destRowNum);

        int colNum = templateRow.getLastCellNum();

        for (int i = 0; i < colNum; i++) {
            Cell newCell = destRow.createCell(i);

            Cell templateCell = templateRow.getCell(i);
            if (templateCell != null) {
                CellStyle newStyle = outputSheet.getWorkbook().createCellStyle();
                newStyle.cloneStyleFrom(templateCell.getCellStyle());
                newCell.setCellStyle(newStyle);
            }

            if (srcRow != null) {
                Cell srcCell = srcRow.getCell(i);
                if (srcCell != null) {
                    copyCellValue(srcCell, newCell);
                }
            }
        }
    }

    private static void copyCellValue(Cell src, Cell dest) {
        switch (src.getCellType()) {
            case STRING:
                dest.setCellValue(src.getStringCellValue());
                break;
            case NUMERIC:
                dest.setCellValue(src.getNumericCellValue());
                break;
            case BOOLEAN:
                dest.setCellValue(src.getBooleanCellValue());
                break;
            case FORMULA:
                dest.setCellFormula(src.getCellFormula());
                break;
            case BLANK:
                dest.setBlank();
                break;
            default:
                dest.setCellValue(src.toString());
        }
    }
}
