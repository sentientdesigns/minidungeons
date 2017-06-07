package dungeon.play;

import util.math2d.Point2D;

public class Hero extends GameCharacter {
	int score = 0;
	int damage;
    //int damageDealt = 0;
    //int monstersKilled = 0;
	
	public Hero(Point2D position, int hitpoints, String name){
		super(position,hitpoints,name); 
		this.score = 0;
		this.damage = damage;
	}

    @Override
    public Hero clone(){
        Hero clone = new Hero(super.getPosition(), super.getHitpoints(), super.getName());
        clone.startingPos = new Point2D(this.startingPos.x,this.startingPos.y);
        clone.position = new Point2D(this.position.x,this.position.y);
        clone.startingHitpoints = this.startingHitpoints;
        clone.hitpoints = this.hitpoints;
        clone.name = this.name;
		clone.score = this.score;
		clone.damage = this.damage;
        //clone.monstersKilled = this.monstersKilled;
        return clone;
    }

    //public int getMonstersKilled(){return monstersKilled;}
    //public void incrementMonstersKilled(){ monstersKilled++; }
	
	public int getScore(){ return score; }
	public void setScore(int value){ score = value; }
	public void addToScore(int value){ score += value; }
	public void resetScore(){ score = 0; }
	
	@Override
	public void reset(){
		super.reset();
		score = 0;
	}

    //public int getDamageDealt(){ return damageDealt; }
	
	@Override 
	public String eventCollision(GameCharacter collided){
		if(collided instanceof Reward){
			Reward reward = (Reward)collided;
			return reward.eventCollision(this);
		}
		if(collided instanceof Monster){
			Monster monster = (Monster)collided;
			return monster.eventCollision(this);
		}
		return "";
	}
}
