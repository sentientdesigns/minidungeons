package experiment;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import controllers.*;

import dungeon.Dungeon;
import dungeon.DungeonLoader;
import dungeon.play.PlayMap;
import dungeon.visualization.PlayVisualizer;

import util.math2d.Matrix2D;
import util.statics.StatisticUtils;

public class DebugMode {
	final int maxActions = 300;
	
	public void runTest(String filename){
		String[] temp = filename.split("/");
		String mapFile = temp[temp.length-1];
		
		String asciiMap = "";
		try { 
			asciiMap = new Scanner(new File(filename)).useDelimiter("\\A").next(); 
		} catch(Exception e){
			System.out.println(e.toString());
		}
		Dungeon testDungeon = DungeonLoader.loadAsciiDungeon(asciiMap);
		PlayMap testPlay = new PlayMap(testDungeon);
		testPlay.startGame();

		Controller testAgent = new RoombaController(testPlay,testPlay.getHero());

		int actions = 0;

		System.out.println(testPlay.toASCII(true));
		while(!testPlay.isGameHalted() && actions<maxActions){
			testPlay.updateGame(testAgent.getNextAction());
			actions++;
			System.out.println("----- ACTION "+actions+" -----");
			System.out.println(testPlay.toASCII(true));
			//System.out.println(PlayVisualizer.renderHeatmapDungeon(testPlay));
		}
	}
	
	public static void main(String[] args) {
		DebugMode exp = new DebugMode();
		exp.runTest("./dungeons/map0.txt");
	}
}
