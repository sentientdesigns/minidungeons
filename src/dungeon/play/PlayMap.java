package dungeon.play;

import java.util.Vector;
import java.util.Arrays;

import controllers.Controller;

import dungeon.Dungeon;
import libraries.PathLibrary;
import util.math2d.Matrix2D;

//import holmgard.CloningUtils;

import util.math2d.Point2D;
import util.statics.RandomNumberManager;

public class PlayMap extends Dungeon implements Cloneable {
	public final static int UP = 0;
	public final static int RIGHT = 1;
	public final static int DOWN = 2;
	public final static int LEFT = 3;
	public final static int IDLE = -1;
	
	public final static int NONE = 101;
	public final static int UNKNOWN = 102;
	public final static int EMPTY = 0;
	public final static int WALL = 1;
	public final static int EXIT = 2;
	public final static int ENTRANCE = 3;
	public final static int MONSTER = 4;
	public final static int TREASURE = 5;
	public final static int POTION = 6;
	
	public final static int TREASURESCORE = 1;
	public final static int MONSTERSCORE = 0;
	
	Point2D entrance;
	//Point2D hero;
	
	public Vector<Reward> rewardChars;
	public Vector<Powerup> potionChars;
	public Vector<Monster> monsterChars;
	Hero hero;
	
	final int viewRange=2;
	
	boolean[][] explored;
	boolean[][] currentView;
    int[][] visited;
	int actionsTaken;
	
	int[][] viewport;
	
	Vector<String> actionLog;
	Vector<String> eventLog;
	boolean gameHalted;

    Dungeon baseMap;

	static int startingHP = 40;
	static int combatRange = 10;
	static int combatBaseline = 10;
	static int treasureBonus = 1;
	static int potionHP = 10;
	
	public PlayMap(Dungeon baseMap){
		super(baseMap.getMapSizeX(),baseMap.getMapSizeY());
        this.baseMap = baseMap;
		clearImpassable();
		gameHalted = true;
		for(int x=0;x<impassable.length;x++){
			for(int y=0;y<impassable[x].length;y++){
				this.impassable[x][y]=!baseMap.isPassable(x, y);
			}
		}
		this.monsters = new Vector<Point2D>();
		for(int i=0;i<baseMap.getMonsterLength();i++){ 
			this.monsters.add(new Point2D(baseMap.getMonster(i))); 
		}
		this.rewards = new Vector<Point2D>();
		for(int i=0;i<baseMap.getRewardLength();i++){ 
			this.rewards.add(new Point2D(baseMap.getReward(i)));
		}
		this.potions = new Vector<Point2D>();
		for(int i=0;i<baseMap.getPotionLength();i++){ 
			this.potions.add(new Point2D(baseMap.getPotion(i)));
		}
		this.exits = new Vector<Point2D>();
		for(int i=0;i<baseMap.getExitLength();i++){ 
			this.exits.add(new Point2D(baseMap.getExit(i))); 
		}
		
		entrance = this.getExit(0);
		finalizeSketch();
		resetLevel();
	}

