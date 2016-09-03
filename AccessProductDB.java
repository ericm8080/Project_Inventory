package finalProject;
/*
 *  Eric Mueller personal project:
 *  AccessProductDB.java (DB connection & management methods)
 *  product inventory Software2016
 */

// Accesses a SQL database with Product information
// from SQL server connection

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
// import java.util.Date;

public class AccessProductDB {
	private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet vrSet = null;

    final private String host = "localhost:8889";
    final private String user = "root";
    final private String passwd = "Root1";
    final private String database = "EMinventoryDB";

    public void connectToDB() throws Exception {
            try {
                    // This will load the MySQL driver, each DB has its own driver
                    Class.forName("com.mysql.jdbc.Driver");
                    // ********Setup the connection with the DB ************************************
                    // *******sample********:  "jdbc:mysql://localhost:3306/projects?user=user1&password=123"
                    
                    connect = DriverManager.getConnection("jdbc:mysql://" + host + "/"
                                    + database + "?" + "user=" + user + "&password=" + passwd);

            } catch (Exception e) {
                    throw e;
            }
    }
// ********************This is where I return an ArrayList built from the SQL data just read********************************
    public List<Product> readProducts() throws Exception {
    		List<Product> vRtrnData_AL = new ArrayList<Product>();
            try {
            		String query = "SELECT item_id, itemName, category, quantity, cost_each, price_each FROM " + database + ".MasterProducts"; // abstracts query text into a variable
                    statement = connect.createStatement();
                    vrSet = statement.executeQuery(query);
                    while (vrSet.next()) {
                            String name = vrSet.getString("itemName");
                            int qnt = vrSet.getInt("quantity");     // E you will need proper DB column names !!
                            float cost = vrSet.getFloat("cost_each");
                            float price = vrSet.getFloat("price_each");
                            Product product = new Product(name, qnt, cost, price);  // builds one product object at a time (future upgrade will use more info constructor)
                            vRtrnData_AL.add(product);								// then adds that instance to an ArrayList
                    }
            } catch (Exception e) {
                    throw e;
            }
            return vRtrnData_AL;  // returns the new ArrayList just built from interating through the SQL database :) yeah!
    }
    
    // ****************This is where I insert new data ITEM into the SQL DB ********************
    public void writeProducts(String name, int qnt) throws Exception {
    	try {
    			String query = "INSERT INTO "+database+".MasterProducts "+"(itemName, quantity) VALUES('"+name+"','"+qnt+"')";
    			statement = connect.createStatement();
    			statement.executeUpdate(query);
    	} catch (Exception e) {
    		throw e;
    	}
    	System.out.println("DBUGspot just checking on the writeProducts method in AccessProductDB class"+name);
    }
    
    // ****************This is where I UPDATE new data (Price) into the SQL DB ********************
    public void writeProdPrice(String name, float price) throws Exception {
    	try {
    			String query = "UPDATE "+database+".MasterProducts SET price_each = "+price+" WHERE itemName = '"+name+"'";
    			statement = connect.createStatement();
    			statement.executeUpdate(query);
    	} catch (Exception e) {
    		System.out.println("writeProdPrice method EXCEPTION TESTER thrown" + name);
    		throw e;
    	}
    	System.out.println("DBUGspot just checking write new price worked in AccessProductDB class"+name);
    }
    
    // You need to close the resultSet
    public void close() {
            try {
                    if (vrSet != null) {
                            vrSet.close();
                    }

                    if (statement != null) {
                            statement.close();
                    }

                    if (connect != null) {
                            connect.close();
                    }
            } catch (Exception e) {

            }
    }

}
