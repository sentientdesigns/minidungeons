package controllers;

import dungeon.play.GameCharacter;
import dungeon.play.PlayMap;

import java.util.Vector;

import util.math2d.Point2D;

import util.statics.RandomNumberManager;

public class PathfindingController extends Controller {
	public PathfindingController(PlayMap map, GameCharacter controllingChar){
		super(map,controllingChar,"ZombieController");
	}
	public PathfindingController(PlayMap map, GameCharacter controllingChar, String label){
		super(map,controllingChar,label);
	}
	
	public int getNextAction(){
		return getBestPathAction();
	}
	
	public int getBestPathAction(){
		Vector<Integer> validMoves = new Vector<Integer>();
		double[] distancesPerMove = new double[4];	// cardinal directions
		Point2D exit = map.getExit(1);				// entrance is 0, exit is 1
		for(int i = 0; i < 4; i++){
			Point2D newMove = map.getHero().getNextPosition(i);
			if(map.isValidMove(newMove)){
                validMoves.add(i);
				distancesPerMove[i] = map.getPaths().getDistance(newMove, exit);
			} else {
				distancesPerMove[i] = Double.NaN;
			}
		}
		// find closest distance
		int bestPathAction = -1;
		double closestDistance = Double.POSITIVE_INFINITY;
		for(int i=0;i<validMoves.size();i++){
			if(distancesPerMove[validMoves.get(i)]<closestDistance){ 
				closestDistance=distancesPerMove[validMoves.get(i)];
				bestPathAction = validMoves.get(i);
			}
		}
		return bestPathAction;
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
