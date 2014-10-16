package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class WordUtilities {
	
	public static int totalSpam = 0;
	public static int totalHam = 0;
	
	private static String delimeter = "   ";
	
	public static void SaveWords(List<Word> words){
		try {
			 
			String content = generateWordString(words);
			
 
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
	
	/*
	 * This method will accept a List of words and generate a string.
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
}
