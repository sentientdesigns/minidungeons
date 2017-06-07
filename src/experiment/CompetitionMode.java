package experiment;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import controllers.*;

import dungeon.Dungeon;
import dungeon.DungeonLoader;
import dungeon.play.GameCharacter;
import dungeon.play.PlayMap;

import dungeon.visualization.PlayVisualizer;

import util.math2d.Matrix2D;
import util.statics.StatisticUtils;

public class CompetitionMode {
	final int totalRuns = 10;
	final int maxActions = 300;
	
	String outputFolder = "./testResults/";	// this folder needs to already exist, it will not be created by the program
	
	String[] controllerNames = {
		"controllers.RandomController",
		"controllers.ZombieController",
		"controllers.RoombaController",
		"controllers.PathfindingController",
	};

	double[][] hpRemaining;
	double[][] monstersKilled;
	double[][] treasuresCollected;
	double[][] potionsDrunk;
	double[][] actionsTaken;
	double[][] tilesExplored;
	
	public void runCompetition(String filename){
		if(controllerNames==null || controllerNames.length==0){ 
			System.out.println("Controllers' class names not provided before running the competition");
		}
		
		String[] temp = filename.split("/");
		String mapFile = temp[temp.length-1];
		
		initMetrics(controllerNames.length);
		
		String asciiMap = "";
		try { 
			asciiMap = new Scanner(new File(filename)).useDelimiter("\\A").next(); 
		} catch(Exception e){
			System.out.println(e.toString());
		}
		Dungeon testDungeon = DungeonLoader.loadAsciiDungeon(asciiMap);
		PlayMap testPlay = new PlayMap(testDungeon);
		for(int r=0;r<totalRuns;r++){
			testPlay.startGame();	// randomizes monster damage
			
			PlayMap[] testInstances = new PlayMap[controllerNames.length];
			Controller[] testAgents = new Controller[controllerNames.length];
			for(int c=0;c<controllerNames.length;c++){
				testInstances[c] = testPlay.clone();
				try { 
					testAgents[c] = (Controller)(Class.forName(controllerNames[c]).getConstructor(PlayMap.class, GameCharacter.class).newInstance(testInstances[c],testInstances[c].getHero()));
				} catch (Exception e){ 
					System.out.println(e.toString()); 
				}
			}
			
			for(int c=0;c<controllerNames.length;c++){
				int actions = 0;
				while(!testInstances[c].isGameHalted() && actions<maxActions){
					testInstances[c].updateGame(testAgents[c].getNextAction());
					actions++;
				}
				updateMetrics(c, r, testInstances[c], actions);
			}
		}
		System.out.println(printFullCompetitionMetrics(maxActions));
		
		try { 
			writeFile(outputFolder+"/competitionReport_of_"+mapFile.replace("txt","csv"), new String[]{ printFullCompetitionMetrics(maxActions), printFullAnalysis() });
		} catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	protected void initMetrics(int controllers){
		initMetrics(totalRuns, controllers);
	}
	
	protected void initMetrics(int runs, int controllers){
		hpRemaining = new double[controllers][runs];
		monstersKilled = new double[controllers][runs];
		treasuresCollected = new double[controllers][runs];
		potionsDrunk = new double[controllers][runs];
		actionsTaken = new double[controllers][runs];
		tilesExplored = new double[controllers][runs];
	}
	
	protected void updateMetrics(int controllerIndex, int runIndex, PlayMap finishedMap, int actions){
		hpRemaining[controllerIndex][runIndex] = finishedMap.getHero().getHitpoints();
		monstersKilled[controllerIndex][runIndex] = Matrix2D.count(finishedMap.getDeadMonsterArray());
		treasuresCollected[controllerIndex][runIndex] = Matrix2D.count(finishedMap.getDeadRewardArray());
		potionsDrunk[controllerIndex][runIndex] = Matrix2D.count(finishedMap.getDeadPotionArray());
		actionsTaken[controllerIndex][runIndex] = actions;
		tilesExplored[controllerIndex][runIndex] = Matrix2D.count(finishedMap.getAnyVisited());
	}
	
	protected String printFullCompetitionMetrics(int maxActions){
		double[] timesUncompleted = new double[controllerNames.length];
		double[] timesDied = new double[controllerNames.length];
		double[] timesCompleted = new double[controllerNames.length];
		
		for(int c=0;c<controllerNames.length;c++){
			timesUncompleted[c] = 0;
			timesDied[c] = 0;
			for(int r=0;r<hpRemaining[c].length;r++){ 
				if(hpRemaining[c][r]<=0){ timesDied[c]+=1.0; } 
				if(actionsTaken[c][r]==maxActions){ timesUncompleted[c]+=1.0; } 
				if(hpRemaining[c][r]>0 && actionsTaken[c][r]<maxActions){ timesCompleted[c]+=1.0; }
			}
		}
		String result = "";
		result += printCompetitionMetric(hpRemaining,"hpRemaining");
		result += "\n" + printCompetitionMetric(monstersKilled,"monstersKilled");
		result += "\n" + printCompetitionMetric(treasuresCollected,"treasuresCollected");
		result += "\n" + printCompetitionMetric(potionsDrunk,"potionsDrunk");
		result += "\n" + printCompetitionMetric(actionsTaken,"actionsTaken");
		result += "\n" + printCompetitionMetric(tilesExplored,"tilesExplored");
		result += "\n" + "---------------------------------------";
		
		result += "\n"+"timesCompleted";
		for(int c=0;c<controllerNames.length;c++){
			result+="\n"+controllerNames[c]+";"+timesCompleted[c];
		}
		result += "\n"+"timesDied";
		for(int c=0;c<controllerNames.length;c++){
			result+="\n"+controllerNames[c]+";"+timesDied[c];
		}
		result += "\n"+"timesUncompleted";
		for(int c=0;c<controllerNames.length;c++){
			result+="\n"+controllerNames[c]+";"+timesUncompleted[c];
		}
		return result;
	}
	
	protected String printCompetitionMetric(double[][] variable, String variableName){
		double[] average = new double[controllerNames.length];
		double[] stdev = new double[controllerNames.length];
		for(int c=0;c<controllerNames.length;c++){
			average[c] = StatisticUtils.average(variable[c]);
			stdev[c] = StatisticUtils.standardDeviation(variable[c]);
		}
		String result = variableName+"";
		
		double max = StatisticUtils.maximum(average);
		double min = StatisticUtils.minimum(average);
		for(int c=0;c<controllerNames.length;c++){ 
			if(max==average[c]){ result+="\nBEST;"+controllerNames[c]+";"+max; } 
		}
		for(int c=0;c<controllerNames.length;c++){ 
			if(min==average[c]){ result+="\nWORST;"+controllerNames[c]+";"+min; } 
		}	
		return result;
	}
	
	protected String printFullAnalysis(){
		String result = "---------------------------------------";
		result += "\n" + printAnalysis(hpRemaining,"hpRemaining");
		result += "\n" + printAnalysis(monstersKilled,"monstersKilled");
		result += "\n" + printAnalysis(treasuresCollected,"treasuresCollected");
		result += "\n" + printAnalysis(potionsDrunk,"potionsDrunk");
		result += "\n" + printAnalysis(actionsTaken,"actionsTaken");
		result += "\n" + printAnalysis(tilesExplored,"tilesExplored");
		result += "\n" + "---------------------------------------" + "\n";
		return result;
	}
	
	protected String printAnalysis(double[][] variable, String variableName){
		String result =  variableName;
		for(int c=0;c<controllerNames.length;c++){ 
			result+="\n"+controllerNames[c];
			for(int r=0;r<variable[c].length;r++){ 
				result+=";"+variable[c][r];
			}
		}
		return result;
	}
	
	public void setOutputFolder(String outputFolder){ this.outputFolder = outputFolder; }
	public void setClassNames(String[] classNames){ this.controllerNames = classNames; }
	
	public static void writeFile(String filename, String line) throws IOException{
		BufferedWriter outputWriter = null;
		outputWriter = new BufferedWriter(new FileWriter(filename));
		outputWriter.write(line);
		outputWriter.flush();  
		outputWriter.close();  
	}
	
	public static void writeFile(String filename, String[] lines) throws IOException{
		BufferedWriter outputWriter = null;
		outputWriter = new BufferedWriter(new FileWriter(filename));
		for (int i = 0; i < lines.length; i++) {
			outputWriter.write(lines[i]);
			outputWriter.newLine();
		}
		outputWriter.flush();  
		outputWriter.close();  
	}
	
	public static void main(String[] args) {
		CompetitionMode exp = new CompetitionMode();
		for(int i=0;i<=10;i++){
			System.out.println("\n--------------\nMAP"+i+"\n--------------\n");
			exp.runCompetition("./dungeons/map"+i+".txt");
		}
	}
}
