package main;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		System.out.println("hi");
		//Read all files in ham
		
		//Read all files in spam
		
		//write to file
		WordUtilities.totalHam = 10;
		WordUtilities.totalSpam = 5;
		Word w = new Word("Test",3,4);
		Word w2 = new Word("Test2", 7,1);
		List<Word> words = new ArrayList<Word>();
		words.add(w);
		words.add(w2);
		
		//Saves all the words into a text file.
		WordUtilities.SaveWords(words);
	}
}
