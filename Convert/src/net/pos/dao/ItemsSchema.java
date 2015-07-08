

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ItemsSchema {

	public static void main(String[] args) {
		Connection connTarget = null;

		// connect to the target
		try
        {
            System.out.println("Target: POS.sqlite");
            Class.forName("org.sqlite.JDBC");  
            connTarget = DriverManager.getConnection("jdbc:sqlite:data\\POS.sqlite");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
		
		
/*		Statement selectStmt = null;
		ResultSet rsSource = null;
		String upc;
		String s;
		
		try {
			rsSource = selectStmt
				    .executeQuery("SELECT UPC,UPCDES,UNIT,LIST,COST,DEPT,CATEGORY,SUBCAT,VENDOR,TX1,TX2 FROM ITEMS ORDER BY UPC");
		} catch (SQLException e) {
			e.printStackTrace();
		}


		long targetRowsInserted = 0;
		long targetRows = 0;
		long sourceRows = 0;

		if(rsSource != null) {
			try {
				while(rsSource.next()) {
	    			
	    			targetRowsInserted++;
					targetRows++;
	            } // while
            } catch (SQLException e) {
				e.printStackTrace();
            }
		} // if
		
		System.out.println("Finished! Source: " + sourceRows + "; Target: " + targetRows + "; Target inserted: " + targetRowsInserted);
*/	}

}
