package utils;

import aquality.selenium.core.logging.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Properties;

public class FileUtils {

    public static Properties loadProperties(String path) {
        Properties properties = null;
        try(FileInputStream fis = new FileInputStream(path)) {
            Logger.getInstance().debug("Loading configuration file: "+path);
            properties = new Properties();
            properties.load(fis);
        } catch (Exception e) {
            Logger.getInstance().error("Properties can't be loaded");
            e.printStackTrace();
        }
        return properties;
    }

    public static void saveToXLSX(String[][] resultArray, String fileName){
        try{
            Logger.getInstance().debug("Saving data from database to .xlsx file");
            File file = new File(String.format("target/log/%s.xlsx",fileName));
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("new sheet");
            int columnsNumber = resultArray[0].length;
            int rowsNumber = resultArray.length;
            Row headerRow = sheet.createRow(0);
            for(int col=0 ;col < columnsNumber;col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(resultArray[0][col]);
            }
            for (int row = 1; row < rowsNumber; row++) {
                Row bodyRow = sheet.createRow(row);
                for(int col=0 ;col < columnsNumber;col++) {
                    Cell cell = bodyRow.createCell(col);
                    cell.setCellValue(resultArray[row][col]);
                }
                if (file.createNewFile()) {
                    FileOutputStream fos = new FileOutputStream(file);
                    workbook.write(fos);
                    fos.close();
                }
            }
        }
        catch (IOException e) {
            Logger.getInstance().debug("Data cannot be saved");
            e.printStackTrace();
        }
    }
}
