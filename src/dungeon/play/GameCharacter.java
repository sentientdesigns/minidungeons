package dungeon.play;

import util.math2d.Point2D;

import controllers.Controller;

public class GameCharacter {
	protected String name;
	// ----
	protected Point2D startingPos;
	protected int startingHitpoints;	
	// ----
	protected Point2D position;
	protected int hitpoints;
	// ----
	protected Controller controller;
	
	public GameCharacter(Point2D position, int hitpoints, String name){
		this.startingPos = new Point2D(position.x,position.y);
		this.startingHitpoints = hitpoints;
		
		this.controller = null;
		
		this.position = new Point2D(position.x,position.y);
		this.hitpoints = hitpoints;
		this.name = name;
	}

    @Override
    public GameCharacter clone(){
        GameCharacter clone = new GameCharacter(new Point2D(this.startingPos.x,this.startingPos.y), this.hitpoints, this.name);
        clone.startingPos = new Point2D(this.startingPos.x,this.startingPos.y);
        clone.position = new Point2D(this.position.x,this.position.y);
        clone.startingHitpoints = this.startingHitpoints;
        clone.hitpoints = this.hitpoints;
        clone.name = this.name;
        clone.setController(this.controller);
        return clone();
    }
	
	public void setController(Controller controller){ this.controller = controller;	}
	public Controller getController(){ return this.controller; }
	
	public void reset(){
		this.position = new Point2D(startingPos);
		this.hitpoints = startingHitpoints;
	}
	
	public void damage(int damage){
		if(hitpoints>0){ hitpoints-=damage; }
		hitpoints = Math.max(0,hitpoints);
        //damageTaken += damage;
	}

    //public int getDamageTaken(){ return damageTaken; }

	public void kill(){ hitpoints=0; }
	
	public boolean isAlive(){ return hitpoints>0; }
	public int getHitpoints(){ return hitpoints; }
	public int getStartingHitpoints(){ return startingHitpoints; }
	public Point2D getPosition(){ return new Point2D(position); }
	public Point2D getStartingPosition(){ return new Point2D(startingPos); }
	public int getX(){ return (int)(position.x); }
	public int getY(){ return (int)(position.y); }
	public String getName(){ return name; }
	
	public void setPosition(int x, int y){ 
		position.x = x; 
		position.y = y;
	}
	public void setPosition(Point2D value){ 
		position.x = value.x; 
		position.y = value.y;
	}
	
	public void setHitpoints(int value){ hitpoints = value; }
	
	public Point2D getNextPosition(int direction){ 
		int cX = getX();
		int cY = getY();
		if(direction==PlayMap.UP){ cY--; }
		if(direction==PlayMap.RIGHT){ cX++; }
		if(direction==PlayMap.DOWN){ cY++; }
		if(direction==PlayMap.LEFT){ cX--; }
		return new Point2D(cX,cY); 
	}
	
	public String update(){ return ""; }
	
	public String eventCollision(GameCharacter collider){ return ""; }
}
