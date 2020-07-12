package com.company.Database;
import com.company.Custumer.Custumer;
import com.company.vehicles.Vehicle;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class JDBCConnector {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5433/carRental";
    private static Connection CONN; //establish connection to database

    static final String USER = "postgres";
    static final String PASS = "haslomaslo";

    public static void connect() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASS);
        CONN = DriverManager.getConnection(DB_URL, props);
        System.out.println("connected");
    }

    public static Statement getStatement() throws SQLException {
        return CONN.createStatement();
    }

    public static void executeSQL(String sql) throws SQLException {
        CONN.createStatement().execute(sql);
    }

    public static ResultSet getDataFromTable(String sql) throws SQLException {
        PreparedStatement ps = CONN.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public static void saveCarsToDB() throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        Vehicle v1 = new Vehicle("Audi","White", 563,"Premium", 23445, 1);
        Vehicle v2 = new Vehicle("Bmw","Black", 635523, "Budget", 654, 2);
        Vehicle v3 = new Vehicle("Mercedes","Red", 344, "Premium", 52354, 3);
        Vehicle v4 = new Vehicle("Mommys","Blue", 64345,"Standard",  6456, 4);
        Vehicle v5 = new Vehicle("Daddys","Yellow", 236456,"Standard", 2344, 5);
        vehicles.add(v1);
        vehicles.add(v2);
        vehicles.add(v3);
        vehicles.add(v4);
        vehicles.add(v5);


        for (Vehicle c : vehicles) {
            //f for float, s for string, d for decimal
            String sql = String.format(Locale.US,
                    "insert into \"vehicles\" values('%s', '%s', %d, '%s', %.2f)",
                    c.getBrand(), c.getColor(), c.getMileage(),c.getSegment(), c.getValue());
            executeSQL(sql);
        }


    }

    public static void CustumersDB() throws SQLException {
        List<Custumer> custumers = new ArrayList<>();
        Custumer c1 = new Custumer(1, "Adrian", "A", new String[]{"Car"}, new String[]{"Bmw"}, true, new String[]{"Budget"}, 1200);
        custumers.add(c1);
        Custumer c2 = new Custumer(2, "Andrzej","B", new String[]{"Car"}, new String[]{"Daddys", "Mommys"}, false, new String[]{"Standard"}, 90000);
        custumers.add(c2);
        Custumer c3 = new Custumer(3, "Adam","C", new String[]{"Car"}, new String[]{"Audi", "Mercedes"}, false, new String[]{"Premium"}, 85000);
        custumers.add(c3);
        Custumer c4 = new Custumer(4, "Albert","D", new String[]{"Car"}, new String[]{"Daddys", "Mommys"}, true, new String[]{"Standard"}, 50000);
        custumers.add(c4);



        for (Custumer c : custumers) {
            String sql = " \"Custumers\"  values(?, ?, ?, ?, ?, ?, ?, ?);";
            Array arrayvehicle = CONN.createArrayOf("text", c.getVehicle());
            Array arrayBrand = CONN.createArrayOf("text", c.getBrands());
            Array arraySegment = CONN.createArrayOf("text", c.getSegment());


            PreparedStatement ps = CONN.prepareStatement(sql);
            ps.setInt(1, c.getId());
            ps.setString(2, c.getName());
            ps.setString(3, c.getSurname());
            ps.setArray(4, arrayvehicle);
            ps.setArray(5, arrayBrand);
            ps.setBoolean(6, c.buyDamaged());
            ps.setArray(7, arraySegment);
            ps.setDouble(8, c.getMoney());

            ps.executeUpdate();
        }
    }
}
