package main;

/*
 * @author Thomas Rahn
 * 
 * This class handles all the values for a particular word.
 * 
 */
public class Word {

	private String word;
	private int spamFrequency;
	private int hamFrequency;
	private static double SMOOTHING_FACTOR = 0.5;

	public Word(String word) {
		this(word, 0, 0);
	}

	public Word(String word, int spamFrequency, int hamFrequency) {
		this.word = word;
		this.spamFrequency = spamFrequency;
		this.hamFrequency = hamFrequency;
	}

	/*
	 * Add one to the value of spam for this particular word
	 */
	public void addSpam() {
		spamFrequency++;
	}

	/*
	 * Add one to the value of ham for this particular word
	 */
	public void addHam() {
		hamFrequency++;
	}

	public void setSpamFrequency(int spamFrequency) {
		this.spamFrequency = spamFrequency;
	}

	public void setHamFrequency(int hamFrequency) {
		this.hamFrequency = hamFrequency;
	}

	public int getSpamFrequency() {
		return spamFrequency;
	}

	public int getHamFrequency() {
		return hamFrequency;
	}

	public String getWord() {
		return word;
	}

	/*
	 * @return the frequency of this word in ham emails with its smoothing
	 * factor
	 */
	public double getHamSmooth() {
		return hamFrequency + SMOOTHING_FACTOR;
	}

	/*
	 * @return the frequency of this word in spam emails with its smoothing
	 * factor
	 */
	public double getSpamSmooth() {
		return spamFrequency + SMOOTHING_FACTOR;
	}
}
