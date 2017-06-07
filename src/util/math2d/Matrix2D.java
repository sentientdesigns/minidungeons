package util.math2d;

import java.util.Vector;

public class Matrix2D {
	public static boolean[][] transpose(boolean[][] base){
		if(base==null){ return null; }
		boolean[][] result = new boolean[base[0].length][base.length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[y][x]=base[x][y];
			}
		}
		return result;
	}
	public static float[][] transpose(float[][] base){
		if(base==null){ return null; }
		float[][] result = new float[base[0].length][base.length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[y][x]=base[x][y];
			}
		}
		return result;
	}
	public static double[][] transpose(double[][] base){
		if(base==null){ return null; }
		double[][] result = new double[base[0].length][base.length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[y][x]=base[x][y];
			}
		}
		return result;
	}
	public static int[][] transpose(int[][] base){
		if(base==null){ return null; }
		int[][] result = new int[base[0].length][base.length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[y][x]=base[x][y];
			}
		}
		return result;
	}
	public static boolean[][] reflectHorizontal(boolean[][] base){
		if(base==null){ return null; }
		boolean[][] result = new boolean[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[(base.length-1)-x][y];
			}
		}
		return result;
	}
	public static boolean[][] reflectVertical(boolean[][] base){
		if(base==null){ return null; }
		boolean[][] result = new boolean[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][(base[0].length-1)-y];
			}
		}
		return result;
	}
	
	public static boolean[][] union(boolean[][] base, boolean[][] addition){
		if(base==null || addition==null || base.length!=addition.length || base[0].length!=addition[0].length){ return null; }
		boolean[][] result = new boolean[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y] || addition[x][y];
			}
		}
		return result;
	}
	
	public static boolean[][] intersection(boolean[][] base, boolean[][] addition){
		if(base==null || addition==null || base.length!=addition.length || base[0].length!=addition[0].length){ return null; }
		boolean[][] result = new boolean[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y] && addition[x][y];
			}
		}
		return result;
	}
	
	public static boolean[][] xor(boolean[][] base, boolean[][] addition){
		if(base==null || addition==null || base.length!=addition.length || base[0].length!=addition[0].length){ return null; }
		boolean[][] result = new boolean[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=(base[x][y] && !addition[x][y]) || (!base[x][y] && addition[x][y]);
			}
		}
		return result;
	}
	
	public static boolean[][] invert(boolean[][] base){
		if(base==null){ return null; }
		boolean[][] result = new boolean[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=!base[x][y];
			}
		}
		return result;
	}
	
	public static int[][] add(int[][] base, int[][] subtracted){
		if(base==null){ return null; }
		int[][] result = new int[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y]+subtracted[x][y];
			}
		}
		return result;
	}
	public static float[][] add(float[][] base, float[][] subtracted){
		if(base==null){ return null; }
		float[][] result = new float[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y]+subtracted[x][y];
			}
		}
		return result;
	}
	public static double[][] add(double[][] base, double[][] subtracted){
		if(base==null){ return null; }
		double[][] result = new double[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y]+subtracted[x][y];
			}
		}
		return result;
	}
	
	public static int[][] add(int[][] base, int value){
		if(base==null){ return null; }
		int[][] result = new int[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y]+value;
			}
		}
		return result;
	}
	public static float[][] add(float[][] base, float value){
		if(base==null){ return null; }
		float[][] result = new float[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y]+value;
			}
		}
		return result;
	}
	public static double[][] add(double[][] base, double value){
		if(base==null){ return null; }
		double[][] result = new double[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y]+value;
			}
		}
		return result;
	}
	
	public static boolean[][] subtract(boolean[][] base, boolean[][] subtracted){
		if(base==null){ return null; }
		boolean[][] result = new boolean[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				if(!subtracted[x][y]){
					result[x][y]=base[x][y];
				} else {
					result[x][y]=false;
				}
			}
		}
		return result;
	}
	public static int[][] subtract(int[][] base, int[][] subtracted){
		if(base==null){ return null; }
		int[][] result = new int[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y]-subtracted[x][y];
			}
		}
		return result;
	}
	public static float[][] subtract(float[][] base, float[][] subtracted){
		if(base==null){ return null; }
		float[][] result = new float[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y]-subtracted[x][y];
			}
		}
		return result;
	}
	public static double[][] subtract(double[][] base, double[][] subtracted){
		if(base==null){ return null; }
		double[][] result = new double[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y]-subtracted[x][y];
			}
		}
		return result;
	}
	
	public static int[][] subtract(int[][] base, int value){
		if(base==null){ return null; }
		int[][] result = new int[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y]-value;
			}
		}
		return result;
	}
	public static float[][] subtract(float[][] base, float value){
		if(base==null){ return null; }
		float[][] result = new float[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y]-value;
			}
		}
		return result;
	}
	public static double[][] subtract(double[][] base, double value){
		if(base==null){ return null; }
		double[][] result = new double[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y]-value;
			}
		}
		return result;
	}
	
	public static int[][] absDifference(int[][] base, int[][] subtracted){
		if(base==null){ return null; }
		int[][] result = new int[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=(int)Math.abs(base[x][y]-subtracted[x][y]);
			}
		}
		return result;
	}
	public static float[][] absDifference(float[][] base, float[][] subtracted){
		if(base==null){ return null; }
		float[][] result = new float[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=(float)Math.abs(base[x][y]-subtracted[x][y]);
			}
		}
		return result;
	}
	public static double[][] absDifference(double[][] base, double[][] subtracted){
		if(base==null){ return null; }
		double[][] result = new double[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=(double)Math.abs(base[x][y]-subtracted[x][y]);
			}
		}
		return result;
	}
	
	public static boolean[][] transformPointList(Vector<Point2D> base, int sizeX, int sizeY){
		if(base==null){ return null; }
		boolean[][] result = new boolean[sizeX][sizeY];
		for(int x=0;x<result.length;x++){
			for(int y=0;y<result[0].length;y++){
				result[x][y]=false;
			}
		}
		for(int i=0;i<base.size();i++){
			result[(int)(base.get(i).x)][(int)(base.get(i).y)]=true;
		}
		return result;
	}
	
	public static Vector<Point2D> transformPointList(boolean[][] base){
		if(base==null){ return null; }
		Vector<Point2D> result = new Vector<Point2D>();
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				if(base[x][y]){ result.add(new Point2D(x,y)); }
			}
		}
		return result;
	}
	
	public static int[][] max(int[][] base, int maxVal){
		if(base==null){ return null; }
		int[][] result = new int[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=(int)Math.max(base[x][y],maxVal);
			}
		}
		return result;
	}
	public static float[][] max(float[][] base, float maxVal){
		if(base==null){ return null; }
		float[][] result = new float[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=(float)Math.max(base[x][y],maxVal);
			}
		}
		return result;
	}
	public static double[][] max(double[][] base, double maxVal){
		if(base==null){ return null; }
		double[][] result = new double[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=(double)Math.max(base[x][y],maxVal);
			}
		}
		return result;
	}
	
	public static int maxValue(int[][] base){
		if(base==null){ return Integer.MIN_VALUE; }
		int result = Integer.MIN_VALUE;
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				if(Double.isNaN(base[x][y])){ continue; }
				if(base[x][y]>result){ result = base[x][y]; }
			}
		}
		return result;
	}
	public static float maxValue(float[][] base){
		if(base==null){ return Float.NEGATIVE_INFINITY; }
		float result = Float.NEGATIVE_INFINITY;
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				if(Double.isNaN(base[x][y])){ continue; }
				if(base[x][y]>result){ result = base[x][y]; }
			}
		}
		return result;
	}
	public static double maxValue(double[][] base){
		if(base==null){ return Double.NEGATIVE_INFINITY; }
		double result = Double.NEGATIVE_INFINITY;
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				if(Double.isNaN(base[x][y])){ continue; }
				if(base[x][y]>result){ result = base[x][y]; }
			}
		}
		return result;
	}
	
	public static int[][] min(int[][] base, int minVal){
		if(base==null){ return null; }
		int[][] result = new int[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=(int)Math.min(base[x][y],minVal);
			}
		}
		return result;
	}
	public static float[][] min(float[][] base, float minVal){
		if(base==null){ return null; }
		float[][] result = new float[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=(float)Math.min(base[x][y],minVal);
			}
		}
		return result;
	}
	public static double[][] min(double[][] base, double minVal){
		if(base==null){ return null; }
		double[][] result = new double[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=(double)Math.min(base[x][y],minVal);
			}
		}
		return result;
	}
	
	public static int minValue(int[][] base){
		if(base==null){ return Integer.MAX_VALUE; }
		int result = Integer.MAX_VALUE;
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				if(base[x][y]<result){ result = base[x][y]; }
			}
		}
		return result;
	}
	public static float minValue(float[][] base){
		if(base==null){ return Float.POSITIVE_INFINITY; }
		float result = Float.POSITIVE_INFINITY;
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				if(base[x][y]<result){ result = base[x][y]; }
			}
		}
		return result;
	}
	public static double minValue(double[][] base){
		if(base==null){ return Double.POSITIVE_INFINITY; }
		double result = Double.POSITIVE_INFINITY;
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				if(base[x][y]<result){ result = base[x][y]; }
			}
		}
		return result;
	}
	
	public static float averageValue(float[][] base){
		if(base==null){ return Float.POSITIVE_INFINITY; }
		float result = 0;
		int entries = 0;
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				if(Float.isNaN(base[x][y])){ continue; }
				result += base[x][y];
				entries++;
			}
		}
		result = result/(float)(entries);
		return result;
	}
	public static double averageValue(double[][] base){
		if(base==null){ return Double.POSITIVE_INFINITY; }
		double result = 0;
		int entries = 0;
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				if(Double.isNaN(base[x][y])){ continue; }
				result += base[x][y];
				entries++;
			}
		}
		result = result/(double)(entries);
		return result;
	}
	
	public static int[][] abs(int[][] base){
		if(base==null){ return null; }
		int[][] result = new int[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=Math.abs(base[x][y]);
			}
		}
		return result;
	}
	public static float[][] abs(float[][] base){
		if(base==null){ return null; }
		float[][] result = new float[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=Math.abs(base[x][y]);
			}
		}
		return result;
	}
	public static double[][] abs(double[][] base){
		if(base==null){ return null; }
		double[][] result = new double[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=Math.abs(base[x][y]);
			}
		}
		return result;
	}
	/*
	public static int[][] normalize(int[][] base){
		if(base==null){ return null; }
		int minVal = Matrix2D.minValue(base);
		int maxVal = Matrix2D.maxValue(base);
		int range = maxVal - minVal;
		int[][] result = new int[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				if(range==0){ 
					result[x][y]=0;
				} else {
					result[x][y]=(int)Math.round((float)(base[x][y]-minVal)/(float)range);
				}
			}
		}
		return result;
	}
	*/
	public static float[][] normalize(float[][] base){
		if(base==null){ return null; }
		float minVal = Matrix2D.minValue(base);
		float maxVal = Matrix2D.maxValue(base);
		float range = maxVal - minVal;
		float[][] result = new float[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				if(range==0){ 
					result[x][y]=0;
				} else {
					result[x][y]=(base[x][y]-minVal)/range;
				}
			}
		}
		return result;
	}
	public static double[][] normalize(double[][] base){
		if(base==null){ return null; }
		double minVal = Matrix2D.minValue(base);
		double maxVal = Matrix2D.maxValue(base);
		double range = maxVal - minVal;
		double[][] result = new double[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				if(range==0){ 
					result[x][y]=0;
				} else {
					result[x][y]=(base[x][y]-minVal)/range;
				}
			}
		}
		return result;
	}
	/*
	public static int[][] normalize(int[][] base, int zeroValue, int oneValue){
		if(base==null){ return null; }
		int range = oneValue - zeroValue;
		int[][] result = new int[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				if(range==0){ 
					result[x][y]=0;
				} else {
					result[x][y]=(int)Math.round((float)(base[x][y]-zeroValue)/(float)range);
					result[x][y]=(int)Math.min(1,(int)Math.max(result[x][y],0));		// clamp
				}
			}
		}
		return result;
	}
	*/ 
	public static float[][] normalize(float[][] base, float zeroValue, float oneValue){
		if(base==null){ return null; }
		float range = oneValue - zeroValue;
		float[][] result = new float[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				if(range==0){
					result[x][y]=0;
				} else {
					result[x][y]=(base[x][y]-zeroValue)/range;
					result[x][y]=(float)Math.min(1,(float)Math.max(result[x][y],0));		// clamp
				}
			}
		}
		return result;
	}
	public static double[][] normalize(double[][] base, double zeroValue, double oneValue){
		if(base==null){ return null; }
		double range = oneValue - zeroValue;
		double[][] result = new double[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				if(range==0){ 
					result[x][y]=0;
				} else {
					result[x][y]=(base[x][y]-zeroValue)/range;
					result[x][y]=Math.min(1,Math.max(result[x][y],0));		// clamp
				}
			}
		}
		return result;
	}
	
	public static int[][] multiply(int[][] base, double value){
		if(base==null){ return null; }
		int minVal = Matrix2D.minValue(base);
		int maxVal = Matrix2D.maxValue(base);
		int range = maxVal - minVal;
		int[][] result = new int[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=(int)Math.round(value*base[x][y]);
			}
		}
		return result;
	}
	public static float[][] multiply(float[][] base, double value){
		if(base==null){ return null; }
		float minVal = Matrix2D.minValue(base);
		float maxVal = Matrix2D.maxValue(base);
		float range = maxVal - minVal;
		float[][] result = new float[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=(float)value*base[x][y];
			}
		}
		return result;
	}
	public static double[][] multiply(double[][] base, double value){
		if(base==null){ return null; }
		double minVal = Matrix2D.minValue(base);
		double maxVal = Matrix2D.maxValue(base);
		double range = maxVal - minVal;
		double[][] result = new double[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=value*base[x][y];
			}
		}
		return result;
	}
	
	public static int count(char[][] base, char toCount){
		if(base==null){ return 0; }
		int result = 0;
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				if(base[x][y]==toCount){ result++; }
			}
		}
		return result;
	}
	public static int countNot(char[][] base, char toCount){
		if(base==null){ return 0; }
		int result = 0;
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				if(base[x][y]!=toCount){ result++; }
			}
		}
		return result;
	}
	public static int count(boolean[][] base){
		if(base==null){ return 0; }
		int result = 0;
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				if(base[x][y]){ result++; }
			}
		}
		return result;
	}
	public static int count(int[][] base){
		if(base==null){ return 0; }
		int result = 0;
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result+=base[x][y];
			}
		}
		return result;
	}
	public static float count(float[][] base){
		if(base==null){ return 0; }
		float result = 0;
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result+=base[x][y];
			}
		}
		return result;
	}
	public static double count(double[][] base){
		if(base==null){ return 0; }
		double result = 0;
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result+=base[x][y];
			}
		}
		return result;
	}
	
	public static char[][] copy(char[][] base){
		if(base==null){ return null; }
		char[][] result = new char[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y];
			}
		}
		return result;
	}
	
	public static boolean[][] copy(boolean[][] base){
		if(base==null){ return null; }
		boolean[][] result = new boolean[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y];
			}
		}
		return result;
	}
	
	public static int[][] copy(int[][] base){
		if(base==null){ return null; }
		int[][] result = new int[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y];
			}
		}
		return result;
	}
	
	public static float[][] copy(float[][] base){
		if(base==null){ return null; }
		float[][] result = new float[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y];
			}
		}
		return result;
	}
	
	public static double[][] copy(double[][] base){
		if(base==null){ return null; }
		double[][] result = new double[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y];
			}
		}
		return result;
	}
	
	public static char[][] copy(char[][] base, int minX, int minY, int maxX, int maxY){
		if(base==null || minX>=maxX || minY>=maxY || minX<0 || minY<0 || maxX>base.length || maxY>base[0].length){ return null; }
		char[][] result = new char[maxX-minX][maxY-minY];
		for(int x=minX;x<maxX;x++){
			for(int y=minY;y<maxY;y++){
				result[x-minX][y-minY]=base[x][y];
			}
		}
		return result;
	}
	
	public static boolean[][] copy(boolean[][] base, int minX, int minY, int maxX, int maxY){
		if(base==null || minX>=maxX || minY>=maxY || minX<0 || minY<0 || maxX>base.length || maxY>base[0].length){ return null; }
		boolean[][] result = new boolean[maxX-minX][maxY-minY];
		for(int x=minX;x<maxX;x++){
			for(int y=minY;y<maxY;y++){
				result[x-minX][y-minY]=base[x][y];
			}
		}
		return result;
	}
	
	public static int[][] copy(int[][] base, int minX, int minY, int maxX, int maxY){
		if(base==null || minX>=maxX || minY>=maxY || minX<0 || minY<0 || maxX>base.length || maxY>base[0].length){ return null; }
		int[][] result = new int[maxX-minX][maxY-minY];
		for(int x=minX;x<maxX;x++){
			for(int y=minY;y<maxY;y++){
				result[x-minX][y-minY]=base[x][y];
			}
		}
		return result;
	}
	
	public static float[][] copy(float[][] base, int minX, int minY, int maxX, int maxY){
		if(base==null || minX>=maxX || minY>=maxY || minX<0 || minY<0 || maxX>base.length || maxY>base[0].length){ return null; }
		float[][] result = new float[maxX-minX][maxY-minY];
		for(int x=minX;x<maxX;x++){
			for(int y=minY;y<maxY;y++){
				result[x-minX][y-minY]=base[x][y];
			}
		}
		return result;
	}
	
	public static double[][] copy(double[][] base, int minX, int minY, int maxX, int maxY){
		if(base==null || minX>=maxX || minY>=maxY || minX<0 || minY<0 || maxX>base.length || maxY>base[0].length){ return null; }
		double[][] result = new double[maxX-minX][maxY-minY];
		for(int x=minX;x<maxX;x++){
			for(int y=minY;y<maxY;y++){
				result[x-minX][y-minY]=base[x][y];
			}
		}
		return result;
	}
	
	public static int[][] makeInteger(int sizeX, int sizeY, int value){
		int[][] result = new int[sizeX][sizeY];
		for(int x=0;x<result.length;x++){
			for(int y=0;y<result[0].length;y++){
				result[x][y]=value;
			}
		}
		return result;
	}
	
	public static int[][] makeInteger(boolean[][] base, int onValue, int offValue){
		if(base==null){ return null; }
		int[][] result = new int[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y] ? onValue : offValue;
			}
		}
		return result;
	}
	
	public static float[][] makeFloat(int sizeX, int sizeY, float value){
		float[][] result = new float[sizeX][sizeY];
		for(int x=0;x<result.length;x++){
			for(int y=0;y<result[0].length;y++){
				result[x][y]=value;
			}
		}
		return result;
	}
	
	public static float[][] makeFloat(boolean[][] base, float onValue, float offValue){
		if(base==null){ return null; }
		float[][] result = new float[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y] ? onValue : offValue;
			}
		}
		return result;
	}
	
	public static double[][] makeDouble(int sizeX, int sizeY, double value){
		double[][] result = new double[sizeX][sizeY];
		for(int x=0;x<result.length;x++){
			for(int y=0;y<result[0].length;y++){
				result[x][y]=value;
			}
		}
		return result;
	}
	
	public static double[][] makeDouble(boolean[][] base, double onValue, double offValue){
		if(base==null){ return null; }
		double[][] result = new double[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=base[x][y] ? onValue : offValue;
			}
		}
		return result;
	}
	
	public static boolean[][] above(int[][] base, int minValue){
		if(base==null){ return null; }
		boolean[][] result = new boolean[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]= (base[x][y]>=minValue);
			}
		}
		return result;
	}
	
	public static boolean[][] above(float[][] base, float minValue){
		if(base==null){ return null; }
		boolean[][] result = new boolean[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]= (base[x][y]>=minValue);
			}
		}
		return result;
	}
	
	public static boolean[][] above(double[][] base, double minValue){
		if(base==null){ return null; }
		boolean[][] result = new boolean[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]= (base[x][y]>=minValue);
			}
		}
		return result;
	}
	
	public static boolean[][] under(int[][] base, int maxValue){
		if(base==null){ return null; }
		boolean[][] result = new boolean[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]= (base[x][y]<=maxValue);
			}
		}
		return result;
	}
	
	public static boolean[][] under(float[][] base, float maxValue){
		if(base==null){ return null; }
		boolean[][] result = new boolean[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]= (base[x][y]<=maxValue);
			}
		}
		return result;
	}
	
	public static boolean[][] under(double[][] base, double maxValue){
		if(base==null){ return null; }
		boolean[][] result = new boolean[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]= (base[x][y]<=maxValue);
			}
		}
		return result;
	}
	public static int[][] filter(int[][] base, boolean[][] filter, int offValue){
		if(base==null){ return null; }
		int[][] result = new int[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=filter[x][y] ? base[x][y] : offValue;
			}
		}
		return result;
	}
	
	public static float[][] filter(float[][] base, boolean[][] filter, float offValue){
		if(base==null){ return null; }
		float[][] result = new float[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=filter[x][y] ? base[x][y] : offValue;
			}
		}
		return result;
	}
	
	public static double[][] filter(double[][] base, boolean[][] filter, double offValue){
		if(base==null){ return null; }
		double[][] result = new double[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=filter[x][y] ? base[x][y] : offValue;
			}
		}
		return result;
	}
	
	
	public static int[][] negative(int[][] base){
		int[][] result = new int[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=-base[x][y];
			}
		}
		return result;
	}
	public static float[][] negative(float[][] base){
		float[][] result = new float[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=-base[x][y];
			}
		}
		return result;
	}
	public static double[][] negative(double[][] base){
		double[][] result = new double[base.length][base[0].length];
		for(int x=0;x<base.length;x++){
			for(int y=0;y<base[0].length;y++){
				result[x][y]=-base[x][y];
			}
		}
		return result;
	}

	public static char[][] initialize(int sizeX, int sizeY, char initialValue){
		char[][] result = new char[sizeX][sizeY];
		for(int x=0;x<sizeX;x++){
			for(int y=0;y<sizeY;y++){
				result[x][y]=initialValue;
			}
		}
		return result;
	}
	public static boolean[][] initialize(int sizeX, int sizeY, boolean initialValue){
		boolean[][] result = new boolean[sizeX][sizeY];
		for(int x=0;x<sizeX;x++){
			for(int y=0;y<sizeY;y++){
				result[x][y]=initialValue;
			}
		}
		return result;
	}
	public static int[][] initialize(int sizeX, int sizeY, int initialValue){
		int[][] result = new int[sizeX][sizeY];
		for(int x=0;x<sizeX;x++){
			for(int y=0;y<sizeY;y++){
				result[x][y]=initialValue;
			}
		}
		return result;
	}
	public static float[][] initialize(int sizeX, int sizeY, float initialValue){
		float[][] result = new float[sizeX][sizeY];
		for(int x=0;x<sizeX;x++){
			for(int y=0;y<sizeY;y++){
				result[x][y]=initialValue;
			}
		}
		return result;
	}
	public static double[][] initialize(int sizeX, int sizeY, double initialValue){
		double[][] result = new double[sizeX][sizeY];
		for(int x=0;x<sizeX;x++){
			for(int y=0;y<sizeY;y++){
				result[x][y]=initialValue;
			}
		}
		return result;
	}
	public static boolean isInside(int index_x, int index_y, char[][] array){
		return (index_x>=0 && index_x<array.length && index_y>=0 && index_y<array[0].length); 
	}
	public static boolean isInside(int index_x, int index_y, boolean[][] array){
		return (index_x>=0 && index_x<array.length && index_y>=0 && index_y<array[0].length); 
	}
	public static boolean isInside(int index_x, int index_y, float[][] array){
		return (index_x>=0 && index_x<array.length && index_y>=0 && index_y<array[0].length); 
	}
	public static boolean isInside(int index_x, int index_y, double[][] array){
		return (index_x>=0 && index_x<array.length && index_y>=0 && index_y<array[0].length); 
	}
	public static String print(boolean[][] base, char on, char off){
		if(base==null){ return null; }
		String result = "";
		for(int y=0;y<base[0].length;y++){
			for(int x=0;x<base.length;x++){
			if(base[x][y]){ 
					result+=on; 
				} else { 
					result+=off; 
				}
			}
			result+="\n";
		}
		return result;
	}
	public static String print(char[][] base){
		if(base==null){ return null; }
		String result = "";
		for(int y=0;y<base[0].length;y++){
			for(int x=0;x<base.length;x++){
				result+=base[x][y]; 
			}
			result+="\n";
		}
		return result;
	}
	public static String print(boolean[][] base){
		if(base==null){ return null; }
		String result = "";
		for(int y=0;y<base[0].length;y++){
			for(int x=0;x<base.length;x++){
			if(base[x][y]){ 
					result+="#"; 
				} else { 
					result+=" "; 
				}
			}
			result+="\n";
		}
		return result;
	}
	public static String print(int[][] base){
		if(base==null){ return null; }
		String result = "";
		for(int y=0;y<base[0].length;y++){
			for(int x=0;x<base.length;x++){
				result+=""+base[x][y]+", \t"; 
			}
			result+="\n";
		}
		return result;
	}
	public static String print(float[][] base){
		if(base==null){ return null; }
		String result = "";
		for(int y=0;y<base[0].length;y++){	
			for(int x=0;x<base.length;x++){
				result+=""+base[x][y]+", \t"; 
			}
			result+="\n";
		}
		return result;
	}
	public static String print(double[][] base){
		if(base==null){ return null; }
		String result = "";
		for(int y=0;y<base[0].length;y++){
			for(int x=0;x<base.length;x++){
				result+=""+base[x][y]+", \t"; 
			}
			result+="\n";
		}
		return result;
	}
	/*
	public static boolean booleanFromString(){
		
	}
	*/ 
}
