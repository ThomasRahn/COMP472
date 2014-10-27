package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

/**
 * @author Thomas Rahn
 * @author Alan Ly
 */
public class WordUtilities {
	
	private static int totalSpam = 0;
	private static int totalHam = 0;
	
	private static String DELIMETER = "   ";
	
	/**
	 * This method accepts a list of words and will save them into a file with their corresponding values. (Frequency and probability)
	 * 
	 * @param words	     the list of words to be saved into a file
	 * @param outputPath the destination of the output file.
	 */
	public static void SaveWords(List<Word> words, String outputPath){
		LocalTime processStartTime = LocalTime.now();

		try {
			// Sort our incoming list of words.
			System.out.print("Sort words...");
			Collections.sort(words);
			System.out.println("Done.");

			// Generate a string listing of our words.
			String output = generateWordString(words);

			File outputFile = new File(outputPath);
			System.out.println("Using output file \"" + outputFile.getAbsolutePath() + "\".");

			// Create a non-existing file.
			if (! outputFile.exists()) {
				System.out.print("File does not exist; creating...");
				outputFile.createNewFile();
				System.out.println("Done.");
			}

			// Create the writer instances,
			FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			System.out.print("Writing output file...");
			bw.write(output);
			bw.close();
			System.out.println("Done.");

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		LocalTime processEndTime = LocalTime.now();
		Duration processDuration = Duration.between(processStartTime, processEndTime);

		System.out.println("File Output Duration: "+processDuration.getSeconds()+" sec.");
	}
	
	/**
	 * This method will accept a List of words and generate a string.
	 * 
	 * @param words A list of words to be converted to a string
	 * 
	 * @return String the string generated from a list of words
	 */
	private static String generateWordString(List<Word> words) {
		String output = "";
		int wordCount = words.size();
		int index = 0;
		int progress = 0;

		System.out.print("Generating output.");

		for (Word word : words) {
			// Determine the smoothed values.
			double spamSmooth = word.getSpamSmooth() / (totalSpam + (0.5 * words.size()));
			double hamSmooth = word.getHamSmooth() / (totalHam + (0.5 * words.size()));

			// Create our output string
			output += ++index + DELIMETER;
			output += word.getWord() + DELIMETER;
			output += word.getHamFrequency() + DELIMETER;
			output += hamSmooth + DELIMETER;
			output += word.getSpamFrequency() + DELIMETER;
			output += spamSmooth + "\n";

			double currentProgress = ((double)index / wordCount) * 100;
			if ((currentProgress - progress) >= 1.0)
			{
				System.out.print(".");
			}
			progress = (int) currentProgress;
		}

		System.out.println("Done.");

		return output;
	}
	/**
	 * Set the total number of spam words and total number of ham words
	 * 
	 * @param spam total number of spam words
	 * @param ham total number of ham words
	 */
	public static void SetTotals(int spam, int ham){
		if(spam > 0 && ham > 0){
			totalSpam = spam;
			totalHam = ham;
		}
	}
	
	
	/**
	 * This method accepts two Maps<String,Integer> and will create Word objects according to the data inside.
	 * 
	 * @param spam all the words found in the spam files and their frequency
	 * @param ham all the words found in the ham files and their frequency
	 * @return the list of words created from the two maps
	 */
	public static List<Word> CreateWordList(Map<String,Integer> spam, Map<String,Integer> ham)
	{
		List<Word> words = new ArrayList<Word>();

		Iterator<String> it = spam.keySet().iterator();
		while(it.hasNext()){
			String element = it.next();
			Word word = new Word(element);
			
			word.setSpamFrequency(spam.get(element));
			int hamFreq = ham.get(element) == null ? 0 : ham.get(element);
			word.setHamFrequency(hamFreq);
			ham.remove(element);
			
			//Add the word to the list.
			words.add(word);
		}
		
		Iterator<String> hamIterator = ham.keySet().iterator();
		while(hamIterator.hasNext()){
			String element = hamIterator.next();
			Word word = new Word(element,0,ham.get(element));
			words.add(word);
		}
		
		return words;
	}
}
