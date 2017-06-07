package util.statics;

import java.util.Collections;
import java.util.Vector;

public class StatisticUtils {
	public static double standardDeviation(double[] data){
		// sd is sqrt of sum of (values-mean) squared divided by n - 1
		
		// calculate the mean
		double mean = average(data);
		// calculate the sum of squares
		double sum = 0;
		int n = 0;
		for (int i=0;i<data.length;i++) {
			if(Double.isNaN(data[i]) || Double.isInfinite(data[i])){ continue; }
			final double v=data[i]-mean;
			sum+=v*v;
			n++;
		}
		if (n<2){ return Double.NaN; }
		// Change to ( n - 1 ) to n if you have complete data instead of a sample.
		return Math.sqrt(sum/(double)(n-1));
	}
	
	public static float standardDeviation(float[] data){
		// calculate the mean
		float mean = average(data);
		// calculate the sum of squares
		float sum = 0;
		int n = 0;
		for (int i=0;i<data.length;i++) {
			if(Float.isNaN(data[i]) || Float.isInfinite(data[i])){ continue; }
			final float v=data[i]-mean;
			sum+=v*v;
			n++;
		}
		if (n<2){ return Float.NaN; }
		// Change to ( n - 1 ) to n if you have complete data instead of a sample.
		return (float)Math.sqrt(sum/(double)(n-1));
	}
	
	public static double average(double[] data){
		double result = 0;
		int n = 0;
		for (int i=0;i<data.length;i++) {
			if(Double.isNaN(data[i]) || Double.isInfinite(data[i])){ continue; }
			result+=data[i];
			n++;
		}
		if (n==0){ return Double.NaN; }
		return result/(double)n;
	}
	
	public static float average(float[] data){
		float result = 0;
		int n = 0;
		for (int i=0;i<data.length;i++) {
			if(Float.isNaN(data[i]) || Float.isInfinite(data[i])){ continue; }
			result+=data[i];
			n++;
		}
		if (n==0){ return Float.NaN; }
		return result/(float)n;
	}
	
	public static double median(double[] data){
		Vector<Double> result = new Vector<Double>();
		int n = 0;
		for (int i=0;i<data.length;i++) {
			if(Double.isNaN(data[i]) || Double.isInfinite(data[i])){ continue; }
			result.add(data[i]);
			n++;
		}
		if (n==0){ return Double.NaN; }
		Collections.sort(result);
		return result.get((result.size()-1)/2);
	}
	
	public static float median(float[] data){
		Vector<Float> result = new Vector<Float>();
		int n = 0;
		for (int i=0;i<data.length;i++) {
			if(Double.isNaN(data[i]) || Double.isInfinite(data[i])){ continue; }
			result.add(data[i]);
			n++;
		}
		if (n==0){ return Float.NaN; }
		Collections.sort(result);
		return result.get((result.size()-1)/2);
	}
	
	public static double maximum(double[] data){
		int n = 0;
		double result = Double.NEGATIVE_INFINITY;
		for (int i=0;i<data.length;i++) {
			if(Double.isNaN(data[i]) || Double.isInfinite(data[i])){ continue; }
			if(data[i]>result){ result = data[i]; }
			n++;
		}
		if (n==0 ){	return Double.NaN; }
		return result;
	}
	
	public static float maximum(float[] data){
		int n = 0;
		float result = Float.NEGATIVE_INFINITY;
		for (int i=0;i<data.length;i++) {
			if(Float.isNaN(data[i]) || Float.isInfinite(data[i])){ continue; }
			if(data[i]>result){ result = data[i]; }
			n++;
		}
		if (n==0 ){	return Float.NaN; }
		return result;
	}
	
	public static double minimum(double[] data){
		int n = 0;
		double result = Double.POSITIVE_INFINITY;
		for (int i=0;i<data.length;i++) {
			if(Double.isNaN(data[i]) || Double.isInfinite(data[i])){ continue; }
			if(data[i]<result){ result = data[i]; }
			n++;
		}
		if (n==0 ){	return Double.NaN; }
		return result;
	}
	
	public static float minimum(float[] data){
		int n = 0;
		float result = Float.POSITIVE_INFINITY;
		for (int i=0;i<data.length;i++) {
			if(Float.isNaN(data[i]) || Float.isInfinite(data[i])){ continue; }
			if(data[i]<result){ result = data[i]; }
			n++;
		}
		if (n==0 ){	return Float.NaN; }
		return result;
	}
	
	public static double[] normalize(double[] data){
		final double max = maximum(data);
		final double min = minimum(data);
		final double range = max-min;
		double[] result = new double[data.length];
		for(int i=0;i<data.length;i++){
			if(Double.isNaN(data[i]) || Double.isInfinite(data[i])){ continue; }
			result[i]=range>0 ? (data[i]-min)/range : 0;
		}
		return result;
	}
	public static float[] normalize(float[] data){
		final float max = maximum(data);
		final float min = minimum(data);
		final float range = max-min;
		float[] result = new float[data.length];
		for(int i=0;i<data.length;i++){
			result[i]=range>0 ? (data[i]-min)/range : 0;
		}
		return result;
	}
	
	public static double PearsonCorrelation(double[] dataX, double[] dataY){
		if(dataX.length!=dataY.length){ 
			System.out.println("data not the same length"); 
			return 0.0; 
		}
		int n = dataX.length;
		
		double sum_X = 0;
		double sum_Y = 0;
		double sum_XY = 0;
		double sum_sqX = 0;
		double sum_sqY = 0;
		
		for(int i=0;i<n;i++){
			sum_X+=dataX[i];
			sum_sqX+=dataX[i]*dataX[i];
			sum_XY+=dataX[i]*dataY[i];
			sum_Y+=dataY[i];
			sum_sqY+=dataY[i]*dataY[i];	
		}
		
		double a = n*sum_XY-sum_X*sum_Y;
		double b = Math.sqrt(n*sum_sqX-sum_X*sum_X)*Math.sqrt(n*sum_sqY-sum_Y*sum_Y);
		return a/b;
	}
}
