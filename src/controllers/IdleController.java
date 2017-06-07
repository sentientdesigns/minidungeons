package controllers;

import dungeon.play.GameCharacter;
import dungeon.play.PlayMap;

public class IdleController extends Controller {
	public IdleController(PlayMap map, GameCharacter controllingChar){
		super(map,controllingChar,"IdleController");
	}
	public IdleController(PlayMap map, GameCharacter controllingChar, String label){
		super(map,controllingChar,label);
	}
	
	public int getNextAction(){
		return PlayMap.IDLE;
	}
}
