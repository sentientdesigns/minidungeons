package dungeon.visualization;

import dungeon.Dungeon;
import dungeon.play.PlayMap;

import util.math2d.Matrix2D;

/*
if(tile=='#') result.setImpassable(x, y); 
if(tile=='m') result.addMonster(x, y);
if(tile=='r') result.addReward(x, y);
if(tile=='p') result.addPotion(x, y);
if(tile=='E') result.addExit(x, y);
*/


public class PlayVisualizer {
	public static String renderFinalDungeon(PlayMap map){
		String output = "";
		for(int y=0;y<map.getMapSizeY();y++){
			for(int x=0;x<map.getMapSizeX();x++){
				// walls or floor
				if(!map.isPassable(x, y)){ 
					output+="#"; 
				} else if(map.isEntrance(x, y)){ 
					output+="E";
				} else if(map.isExit(x, y)){ 
					output+="X";
				} else if(map.isReward(x, y)){ 
					int index = map.getRewardIndex(x, y);
					output+= map.getRewardChar(index).isAlive() ? "r" : ".";
				} else if(map.isPotion(x, y)){ 
					int index = map.getPotionIndex(x, y);
					output+= map.getPotionChar(index).isAlive() ? "p" : ".";
				} else if(map.isMonster(x, y)){ 
					int index = map.getMonsterIndex(x, y);
					output+= map.getMonsterChar(index).isAlive() ? "e" : ".";
				} else {
					output+=".";
				}	
			}
			output+="\n";
		}
		return output;
	}
	
	public static String renderHeatmapDungeon(PlayMap map){
		boolean[][] visited = map.getAnyVisited();
		String output = "";
		for(int y=0;y<map.getMapSizeY();y++){
			for(int x=0;x<map.getMapSizeX();x++){
				// walls or floor
				if(visited[x][y]){ 
					output+="%"; 
				} else if(!map.isPassable(x, y)){ 
					output+="#"; 
				} else if(map.isEntrance(x, y)){ 
					output+="E";
				} else if(map.isExit(x, y)){ 
					output+="X";
				} else if(map.isReward(x, y)){ 
					int index = map.getRewardIndex(x, y);
					output+= map.getRewardChar(index).isAlive() ? "r" : ".";
				} else if(map.isPotion(x, y)){ 
					int index = map.getPotionIndex(x, y);
					output+= map.getPotionChar(index).isAlive() ? "p" : ".";
				} else if(map.isMonster(x, y)){ 
					int index = map.getMonsterIndex(x, y);
					output+= map.getMonsterChar(index).isAlive() ? "e" : ".";
				} else {
					output+=".";
				}	
			}
			output+="\n";
		}
		return output;
	}
}
