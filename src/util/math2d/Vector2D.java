package util.math2d;

public class Vector2D {
	Point2D start, end;
	double distance;

	public Vector2D(Point2D start, Point2D end){
		this.start = start;
		this.end = end;
		distance = euclidianDistance(start, end);
	}
	
	public Vector2D(double startX, double startY, double endX, double endY){
		this.start = new Point2D(startX,startY);
		this.end = new Point2D(endX,endY);
		distance = euclidianDistance(start, end);
	}
	public Vector2D(Vector2D copy){
		this.start = new Point2D(copy.start.x,copy.start.y);
		this.end = new Point2D(copy.end.x,copy.end.y);
		distance = copy.distance;
	}
	
	public Point2D getStartCopy(){ return new Point2D(start.x,start.y); }
	public Point2D getEndCopy(){ return new Point2D(end.x,end.y); }
	
	public void normalize(){
		double diff_x = end.x-start.x;
		double diff_y = end.y-start.y;
		if(diff_x==0 && diff_y==0){ System.out.println("this vector can't be normalized"); }
		if(diff_x==0){ 
			diff_y = diff_y>0 ? 1 : -1;
			end.y = start.y+diff_y;
		} else if(diff_y==0){ 
			diff_x = diff_x>0 ? 1 : -1;
			end.x = start.x+diff_x;
		} else {
			double dist = (float)Math.sqrt(diff_x*diff_x+diff_y*diff_y);
			diff_x = diff_x/dist;
			diff_y = diff_y/dist;
			end.x = start.x+diff_x;
			end.y = start.y+diff_y;
		}
		//return (float)Math.sqrt(diff_x*diff_x+diff_y*diff_y);
	}
	public static Vector2D normalize(Vector2D vec){
		Vector2D result = new Vector2D(vec);
		result.normalize();
		return result;
	}
	
	public static double dotProduct(Vector2D vec1, Vector2D vec2){
		return (vec1.end.x-vec1.start.x)*(vec2.end.x-vec2.start.x)+(vec1.end.y-vec1.start.y)*(vec2.end.y-vec2.start.y);
	}
	
	public static double euclidianDistance(Point2D start, Point2D end){
		if(start==null || end==null){
			System.out.println("invalid start or end point");
			return 0.f;
		}
		double diff_x = end.x-start.x;
		double diff_y = end.y-start.y;
		return (double)Math.sqrt(diff_x*diff_x+diff_y*diff_y);
	}
	
	public static double manhattanDistance(Point2D start, Point2D end){
		if(start==null || end==null){
			System.out.println("invalid start or end point");
			return 0.f;
		}
		double diff_x = end.x-start.x;
		double diff_y = end.y-start.y;
		return Math.abs(diff_x)+Math.abs(diff_y);
	}
	
	public static double gradient(Point2D start, Point2D end){
		if(start==null || end==null){
			System.out.println("invalid start or end point");
			return 0.f;
		}
		double diff_x = end.x-start.x;
		double diff_y = end.y-start.y;
		return diff_y/diff_x;
	}
	
	public static double normal(Point2D start, Point2D end){
		if(start==null || end==null){
			System.out.println("invalid start or end point");
			return 0.f;
		}
		double diff_x = end.x-start.x;
		double diff_y = end.y-start.y;
		return -diff_x/diff_y;
	}
	
	/**
	 * 
	 * @param point1
	 * @param point2
	 * @return point1+point2
	 */
	public static Point2D add(Point2D point1, Point2D point2){
		if(point1==null || point2==null){
			System.out.println("invalid start or end point");
			return null;
		}
		double result_x = point1.x+point2.x;
		double result_y = point1.y+point2.y;
		return new Point2D(result_x,result_y);
	}
	/**
	 * 
	 * @param point1
	 * @param point2
	 * @return point1-point2
	 */
	public static Point2D subtract(Point2D point1, Point2D point2){
		if(point1==null || point2==null){
			System.out.println("invalid start or end point");
			return null;
		}
		double result_x = point1.x-point2.x;
		double result_y = point1.y-point2.y;
		return new Point2D(result_x,result_y);
	}
	
