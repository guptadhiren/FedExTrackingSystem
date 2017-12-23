class Edge
{
    public final Vertex target;
    public final double weight;
    public Edge(Vertex argTarget, double argWeight)
    { target = argTarget; weight = argWeight; }
    public Vertex getTarget()
    {
    	return target;
    }
    public double getWeight()
    {
    	return weight;
    }
}
