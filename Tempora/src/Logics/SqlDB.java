/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logics;

/**
 *
 * @author Hanze
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Hanze
 */
public class SqlDB
{


    public SqlDB()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            
        }
    }

    /**
     *
     * @return
     */
    public Connection openConnection()
    {
        try
        {
           return DriverManager.getConnection(
                    "jdbc:mysql://" + Settings.DB_IP + "/" + Settings.DB_NAME + "?"
                    + "user=" + Settings.DB_USERNAME
                    + "&password=" + Settings.DB_PASSWORD);
        }
        catch (SQLException e)
        {
            
            return null;
        }
    }

    

}
