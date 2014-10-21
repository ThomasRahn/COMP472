package main;

import main.WordType;

public class Word {
	private String word;
	private double frequency;
	private WordType type;
	
	
	public Word(String word){
		this(word, 0.0,WordType.IGNORE);
	}
	
	public Word(String word, double frequency, WordType type){
		this.word = word;
		this.frequency = frequency;
		this.type = type;
	}
	
	
	public void setFrequency(double frequency){
		this.frequency = frequency;
	}
	
	public void setType(WordType type) {
		this.type = type;
	}
	
	public double getFrequency(){
		return frequency;
	}

	public WordType getType() {
		return type;
	}
	
	public String getWord(){
		return word;
	}
}
