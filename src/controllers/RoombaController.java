package controllers;

import dungeon.play.GameCharacter;
import dungeon.play.PlayMap;
import util.statics.RandomNumberManager;

import java.util.Vector;

/**
 * The Roomba controller will pick a random direction and keep following the same
 * direction until it hits a wall, at which point it will pick another random
 * direction (including the possibility of backtracking). The name is used here,
 * under fair use, to refer to the autonomous vacuum cleaner by iRobot only due 
 * to the similar patterns of behavior. No copyright infringement is intended.
 */
public class RoombaController extends Controller {
	int prevAction = PlayMap.IDLE;
	int currAction = PlayMap.IDLE;
	
	public RoombaController(PlayMap map, GameCharacter controllingChar){
		super(map,controllingChar,"RumbaController");
	}
	public RoombaController(PlayMap map, GameCharacter controllingChar, String label){
		super(map,controllingChar,label);
	}
	
	public void reset(){
		prevAction = PlayMap.IDLE;
		currAction = PlayMap.IDLE;
	}
	
	public int getNextAction(){
		prevAction = currAction;
		if(prevAction == PlayMap.IDLE) { prevAction = getRandomAction(); }
		if(map.isValidMove(controllingChar.getNextPosition(prevAction))){
			currAction = prevAction;
		} else {
			currAction = getRandomAction();
		}
		return currAction;
	}
	
	public int getRandomAction(){
		Vector<Integer> possibleDirs = new Vector<Integer>();
		for(int i=0;i<4;i++){
			if(map.isValidMove(controllingChar.getNextPosition(i))){ 
				possibleDirs.add(i); 
			}
		}
		int result = PlayMap.IDLE;
		if(!possibleDirs.isEmpty()){ 
			int roll = RandomNumberManager.getRandomInt(0,possibleDirs.size()); 
			result = possibleDirs.get(roll);
		}
		return result;
	}
}
