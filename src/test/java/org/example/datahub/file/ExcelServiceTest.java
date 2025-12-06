package org.example.datahub.file;


import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExcelServiceTest {
    private static final ExcelService EXCEL_SERVICE = new ExcelService();

    private static final String templateFilePath = "template.xlsx";
    private static final String inputFilePath1 = "input1.xlsx";
    private static final String inputFilePath2 = "input2.xlsx";
    private static final String inputFilePath3 = "input3.xlsx";
    private static final String outputFilePath = "output.xlsx";

    @BeforeAll
    static void prepare() {
        String[] header = {"name", "age", "gender"};
        try {
            EXCEL_SERVICE.createExcelFile(templateFilePath, List.of(new String[][]{header}));
            EXCEL_SERVICE.createExcelFile(inputFilePath1, List.of(
                header,
                new String[] {"Alice", "25", "female"},
                new String[] {"Bob", "30", "male"}
            ));
            EXCEL_SERVICE.createExcelFile(inputFilePath2, List.of(
                header,
                new String[] {"Charlie", "35", "male"},
                new String[] {"David", "40", "male"}
            ));
            EXCEL_SERVICE.createExcelFile(inputFilePath3, List.of(
                header,
                new String[] {"Emily", "45", "female"},
                new String[] {"Frank", "50", "male"}
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to prepare test data", e);
        }
    }

    @AfterAll
    static void cleanUp() throws Exception {
        Files.deleteIfExists(Paths.get(templateFilePath));
        Files.deleteIfExists(Paths.get(inputFilePath1));
        Files.deleteIfExists(Paths.get(inputFilePath2));
        Files.deleteIfExists(Paths.get(inputFilePath3));
        Files.deleteIfExists(Paths.get(outputFilePath));
    }

    @Test
    void testMergeExcelFiles() throws Exception {
        EXCEL_SERVICE.mergeExcelFiles(
            templateFilePath,
            List.of(inputFilePath1, inputFilePath2, inputFilePath3),
            outputFilePath
        );

        assertTrue(Files.exists(Paths.get(outputFilePath)));
        File outputFile = new File(outputFilePath);
        try (Workbook workbook = WorkbookFactory.create(new File(outputFilePath))) {
            Sheet sheet = workbook.getSheetAt(0);
            assertEquals(6, sheet.getLastRowNum());
            assertEquals("name", sheet.getRow(0).getCell(0).getStringCellValue());
            assertEquals("age", sheet.getRow(0).getCell(1).getStringCellValue());
            assertEquals("gender", sheet.getRow(0).getCell(2).getStringCellValue());
        }
    }

}
