
public class ShipingDetailsBean {
	
	private double shippingID;
	private String	startCity;
	private String destination;
	private String deliveryDate ;
	private String  name;
	private	double weight;
	private String dimensions;
	private String shippmentBookedDate ;
	private String shipStatus;
	private String transitPath;
	private double distance;
	private String CurrentNode;
	
	public ShipingDetailsBean()
	{
		System.out.println("Shippment Bean initiated");
	}
	
	/**
	 * @return the shippingID
	 */
	public double getShipingID() {
		return shippingID;
	}
	/**
	 * @param shippingID the shippingID to set
	 */
	public void setShipingID(double shippingID) {
		this.shippingID = shippingID;
	}
	/**
	 * @return the startCity
	 */
	public String getStartCity() {
		return startCity;
	}
	/**
	 * @param startCity the startCity to set
	 */
	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return the deliveryDate
	 */
	public String getDeliveryDate() {
		return deliveryDate;
	}
	/**
	 * @param deliveryDate the deliveryDate to set
	 */
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	/**
	 * @return the dimensions
	 */
	public String getDimensions() {
		return dimensions;
	}
	/**
	 * @param dimensions the dimensions to set
	 */
	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}
	/**
	 * @return the shippmentBookedDate
	 */
	public String getShippmentBookedDate() {
		return shippmentBookedDate;
	}
	/**
	 * @param shippmentBookedDate the shippmentBookedDate to set
	 */
	public void setShippmentBookedDate(String shippmentBookedDate) {
		this.shippmentBookedDate = shippmentBookedDate;
	}

	

	/**
	 * @return the shipStaus
	 */
	public String getShipStatus() {
		return shipStatus;
	}

	/**
	 * @param shipStaus the shipStaus to set
	 */
	public void setShipStatus(String shipStatus) {
		this.shipStatus = shipStatus;
	}

	/**
	 * @return the transitPath
	 */
	public String getTransitPath() {
		return transitPath;
	}

	/**
	 * @param transitPath the transitPath to set
	 */
	public void setTransitPath(String transitPath) {
		this.transitPath = transitPath;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * @return the currentNode
	 */
	public String getCurrentNode() {
		return CurrentNode;
	}

	/**
	 * @param currentNode the currentNode to set
	 */
	public void setCurrentNode(String currentNode) {
		CurrentNode = currentNode;
	}	      
		      

}
