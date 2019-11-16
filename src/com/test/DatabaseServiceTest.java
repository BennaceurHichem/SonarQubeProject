package com.test;

import com.example.model.City;
import com.example.service.DatabaseConnection;
import com.example.service.DatabaseService;
import org.h2.jdbc.JdbcSQLException;
import org.junit.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseServiceTest {
    Connection conn;
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());
    DatabaseConnection db;
    static final String USER = "sa";
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:mem:test";

    @Before
    public void setUp() throws Exception {
        db = new DatabaseConnection(USER, JDBC_DRIVER, DB_URL);
        conn = db.connect();
        db.createDb(conn);
    }

    @Test
    public void testAddCity() throws SQLException {
        City city = new City(1, "Alger", 300000000, "Belle ville");
        int i = DatabaseService.addCity(conn, city);
        Assert.assertEquals(i, 1);
    }

    @Test
    public void testGetCity() throws SQLException {
        City city = new City(1, "Alger", 300000000, "Belle ville");
        DatabaseService.addCity(conn, city);
        City city2 = DatabaseService.getCity(conn, 1);
        Assert.assertEquals(city2, city);
    }
    @Test(expected = JdbcSQLException.class)
    public void testNameGetCities() throws IOException, SQLException,JdbcSQLException {
       String USER = "root";
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
       String DB_URL = "jdbc:mysql://localhost:3306/citiesdb";
        City city2 = new City(10,"Paris",300000000,"Belle ville");

        DatabaseConnection db = new DatabaseConnection("root",JDBC_DRIVER, DB_URL);
        DatabaseService.addCity(conn,city2);
        Assert.assertEquals(city2.getName(),DatabaseService.getCityByName(conn,city2.getName()).getName() );


    }

    @Test(expected = SQLException.class)
    public void testSql() throws SQLException {
    City city2 = new City(10,"Paris",300000000,"Belle ville");
    DatabaseService.addCity(conn,city2);
        Assert.assertEquals(city2.getName(),DatabaseService.getCityByName(conn,city2.getName()).getName() );

    }
    @Test
    public void testGetCities() throws SQLException {
        City city1 = new City(1, "Alger", 300000000, "Belle ville");
        City city2 = new City(2, "Oran", 300000000, "Belle ville");
        City city3 = new City(3, "Annaba", 300000000, "Belle ville");
        City[] expectedCities = {city1, city2, city3};
        DatabaseService.addCity(conn, city1);
        DatabaseService.addCity(conn, city2);
        DatabaseService.addCity(conn, city3);
        List cities = DatabaseService.getCities(conn);
        Assert.assertArrayEquals(cities.toArray(), expectedCities);
    }

    @Test(expected = SQLException.class)
    public void sqExecuteUpdatelTest() throws Exception {

        Statement stmt = conn.createStatement();
        String sql = "CREATE TABLE   City " +
                "(idCity INTEGER not NULL, " +
                " name VARCHAR(255), " +
                " touristNumber INTEGER, " +
                " des VARHAR(255), " +
                " PRIMARY KEY ( idCity ))";
        stmt.executeUpdate(sql);
     // stmt = conn.createStatement();


    }

    @Test(expected = SQLException.class)
    public void driverManagerTest() throws SQLException {
        conn = DriverManager.getConnection("url","user" , "root");
    }
    @Test(expected = SQLException.class)
    public void closingTest() throws IOException, SQLException {
        String USER = "root";
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3306/citiesdb";
        DatabaseConnection db = new DatabaseConnection("false_user",JDBC_DRIVER, DB_URL);
        Connection conn = db.connect();


        conn.close();
    }

    @Test(expected = SQLException.class)
    public void rsTest() throws SQLException {

        Statement stmt = conn.createStatement();
        String sql = "CREATE TABLE   City " +
                "(idCity INTEGER not NULL, " +
                " name VARCHAR(255), " +
                " touristNumber INTEGER, " +
                " des VARHAR(255), " +
                " PRIMARY KEY ( idCity ))";
        stmt.executeUpdate(sql);
        ResultSet rs = stmt.executeQuery(sql);


    }
    @Test
    public void testGetCityByName()  {
        try {
            City city = new City(1,"Alger",300000000,"Belle ville");
            DatabaseService.addCity(conn,city);
            City city2 = DatabaseService.getCityByName(conn,"Alger");
            Assert.assertEquals(city2,city);
        }catch (SQLException se) {
            LOGGER.log(Level.INFO,se.getMessage(),se);
        }

    }
    @After
    public void tearDown() throws Exception {
        db.disconnect(conn);
    }


}