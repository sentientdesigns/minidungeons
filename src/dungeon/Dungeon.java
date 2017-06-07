package dungeon;

import java.util.Vector;

import util.math2d.Point2D;
import util.statics.RandomNumberManager;

public class Dungeon {
	protected int mapSizeX, mapSizeY;
	protected boolean[][] impassable;
	protected Vector<Point2D> exits;
	protected Vector<Point2D> monsters;
	protected Vector<Point2D> rewards;
	protected Vector<Point2D> potions;
	
	protected DungeonPaths paths;
	
	public Dungeon(int sizeX, int sizeY){ 
		this.mapSizeX = sizeX;
		this.mapSizeY = sizeY;
		clearImpassable();
		exits = new Vector<Point2D>();
		monsters = new Vector<Point2D>();
		rewards = new Vector<Point2D>();
		potions = new Vector<Point2D>();
		paths = new DungeonPaths(this);
	}
	
	public Dungeon(Dungeon copy){
		this.mapSizeX = copy.mapSizeX;
		this.mapSizeY = copy.mapSizeX;
		clearImpassable();
		for(int x=0;x<impassable.length;x++){
			for(int y=0;y<impassable[x].length;y++){
				this.impassable[x][y]=copy.impassable[x][y];
			}
		}
		this.monsters = new Vector<Point2D>();
		for(int i=0;i<copy.monsters.size();i++){ 
			this.monsters.add(new Point2D(copy.monsters.get(i))); 
		}
		this.rewards = new Vector<Point2D>();
		for(int i=0;i<copy.rewards.size();i++){ 
			this.rewards.add(new Point2D(copy.rewards.get(i))); 
		}
		this.potions = new Vector<Point2D>();
		for(int i=0;i<copy.potions.size();i++){ 
			this.potions.add(new Point2D(copy.potions.get(i))); 
		}
		this.exits = new Vector<Point2D>();
		for(int i=0;i<copy.exits.size();i++){ 
			this.exits.add(new Point2D(copy.exits.get(i))); 
		}
		this.paths = new DungeonPaths(this);
		this.finalizeSketch();
	}
	
	public int getMapSizeX(){ return mapSizeX; }
	public int getMapSizeY(){ return mapSizeY; }
	public DungeonPaths getPaths(){ return paths; }

	public int getTileDifference(Dungeon other){
		if(other.mapSizeX!=this.mapSizeX || other.mapSizeY!=this.mapSizeY){
			System.out.println("Comparing differnet-sized sketches");
			return Integer.MAX_VALUE;
		}
		int result = 0;
		boolean[][] exitArray = this.getExitArray();
		boolean[][] monsterArray = this.getMonsterArray();
		boolean[][] rewardArray = this.getRewardArray();
		boolean[][] potionArray = this.getPotionArray();
		boolean[][] otherExitArray = other.getExitArray();
		boolean[][] otherMonsterArray = other.getMonsterArray();
		boolean[][] otherRewardArray = other.getRewardArray();
		boolean[][] otherPotionArray = other.getPotionArray();
		for(int x=0;x<impassable.length;x++){
			for(int y=0;y<impassable[x].length;y++){
				if( impassable[x][y]!=other.impassable[x][y] ||
					exitArray[x][y]!=otherExitArray[x][y] || 
					rewardArray[x][y]!=otherRewardArray[x][y] || 
					potionArray[x][y]!=otherPotionArray[x][y] || 
					monsterArray[x][y]!=otherMonsterArray[x][y] ){ 
					result++; 
				}
			}
		}
		return result;
	}
	
	public void clearImpassable(){
		impassable = new boolean[mapSizeX][mapSizeY];
		for(int x=0;x<impassable.length;x++){
			for(int y=0;y<impassable[0].length;y++){
				impassable[x][y]=false;
			}
		}
	}
	
	public void clearImpassable(boolean initialValue){
		impassable = new boolean[mapSizeX][mapSizeY];
		for(int x=0;x<impassable.length;x++){
			for(int y=0;y<impassable[0].length;y++){
				impassable[x][y]=initialValue;
			}
		}
	}

	public boolean addMonster(int x, int y){
		if(isEmpty(x,y)){
			monsters.add(new Point2D(x,y));
			return true;
		}
		return false;
	}
	public boolean removeMonster(int x, int y){
		int index = getMonsterIndex(x,y);
		if(index!=-1){ 
			monsters.remove(index);
			return true;
		}
		return false;
	}
	
	public int getMonsterIndex(int x, int y){
		for(int i=0;i<monsters.size();i++){
			if(monsters.get(i).isAt(x, y)){
				return i;
			}
		}
		return -1;
	}
	
	public boolean addReward(int x, int y){
		if(isEmpty(x,y)){
			rewards.add(new Point2D(x,y));
			return true;
		}
		return false;
	}
	public boolean removeReward(int x, int y){
		int index = getRewardIndex(x,y);
		if(index!=-1){ 
			rewards.remove(index);
			return true;
		}
		return false;
	}
	
	public int getRewardIndex(int x, int y){
		for(int i=0;i<rewards.size();i++){
			if(rewards.get(i).isAt(x, y)){
				return i;
			}
		}
		return -1;
	}
	