    @Override
    public PlayMap clone(){
        PlayMap clone = new PlayMap(this.baseMap);
		
		clone.explored = Matrix2D.copy(this.explored);
		clone.currentView = Matrix2D.copy(this.currentView);
		clone.visited = Matrix2D.copy(this.visited);
		clone.viewport = Matrix2D.copy(this.viewport);

        Vector cloneRewards = new Vector(this.getRewardChars().size());
        for(int i = 0; i < getRewardChars().size(); i++) {
            cloneRewards.add(getRewardChars().get(i).clone());
        }
        clone.setRewardChars(cloneRewards);

        Vector cloneMonsters = new Vector(this.getMonsterChars().size());
        for(int i = 0; i < getMonsterChars().size(); i++) {
            cloneMonsters.add(getMonsterChars().get(i).clone());
        }
        clone.setMonsterChars(cloneMonsters);

        Vector clonePotions = new Vector(this.getPotionChars().size());
        for(int i = 0; i < this.getPotionChars().size(); i++) {
            clonePotions.add(getPotionChars().get(i).clone());
        }
        clone.setPotionChars(clonePotions);

        if(eventLog != null) {
			Vector cloneEventLog = new Vector(eventLog.size());
            for(int logItem = 0; logItem < eventLog.size(); logItem++){
                cloneEventLog.setElementAt(eventLog.get(logItem),logItem);
            }
			clone.eventLog = cloneEventLog;
        }
        if(actionLog != null) {
			Vector cloneActionLog = new Vector(actionLog.size());
            for(int logItem = 0; logItem < actionLog.size(); logItem++){
                cloneActionLog.setElementAt(actionLog.get(logItem),logItem);
            }
			clone.actionLog = cloneActionLog;
        }
        
		if(hero!=null){ clone.hero = this.getHero().clone(); }
        clone.gameHalted = this.gameHalted;

        return clone;
    }

	public Hero getHero(){ return hero; }
	public boolean isGameHalted(){ return gameHalted; }
	
	public int getViewRange(){ return viewRange; }
	
	public boolean[][] getExplored(){ 
		boolean[][] result = new boolean[getMapSizeX()][getMapSizeY()];
		for(int x=0;x<explored.length;x++){
			result[x] = Arrays.copyOf(explored[x], explored[x].length);
		}
		return result;
	}
	
	public boolean[][] getUnexplored(){ 
		boolean[][] result = new boolean[getMapSizeX()][getMapSizeY()];
		for(int x=0;x<explored.length;x++){
			for(int y=0;y<explored[x].length;y++){
				result[x][y]=!explored[x][y];
			}
		}
		return result;
	}
	
	public int[][] getVisited(){ 
		int[][] result = new int[getMapSizeX()][getMapSizeY()];
		for(int x=0;x<visited.length;x++){
			result[x] = Arrays.copyOf(visited[x], visited[x].length);
		}
		return result;
	}

	public boolean[][] getAnyVisited(){ 
		boolean[][] result = new boolean[getMapSizeX()][getMapSizeY()];
		for(int x=0;x<visited.length;x++){
			for(int y=0;y<visited[x].length;y++){
				result[x][y]=(visited[x][y]>0);
			}
		}
		return result;
	}

	public int getActionsTaken(){ return actionsTaken; }
	
	public boolean[][] getCurrentView(){ 
		boolean[][] result = new boolean[getMapSizeX()][getMapSizeY()];
		for(int x=0;x<currentView.length;x++){
			result[x] = Arrays.copyOf(currentView[x], currentView[x].length);
		}
		return result;
	}
	
	public boolean[][] getExploredNoCurrentView(){ 
		boolean[][] result = new boolean[getMapSizeX()][getMapSizeY()];
		for(int x=0;x<explored.length;x++){
			for(int y=0;y<explored[x].length;y++){
				result[x][y]=(explored[x][y] && !currentView[x][y]);
			}
		}
		return result;
	}
	
	public Point2D getEntrance(){ return entrance; }
	
	// ----------------------------------------------------------------
	// MOVES 
	// ----------------------------------------------------------------
	public boolean isValidMove(Point2D position){
		return isValidMove((int)(position.x),(int)(position.y));
	}
	public boolean isValidMove(int x, int y){
		if(!this.isWithinBounds(x, y) || !this.isPassable(x,y)){ return false; }
		return true;
	}
	
	// ----------------------------------------------------------------
	// VIEWPORT getters
	// ----------------------------------------------------------------
	
	public int[][] getViewPort(){ 
		int[][] result = new int[viewport.length][viewport[0].length];
		for(int x=0;x<viewport.length;x++){
			viewport[x] = Arrays.copyOf(viewport[x], viewport[x].length);
		}
		return result;
	}
	
