package dungeon.play;

import util.math2d.Point2D;
import util.statics.RandomNumberManager;

public class Monster extends GameCharacter {
	int damage;
	
	public Monster(Point2D position, String name, int damage){
		super(position,1,name);
		this.damage = damage;
	}

	public Monster(Point2D position, String name, int damage, int damageRange){
		super(position,1,name); 
		if(damageRange>0){
			this.damage = RandomNumberManager.getRandomInt(damage-damageRange/2, damage+damageRange/2);
		} else {
			this.damage = damage;
		}
	}

    @Override
    public Monster clone(){
		Monster clone = new Monster(new Point2D(this.startingPos.x,this.startingPos.y),this.name,this.damage);
        clone.startingPos = new Point2D(this.startingPos.x,this.startingPos.y);
        clone.position = new Point2D(this.position.x,this.position.y);
        clone.startingHitpoints = this.startingHitpoints;
        clone.hitpoints = this.hitpoints;
        clone.name = this.name;
		clone.damage = this.damage;
        return clone;
    }
	
	public int getDamage(){ return this.damage; }
	public void setDamage(int value){ this.damage = value; }
	
	@Override
	public String eventCollision(GameCharacter collider){
		if(collider instanceof Hero){ 
			Hero hero = (Hero)collider;
			hero.damage(damage);
			hero.setPosition(new Point2D(position));
            //hero.incrementMonstersKilled();
            this.kill();

			if(hero.isAlive()){
				return "Hero is damaged by "+this.getName()+" for "+this.getDamage()+" HP.";
			} else {
				return "Hero is damaged by "+this.getName()+" for "+this.getDamage()+" HP and dies.";
			}
		}
		return "";
	}
}