	public boolean addPotion(int x, int y){
		if(isEmpty(x,y)){
			potions.add(new Point2D(x,y));
			return true;
		}
		return false;
	}
	public boolean removePotion(int x, int y){
		int index = getPotionIndex(x,y);
		if(index!=-1){ 
			potions.remove(index);
			return true;
		}
		return false;
	}
	
	public int getPotionIndex(int x, int y){
		for(int i=0;i<potions.size();i++){
			if(potions.get(i).isAt(x, y)){
				return i;
			}
		}
		return -1;
	}
	
	public boolean addExit(int x, int y){
		if(isEmpty(x,y)){
			exits.add(new Point2D(x,y));
			return true;
		}
		return false;
	}
	public boolean removeExit(int x, int y){
		int index = getExitIndex(x,y);
		if(index!=-1){ 
			exits.remove(index);
			return true;
		}
		return false;
	}
	
	public int getExitIndex(int x, int y){
		for(int i=0;i<exits.size();i++){
			if(exits.get(i).isAt(x, y)){
				return i;
			}
		}
		return -1;
	}

	public boolean isEmpty(int x, int y){
		return (isWithinBounds(x,y) && !impassable[x][y] && getRewardIndex(x,y)==-1 && getPotionIndex(x,y)==-1 && getMonsterIndex(x,y)==-1 && getExitIndex(x,y)==-1);
	}
	
	public void setPassable(int x, int y){
		if(isWithinBounds(x,y)){ impassable[x][y]=false; }
	}
	public void setImpassable(int x, int y){
		if(isWithinBounds(x,y)){ impassable[x][y]=true; }
	}
	
	public void setPassable(boolean[][] pattern, int centerX, int centerY){
		for(int x=0;x<pattern.length;x++){
			for(int y=0;y<pattern[0].length;y++){
				if(pattern[x][y]){
					int global_x = centerX-pattern.length/2+x*pattern.length;
					int global_y = centerY-pattern[0].length/2+y*pattern[0].length;
					if(isWithinBounds(global_x,global_y)){ impassable[global_x][global_y]=false; }
				}
			}
		}
	}
	
	public void setImpassable(boolean[][] pattern, int centerX, int centerY){
		for(int x=0;x<pattern.length;x++){
			for(int y=0;y<pattern[0].length;y++){
				if(pattern[x][y]){
					int global_x = centerX-pattern.length/2+x;
					int global_y = centerY-pattern[0].length/2+y;
					if(isWithinBounds(global_x,global_y) ){ impassable[global_x][global_y]=true; }
				}
			}
		}
	}
	
	public void setImpassableChunk(int centerX, int centerY, int sizeX, int sizeY){
		for(int x=centerX-sizeX/2;x<centerX+sizeX/2;x++){
			for(int y=centerY-sizeY/2;y<centerY+sizeY/2;y++){
				if(isWithinBounds(x,y)){ impassable[x][y]=true; }
			}
		}
	}
	
	public int getExitLength(){ return exits.size(); }
	public int getMonsterLength(){ return monsters.size(); }
	public int getRewardLength(){ return rewards.size(); }
	public int getPotionLength(){ return potions.size(); }

	public Point2D getExit(int index){ return exits.get(index); }
	public Point2D getMonster(int index){ return monsters.get(index); }
	public Point2D getReward(int index){ return rewards.get(index); }
	public Point2D getPotion(int index){ return potions.get(index); }
	
	public boolean isExit(int x, int y){ 
		if(isWithinBounds(x,y)){ return inList(x,y,this.exits); } 
		return false;
	}
	public boolean isMonster(int x, int y){ 
		if(isWithinBounds(x,y)){ return inList(x,y,this.monsters); } 
		return false;
	}
	public boolean isReward(int x, int y){ 
		if(isWithinBounds(x,y)){ return inList(x,y,this.rewards); } 
		return false;
	}
	public boolean isPotion(int x, int y){ 
		if(isWithinBounds(x,y)){ return inList(x,y,this.potions); } 
		return false;
	}
	public boolean inList(int x, int y, Vector<Point2D> list){
		for(int i=0;i<list.size();i++){
			Point2D currPoint = list.get(i);
			if(x==(int)(currPoint.x) && y==(int)(currPoint.y)){ return true;} 
		}
		return false;
	}
	public boolean inList(Point2D point, Vector<Point2D> list){
		for(int i=0;i<list.size();i++){
			Point2D currPoint = list.get(i);
			if((int)(point.x)==(int)(currPoint.x) && (int)(point.y)==(int)(currPoint.y)){ return true;} 
		}
		return false;
	}
	
	public void finalizeSketch(){
		paths.init();
		paths.calculateAllPaths();
	}
	
	public boolean isPassable(int x, int y){
		if(!isWithinBounds(x,y)){
			return false;
		} else {
			return !(impassable[x][y]);
		}
	}
	public boolean isWithinBounds(int x, int y){
		return (x>=0 && x<mapSizeX && y>=0 && y<mapSizeY);
	}
	public boolean isCorner(int x, int y){
		if(x==0 && y==0){ return true; }
		if(x==0 && y==mapSizeY-1){ return true; }
		if(x==mapSizeX-1 && y==0){ return true; }
		if(x==mapSizeX-1 && y==mapSizeY-1){ return true; }
		return false;
	}

