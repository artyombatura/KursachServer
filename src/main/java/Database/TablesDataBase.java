package Database;

import Constants.Constants;

import java.sql.*;

public class TablesDataBase {
    private Connection connection;
    private Statement statement;

    public TablesDataBase(Connection connection, Statement statement) {
        this.connection = connection;
        this.statement = statement;
        addTableWorker();
        addTableAdmins();
        addTableUser();
    }

    private void addTableWorker(){
        if(tableExists(Constants.WORKER_TABLE)) {
            try {
                String SQL = "CREATE TABLE "+ Constants.WORKER_TABLE +
                        "( " +
                        " id  SERIAL PRIMARY KEY," +
                        " firstName VARCHAR (50), " +
                        " lastName VARCHAR (50), " +
                        " patronymic VARCHAR (50), " +
                        " countedSalary INTEGER " +
                        ")";



                statement.executeUpdate(SQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void addTableUser(){
        if(tableExists(Constants.USER_TABLE)) {
            try {
                String SQL = "CREATE TABLE "+ Constants.USER_TABLE +
                        "( " +
                        " id  SERIAL PRIMARY KEY," +
                        " login VARCHAR (50), " +
                        " password VARCHAR (50) " +
                        ")";

                statement.executeUpdate(SQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void addTableAdmins(){
        if(tableExists(Constants.ADMINS_TABLE)) {
            try {
                String SQL = "CREATE TABLE "+ Constants. ADMINS_TABLE+
                        "( " +
                        " id  SERIAL PRIMARY KEY," +
                        " login VARCHAR (50), " +
                        " password VARCHAR (50) " +
                        ")";



                statement.executeUpdate(SQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




    private boolean tableExists(String nameTable) {
        try{
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, nameTable, null);
            rs.last();
            return rs.getRow() <= 0;
        }catch(SQLException ignored){

        }
        return true;
    }
}
