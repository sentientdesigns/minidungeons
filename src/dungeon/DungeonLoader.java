package dungeon;

import java.util.Vector;
import util.math2d.Point2D;

import dungeon.Dungeon;

public class DungeonLoader {
	public static Dungeon loadAsciiDungeon(String asciimap){
		String[] lines = asciimap.split("\n");
		Dungeon result = new Dungeon(lines[0].length(),lines.length-1);
		
		for(int y=0;y<lines.length-1;y++){
			for(int x=0;x<lines[y].length();x++){
				char tile = lines[y].charAt(x);
				if(tile=='#'){
					result.setImpassable(x, y);
				}
				if(tile=='m'){
					result.addMonster(x, y);
				}
				if(tile=='r'){
					result.addReward(x, y);
				}
				if(tile=='p'){
					result.addPotion(x, y);
				}
				if(tile=='E'){
					result.addExit(x, y);
				}
			}
		}
		// NEED TO REITERATE BECAUSE EXIT MUST ALWAYS BE SECOND IN ORDER
		for(int y=0;y<lines.length-1;y++){
			for(int x=0;x<lines[y].length();x++){
				char tile = lines[y].charAt(x);
				if(tile=='X'){
					result.addExit(x, y);
				}
			}
		}		
		return result;
	}
	
	public Dungeon randomizeDungeon(){
		Dungeon tm;
		do{
			tm = new Dungeon(12,12);
			tm.randomizeChunks(25);
			tm.randomize(2, 9, 6, 4);
			tm.finalizeSketch();
		} while(tm.getPaths().getDisconnectedPaths()>0 || tm.getExitLength()<2);
		return tm;
	}
}
