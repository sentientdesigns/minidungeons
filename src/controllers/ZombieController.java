package controllers;

import dungeon.play.GameCharacter;
import dungeon.play.PlayMap;

import java.util.Vector;

import util.math2d.Point2D;

import util.statics.RandomNumberManager;

/**
 * The Zombie controller picks a random direction in every step, 
 * with no regard as to whether it will bump into a wall in that direction.
 * Therefore, the Zombie controller must takes many more actions than 
 * the Random controller to cover the same distance.
 */
public class ZombieController extends Controller {
	int prevAction = PlayMap.IDLE;
	int currAction = PlayMap.IDLE;
	
	public ZombieController(PlayMap map, GameCharacter controllingChar){
		super(map,controllingChar,"ZombieController");
	}
	public ZombieController(PlayMap map, GameCharacter controllingChar, String label){
		super(map,controllingChar,label);
	}
	
	public int getNextAction(){
		currAction = getRandomAction();
		return currAction;
	}
	
	public int getRandomAction(){
		return getRandomValidMove();
	}
	
	public int getRandomValidMove(){
        Vector<Integer> validMoves = generateValidMoves();
        return validMoves.get(RandomNumberManager.getRandomInt(0, validMoves.size()));
    }
	
	public Vector<Integer> generateValidMoves(){
        Vector<Integer> result = new Vector<Integer>();
        for(int i = 0; i < 4; i++){
            Point2D newMove = map.getHero().getNextPosition(i);
            if(map.isValidMove(newMove)){
                result.add(i);
			}
        }
		return result;
    }
}