package main;

public class Word {
	private String word;
	private int spamFrequency;
	private int hamFrequency;
	
	public Word(String word){
		this(word, 0,0);
	}
	
	public Word(String word, int spamFrequency, int hamFrequency){
		this.word = word;
		this.spamFrequency = spamFrequency;
		this.hamFrequency = hamFrequency;
	}
	
	public void addSpam(){
		spamFrequency++;
	}
	
	public void addHam(){
		hamFrequency++;
	}
	
	public void setSpamFrequency(int spamFrequency){
		this.spamFrequency = spamFrequency;
	}
	
	public void setHamFrequency(int hamFrequency) {
		this.hamFrequency = hamFrequency;
	}
	
	public int getSpamFrequency(){
		return spamFrequency;
	}

	public int getHamFrequency() {
		return hamFrequency;
	}
	
	public String getWord(){
		return word;
	}
	
	public double getHamSmooth(){
		return hamFrequency + 0.5;
	}
	
	public double getSpamSmooth(){
		return spamFrequency + 0.5;
	}
}
