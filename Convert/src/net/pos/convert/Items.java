package net.pos.convert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import org.sqlite.JDBC;

public class Items {

	public static String appendSqlString(String s) {
		if(s != null) {
			s.replaceAll("\'", "\'\'");
			s.replaceAll("\"", "\'\'");
		}

		return "," + ((s == null) ? "NULL" : ("\"" + s + "\""));
	}
	
	public static void main(String[] args) {
		Connection connSource = null;
		Connection connTarget = null;

		// connect to the source
		try
        {
            System.out.println("Source: POS.origin.mdb");
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String database = 
                      "jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb)};DBQ=C:\\Users\\User\\Yuming\\workspace\\MyPOS\\POS.origin.mdb";
            connSource = DriverManager.getConnection(database, "", "");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
		
		// connect to the target
		try
        {
            System.out.println("Target: POS.sqlite");
            Class.forName("org.sqlite.JDBC");  
            connTarget = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\User\\Yuming\\workspace\\MyPOS\\POS.sqlite");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
		
		
		Statement selectStmt = null, insertStmt = null;
		ResultSet rsSource = null;
		String upc;
		String s;
		
		try {
			selectStmt = connSource.createStatement();
			rsSource = selectStmt
				    .executeQuery("SELECT UPC,UPCDES,UNIT,LIST,COST,DEPT,CATEGORY,SUBCAT,VENDOR,TX1,TX2 FROM ITEMS ORDER BY UPC");
			

			insertStmt = connTarget.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		if(rsSource != null) {
			try {
				while(rsSource.next()) {
	/*                System.out.println(rsSource.getString(1));    //First Column
	                System.out.println(rsSource.getString(2));    //Second Column
	                System.out.println(rsSource.getString(3));    //Third Column
	                System.out.println(rsSource.getString(4));    //Fourth Column
	                System.out.println(rsSource.getString(10));
	                System.out.println(rsSource.getString(11));
	*/
					
	    			String sQuery = "\"" + rsSource.getString(1) + "\"";
					upc = sQuery;
					
					s = rsSource.getString(2);
					sQuery = sQuery + appendSqlString(s);
	
					s = rsSource.getString(3);
					sQuery = sQuery + appendSqlString(s);
	
					s = rsSource.getString(4);
					sQuery = sQuery + "," + ((s == null) ? 0.0 : s);
	
					s = rsSource.getString(5);
					sQuery = sQuery + "," + ((s == null) ? 0.0 : s);
	
					s = rsSource.getString(6);
					sQuery = sQuery + appendSqlString(s);
	
					s = rsSource.getString(7);
					sQuery = sQuery + appendSqlString(s);
	
					s = rsSource.getString(8);
					sQuery = sQuery + appendSqlString(s);
	
					s = rsSource.getString(9);
					sQuery = sQuery + appendSqlString(s);
	
					s = rsSource.getString(10);
					sQuery = sQuery + "," + ((s == null) ? 1 : s);
	
					s = rsSource.getString(11);
					sQuery = sQuery + "," + ((s == null) ? 0 : s);
	
					sQuery = "INSERT INTO ITEMS (UPC,UPCDES,UNIT,LIST,COST,DEPT,CATEGORY,SUBCAT,VENDOR,TAX1,TAX2) VALUES ("+ sQuery + ")";
	    			
	    			System.out.println(sQuery);
	    			
	    			try {
	    				insertStmt.executeUpdate(sQuery);
	    	  		} catch (SQLException e) {
	    				if(e.toString().contains("is not unique")) {
	//    					s = "UPDATE ITEMS SET UPC,UPCDES,UNIT,LIST,COST,DEPT,CATEGORY,SUBCAT,VENDOR,TAX1,TAX2) SET column1=value1,column2=value2,... WHERE UPC=" + upc;
	    	    			System.out.println("UPC (" + upc + "): is not unique!");
	    				} else {
	    					e.printStackTrace();
	    			//		break;
	    				}
	    			}
	            } // while
            } catch (SQLException e) {
				e.printStackTrace();
            }
		} // if
		System.out.println("Finished!");
	}

}
