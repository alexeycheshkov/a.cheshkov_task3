import aquality.selenium.core.logging.Logger;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.DBUtils;
import utils.FileUtils;

import java.util.Properties;

public class SqlDbTest {
    private final Properties properties = FileUtils.loadProperties("src/main/resources/db_sql_query.properties");
    private final String query1 = properties.getProperty("db_select_query_1");
    private final String query2 = properties.getProperty("db_select_query_2");
    private final String query3 = properties.getProperty("db_select_query_3");
    private final String query4 = properties.getProperty("db_select_query_4");

    @Parameters({"date_filter","browser_filter_1","browser_filter_2"})
    @Test
    public void sqlTest(String date,String browser1, String browser2){
        Logger.getInstance().info("Sending SELECT query to get unique tests with minimum working time");
        FileUtils.saveToXLSX(DBUtils.getArrayTableFromDB(query1), "step1");
        Logger.getInstance().info("Sending SELECT query to get projects with count of unique tests");
        FileUtils.saveToXLSX(DBUtils.getArrayTableFromDB(query2),"step2");
        Logger.getInstance().info("Sending SELECT query to get tests which completed after "+date);
        FileUtils.saveToXLSX(DBUtils.getArrayTableFromDB(String.format(query3,date)),"step3");
        Logger.getInstance().info("Sending SELECT query to get tests which executed on "+browser1+" and "+browser2+" web drivers");
        FileUtils.saveToXLSX(DBUtils.getArrayTableFromDB(String.format(query4,browser1,browser2)),"step4");
    }
}
