package util.math2d;

public class Point2D {
	public double x;
	public double y;
	
	public Point2D(double x, double y){ 
		this.x=x; 
		this.y=y;
	}
	
	public Point2D(double[] values){
		if(values.length!=2){ System.out.println("input vector of wrong size"); }
		this.x=values[0]; 
		this.y=values[1];
	}
	
	public Point2D(Point2D copy){ 
		this.x=copy.x; 
		this.y=copy.y;
	}
	
	@Override
	public String toString(){ return "("+this.x+","+this.y+")"; }
	
	public double[] toArray(){ return new double[]{ this.x, this.y }; }
	
	public boolean isAt(Point2D ref){ return (this.x==ref.x && this.y==ref.y);	}
	public boolean isAt(double x, double y){ return (this.x==x && this.y==y);	}
	public boolean isAt(float x, float y){ return (this.x==x && this.y==y);	}
	public boolean isAt(int x, int y){ return (this.x==x && this.y==y);	}
}