	public boolean[][] getViewedOfType(int type){ 
		boolean[][] result = new boolean[viewport.length][viewport[0].length];
		for(int x=0;x<viewport.length;x++){
			for(int y=0;y<viewport[x].length;y++){
				result[x][y]=(viewport[x][y]==type);
			}
		}
		return result;
	}
	public boolean[][] getViewedTreasures(){ return getViewedOfType(TREASURE); }
	public boolean[][] getViewedMonsters(){ return getViewedOfType(MONSTER); }
	public boolean[][] getViewedExits(){ return getViewedOfType(EXIT); }
	public boolean[][] getViewedEntrances(){ return getViewedOfType(ENTRANCE); }
	public boolean[][] getViewedPotions(){ return getViewedOfType(POTION); }
	public boolean[][] getViewedWalls(){ return getViewedOfType(WALL); }
	public boolean[][] getViewedEmpty(){ return getViewedOfType(EMPTY); }
	public boolean[][] getViewedUnknown(){ return getViewedOfType(UNKNOWN); }
	public boolean[][] getViewedNone(){ return getViewedOfType(NONE); }
	
	// ----------------------------------------------------------------
	
	protected void randomizeEntrance(){
		int randomIndex = RandomNumberManager.getRandomInt(1, this.getExitLength());
		entrance = this.getExit(randomIndex);
	}
	
	public void startGame(){
		clearExplored();
		clearEventLog();
        clearVisited();
		actionsTaken = 0;
		hero = new Hero(entrance,startingHP,"hero");
		visited[(int)(hero.getStartingPosition().x)][(int)(hero.getStartingPosition().y)]=1;
		resetLevel();
		updateViewport();
		gameHalted = false;
	}

    public void startGame(Controller aiAgent){
        clearExplored();
        clearEventLog();
        hero = new Hero(entrance,startingHP,"hero");
        resetLevel();
        updateViewport();
        gameHalted = false;
    }
	
	public void resetLevel(){
		this.setMonsterChars(new Vector<Monster>());
		for(int i=0;i<super.getMonsterLength();i++){ 
			this.getMonsterChars().add(new Monster(super.getMonster(i), "monster",combatBaseline, combatRange));
		}
		this.setRewardChars(new Vector<Reward>());
		for(int i=0;i<super.getRewardLength();i++){ 
			this.getRewardChars().add(new Reward(super.getReward(i), "treasure", treasureBonus));
		}
		this.setPotionChars(new Vector<Powerup>());
		for(int i=0;i<super.getPotionLength();i++){ 
			this.getPotionChars().add(new Powerup(super.getPotion(i), "potion", potionHP));
		}
	}
	
	public void resetLevelStatic(){
		this.setMonsterChars(new Vector<Monster>());
		for(int i=0;i<super.getMonsterLength();i++){ 
			this.getMonsterChars().add(new Monster(super.getMonster(i), "monster",combatBaseline, 0));
		}
		this.setRewardChars(new Vector<Reward>());
		for(int i=0;i<super.getRewardLength();i++){ 
			this.getRewardChars().add(new Reward(super.getReward(i), "treasure", treasureBonus));
		}
		this.setPotionChars(new Vector<Powerup>());
		for(int i=0;i<super.getPotionLength();i++){ 
			this.getPotionChars().add(new Powerup(super.getPotion(i), "potion", potionHP));
		}
	}
	
	public void updateGame(int heroMovement){
		if(!gameHalted){
			updateHero(heroMovement);
			updateGameState();
			updateViewport();
		}
	}

