package com.example.main;

import com.example.model.City;
import com.example.service.DatabaseConnection;
import com.example.service.DatabaseService;


import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;


public class Home {
    //exception thrown
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(DatabaseConnection.class.getName());

    public static final String USER = "root";




    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/citiesdb";



    public static void main(String[] args) throws IOException {

        DatabaseConnection db = new DatabaseConnection(USER,JDBC_DRIVER, DB_URL);
        Connection conn = db.connect();
        try {
            if (args.length > 0) {
                int id = Integer.parseInt(args[0]);
                String name = args[1];
                int numTourist = Integer.parseInt(args[2]);
                String desc = args[3];
                City city1 = new City(id, name, numTourist, desc);
                DatabaseService.addCity(conn, city1);
            }
        }
        catch (NumberFormatException | SQLException e) {
            LOGGER.warning(e.getMessage());
            System.exit(1);
        }

    }


    public static String getPassFromFile() throws IOException
    {
        String st="";
        String path="pass.txt";
        File file = new File(path);
        BufferedReader br=new BufferedReader(new FileReader(file));
        try {
            while ((st = br.readLine()) != null) {
            LOGGER.info(st);
            return st;
            }


        }finally {
            br.close();

        }

        return st;
    }

}
