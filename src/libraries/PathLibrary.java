package libraries;

import ai.pathfinder.Node;
import ai.pathfinder.Pathfinder;
import java.util.ArrayList;
import java.util.Vector;
import util.math2d.Point2D;
import util.math2d.Vector2D;

public class PathLibrary {
	static public Pathfinder init(boolean[][] map){
		Pathfinder result = new Pathfinder();
		int spacing = 1;
		//astar.corners=false;
		result.setCuboidNodes(map.length, map[0].length, spacing);
		for(Object temp : result.nodes){
			Node n = (Node)temp;
			n.walkable = !map[(int)(n.x)][(int)(n.y)];
		}
		result.radialDisconnectUnwalkables();
		return result;
	}
	
	static public Node findNode(int x, int y, Pathfinder pf){
		for(Object temp : pf.nodes){
			Node n = (Node)temp;
			if(n.x == x && n.y == y){ return n; }
		}
		return null;
	}
	
	static public ArrayList getPath(Point2D p1, Point2D p2, boolean[][] map){
		return getPath((int)(p1.x),(int)(p1.y),(int)(p2.x),(int)(p2.y), map);
	}
	static public ArrayList getPath(int x1,int y1,int x2,int y2, boolean[][] map){ 
		Pathfinder pf = init(map);
		Node start = findNode(x1,y1,pf);
		Node end = findNode(x2,y2,pf);
		ArrayList result = pf.aStar(findNode(x1,y1,pf),findNode(x2,y2,pf));
		if(	hasStart(result,x2,y2) && hasEnd(result,x1,y1) ){ 
			return result;
		}
		return null;
	}
	
	public static boolean hasStart(ArrayList path, int x, int y){
		if(path!=null){
			Node temp = (Node)path.get(0);
			if(temp.x==x && temp.y==y){ return true; }
		}
		return false;
	}
	
	public static boolean hasNode(ArrayList path, int x, int y){
		if(path!=null){
			for(int i = 0; i < path.size(); i++){
				Node temp = (Node)path.get(i);
				if(temp.x==x && temp.y==y){ return true; }
			}
		}
		return false;
	}
	
	public static boolean hasEnd(ArrayList path, int x, int y){
		if(path!=null){
			Node temp = (Node)path.get(path.size()-1);
			if(temp.x==x && temp.y==y){ return true; }
		}
		return false;
	}
	
	public static Vector<Point2D> transformPath(ArrayList path){
		Vector<Point2D> result = new Vector<Point2D>();
		for(int i=0;i<path.size();i++){
			Node n = (Node)(path.get(i));
			result.add(new Point2D(n.x,n.y));
		}
		return result;
	}
	public static ArrayList transformPath(Vector<Point2D> path){
		ArrayList result = new ArrayList();
		for(int i=0;i<path.size();i++){
			Point2D n = path.get(i);
			result.add(new Node((float)(n.x),(float)(n.y)));
		}
		return result;
	}
	public static Point2D transformNode(Node n){
		return new Point2D(n.x,n.y);
	}
	public static Node transformNode(Point2D n){
		return new Node((float)(n.x),(float)(n.y));
	}
	public static float calculateDistance(ArrayList path){
		if(path.size()<2){ return 0.f; }
		float result = 0.f;
		for(int i=1;i<path.size();i++){
			Node curr = (Node)(path.get(i));
			Node prev = (Node)(path.get(i-1));
			result += Vector2D.euclidianDistance(new Point2D(curr.x,curr.y), new Point2D(prev.x,prev.y));
		}
		return result;
	}
	
	public static boolean[][] transformPath(ArrayList base, int sizeX, int sizeY){
		return transformPath(transformPath(base),sizeX,sizeY);
	}
	public static boolean[][] transformPath(Vector<Point2D> base, int sizeX, int sizeY){
		if(base==null || base.size()==0){ return null; }
		boolean[][] result = new boolean[sizeX][sizeY];
		for(int x=0;x<result.length;x++){
			for(int y=0;y<result[0].length;y++){
				result[x][y]=false;
			}
		}
		for(int i=0;i<base.size();i++){
			int x = (int)(base.get(i).x);
			int y = (int)(base.get(i).y);
			if(i<base.size()-1 && x!=base.get(i+1).x && y!=base.get(i+1).y){
				result[(int)(base.get(i+1).x)][y]=true;
				result[x][(int)(base.get(i+1).y)]=true;
			}
			result[x][y]=true;
		}
		return result;
	}
	
	public static String print(Vector<Point2D> path){
		String result = "";
		for(int i=0;i<path.size();i++){
			result+="("+path.get(i).x+","+path.get(i).y+")";
			if(i<path.size()-1){ result+="->"; } 
		}
		return result;
	}
	
	public static String print(ArrayList path){
		String result = "";
		for(int i=0;i<path.size();i++){
			Node n = (Node)(path.get(i));
			result+="("+n.x+","+n.y+")";
			if(i<path.size()-1){ result+="->"; } 
		}
		return result;
	}
}