	protected void updateHero(int heroMovement){
		int cHeroX = hero.getX();
		int cHeroY = hero.getY();
		if(heroMovement==UP){ cHeroY--; }
		if(heroMovement==RIGHT){ cHeroX++; }
		if(heroMovement==DOWN){ cHeroY++; }
		if(heroMovement==LEFT){ cHeroX--; }
		if(this.isWithinBounds(cHeroX, cHeroY) && this.isPassable(cHeroX, cHeroY)){
			int rIndex = this.getRewardIndex(cHeroX, cHeroY);
			int pIndex = this.getPotionIndex(cHeroX, cHeroY);
			int mIndex = this.getMonsterIndex(cHeroX, cHeroY);
			if(mIndex!=-1){ 
				String event = monsterChars.get(mIndex).eventCollision(hero);
				logEvent(event);
			} else if(rIndex!=-1){ 
				String event = rewardChars.get(rIndex).eventCollision(hero);
				logEvent(event);
			} else if(pIndex!=-1){ 
				String event = potionChars.get(pIndex).eventCollision(hero);
				logEvent(event);
			} else if(this.isExit(cHeroX,cHeroY) && !entrance.isAt(cHeroX,cHeroY)){ 
				hero.setPosition(cHeroX,cHeroY);
				logEvent("Hero reaches the exit and completes the level.");
				gameHalted = true;
			} else {
				hero.setPosition(cHeroX,cHeroY);
			}

            //Update visited matrix
            visited[cHeroX][cHeroY]++;
			actionsTaken++;
		}
	}
	