	public boolean[][] getPassableArray(){
		boolean[][] result = new boolean[impassable.length][impassable[0].length];
		for(int x=0;x<impassable.length;x++){
			for(int y=0;y<impassable[0].length;y++){
				result[x][y]=!impassable[x][y];
			}
		}
		return result;
	}
	public boolean[][] getImpassableArray(){
		boolean[][] result = new boolean[impassable.length][impassable[0].length];
		for(int x=0;x<impassable.length;x++){
			for(int y=0;y<impassable[0].length;y++){
				result[x][y]=impassable[x][y];
			}
		}
		return result;
	}
	public boolean[][] getMonsterArray(){
		boolean[][] result = new boolean[impassable.length][impassable[0].length];
		for(int x=0;x<impassable.length;x++){
			for(int y=0;y<impassable[0].length;y++){
				result[x][y]=false;
			}
		}
		for(int i=0;i<monsters.size();i++){
			result[(int)(monsters.get(i).x)][(int)(monsters.get(i).y)]=true;
		}
		return result;
	}
	public boolean[][] getRewardArray(){
		boolean[][] result = new boolean[impassable.length][impassable[0].length];
		for(int x=0;x<impassable.length;x++){
			for(int y=0;y<impassable[0].length;y++){
				result[x][y]=false;
			}
		}
		for(int i=0;i<rewards.size();i++){
			result[(int)(rewards.get(i).x)][(int)(rewards.get(i).y)]=true;
		}
		return result;
	}
	public boolean[][] getPotionArray(){
		boolean[][] result = new boolean[impassable.length][impassable[0].length];
		for(int x=0;x<impassable.length;x++){
			for(int y=0;y<impassable[0].length;y++){
				result[x][y]=false;
			}
		}
		for(int i=0;i<potions.size();i++){
			result[(int)(potions.get(i).x)][(int)(potions.get(i).y)]=true;
		}
		return result;
	}
	public boolean[][] getExitArray(){
		boolean[][] result = new boolean[impassable.length][impassable[0].length];
		for(int x=0;x<impassable.length;x++){
			for(int y=0;y<impassable[0].length;y++){
				result[x][y]=false;
			}
		}
		for(int i=0;i<exits.size();i++){
			result[(int)(exits.get(i).x)][(int)(exits.get(i).y)]=true;
		}
		return result;
	}
	public boolean[][] getArray(Vector<Point2D> points){
		boolean[][] result = new boolean[impassable.length][impassable[0].length];
		for(int x=0;x<impassable.length;x++){
			for(int y=0;y<impassable[0].length;y++){
				result[x][y]=false;
			}
		}
		for(int i=0;i<points.size();i++){
			result[(int)(points.get(i).x)][(int)(points.get(i).y)]=true;
		}
		return result;
	}
	public void randomize(int exitCount,int monsterCount,int rewardCount){
		this.exits.clear();
		for(int i=0;i<exitCount;i++){
			int x = RandomNumberManager.getRandomInt(0,mapSizeX);
			int y = RandomNumberManager.getRandomInt(0,mapSizeY);
			addExit(x,y);
		}
		this.monsters.clear();
		for(int i=0;i<monsterCount;i++){
			int x = RandomNumberManager.getRandomInt(0,mapSizeX);
			int y = RandomNumberManager.getRandomInt(0,mapSizeY);
			addMonster(x,y);
		}
		this.rewards.clear();
		for(int i=0;i<monsterCount;i++){
			int x = RandomNumberManager.getRandomInt(0,mapSizeX);
			int y = RandomNumberManager.getRandomInt(0,mapSizeY);
			addReward(x,y);
		}
	}
	public void randomize(int exitCount,int monsterCount,int rewardCount, int potionCount){
		randomize(exitCount, monsterCount, rewardCount);
		this.potions.clear();
		for(int i=0;i<potionCount;i++){
			int x = RandomNumberManager.getRandomInt(0,mapSizeX);
			int y = RandomNumberManager.getRandomInt(0,mapSizeY);
			addPotion(x,y);
		}
	}
	public void randomizeChunks(int chunkCount){
		clearImpassable();
		for(int i=0;i<chunkCount;i++){
			int x = RandomNumberManager.getRandomInt(0,mapSizeX);
			int y = RandomNumberManager.getRandomInt(0,mapSizeY);
			impassable[x][y]=true;
		}
	}
	
	public String toASCII(){
		String result="";
		for(int y=0;y<mapSizeX;y++){
			for(int x=0;x<mapSizeX;x++){
				if(impassable[x][y]){ 
					result+="#"; 
				} else if(isExit(x,y)){
					result+="E"; 
				} else if(isMonster(x,y)){
					result+="m"; 
				} else if(isReward(x,y)){
					result+="r"; 
				} else if(isPotion(x,y)){
					result+="p"; 
				} else {
					result+=".";
				}				
			}
			result+=";";
		}
		return result;
	}
}
