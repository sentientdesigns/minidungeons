package dungeon;

import java.util.Vector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Arrays;

import ai.pathfinder.*;

import util.math2d.Matrix2D;
import util.math2d.Point2D;
import util.math2d.Vector2D;

import libraries.PathLibrary;

public class DungeonPaths {
	Dungeon parent;
	Pathfinder astar;
	
	Vector<ArrayList> exitPaths;
	Vector<ArrayList> exit_monsterPaths;
	Vector<ArrayList> monster_rewardPaths;
	
	int disconnectedPaths;
	
	public DungeonPaths(Dungeon parent){ 
		this.parent = parent;
	}
	
	public void init(){
		astar = new Pathfinder();
		int spacing = 1;
		astar.corners=false;
		astar.setCuboidNodes(parent.getMapSizeX(), parent.getMapSizeY(), spacing);
		for(Object temp : astar.nodes){
			Node n = (Node)temp;
			n.walkable = parent.isPassable((int)(n.x), (int)(n.y));
		}
		astar.radialDisconnectUnwalkables();
	}
	
	public Node findNode(int x, int y){
		for(Object temp : astar.nodes){
			Node n = (Node)temp;
			if(n.x == x && n.y == y){ return n; }
		}
		return null;
	}
	
	public void calculateAllPaths(){
		if(astar==null){ init(); }
		disconnectedPaths = 0;
		exitPaths = new Vector<ArrayList>();
		for(int i=0;i<parent.exits.size();i++){
			for(int j=i+1;j<parent.exits.size();j++){
				ArrayList path = this.getPath(parent.exits.get(j), parent.exits.get(i));
				if(	path!=null ){ exitPaths.add(path); } else { disconnectedPaths++; }
			}
		}
		exit_monsterPaths = new Vector<ArrayList>();
		for(int i=0;i<parent.exits.size();i++){
			for(int j=0;j<parent.monsters.size();j++){
				ArrayList path = this.getPath(parent.monsters.get(j), parent.exits.get(i));
				if(	path!=null ){ exit_monsterPaths.add(path); } else { disconnectedPaths++; }
			}
		}
		monster_rewardPaths = new Vector<ArrayList>();
		for(int i=0;i<parent.monsters.size();i++){
			for(int j=0;j<parent.rewards.size();j++){
				ArrayList path = this.getPath(parent.rewards.get(j), parent.monsters.get(i));
				if(	path!=null ){ monster_rewardPaths.add(path); } else { disconnectedPaths++; }
			}
		}
	}
	
	public Vector<ArrayList> getMonster_rewardPaths(){ return monster_rewardPaths; }
	public Vector<ArrayList> getExit_monsterPaths(){ return exit_monsterPaths; }
	public Vector<ArrayList> getExitPaths(){ return exitPaths; }
	public int getMonster_rewardPathLength(){ return monster_rewardPaths.size(); }
	public int getExit_monsterPathLength(){ return exit_monsterPaths.size(); }
	public int getExitPathLength(){ return exitPaths.size(); }
	public int getDisconnectedPaths(){ return disconnectedPaths; }
	
	public Pathfinder getAStar(){ return astar; }
	
	public Vector<ArrayList> getRewardsPathsFromMonster(int monsterIndex){
		Vector<ArrayList> result = new Vector<ArrayList>();
		if(monsterIndex<0 || monsterIndex>=parent.monsters.size()){ 
			System.out.println("Base index out of bounds");
			return result;
		}
		int monsterX = (int)(parent.monsters.get(monsterIndex).x);
		int monsterY = (int)(parent.monsters.get(monsterIndex).y);
		for(int i=0;i<monster_rewardPaths.size();i++){
			if(PathLibrary.hasStart(monster_rewardPaths.get(i),monsterX,monsterY) || PathLibrary.hasEnd(monster_rewardPaths.get(i),monsterX,monsterY)) {
				result.add(monster_rewardPaths.get(i));
			}
		}
		return result;
	}
	
	public Vector<ArrayList> getRewardsPathsFromReward(int rewardIndex){
		Vector<ArrayList> result = new Vector<ArrayList>();
		if(rewardIndex<0 || rewardIndex>=parent.rewards.size()){ 
			System.out.println("Resource index out of bounds");
			return result;
		}
		int rewardX = (int)(parent.rewards.get(rewardIndex).x);
		int rewardY = (int)(parent.rewards.get(rewardIndex).y);
		for(int i=0;i<monster_rewardPaths.size();i++){
			if(PathLibrary.hasStart(monster_rewardPaths.get(i),rewardX,rewardY) || PathLibrary.hasEnd(monster_rewardPaths.get(i),rewardX,rewardY)) {
				result.add(monster_rewardPaths.get(i));
			}
		}
		return result;
	}

	public ArrayList getRewardPathFromMonster(int rewardIndex, int monsterIndex){
		if(monsterIndex<0 || monsterIndex>=parent.monsters.size()){ 
			System.out.println("Monster index out of bounds");
			return null;
		}
		if(rewardIndex<0 || rewardIndex>=parent.rewards.size()){ 
			System.out.println("Reward index out of bounds");
			return null;
		}
		int monsterX = (int)(parent.monsters.get(monsterIndex).x);
		int monsterY = (int)(parent.monsters.get(monsterIndex).y);
		int rewardX = (int)(parent.rewards.get(rewardIndex).x);
		int rewardY = (int)(parent.rewards.get(rewardIndex).y);
		for(int i=0;i<monster_rewardPaths.size();i++){
			if(PathLibrary.hasStart(monster_rewardPaths.get(i),monsterX,monsterY) && PathLibrary.hasEnd(monster_rewardPaths.get(i),rewardX,rewardY)) {
				return monster_rewardPaths.get(i);
			} else if(PathLibrary.hasStart(monster_rewardPaths.get(i),rewardX,rewardY) && PathLibrary.hasEnd(monster_rewardPaths.get(i),monsterX,monsterY)) {
				return monster_rewardPaths.get(i);
			}
		}
		return null;
	}

	public ArrayList getPath(Point2D p1, Point2D p2){
		return getPath((int)(p1.x),(int)(p1.y),(int)(p2.x),(int)(p2.y));
	}
	public ArrayList getPath(Point2D p1, int x2,int y2){
		return getPath((int)(p1.x),(int)(p1.y),x2,y2);
	}
	public ArrayList getPath(int x1,int y1, Point2D p2){
		return getPath(x1,y1,(int)(p2.x),(int)(p2.y));
	}
	public ArrayList getPath(int x1,int y1,int x2,int y2){ 
		ArrayList result = astar.aStar(findNode(x1,y1),findNode(x2,y2));
		if(PathLibrary.hasStart(result,x2,y2) && PathLibrary.hasEnd(result,x1,y1)){ 
			return result;
		}
		return null; 
	}
	
	public double getDistance(Point2D p1, Point2D p2){
		return getDistance((int)(p1.x),(int)(p1.y),(int)(p2.x),(int)(p2.y));
	}
	public double getDistance(Point2D p1, int x2,int y2){
		return getDistance((int)(p1.x),(int)(p1.y),x2,y2);
	}
	public double getDistance(int x1,int y1, Point2D p2){
		return getDistance(x1,y1,(int)(p2.x),(int)(p2.y));
	}
	public double getDistance(int x1,int y1,int x2,int y2){ 
		ArrayList result = astar.aStar(findNode(x1,y1),findNode(x2,y2));
		if(PathLibrary.hasStart(result,x2,y2) && PathLibrary.hasEnd(result,x1,y1)){ 
			return PathLibrary.calculateDistance(result);
		}
		return Double.NaN; 
	}
}