	public static Point2D interpolate(Point2D point1, Point2D point2, float ratio1vs2){
		if(point1==null || point2==null){
			System.out.println("invalid start or end point");
			return null;
		}
		double result_x = point1.x*ratio1vs2+point2.x*(1-ratio1vs2);
		double result_y = point1.y*ratio1vs2+point2.y*(1-ratio1vs2);
		return new Point2D(result_x,result_y);
	}
	public static boolean isOnSegment(Vector2D vec, Point2D point){
		return isOnSegment(vec.start,vec.end,point); 
	}	
	public static boolean isOnSegment(Point2D vecStart, Point2D vecEnd, Point2D point){
		if(isOnLine(vecStart,vecEnd,point)){
			if( point.x<=Math.max(vecStart.x,vecEnd.x) &&
				point.x>=Math.min(vecStart.x,vecEnd.x) &&
				point.y<=Math.max(vecStart.y,vecEnd.y) &&
				point.y>=Math.min(vecStart.y,vecEnd.y) ){
				return true;
			}
		}
		return false;
	}
	public static boolean isOnLine(Vector2D vec, Point2D point){ 
		return isOnLine(vec.start,vec.end,point); 
	}	
	public static boolean isOnLine(Point2D vecStart, Point2D vecEnd, Point2D point){
		double d = det(Vector2D.subtract(point,vecStart),Vector2D.subtract(point,vecEnd));
		return(d<1E-6);
	}
	
	public static Point2D intersect(Vector2D vec1, Vector2D vec2){
		return intersect(vec1.start,vec1.end,vec2.start,vec2.end);
	}
	public static Point2D intersect(Point2D vec1start, Point2D vec1end, Point2D vec2start, Point2D vec2end){
		Point2D result = lineIntersect(vec1start,vec1end,vec2start,vec2end);
		// check if contained within vec1 and vec2
		if(result!=null && isOnSegment(vec1start,vec1end,result)){
			return result;
		}		
		return null;
	}
	
	public static Point2D lineIntersect(Vector2D vec1, Vector2D vec2){
		return lineIntersect(vec1.start,vec1.end,vec2.start,vec2.end);
	}
	public static Point2D lineIntersect(Point2D vec1start, Point2D vec1end, Point2D vec2start, Point2D vec2end){
		double det1 = det(vec1start,vec1end);
		double det2 = det(vec2start,vec2end);
		Point2D d1 = Vector2D.subtract(vec1start,vec1end);
		Point2D d2 = Vector2D.subtract(vec2start,vec2end);
		double det1to2 = det(d1, d2);
		
		if (det1to2 == 0){
			// the denominator is zero so the lines are parallel and there's either no solution (or multiple solutions if the lines overlap) so return null.
			return null;
		}
		double x = (det(det1,d1.x,det2,d2.x)/det1to2);
		double y = (det(det1,d1.y,det2,d2.y)/det1to2);
		return new Point2D(x,y);
	}
	
	public static Point2D segmentIntersect(Vector2D vec1, Vector2D vec2){
		return segmentIntersect(vec1.start,vec1.end,vec2.start,vec2.end);
	}
	public static Point2D segmentIntersect(Point2D vec1start, Point2D vec1end, Point2D vec2start, Point2D vec2end){
		double det1 = det(vec1start,vec1end);
		double det2 = det(vec2start,vec2end);
		Point2D d1 = Vector2D.subtract(vec1start,vec1end);
		Point2D d2 = Vector2D.subtract(vec2start,vec2end);
		double det1to2 = det(d1, d2);
		
		if (det1to2 == 0){
			// the denominator is zero so the lines are parallel and there's either no solution (or multiple solutions if the lines overlap) so return null.
			return null;
		}
		Point2D d1to2 = Vector2D.subtract(vec2start,vec1start);
		double t = (d1to2.x * d2.y - d1to2.y * d2.x) / det1to2;
		if(t < 0 || t > 1) { return null; }
		double u = (d1to2.x * d1.y - d1to2.y * d1.y) / det1to2;
		if(u < 0 || u > 1) { return null; }
		
		double x = (det(det1,d1.x,det2,d2.x)/det1to2);
		double y = (det(det1,d1.y,det2,d2.y)/det1to2);
		return new Point2D(x,y);
	}
	
	protected static double det(double a, double b, double c, double d) {
		return a*d-b*c;
	}
	protected static double det(Point2D p1, Point2D p2) {
		return p1.x*p2.y-p1.y*p2.x;
	}
	protected static double det(Vector2D vec) {
		return vec.start.x*vec.end.y-vec.start.y*vec.end.x;
	}
}
