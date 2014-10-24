package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WordUtilities {
	
	private static int totalSpam = 0;
	private static int totalHam = 0;
	
	private static String delimeter = "   ";
	
	/**
	 * This method accepts a list of words and will save them into a file with their corresponding values. (Frequency and probability)
	 * 
	 * @param List<Word>	List of words to be saved into a file
	 * 
	 */
	public static void SaveWords(List<Word> words){
		try {
			 
			System.out.println("starting");
			String content = generateWordString(words);
			System.out.println("ending");
 
			File file = new File("words/model.txt");
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will accept a List of words and generate a string.
	 * 
	 * @param List<Word> A list of words to be converted to a string
	 * 
	 * @return String the string generated from a list of words
	 */
	private static String generateWordString(List<Word> words){
		String content = "";
		Iterator<Word> it = words.iterator();
		int counter = 1;
		while(it.hasNext()){
			Word w = it.next();
			
			double spamSmooth = w.getSpamSmooth() / (totalSpam + (0.5 * words.size()));
			double hamSmooth = w.getHamSmooth() / (totalHam + (0.5 * words.size()));
			
			content += counter + 
					delimeter + w.getWord() + 
					delimeter + w.getHamFrequency() + 
					delimeter + hamSmooth + 
					delimeter + w.getSpamFrequency() + 
					delimeter + spamSmooth + "\n";
			
			counter++;
		}
		
		return content;
		
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
