package controllers;

import dungeon.play.GameCharacter;
import dungeon.play.PlayMap;
import util.math2d.Point2D;
import util.statics.RandomNumberManager;

import java.util.Vector;

/**
 * The Random controller picks a random direction in every step, 
 * as long as that direction is not blocked by a wall.
 * The random controller will also avoid backtracking to its last visited 
 * tile unless no other option is available.
 */
public class RandomController extends Controller {
	int prevAction = PlayMap.IDLE;
	int currAction = PlayMap.IDLE;
	
	Point2D prevPos;
	
	public RandomController(PlayMap map, GameCharacter controllingChar){
		super(map,controllingChar,"RandomController");
		updatePrev();
	}
	public RandomController(PlayMap map, GameCharacter controllingChar, String label){
		super(map,controllingChar,label);
		updatePrev();
	}
	
	public void reset(){ 
		prevAction = PlayMap.IDLE;
		currAction = PlayMap.IDLE;
		prevPos = null; 
	}
	
	public int reversePrevAction(){
		int result = prevAction-2;	// backwards
		if(result<0){ result+=4; }
		return result;
	}
	
	public void updatePrev(){
		prevPos = controllingChar.getPosition();
		prevAction = currAction;
	}
	
	public int getNextAction(){
		currAction = getRandomAction();
		updatePrev();
		return currAction;
	}
	
	public int getRandomAction(){
		Vector<Integer> possibleDirs = new Vector<Integer>();
		for(int i=0;i<4;i++){
			if(!prevPos.isAt(controllingChar.getNextPosition(i)) && map.isValidMove(controllingChar.getNextPosition(i))){ 
				possibleDirs.add(i); 
			}
		}
		int result = PlayMap.IDLE;
		if(possibleDirs.isEmpty()){ 
			return reversePrevAction();	// backtracking only allowed under these conditions
		} else {
			int roll = RandomNumberManager.getRandomInt(0,possibleDirs.size()); 
			result = possibleDirs.get(roll);
		}
		return result;
	}
}
