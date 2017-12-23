import java.util.*;
import java.io.IOException;
import java.sql.*;

public class ShipmentDetails {

	//private double shipmentId;

	ShipingDetailsBean shipingBean = new ShipingDetailsBean();

	public  ShipmentDetails(double id) throws SQLException, IOException
	{
		//shipmentId=ld;
		
		System.out.println("entered Id is "+id);
		//Shippment ship =new Shippment();
		//ship.main(null);
		ConnectionManager node=new ConnectionManager();
		
		try{
			Statement stmt = node.getConnectionManager().createStatement();
			String getShipmentDetails="select sd.Shipping_ID,sd.Start_City,sd.Destination,CONVERT(VARCHAR(19),sd.Actual_or_Expected_Delivery_date,101) Actual_or_Expected_Delivery_date,sd.Full_Name,"+
											"sd.weight,sd.length,sd.breadth,sd.height,CONVERT(VARCHAR(19),sd.Start_date,101) Start_date,sd.ship_status,ISNULL(djk.transit_path,0) "
											+ "transit_path,ISNULL(djk.distance,0) distance,ISNULL(sd.current_city,0) current_city "+ 
											"from shipping_details sd join dijkstra djk on  sd.Start_City=djk.source and sd.destination=djk.destination  "+
											"where Shipping_ID= "+id;
			ResultSet shippingRecordDetail = stmt.executeQuery(getShipmentDetails);
			//ShipingDetailsBean shipingBean= new ShipingDetailsBean();
			
			
			while (shippingRecordDetail.next()) {
				shipingBean.setShipingID(id);
				shipingBean.setDeliveryDate(shippingRecordDetail.getString("Actual_or_Expected_Delivery_date").trim());
				shipingBean.setStartCity(shippingRecordDetail.getString("Start_City").replace("_", ",").trim());
				shipingBean.setDestination(shippingRecordDetail.getString("Destination").replace("_", ",").trim());
				shipingBean.setName(shippingRecordDetail.getString("Full_Name").trim());
				shipingBean.setWeight(Double.parseDouble(shippingRecordDetail.getString("weight").trim()));
				shipingBean.setShippmentBookedDate(shippingRecordDetail.getString("Start_date").trim());
				shipingBean.setDimensions(shippingRecordDetail.getString("length").trim()+"*"+shippingRecordDetail.getString("breadth").trim()+"*"+shippingRecordDetail.getString("height").trim());
				shipingBean.setShipStatus(shippingRecordDetail.getString("ship_status").trim());
				shipingBean.setTransitPath(shippingRecordDetail.getString("transit_path").trim());
				shipingBean.setDistance(Double.parseDouble(shippingRecordDetail.getString("distance").trim()));
				shipingBean.setCurrentNode(shippingRecordDetail.getString("current_City").trim());
										
			}
		}
		catch(Exception ex)
		{
			System.out.println("In shippment Exception");
			System.out.println(ex);
			System.out.println(ex.getMessage());
			Shipment.main(null);
			//for (int i = 0; i < 50; ++i) System.out.println();
		}
		finally {

			if (node.getConnectionManager() != null) {
				node.getConnectionManager().close();
			}

		}
		
	}

	public ShipingDetailsBean getShipmentDetails() {
		return shipingBean;
	}

}
