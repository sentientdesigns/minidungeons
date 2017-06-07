package dungeon.play;

import util.math2d.Point2D;

public class Powerup extends Reward {
	int healing;
	boolean overheal;

	public Powerup(Point2D position, String name, int healing){
		super(position,name,0,healing);
		this.healing = healing;
		this.overheal = false;
	}
	public Powerup(Point2D position, String name, int healing, boolean overheal){
		super(position,name,0,healing);
		this.healing = healing;
		this.overheal = overheal;
	}

    public Powerup clone(){
        Powerup clone = new Powerup(new Point2D(this.startingPos.x,this.startingPos.y), this.name, this.healing, this.overheal);
		clone.startingPos = new Point2D(this.startingPos.x,this.startingPos.y);
        clone.position = new Point2D(this.position.x,this.position.y);
        clone.startingHitpoints = this.startingHitpoints;
        clone.hitpoints = this.hitpoints;
        clone.name = this.name;
        clone.healing = this.healing;
		clone.overheal = this.overheal;
        return clone;
    }
	
	int getHealing(){ return healing; }
	boolean isOverheal(){ return overheal; }
	
	@Override
	public String eventCollision(GameCharacter collider){
		if(collider instanceof Hero){ 
			Hero hero = (Hero)collider;
			this.kill();
			hero.setPosition(new Point2D(position));
			hero.addToScore(treasureBonus);
			int healedAmount;
			if(overheal){
				healedAmount = healing;
			} else {
				if(hero.getHitpoints()>=hero.getStartingHitpoints()){
					healedAmount = 0;
				} else {
					healedAmount = Math.min(healing,hero.getStartingHitpoints()-hero.getHitpoints());
				}
			}
			hero.damage(-healedAmount);
			if(healedAmount>0){
				return "Hero acquires "+this.getName()+" and gains "+healedAmount+" HP.";
			} else if(healedAmount<0){
				return "Hero acquires "+this.getName()+" but loses "+healedAmount+" HP.";
			} else {
				return "Hero acquires "+this.getName()+", but is at full health.";
			}
		}
		return "";
	}
/*=======
package dungeon.sketchplay;

import util.math2d.Point2D;

public class Powerup extends Reward {
	int healing;
	boolean overheal;

	public Powerup(Point2D position, String name, int healing){
		super(position,name,0,healing);
		this.healing = healing;
		this.overheal = false;
	}
	public Powerup(Point2D position, String name, int healing, boolean overheal){
		super(position,name,0,healing);
		this.healing = healing;
		this.overheal = overheal;
	}

    public Powerup clone(){
        Powerup clone = new Powerup( new Point2D(this.position.x,this.position.y), this.name, this.healing, this.overheal    );
        return clone;
    }
	
	public int getHealing(){ return healing; }
	boolean isOverheal(){ return overheal; }
	
	@Override
	public String eventCollision(GameCharacter collider){
		if(collider instanceof Hero){ 
			Hero hero = (Hero)collider;
			this.kill();
			hero.setPosition(new Point2D(position));
			hero.addToScore(treasureBonus);
			int healedAmount;
			if(overheal){
				healedAmount = healing;
			} else {
				if(hero.getHitpoints()>=hero.getStartingHitpoints()){
					healedAmount = 0;
				} else {
					healedAmount = Math.min(healing,hero.getStartingHitpoints()-hero.getHitpoints());
				}
			}
			hero.damage(-healedAmount);
			if(healedAmount>0){
				return "Hero acquires "+this.getName()+" and gains "+healedAmount+" HP.";
			} else if(healedAmount<0){
				return "Hero acquires "+this.getName()+" but loses "+healedAmount+" HP.";
			} else {
				return "Hero acquires "+this.getName()+", but is at full health.";
			}
		}
		return "";
	}*/
}