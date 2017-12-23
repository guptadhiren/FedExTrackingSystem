import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Simulator extends Thread {

	public void run() {
        System.out.println("Hello from a thread!");
   
		ConnectionManager path=new ConnectionManager();
		
		String updateShipStatusString="update shipping_details set ship_status= case when ship_status2=1 then"+
						"'Picked up' "+
						"when  ship_status2=2 then "+
						"'In Transit' "+
						" when  ship_status2=3 then "+
						"'On FedEx vehicle for delivery' "+
						"when  ship_status2=4 then  "+
						"'Delivered' "+
						"End "+
						" from "
						+ "(select shipping_id,ship_status2= "+
						"case when ship_status='Picked up' then "+
						"1 + ROUND( 3 *RAND(convert(varbinary, newid())), 0) "+
						"when  ship_status='In Transit' then "+
						"2 + ROUND( 2 *RAND(convert(varbinary, newid())), 0) "+
						" when  ship_status='On FedEx vehicle for delivery' then "+
						"3 + ROUND( 1 *RAND(convert(varbinary, newid())), 0) "+
						"when  ship_status='Delivered' then "+
						"4 "+						 
						"End "+
						"from (select shipping_id,ship_status	from  shipping_details)b)newStatus  join shipping_details on shipping_details.shipping_id=newStatus.shipping_id";
		
		PreparedStatement updateShipStatus=null;
		PreparedStatement  updateCurrentCity=null;
		try {
			updateShipStatus = path.getConnectionManager().prepareStatement(updateShipStatusString);
		
		updateShipStatus.executeUpdate();

		String updateCurrentCityString="update shipping_details set current_city=destination where ship_status in('On FedEx vehicle for delivery','Delivered')"
				+"update shipping_details set current_city=start_city where ship_status in('Picked up')";
		  updateCurrentCity= path.getConnectionManager().prepareStatement(updateCurrentCityString);
		updateCurrentCity.executeUpdate();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in thread Exception");
			e.printStackTrace();
		}
		finally {
	        if (updateShipStatus != null) {
	        	try {
					updateShipStatus.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        if (updateCurrentCity != null) {
	        	try {
					updateCurrentCity.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        

	    }
	}
	
	
}
