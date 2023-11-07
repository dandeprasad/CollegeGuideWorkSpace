import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.*;
import java.util.*;
import java.sql.*; 
public class XlsToOracle {  
        public static void main(String[] args) 
        		throws Exception{                
                /* Create Connection objects */
                Class.forName ("com.mysql.jdbc.Driver"); 
                Connection conn = DriverManager.getConnection("jdbc:mysql://gnyanndatabase.cxrwezztiru3.us-east-2.rds.amazonaws.com:3306/collegeguideworkspace", "dandemaster2", "Welcome1234");
                
                PreparedStatement sql_statement = null;
                String jdbc_insert_sql =  "INSERT INTO cutoffs_detail_colleges_iits(ROUND,COLLEGE_ID,COLLEGE_NAME,COURSE,CATEGORY,QUOTA,OPENING_RANK,CLOSING_RANK,SEAT_POOL ,CUTOFF_YEAR) VALUES(?,?,?,?,?,?,?,?,?,?)";              
                
                sql_statement = conn.prepareStatement(jdbc_insert_sql);
                /* We should now load excel objects and loop through the worksheet data */
                //"C:\\Users\\mahesh.beeram\\Desktop\\xlsfile\\xls_to_oracle.xls"
                FileInputStream input_document = new FileInputStream(new File("//Users//dandereddyprasad//Desktop//CollegeexploreContents//xls_to_oracle.xls"));
                /* Load workbook */
                HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_document);
                /* Load worksheet */
                HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0);
                int maxNumOfCells = my_worksheet.getRow(0).getLastCellNum();
                System.out.println(maxNumOfCells);
                // we loop through and insert data
                Iterator<Row> rowIterator = my_worksheet.iterator(); 
                
                System.out.println(my_worksheet.getLastRowNum());
                int countVal=2;
                while(rowIterator.hasNext()) {
                        Row row = rowIterator.next();
                        if(row.getRowNum()==0){
                        	continue;
                        }
                        boolean flag=isRowEmpty(row);
                        if(!flag){
                        Iterator<Cell> cellIterator = row.cellIterator();
                        int count = 1;
                        		for(int x=0;x<maxNumOfCells;x++){
                             /*   while(cellIterator.hasNext()) {*/
                                       // Cell cell = cellIterator.next();
                        		Cell cell=	row.getCell(x, row.CREATE_NULL_AS_BLANK);
                        		
                                        switch(cell.getCellType()) { 
                                        case Cell.CELL_TYPE_STRING: //handle string columns
                                                sql_statement.setString(count, cell.getStringCellValue());  
                                                count++;
                                                break;
                                        case Cell.CELL_TYPE_NUMERIC: //handle double data
                                                sql_statement.setInt(count,(int) cell.getNumericCellValue() );
                                                count++;
                                                break;
                                        default:
                                        	
                                        	  sql_statement.setString(count,null);
                                        	  count++;
                                        	
                                        	  break;
                                                
                                        }
                                        }
                    					
                                      
                              /*  }*/
                //we can execute the statement before reading the next row
                sql_statement.executeUpdate();
                        }
                }
                /* Close input stream */
                input_document.close();
                /* Close prepared statement */
                sql_statement.close();
                /* COMMIT transaction */
                conn.commit();
                /* Close connection */
                conn.close();
        }
        
        
        
        public static boolean isRowEmpty(Row row) {
            for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
                Cell cell = row.getCell(c);
                if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                    return false;
            }
            return true;
        }
     
}