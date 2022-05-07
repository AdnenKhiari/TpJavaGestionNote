package dbmodels;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBUtils {

	public	static PreparedStatement execute(String query,Object[] args) {
			
			try {				
				
				Connection cnt = DriverManager.getConnection("jdbc:mysql://localhost:3306/tpjava", "tpjavauser","tpjavauser123456");
				PreparedStatement prd = cnt.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
				int i = 1;
				if(args != null) {
					for(Object o : args) {
						if(o instanceof Integer || o.getClass().getName().equals( "int")) {
							prd.setInt(i++, (Integer)o);
						}else 	if(o instanceof String) {
							prd.setString(i++, (String)o);
						}else if(o instanceof Double || o.getClass().getName().equals( "double")) {
							prd.setDouble(i++, (Double) o);
						}
					}
				}
				System.out.println("prd mawjoud");
				
				return prd;
			}catch(Exception e) {
				System.out.println("Erreur db");
				e.printStackTrace();
			}
			return null;
			
		}
}
