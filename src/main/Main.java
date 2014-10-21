package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import main.Filesystem.DatasetFile;
import main.Filesystem.Directory;

public class Main {

	public static void main(String[] args) {
		System.out.println("hi");
		//Read all files in ham
		try {
			Directory spamDirectory = new Directory("words/spam_2");
			File[] files = spamDirectory.getFiles();
		
			DatasetFile dataset = new DatasetFile(files[0]);
			dataset.getWords();
				
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
		
		
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
