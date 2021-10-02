package utils;

import aquality.selenium.core.logging.Logger;

import java.sql.*;
import java.util.Properties;

public class DBUtils {
    private static final Properties properties = FileUtils.loadProperties("src/main/resources/db_config.properties");
    private static Connection connection;
    private static Statement statement;

    public static void connect() {
        try {
            Logger.getInstance().debug("Connecting to the mySQL database");
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(properties.getProperty("db_url"), properties.getProperty("root_name"), properties.getProperty("root_password"));
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        } catch (Exception ex) {
            Logger.getInstance().debug("Couldn't connect to the database");
            ex.printStackTrace();
        }
    }

    public static String[][] getArrayTableFromDB(String sqlScript){
        connect();
        Logger.getInstance().debug("Getting result set from database");
        String[][] resultArray = null;
        try {
            ResultSet resultSet = statement.executeQuery(sqlScript);
            int columnAmount = resultSet.getMetaData().getColumnCount();
            resultSet.last();
            resultArray = new String[resultSet.getRow()][columnAmount];
            resultSet.beforeFirst();
            while (resultSet.next()){
                for (int i = 1; i <= columnAmount; i++) {
                    resultArray[resultSet.getRow()-1][i-1] = resultSet.getString(i);
                }
            }
        } catch (SQLException ex) {
            Logger.getInstance().debug("The result cannot be obtained");
            ex.printStackTrace();
        }
        disconnect();
        return resultArray;
    }

    public static void disconnect() {
        try {
            Logger.getInstance().debug("Disconnecting from database");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
