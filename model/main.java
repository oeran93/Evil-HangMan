package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import controller.Model;

public class Main {
	
	public static ArrayList<String> guessedLetters= new ArrayList<String>();
	
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		String input="start";
		int lengthWord;
		while((lengthWord =isValidNumber(input))==-1){
			System.out.print("How long do you want the word to be? (the input must be an integer)");
			input = scan.next();
		}
		Model ct = new Model(lengthWord);
	}
	
	/**
	 * checks if the input is a valid integer
	 * @param input String input of the user
	 * @return -1 if int is invalid otherwise the parsed int
	 */
	public static int isValidNumber(String input){
		try{
			int test = Integer.parseInt(input);
			return test;
		}catch(Exception e){
			return -1;
		}
	}
	
	/**
	 * check if the user entered a valid char that corresponds to a letter in the alphabet
	 * @param input String the user input
	 * @return String if the car is valid it returns a success string otherwise an error message
	 */
	public static String isValidChar(String input){
		String possibleChars="abcdefghijklmnopqrstuvwxyz";
		if(input.length()>1 || input.length()==0){
			return "Input must be a single character: ";
		}if(!possibleChars.contains(input)){
			return "Input must be a letter from a to z: ";
		}if(guessedLetters.contains(input)){
			return "You have already guessed this letter: ";
		}
		return "success";
	}
	
	/**
	 * gets a new input from the user
	 * @return String the correct input
	 */
	public static String newInput(){
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter a letter: ");
		String input = scan.next();
		String error;
		while((error=isValidChar(input))!="success"){
			System.out.print(error);
			input = scan.next();
		}
		guessedLetters.add(input);
		return input;
	}
	
	/**
	 * prints out the current game status
	 * @param word String current word
	 * @param numTries int number of letters attempted
	 * @param maxTries int max number of attempts
	 * @param lastResult String information on the good or bad result of the last attempt
	 */
	public static void giveStats(String word, int numTries, int maxTries,String lastResult){
		System.out.println("The word is: "+word);
		if(guessedLetters.size()>0){
			System.out.print("You have guessed:");
			for(String letter:guessedLetters){
				System.out.print(" "+letter);
			}
			System.out.println();
		}
		System.out.println(lastResult+". You have "+(maxTries-numTries)+" attempts left.");
		
	}
	
	/**
	 * seems redundant but if the program had to implement a graphical interface this would draw on the window
	 * @param say
	 */
	public static void printToScreen(String say){
		System.out.println(say);
	}
}
