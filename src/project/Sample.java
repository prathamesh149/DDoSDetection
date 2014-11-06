package project;

public class Sample {

    public int x;
    public int y;
    public boolean label;
    
    public Sample(int x,int y, boolean label)
    {
    	this.x = x;
    	this.label = label;
    	this.y = y;
    }
    
	public double distance(Sample s)
	{
		return (this.x-s.x)*(this.x-s.x)+(this.y-s.y)*(this.y-s.y);
	}

}
