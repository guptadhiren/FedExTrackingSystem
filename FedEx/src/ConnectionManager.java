import java.sql.*;

public class ConnectionManager {
	
	
		public Connection getConnectionManager()
		{
			Connection con=null;
			try{
				//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			 con=DriverManager.getConnection("jdbc:sqlserver://localhost;DatabaseName=FedEx;integratedSecurity=true;");
				if(con !=null) 
				{
					//System.out.println("connection successfull");
					 			
				}
				else {
					
					System.out.println("connection failed");
					
				}
				
			}
			catch(Exception e)
			{
				System.out.println(e);
				System.out.println("connection failed");
				
			}
			return con;
		}
		
	
	
}
	
