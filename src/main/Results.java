package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import main.Filesystem.DatasetFile;
import main.Filesystem.Directory;


/**
 * @author Thomas Rahn
 */
public class Results {
	private static String DELIMETER = "   ";

	private static String ANALYSIS_FILE_PATH = "words/analysis.txt";
	
	private final static String HAM = "Ham";
	private final static String SPAM = "Spam";
	
	private static int totalSpam = 0;
	private static int totalHam = 0;
	
	private static double probHam = 0;
	private static double probSpam = 0;
	
	private static double totalSpamSmooth;
	private static double totalHamSmooth;
	
	private static List<Word> words;
	private static int counter = 0;
	
	private static Map<String,String> analysis;
	
	/**
	 * This method will write the results of each file that needs to be classified.
	 * 
	 * @param outputPath this contains the path to where the file should be saved.
	 * @param classifyDirectory All the files that need to be classified
	 * @param words All the words in the original files.
	 * 
	 */
	public static void SaveResults(String outputPath, String classifyDirectory, List<Word> words){
		LocalTime processStartTime = LocalTime.now();
		analysis = new HashMap<String,String>();
		
		Results.words = words;
		try{
			StringBuffer output = generateResultsString(classifyDirectory);
			
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
			bw.write(output.toString());
			bw.close();
			System.out.println("Done.");
		}catch(Exception e){
			System.out.println("Error: " + e.getMessage());
		}
		
		LocalTime processEndTime = LocalTime.now();
		Duration processDuration = Duration.between(processStartTime, processEndTime);
		
		System.out.println("Results Output Duration: "+processDuration.getSeconds()+" sec.");
		
		CreateAnalysisFile();
	}

	/**
	 * @return StringBuffer that containers the results to be saved to the Results file.
	 * @throws Exception If the directory is not readable
	 * 
	 */
	private static StringBuffer generateResultsString(String classifyDirectory) throws Exception{
		StringBuffer buffer = new StringBuffer();
		counter = 1;
		//Get the spam files that need to be classified
		Directory dirSpam = new Directory(classifyDirectory + "/" + "spam");
		
		//Smoothed total
		totalSpamSmooth = (totalSpam + (0.5 * words.size()));
		totalHamSmooth =  (totalHam + (0.5 * words.size()));
		
		File[] spamFiles = dirSpam.getFiles();
		
		buffer = classifyDirectory(buffer, spamFiles, SPAM);
		
		Directory dirHam = new Directory(classifyDirectory + "/" + "ham");
		
		File[] hamFiles = dirHam.getFiles();
		buffer = classifyDirectory(buffer, hamFiles, HAM);
		
		return buffer;
	}

	private static StringBuffer classifyDirectory(StringBuffer buffer, File[] files, String correct) throws Exception, IOException {
		for(int i = 0; i < files.length; i++){
			DatasetFile dataset = new DatasetFile(files[i]);
		
			//get the words from the file
			Map<String,Integer> wordMap = dataset.getWords();
			Iterator<String> it = wordMap.keySet().iterator();
			
			//Initial scores
			double hamScore = probHam;
			double spamScore = probSpam;
			
			//Loop through all words
			while(it.hasNext()){
				String word = it.next();
				
				//get the word instance from the list
				Word wordInstance = getWordInstance(words, word);
				
				if(wordInstance != null) {
					double hamTemp = wordInstance.getHamSmooth() / totalHamSmooth;
					double spamTemp = wordInstance.getSpamSmooth() / totalSpamSmooth;
					hamScore += Math.log(hamTemp);
					spamScore += Math.log(spamTemp);
				}
			}
			
			//Determine if it is ham or spam
			String classifier = "";
			
			
			if (hamScore > spamScore){
				classifier = HAM;
				analysis.put(files[i].getName(), HAM + ";" + correct); 
			} else {
				classifier = SPAM;
				analysis.put(files[i].getName(), SPAM + ";" + correct); 
			}

			
			String output = counter + DELIMETER 
					+ files[i].getName() + DELIMETER 
					+ classifier + DELIMETER 
					+ hamScore + DELIMETER 
					+ spamScore + String.format("%n");;  
			
			//append line to buffer
			buffer.append(output);
			counter++;
		}
		return buffer;
	}
	/**
	 * This method will return the word that is in the list of words or return null
	 * @param words List of word objects
	 * @param word a word to be found in the list
	 * @return null or a Word instance
	 */
	private static Word getWordInstance(List<Word> words, String word){
		//find the word in the list otherwise return null
		for(int i = 0; i< words.size(); i++){
			if(word.equals(words.get(i).getWord())){
				return words.get(i);
			}
		}
		return null;
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
	 * Set the number of Spam and Ham files to calculate the probability of spam or ham
	 * 
	 * @param spam total number of spam words
	 * @param ham total number of ham words
	 */
	public static void SetProbabilities(int spam, int ham){
		if(spam > 0 && ham > 0){
			probHam = Math.log10((ham*1.0) / (ham+spam));
			probSpam = Math.log10((spam*1.0)/ (ham+spam));
		}
	}
	

	private static void CreateAnalysisFile(){
		LocalTime processStartTime = LocalTime.now();
		try{
			StringBuffer output = new StringBuffer();
			
			
			//Iterate through all the files
			Iterator<String> it = analysis.keySet().iterator();
			int correct = 0;
			int iterator = 0;
			
			int hamCor = 0;
			int hamIn = 0;
			int spamCor = 0;
			int spamIn = 0;
			while(it.hasNext()){
				//get the filename
				String filename = it.next();
				
				
				String[] response = analysis.get(filename).split(";");
				
				//Get determined and actual values.
				String determinedAnswer = response[0];
				String actual = response[1];
				
				boolean isCorrect = determinedAnswer.equals(actual);
				String line = iterator + DELIMETER 
						+ filename + DELIMETER 
						+ determinedAnswer + DELIMETER
						+ actual + DELIMETER
						+ isCorrect + String.format("%n");
				
				if(actual.equals(HAM)){
					if(isCorrect){
						hamCor++;
					}else{
						hamIn++;
					}
				}
				if(actual.equals(SPAM)){
					if(isCorrect){
						spamCor++;
					}else{
						spamIn++;
					}
				}
				if(isCorrect){
					correct++;
				}
				output.append(line);
				iterator++;
			}
			
			//last 3 lines of output file as specified by the deliverable 2 specifications
			output.append("Correct Classifications: " + correct + String.format("%n"));
			double accuracy = (correct/ (iterator * 1.0)) * 100;
			output.append("Correct Accuracy: " + accuracy + String.format("%n"));
			
			//Confusion matrix here.
			String matrix = "\t\t Spam \t Ham \n";
			matrix += "Spam \t" + spamCor + "\t\t" + spamIn + "\n";
			matrix += "Ham \t" +hamIn + "\t\t" + hamCor + "\n";
			System.out.println(matrix);
			
			output.append(matrix);
			
			File outputFile = new File(ANALYSIS_FILE_PATH);
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
			bw.write(output.toString());
			bw.close();
			System.out.println("Done.");
		}catch(Exception e){
			System.out.println("Error: " + e.getMessage());
		}
		
		LocalTime processEndTime = LocalTime.now();
		Duration processDuration = Duration.between(processStartTime, processEndTime);
		
		System.out.println("Analysis Output Duration: "+processDuration.getSeconds()+" sec.");
	}
	
}
