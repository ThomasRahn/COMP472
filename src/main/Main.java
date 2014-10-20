package main;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		System.out.println("hi");
		//Read all files in ham
		
		//Read all files in spam
		
		//write to file
		WordUtilities.SetTotals(12,6);
		Word w = new Word("Test",5,5);
		Word w2 = new Word("Test2", 7,1);
		List<Word> words = new ArrayList<Word>();
		words.add(w);
		words.add(w2);
		
		//Saves all the words into a text file.
		WordUtilities.SaveWords(words);
	}
}
