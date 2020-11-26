package Database;

import Constants.Constants;

import java.sql.*;
import java.util.LinkedList;

public class DatabaseAction {
    private Connection connection;
    private Statement statement;

    public DatabaseAction(){
        connectionToDataBase();
        new TablesDataBase(connection,statement);
    }


    private void connectionToDataBase(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employees",
                   "postgres",
                 "123456");
            statement= connection.createStatement();

            System.out.println("Database connection is done");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addWorkerInDataBase(String firstnameDb, String lastNameDb , String patronymicDb,int countedSalaryDb){

        try {
            String query = " insert into "+Constants.WORKER_TABLE + " (firstName,lastName,patronymic,countedSalary )"
                    + " values ( ?, ?,?,?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString (1, firstnameDb);
            preparedStmt.setString (2, lastNameDb);
            preparedStmt.setString (3, patronymicDb);
            preparedStmt.setInt (4, countedSalaryDb);


            preparedStmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void showAllWorkers(LinkedList<String> workers){
        String query = "SELECT "+Constants.ID_WORKER +" , " + Constants.FIRST_NAME+" , "+Constants.LAST_NAME+" , "
                +Constants.PATRONYMIC+" ," +Constants.COUNTED_SALARY +" FROM " + Constants.WORKER_TABLE;
        ResultSet rs = null;
        String worker="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                worker+=rs.getString(Constants.ID_WORKER)+" ";
                worker+=rs.getString(Constants.FIRST_NAME)+" ";
                worker+=rs.getString(Constants.LAST_NAME)+" ";
                worker+=rs.getString(Constants.PATRONYMIC)+" ";
                worker+=rs.getString(Constants.COUNTED_SALARY)+" ";
                workers.add(worker);

                worker="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Boolean authorization(String login,String password){
        try {
            String query = "SELECT " + Constants.LOGIN_ADMIN + "," + Constants.PASSWORD_ADMIN + " FROM " + Constants.ADMINS_TABLE +
                    " WHERE " + Constants.LOGIN_ADMIN + " = " + "'" + login + "'" + " AND " + Constants.PASSWORD_ADMIN + " = " + "'" + password + "'";

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                if (!rs.getString(Constants.LOGIN_ADMIN).equals("") &&
                        !rs.getString(Constants.PASSWORD_ADMIN).equals("")) {
                   return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void deleteWorker(int id){
        String selectSQL = "DELETE FROM "+Constants.WORKER_TABLE +  " WHERE id = ?";
        try {
            connection.prepareStatement(selectSQL);
            PreparedStatement preparedStmt = connection.prepareStatement(selectSQL);
            preparedStmt.setInt(1, id);
            preparedStmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean auth(String login, String password) {
        try {
            String query = "SELECT " + Constants.USER_LOGIN + "," + Constants.USER_PASSWORD + " FROM " + Constants.USER_TABLE +
                    " WHERE " + Constants.USER_LOGIN + " = " + "'" + login + "'" + " AND " + Constants.USER_PASSWORD + " = " + "'" + password + "'";

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                if (!rs.getString(Constants.USER_LOGIN).equals("") &&
                        !rs.getString(Constants.USER_PASSWORD).equals("")) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
