import java.util.*;
import java.util.Map.Entry;
import java.sql.*;

public class Dijkstra {

	public static void computePaths(Map<String, Vertex> cityList,String key,Connection con) {

		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		List<Vertex> path = new ArrayList<Vertex>();
		String	updatePath=null;
		
		//con = DriverManager.getConnection("jdbc:sqlserver://localhost;DatabaseName=FedEx;integratedSecurity=true;");
		PreparedStatement updateShortestPath=null;
		
			//System.out.println(
				//	"in computation path:" + entry.getValue()+ " adjacencies  " );

			vertexQueue.clear();
			Vertex source =  cityList.get(key);
			
			
			for (Map.Entry<String, Vertex> ent : cityList.entrySet())
			{
				ent.getValue().minDistance=Double.POSITIVE_INFINITY;
				ent.getValue().previous=null;
			}
			source.minDistance = 0.;
			
			vertexQueue.add(source);
			System.out.println("In compute Paths:" + source + " length : " + vertexQueue.size());
			while (!vertexQueue.isEmpty()) {
				//System.out.println("In compute Paths:" + source + " length : " + vertexQueue.size() + "vertex peeking  "
						//+ vertexQueue.peek());

				Vertex u = vertexQueue.poll(); // get the head vertex n remove
												// from queue

				// Visit each edge exiting u
				System.out.println("array length" + vertexQueue.size() + "vertex name   " + u);
				
				
				//Edge[] arrEdge=cityList.get(u).toArray(new Edge[cityList.get(u).size()]);
				//System.out.println("arredge Length "+arrEdge.length);
				//edgeCities.get(entry.getKey())
				for (Edge e : u.adjacencies) {
					System.out.println("Edge Name " + e.target + "vertex name   " + u+ " Distance "+e.weight);
					Vertex v = e.target;
					System.out.println("vertex" + v);
					double weight = e.weight;
					double distanceThroughU = u.minDistance + weight;
					if (distanceThroughU < v.minDistance) {
						vertexQueue.remove(v);

						v.minDistance = distanceThroughU;
						v.previous = u;
						vertexQueue.add(v);
					}
				}
			}
			//System.gc();
			
			for (Map.Entry<String, Vertex> entry1 : cityList.entrySet()) {
				System.out.println("From "+cityList.get(key)+"Distance to " + entry1.getValue() + ": " + entry1.getValue().minDistance);
				 path = getShortestPathTo(entry1.getKey(),cityList,path);
				System.out.println("Path: " + path);
				updatePath="update dijkstra set Transit_path='"+(((path.toString().replace(", ",";")).replace("_",", ")).replace("[", "")).replace("]","")+"', distance="+ entry1.getValue().minDistance+" where source='"+cityList.get(key).toString().replace("_",", ")+ "' and destination='"+entry1.getValue().toString().replace("_",", ")+"'";
				path.clear();
				//System.out.println("updateShortestPath : "+updatePath);
				try {
					updateShortestPath = con.prepareStatement(updatePath);
					updateShortestPath.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					try {
						updateShortestPath.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			//break;
		
	}
	
	public static <String, Vertex> String getKeyByValue(Map<String, Vertex> map, String value) {
	    for (Map.Entry<String, Vertex> entry : map.entrySet()) {//System.out.println("hello"+entry.getValue().toString());
	        if (Objects.equals(value, entry.getValue().toString())) {//System.out.println("hello"+entry.getValue());
	            return entry.getKey();
	        }
	    }
	    return null;
	}

	public static List<Vertex> getShortestPathTo(String target, Map<String, Vertex>cityList, List<Vertex> path) {//System.out.println("in hello"+ cityList.get(target));
		
		
		for (Vertex vertex = cityList.get(target); vertex != null; vertex = vertex.previous)
		{//System.out.println("in getShortestPathTo "+vertex);
			path.add(vertex);
		}

		Collections.reverse(path);
		return path;
	}

	public static void main(String[] args) {
		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;
		try {
			// Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection("jdbc:sqlserver://localhost;DatabaseName=FedEx;integratedSecurity=true;");
			if (con != null) {
				System.out.println("connection successfull");

			} else
				System.out.println("connection failed");

			System.out.println("Creating statement...");
			stmt = con.createStatement();
			String cityNames;
			String adjacentNodes;
			cityNames = "select distinct city from city_nodes where [city] !='city 1'";
			rs = stmt.executeQuery(cityNames);
			int i = 0;

			Map<String,Vertex> cityList = new HashMap<String,Vertex>();

			while (rs.next()) {

				cityList.put("v" + String.valueOf(i),new Vertex(rs.getString("city")));

				//System.out.println("Record no    " + i + "   " + rs.getString("city"));
				i++;
			}
			System.out.println("cityList ::"+cityList.size());
			Map<String, List<Edge>> edgeCities = new HashMap<String, List<Edge>>();
			// String edgeContainer="" ;
			// String vertex="";
			for (Map.Entry<String,Vertex> entry : cityList.entrySet()) {
				String  key= entry.getKey();
				Vertex value = entry.getValue();
				
				//System.out.println("Key = " + key);
				//System.out.println("Values = " + value + "\n");

				adjacentNodes = "select city, [" + entry.getValue() + "] from city_nodes where city !='city 1' and ["
						+ entry.getValue() + "]<>99999 and [" + entry.getValue() + "]<> 0";
				//System.out.println("sql Statement    :" + adjacentNodes);
				ResultSet adjacentCities = stmt.executeQuery(adjacentNodes);

				edgeCities.put(entry.getKey(), new ArrayList<Edge>());
				while (adjacentCities.next()) {
					//System.out.println("key by value "+getKeyByValue(cityList,adjacentCities.getString("city")));
					edgeCities.get(entry.getKey()).add(new Edge(cityList.get(getKeyByValue(cityList,adjacentCities.getString("city"))),
							Double.parseDouble(adjacentCities.getString(entry.getValue().toString()))));
					// edgeCities.put(entry.getKey(), new Edge(new
					// Vertex(adjacentCities.getString("city")),
					// Double.parseDouble(adjacentCities.getString(entry.getKey().toString()))));
					//System.out.println("Record no       " + adjacentCities.getString("city") + "distnace"
						//	+ adjacentCities.getString(entry.getValue().toString()));

				}
				entry.getValue().adjacencies = new Edge[edgeCities.get(entry.getKey()).size()];
				for (int k = 0; k < edgeCities.get(entry.getKey()).size(); k++) {
					entry.getValue().adjacencies[k] = edgeCities.get(entry.getKey()).get(k);
				}
			}
			/*for (Map.Entry<String, List<Edge>> entryMap : edgeCities.entrySet()) {
				System.out.println("Map Key  "+cityList.get(entryMap.getKey())+"Array Length  "+entryMap.getValue().size());
				for(int j=0;j<entryMap.getValue().size();j++)
				{
					Vertex target=edgeCities.get(entryMap.getKey()).get(j).getTarget();
					double weight =edgeCities.get(entryMap.getKey()).get(j).getWeight();
					
					System.out.println("Map Key  "+entryMap.getKey()+"vertex  "+target+" weight "+weight);
				}
				
			}*/
			//Vertex str= new Vertex("Memphis_TN");
			
			  //System.out.println("Testing Hashmap"+edgeCities.get(str).get(0).getTarget()  +"target" +edgeCities.get(str).get(0).getWeight() +"weight");
			//
			for (Map.Entry<String,Vertex> entry : cityList.entrySet()){
			computePaths(cityList,entry.getKey(),con);
			}
			// break;
			// }

			

		} catch (Exception e) {
			System.out.println(e);
			System.out.println(e.getMessage());
		}finally {
			
			try {
				stmt.close();
				rs.close();
				if(null != con)
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*
		 * Vertex v0 = new Vertex("Harrisburg"); Vertex v1 = new
		 * Vertex("Baltimore"); Vertex v2 = new Vertex("Washington"); Vertex v3
		 * = new Vertex("Philadelphia"); Vertex v4 = new Vertex("Binghamton");
		 * Vertex v5 = new Vertex("Allentown"); Vertex v6 = new Vertex(
		 * "New York"); v0.adjacencies = new Edge[]{ new Edge(v1, 79.83), new
		 * Edge(v5, 81.15) }; v1.adjacencies = new Edge[]{ new Edge(v0, 79.75),
		 * new Edge(v2, 39.42), new Edge(v3, 103.00) }; v2.adjacencies = new
		 * Edge[]{ new Edge(v1, 38.65) }; v3.adjacencies = new Edge[]{ new
		 * Edge(v1, 102.53), new Edge(v5, 61.44), new Edge(v6, 96.79) };
		 * v4.adjacencies = new Edge[]{ new Edge(v5, 133.04) }; v5.adjacencies =
		 * new Edge[]{ new Edge(v0, 81.77), new Edge(v3, 62.05), new Edge(v4,
		 * 134.47), new Edge(v6, 91.63) }; v6.adjacencies = new Edge[]{ new
		 * Edge(v3, 97.24), new Edge(v5, 87.94) }; //ArrayList<Vertex> vertices=
		 * new ArrayList<Vertex>(); //Vertex[] vertices = { v0, v1, v2, v3, v4,
		 * v5, v6 };
		 * 
		 * Vertex[] vertices = { v0, v1, v2, v3, v4, v5, v6 };
		 * 
		 * computePaths(v0); for (Vertex v : vertices) { System.out.println(
		 * "Distance to " + v + ": " + v.minDistance); List<Vertex> path =
		 * getShortestPathTo(v); System.out.println("Path: " + path); }
		 */
	}
}