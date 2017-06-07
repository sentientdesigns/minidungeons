package controllers;

import dungeon.play.GameCharacter;
import dungeon.play.PlayMap;

public abstract class Controller {
	public PlayMap map;
	GameCharacter controllingChar;
	String label;
	
	public Controller(PlayMap map, GameCharacter controllingChar){
		this.map = map;
		this.controllingChar = controllingChar;
		this.label = "DefaultController";
		reset();
	}
	public Controller(PlayMap map, GameCharacter controllingChar, String label){
		this.map = map;
		this.controllingChar = controllingChar;
		this.label = label;
		reset();
	}
	
	public String getLabel(){ return this.label; }
	public void setLabel(String value){ this.label = value; }
	
	public abstract int getNextAction();
	
	public void reset(){}
}
