package com.example.service;

import com.example.model.City;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseService {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

    private static String touristNumber="touristNumber";
    private static String idCity ="idCity";
    private static String description ="description";

            private DatabaseService(){

            }

    public static int addCity(Connection conn, City city) throws SQLException {

        int i = -1;
        String sql = "INSERT INTO City " + "VALUES (?,?,?,?)";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, city.getIdCity());
            pstmt.setString(2, city.getName());
            pstmt.setInt(3, city.getTouristNumber());
            pstmt.setString(4, city.getDescription());
            i= pstmt.executeUpdate();

        }
        return i;
    }


   public static City getCity(Connection conn,int idCity) {

        City city = new City();
       String sql = "SELECT * FROM City where idCity=?";
        try( PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCity);

            try(ResultSet rs = pstmt.executeQuery())
            {
                while (rs.next()) {
                   city=  fillCityInfo(rs,"idCity","name",touristNumber,description);

                }
            }

        } catch (SQLException se) {
            LOGGER.warning(se.getMessage());
        }
        return  city;
    }


    public static List<City> getCities(Connection conn) {
        Statement stmt = null;
        List<City> cities = new ArrayList<>();

        try {

            String sql = "SELECT * FROM City";
            stmt = conn.createStatement();

            try( ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                        City city = new City();
                    city=  fillCityInfo(rs,idCity,"name",touristNumber,description);


                    cities.add(city);
                }
            } finally {

                stmt.close();
            }


        } catch (SQLException se) {
            LOGGER.warning(se.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
                LOGGER.warning(se2.getMessage());

            }
        }
        return  cities;
    }
// New method

    public static City getCityByName(Connection conn,String name) throws SQLException {

        City city = new City();
        String sql = "SELECT * FROM City where name=?";
        try(Statement stmt = conn.createStatement()) {


            try( ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    city=  fillCityInfo(rs,idCity,name,touristNumber,description);

                }
            }
            return city;
        }

    }
public static City fillCityInfo(ResultSet rs, String idCity, String name, String touristNumber, String desc) throws SQLException {
       City city =new City() ;
    city.setIdCity(rs.getInt(idCity));
    city.setName(rs.getString(name));
    city.setTouristNumber(rs.getInt(touristNumber));
    city.setDescription(rs.getString(desc));
    return city;
}

}
