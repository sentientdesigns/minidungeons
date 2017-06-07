package dungeon.play;

import util.math2d.Point2D;

public class Reward extends GameCharacter {
	int treasureBonus;
	
	public Reward(Point2D position, String name, int treasureBonus){
		super(position,1,name);
		this.treasureBonus = treasureBonus;
	}
	
	public Reward(Point2D position, String name, int treasureBonus, int healing){
		super(position,1,name);
		this.treasureBonus = treasureBonus;
	}

    public Reward clone(){
		Reward clone = new Reward(new Point2D(this.startingPos.x,this.startingPos.y), this.name, this.treasureBonus);
        clone.startingPos = new Point2D(this.startingPos.x,this.startingPos.y);
        clone.position = new Point2D(this.position.x,this.position.y);
        clone.startingHitpoints = this.startingHitpoints;
        clone.hitpoints = this.hitpoints;
        clone.setController(this.controller);
        clone.treasureBonus = this.treasureBonus;
        return clone;
    }
	
	int getTreasureBonus(){ return treasureBonus; }
	
	@Override
	public String eventCollision(GameCharacter collider){
		if(collider instanceof Hero){ 
			Hero hero = (Hero)collider;
			this.kill();
			hero.setPosition(new Point2D(position));
			hero.addToScore(treasureBonus);
			return "Hero acquires "+this.getName()+".";
		}
		return "";
	}
}