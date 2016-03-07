package com.test.util;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.test.bean.TestCase;
import com.test.bean.TestResult;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by snail on 11/21/15.
 */
public class  WriteResultUtils{
	
	
    private static String path = "D:\\excel\\result.xls";

    //get the HSSFWorkbook
    private HSSFWorkbook getWorkBook(){
        InputStream stream = null;
        HSSFWorkbook workbook = null;
        try{
            stream = new FileInputStream(path);
            workbook = new HSSFWorkbook(stream);
        }catch (Exception e){
            System.out.print("ERROR");
        }

        return workbook;
    }

    public boolean writeResultSummary(TestResult summary, String filePath){
        HSSFWorkbook workbook = getWorkBook();
        HSSFSheet sheet = workbook.getSheetAt(0);
        int startRow=1,startColumn=2,rowCount=7;
        HSSFRow row = null;
        String[] result = getResultArray(summary);


        for(int i=0;i<rowCount;i++){
            row = sheet.getRow(startRow+i);
            HSSFCell cell = row.getCell(startColumn);
            cell.setCellValue(result[i]);
        }
        writeTestItem(sheet,summary);

        System.out.print("OK");

        return writeWorkbook(workbook,filePath);
    }

    private void writeTestItem(HSSFSheet sheet,TestResult summary){
        int startRow = 10;
        if(summary.getItem()!=null && summary.getItem().size()>0){
            int index = 0;
            for(TestCase item:summary.getItem()){
                HSSFRow row = sheet.getRow(startRow+index);
                HSSFCell cell = row.getCell(0);
                cell.setCellValue(index);
                cell = row.getCell(1);
                cell.setCellValue(item.getBussinessDesp());
                cell = row.getCell(2);
                cell.setCellValue(item.getResult());
                cell = row.getCell(3);
                cell.setCellValue(item.getRemark());
                index++;
            }
        }
    }

    private boolean writeWorkbook(HSSFWorkbook workbook,String filePath){
        boolean result = false;
        try{
            OutputStream out = new FileOutputStream(filePath);
            workbook.write(out);
            out.close();
            result = true;
        }catch (Exception e){
            System.out.print("[writeWorkbook] ERROR");
        }

        return result;
    }

    private String[] getResultArray(TestResult summary){
        String[] result = new String[7];

        result[0] = summary.getDate();
        result[1] = summary.getStartTime();
        result[2] = summary.getEndTime();
        result[3] = summary.getDuration();
        result[4] = summary.getTotalCount();
        result[5] = summary.getPassCount();
        result[6] = summary.getFailCount();

        return result;
    }

}
