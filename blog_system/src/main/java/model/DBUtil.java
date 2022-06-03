package model;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author qiu
 * @version 1.8.0
 */
public class DBUtil {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/qrx_blog?characterEncoding=utf-8&useSSL=false";
    private static final String username = "root";
    private static final String password = "qrx123000";
    private static volatile DataSource dataSource = null;

    private static DataSource getDataSource(){
        if(dataSource == null) {
            synchronized (DataSource.class) {
                if (dataSource == null) {
                    dataSource = new MysqlDataSource();
                    ((MysqlDataSource)dataSource).setUrl(url);
                    ((MysqlDataSource)dataSource).setPassword(password);
                    ((MysqlDataSource)dataSource).setUser(username);
                }
            }
        }
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    public static void close(Connection connection, PreparedStatement statement, ResultSet resultSet){
        if(resultSet!=null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
