package main;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Filesystem.DatasetFile;
import main.Filesystem.Directory;

public class Main {

	public static void main(String[] args) {
		System.out.println("hi");
		
		int spamTotal = 0;
		Map<String, Integer> spamWords = new HashMap<String, Integer>();
		
		int hamTotal = 0;
		Map<String, Integer> hamWords = new HashMap<String, Integer>();

		try {
			// SPAM SECTION
			Directory spamDirectory = new Directory("words/spam_2");
			
			File[] spamFiles = spamDirectory.getFiles();
			
			System.out.println("Reading spam files");
			for (int i = 0; i < spamFiles.length; i++) {
				DatasetFile dataset = new DatasetFile(spamFiles[i], spamWords);
				spamWords = dataset.getWords();
				spamTotal += dataset.GetNumberOfWords();
			}

			// HAM SECTION
			Directory hamDirectory = new Directory("words/easy_ham_2");
			File[] hamFiles = hamDirectory.getFiles();
			
			System.out.println("Reading ham files");
			for (int i = 0; i < hamFiles.length; i++) {
				DatasetFile dataset = new DatasetFile(hamFiles[i], hamWords);
				hamWords = dataset.getWords();
				hamTotal += dataset.GetNumberOfWords();
			}

		}catch (Exception e){
			e.printStackTrace();
		}

		// write to file
		WordUtilities.SetTotals(spamTotal, hamTotal);
		
		//Get the list of words
		List<Word> words = WordUtilities.CreateWordList(spamWords, hamWords);
		
		// Saves all the words into a text file.
		WordUtilities.SaveWords(words);
	}
}
