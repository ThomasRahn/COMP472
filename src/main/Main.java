package main;

import java.io.File;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Filesystem.DatasetFile;
import main.Filesystem.Directory;

public class Main {

	private static String SPAM_DIRECTORY_PATH = "words/spam_2";
	private static String HAM_DIRECTORY_PATH  = "words/easy_ham_2";
	private static String MODEL_FILE_PATH     = "words/model.txt";

	public static void main(String[] args) {
		int spamTotal = 0;
		Map<String, Integer> spamWords = new HashMap<String, Integer>();
		
		int hamTotal = 0;
		Map<String, Integer> hamWords = new HashMap<String, Integer>();

		LocalTime processStartTime = LocalTime.now();

		try {
			/*
			 * Handle SPAM processing.
			 */
			Directory spamDirectory = new Directory(SPAM_DIRECTORY_PATH);
			File[] spamFiles = spamDirectory.getFiles();
			
			System.out.print("Reading "+spamFiles.length+" spam files...");
			for (int i = 0; i < spamFiles.length; i++) {
				DatasetFile dataset = new DatasetFile(spamFiles[i], spamWords);
				spamWords = dataset.getWords();
				spamTotal += dataset.GetNumberOfWords();
			}

			System.out.println("Done.");
			System.out.println("\t- "+spamTotal+" total spam keywords.");
			System.out.println("\t- "+spamWords.size()+" total unique spam keywords.");

			/*
			 * Handle HAM processing.
			 */
			Directory hamDirectory = new Directory(HAM_DIRECTORY_PATH);
			File[] hamFiles = hamDirectory.getFiles();
			
			System.out.print("Reading "+hamFiles.length+" ham files...");
			for (int i = 0; i < hamFiles.length; i++) {
				DatasetFile dataset = new DatasetFile(hamFiles[i], hamWords);
				hamWords = dataset.getWords();
				hamTotal += dataset.GetNumberOfWords();
			}

			System.out.println("Done.");
			System.out.println("\t- "+hamTotal+" total ham keywords.");
			System.out.println("\t- "+hamWords.size()+" total unique ham keywords.");
		}catch (Exception e){
			e.printStackTrace();
		}

		LocalTime processEndTime = LocalTime.now();

		Duration processTime = Duration.between(processStartTime, processEndTime);
		System.out.println("Process Duration: "+processTime.getSeconds()+" sec.");

		// write to file
		WordUtilities.SetTotals(spamTotal, hamTotal);

		//Get the list of words
		List<Word> words = WordUtilities.CreateWordList(spamWords, hamWords);

		// Saves all the words into a text file.
		WordUtilities.SaveWords(words, MODEL_FILE_PATH);
	}
}