	@Override
	public int getMonsterIndex(int x, int y){
		for(int i=0;i<getMonsterChars().size();i++){
			if(getMonsterChars().get(i).isAlive() && getMonsterChars().get(i).getX()==x && getMonsterChars().get(i).getY()==y){
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public boolean[][] getMonsterArray(){
		boolean[][] result = new boolean[impassable.length][impassable[0].length];
		for(int x=0;x<impassable.length;x++){
			for(int y=0;y<impassable[0].length;y++){
				result[x][y]=false;
			}
		}
		for(int i=0;i<getMonsterChars().size();i++){
			if(getMonsterChars().get(i).isAlive()){
				result[(int)(getMonsterChars().get(i).getX())][(int)(getMonsterChars().get(i).getY())]=true;
			}
		}
		return result;
	}
	
	public boolean[][] getDeadMonsterArray(){
		boolean[][] result = new boolean[impassable.length][impassable[0].length];
		for(int x=0;x<impassable.length;x++){
			for(int y=0;y<impassable[0].length;y++){
				result[x][y]=false;
			}
		}
		for(int i=0;i<getMonsterChars().size();i++){
			if(!getMonsterChars().get(i).isAlive()){
				result[(int)(getMonsterChars().get(i).getX())][(int)(getMonsterChars().get(i).getY())]=true;
			}
		}
		return result;
	}
	
	@Override
	public int getRewardIndex(int x, int y){
		for(int i=0;i<getRewardChars().size();i++){
			if(getRewardChars().get(i).isAlive() && getRewardChars().get(i).getX()==x && getRewardChars().get(i).getY()==y){
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public boolean[][] getRewardArray(){
		boolean[][] result = new boolean[impassable.length][impassable[0].length];
		for(int x=0;x<impassable.length;x++){
			for(int y=0;y<impassable[0].length;y++){
				result[x][y]=false;
			}
		}
		for(int i=0;i<getRewardChars().size();i++){
			if(getRewardChars().get(i).isAlive()){
				result[(int)(getRewardChars().get(i).getX())][(int)(getRewardChars().get(i).getY())]=true;
			}
		}
		return result;
	}
	
	public boolean[][] getDeadRewardArray(){
		boolean[][] result = new boolean[impassable.length][impassable[0].length];
		for(int x=0;x<impassable.length;x++){
			for(int y=0;y<impassable[0].length;y++){
				result[x][y]=false;
			}
		}
		for(int i=0;i<getRewardChars().size();i++){
			if(!getRewardChars().get(i).isAlive()){
				result[(int)(getRewardChars().get(i).getX())][(int)(getRewardChars().get(i).getY())]=true;
			}
		}
		return result;
	}
	
	@Override
	public int getPotionIndex(int x, int y){
		for(int i=0;i<getPotionChars().size();i++){
			if(getPotionChars().get(i).isAlive() && getPotionChars().get(i).getX()==x && getPotionChars().get(i).getY()==y){
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public boolean[][] getPotionArray(){
		boolean[][] result = new boolean[impassable.length][impassable[0].length];
		for(int x=0;x<impassable.length;x++){
			for(int y=0;y<impassable[0].length;y++){
				result[x][y]=false;
			}
		}
		for(int i=0;i<getPotionChars().size();i++){
			if(getPotionChars().get(i).isAlive()){
				result[(int)(getPotionChars().get(i).getX())][(int)(getPotionChars().get(i).getY())]=true;
			}
		}
		return result;
	}
	
	public boolean[][] getDeadPotionArray(){
		boolean[][] result = new boolean[impassable.length][impassable[0].length];
		for(int x=0;x<impassable.length;x++){
			for(int y=0;y<impassable[0].length;y++){
				result[x][y]=false;
			}
		}
		for(int i=0;i<getPotionChars().size();i++){
			if(!getPotionChars().get(i).isAlive()){
				result[(int)(getPotionChars().get(i).getX())][(int)(getPotionChars().get(i).getY())]=true;
			}
		}
		return result;
	}
	
	protected void updateGameState(){
		// game logic (monster movement, hp lost, etc.)
		int cHeroX = hero.getX();
		int cHeroY = hero.getY();
		// check if monster adjacent to hero
		if(!hero.isAlive()){ gameHalted = true; }
	}
	
	protected void updateViewport(){
		clearCurrentView();
		viewport = new int[2*viewRange+1][2*viewRange+1];
		for(int x=-viewRange;x<=viewRange;x++){
			for(int y=-viewRange;y<=viewRange;y++){
				int glob_x = (int)(hero.getX()+x);
				int glob_y = (int)(hero.getY()+y);
				int view_x = (int)(viewRange+x);
				int view_y = (int)(viewRange+y);
				if(isWithinViewRange(glob_x, glob_y)){
					if(this.isPassable(glob_x,glob_y)){ viewport[view_x][view_y]=EMPTY; }
					if(!this.isPassable(glob_x,glob_y)){ viewport[view_x][view_y]=WALL; }
					if(this.isMonster(glob_x,glob_y)){ viewport[view_x][view_y]=MONSTER; }
					if(this.isReward(glob_x,glob_y)){ viewport[view_x][view_y]=TREASURE; }
					if(this.isPotion(glob_x,glob_y)){ viewport[view_x][view_y]=POTION; }
					if(this.isExit(glob_x,glob_y)){ 
						if(glob_x == entrance.x && glob_y == entrance.y){ 
							viewport[view_x][view_y]=ENTRANCE; 
						} else { 
							viewport[view_x][view_y]=EXIT; 
						}
					}
					// UPDATE EXPLORATION TABLE
					explored[glob_x][glob_y]=true;
					currentView[glob_x][glob_y]=true;
				} else {
					viewport[view_x][view_y]=NONE;
				}
			}
		}
	}
	
	protected boolean isWithinViewRange(Point2D glob_pos){ 
		return isWithinViewRange((int)(glob_pos.x),(int)(glob_pos.y)); 
	}
	protected boolean isWithinViewRange(int glob_x, int glob_y){
		if(!isWithinBounds(glob_x,glob_y)){ return false; }	// sanity check
		int rel_x = (int)(glob_x-hero.getX());
		int rel_y = (int)(glob_y-hero.getY());
		if(viewRange==1){ 
			if(Math.abs(rel_x)<=1 && rel_y==0){ return true; }
			if(Math.abs(rel_y)<=1 && rel_x==0){ return true; }
			return false;
		}
		if(viewRange==2){
			//if(Math.abs(rel_x)==2 && rel_y==0){ return true; }
			//if(Math.abs(rel_y)==2 && rel_x==0){ return true; }
			if(Math.abs(rel_x)<=1 && Math.abs(rel_y)<=1){ return true; }
			if(rel_x==2 && rel_y==0 && this.isPassable(glob_x-1, glob_y)){ return true; }
			if(rel_x==-2 && rel_y==0 && this.isPassable(glob_x+1, glob_y)){ return true; }
			if(rel_y==2 && rel_x==0 && this.isPassable(glob_x, glob_y-1)){ return true; }
			if(rel_y==-2 && rel_x==0 && this.isPassable(glob_x, glob_y+1)){ return true; }
			return false;
		}
		return(rel_x*rel_x+rel_y*rel_y<=viewRange*viewRange);
	}
	
	protected void clearCurrentView(){
		currentView = new boolean[this.getMapSizeX()][this.getMapSizeY()];
		for(int x=0;x<currentView.length;x++){
			for(int y=0;y<currentView[x].length;y++){
				currentView[x][y]=false;
			}
		}
	}
	
	protected void clearExplored(){
		explored = new boolean[getMapSizeX()][getMapSizeY()];
		for(int x=0;x<explored.length;x++){
			for(int y=0;y<explored[x].length;y++){
				explored[x][y]=false;
			}
		}
	}

    protected void clearVisited(){
        visited = new int[getMapSizeX()][getMapSizeY()];
        for(int x=0;x<visited.length;x++){
            for(int y=0;y<visited[x].length;y++){
                visited[x][y] = 0;
            }
        }
    }
	
	
	public boolean isExit(int x, int y){ 
		int index = getExitIndex(x,y);
		if(index>=0 && !entrance.isAt(x, y)){ return true; }
		return false;
	}
	public boolean isEntrance(int x, int y){ 
		return entrance.isAt(x, y);
	}
	public boolean isHero(int x, int y){ 
		if(hero!=null && hero.getPosition().x==x && hero.getPosition().y==y){ return true; }
		return false;
	}
	public boolean isMonster(int x, int y){ 
		if(getMonsterIndex(x,y)>=0){ return true; }
		return false;
	}
	public boolean isReward(int x, int y){ 
		if(getRewardIndex(x,y)>=0){ return true; }
		return false;
	}
	public boolean isPotion(int x, int y){ 
		if(getPotionIndex(x,y)>=0){ return true; }
		return false;
	}
	
	protected void clearEventLog(){
		eventLog = new Vector<String>();
	}
	public void logEvent(String event){ 
		if(eventLog==null){ clearEventLog(); }
		eventLog.add(event);
	}
	public String getLastEvent(){ 
		if(eventLog!=null && !eventLog.isEmpty()){ return eventLog.lastElement(); }
		return "";
	}

    public Vector<Reward> getRewardChars() { return rewardChars; }
    public void setRewardChars(Vector<Reward> rewardChars) { this.rewardChars = rewardChars; }
    public Reward getRewardChar(int index) { return this.rewardChars.get(index); }
    public Vector<Powerup> getPotionChars() { return potionChars; }
    public void setPotionChars(Vector<Powerup> potionChars) { this.potionChars = potionChars; }
    public Powerup getPotionChar(int index) { return this.potionChars.get(index); }
    public Vector<Monster> getMonsterChars() { return monsterChars; }
    public void setMonsterChars(Vector<Monster> monsterChars) { this.monsterChars = monsterChars; }
	public Monster getMonsterChar(int index) { return this.monsterChars.get(index); }
	
	public void incVisited(int x, int y){ visited[x][y]++; }
	public void setVisited(int x, int y, int value){ visited[x][y] = value; }
	
	public String toASCII(){ return toASCII(true); }
	public String toASCII(boolean includeHP){
		String result="";
		for(int y=0;y<mapSizeY;y++){
			for(int x=0;x<mapSizeX;x++){
				if(impassable[x][y]){ 
					result+="#"; 
				} else if(isHero(x,y)){
					result+="@"; 
				} else if(isEntrance(x,y)){
					result+="E"; 
				} else if(isExit(x,y)){
					result+="X"; 
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
			result+="\n";
		}
		if(includeHP){
			result+=hero.getHitpoints()+"\n";
		}
		return result;
	}
}