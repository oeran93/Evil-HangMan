package controller;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.Test;

public class ModelTest {
	
	/**
	 * tested by using other software to see how many 24 letters word where in the dictionary 
	 * and then check our result
	 */
	@Test
	public void testInitializeCurrentSet() {
		Model ct = new Model(24);
		assertEquals(1,ct.getCurrentSet().size());  //there is just one word that is 24 letters long
		ct = new Controller(10);
		assertEquals(12308,ct.getCurrentSet().size()); // there are 12308 words with 10 letters
	}

	/**
	 * prints out all the sets and the form of words they are expected to contain
	 */
	@Test
	public void testCreatePossibleSets() {
		Model ct = new Model(5);
		ct.createPossibleSets('z');
		HashMap<String,HashSet<String>> map= ct.getPossibleSets();
		for(String key: map.keySet()){
			HashSet<String> set =map.get(key);
			System.out.println("words of the form "+key);
			int counter=0;
			for(String word:set){
				System.out.println(word);
				counter++;
			}
			System.out.println("FOUND "+counter+" +++++++++++++++++++++++++++++++++++++++");
		}
	}
	
	
	@Test
	public void testFindLetterInWord(){
		Model ct = new Model(5);
		StringBuilder sb = new StringBuilder();
		sb.append("_____");
		assertEquals("__ll_",ct.findLetterInWord("callo", 'l', sb));
	}
	
	/**
	 * the number 8340 was found using testCreatePossibleSets. this method prints out the number of words in a set.
	 * I just checked which one had the most words. This should be the one chosen.
	 */
	@Test
	public void testChooseCurrentSet(){
		Model ct = new Model(5);
		ct.createPossibleSets('z');
		ct.chooseCurrentSet();
		assertEquals(8340,ct.getCurrentSet().size());
	}

}
