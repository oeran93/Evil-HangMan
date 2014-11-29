package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import model.Main;

public class Model {
	
	private final int MAXTRIES=20;
	private final String DICTIONARY="dictionary.txt";
	
	private boolean win=false;
	private int numTries=0;
	private StringBuilder displayedWord;
	private HashSet<String> currentSet;
	private String lastResult="";
	private HashMap<String,HashSet<String>> possibleSets;
	
	/**
	 * sets the word length and creates the initial empty word
	 * @param wordLength int length of the word to guess
	 */
	public Model(int wordLength){
		displayedWord = new StringBuilder();
		for(int i=0; i<wordLength;i++){ //creates an empty word of the determined size
			displayedWord.append("_");
		}
		initializeCurrentSet(wordLength);
		gameRoutine();
	}
	
	/**
	 * starts the game routine
	 */
	public void gameRoutine(){
		while(!win && numTries<MAXTRIES){
			String input = Main.newInput();
			createPossibleSets(input.charAt(0));
			numTries++;
			chooseCurrentSet();
			if(getCurrentSet().size()==1){
				win=true;
			}
			Main.giveStats(displayedWord.toString(), numTries, MAXTRIES,lastResult); //need a last happened field
		}
		if(win){
			Main.printToScreen("You won! the word was: "+displayedWord.toString());
			System.exit(0);
		}
		Iterator it = currentSet.iterator();
		Main.printToScreen("Sorry too many tries...it was sooo easy! the word was " + it.next());
	}
	
	/**
	 *initializes the current set to all the words that match the given size
	 *words will be taken from a "dictionary" file.
	 *@param wordLength int the length of the words
	 */
	public void initializeCurrentSet(int wordLength){
		currentSet = new HashSet<String>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(DICTIONARY));
			String line;
			while ((line = br.readLine()) != null) {
				if(line.length()==wordLength){
					currentSet.add(line);
				}
			}
			br.close();
		}catch(Exception e){
			System.out.println("Unable to find a dictionary.");
		}
		if (currentSet.size()==0){
			Main.printToScreen("There are no words that match the chosen size");
			System.exit(0);
		}
	}
	
	/**
	 * creates a HashMap containing all the possible sets given a letter
	 * @param letter String letter to be searched in the dictionary
	 */
	public void createPossibleSets(char letter){
		possibleSets = new HashMap<String,HashSet<String>>();
		for(String word : currentSet){
			StringBuilder sb = new StringBuilder();
			sb.append(displayedWord.toString());
			String key=findLetterInWord(word,letter,sb);
			if(key.equals(displayedWord.toString())){
				lastResult=" you are one step closer to dying.";
			}else{
				lastResult=letter+" was found in the word";
			}
			if (possibleSets.get(key)==null){
				HashSet<String> newSet = new HashSet<String>();
				newSet.add(word);
				possibleSets.put(key,newSet);
			}else{
				possibleSets.get(key).add(word);
			}
		}
	}
	
	/**
	 * recursively finds all the occurrences of a given letter in a given word
	 * @param word String the word that could contain the given letter
	 * @param letter String the letter that could be contained in the given word
	 * @param displayedWord String the word that will be displayed if this word fits in the currentSet
	 * @return String the displayedWord after finding all the occurrences of the given letter
	 */
	public String findLetterInWord(String word,char letter,StringBuilder displayedWord){
		int index=word.indexOf(letter);
		if(index==-1){
			return displayedWord.toString();
		}
		displayedWord.setCharAt(index, letter);
		word=word.substring(0,index)+"^"+word.substring(index+1);
		return findLetterInWord(word,letter,displayedWord);
	}
	
	/**
	 * chooses the largest out of the possible sets
	 */
	public void chooseCurrentSet(){
		HashSet<String> largest = new HashSet<String>();
		for(String key: possibleSets.keySet()){
			HashSet<String> set= possibleSets.get(key);
			if (set.size()>largest.size()){
				largest = set;
				setDisplayedWord(key);
			}
		}
		currentSet=largest;
	}
	
	/**
	 * @return the currentWord
	 */
	public String getDisplayedWord() {
		return displayedWord.toString();
	}

	/**
	 * @param currentWord the currentWord to set
	 */
	public void setDisplayedWord(String currentWord) {
		this.displayedWord.replace(0, displayedWord.length(),currentWord);
	}

	/**
	 * @return the currentSet
	 */
	public HashSet<String> getCurrentSet() {
		return currentSet;
	}

	/**
	 * @param currentSet the currentSet to set
	 */
	public void setCurrentSet(HashSet<String> currentSet) {
		this.currentSet = currentSet;
	}

	/**
	 * @return the possibleSets
	 */
	public HashMap<String, HashSet<String>> getPossibleSets() {
		return possibleSets;
	}

	/**
	 * @param possibleSets the possibleSets to set
	 */
	public void setPossibleSets(HashMap<String, HashSet<String>> possibleSets) {
		this.possibleSets = possibleSets;
	}

	
	
}
