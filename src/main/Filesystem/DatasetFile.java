package main.Filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alan Ly on 16-Oct-2014.
 * 			  Thomas Rahn
 */
public class DatasetFile {
	private File file;
	private int numberOfWords = 0;
	private Map<String, Integer> wordsAndCounts;

	/**
	 * @param file
	 *            A `java.io.File` instance.
	 * @throws Exception
	 *             Thrown when the specified instance does not reference an
	 *             existing file.
	 */
	public DatasetFile(File file) throws Exception {
		this.setFileInstance(file);
	}

	/**
	 * @param file
	 *            A `java.io.File` instance.
	 * @param Map
	 *            the words and the frequency in the file.
	 * @throws Exception
	 *             Thrown when the specified instance does not reference an
	 *             existing file.
	 */
	public DatasetFile(File file, Map<String, Integer> words) throws Exception {
		this.setFileInstance(file);
		this.setMap(words);
	}

	public Path getPath() {
		return Paths.get(this.file.getAbsolutePath());
	}

	public Map<String, Integer> getWords() throws IOException {
		String content = new String(Files.readAllBytes(getPath()));

		// remove punctuation
		content = content.replaceAll("[\\[\\]\".,;:\\(\\)=+]", "");

		// remove all words with a number in it.
		content = content.replaceAll("([a-z]*\\d+[a-z]*)", "");

		// remove all words with 3 characters or less.
		content = content.replaceAll("\\b\\w{1,3}\\b", "");

		String[] words = content.split(" ");
		for (int i = 0; i < words.length; i++) {
			String word = words[i].trim().toLowerCase();

			// if the word does not exist in the map, the count is 0
			int count = this.wordsAndCounts.get(word) == null ? 0 : this.wordsAndCounts.get(word);

			// increase the number of total words
			numberOfWords++;

			this.wordsAndCounts.put(word, count + 1);
		}

		return this.wordsAndCounts;
	}

	/**
	 * Load the specified File instance.
	 *
	 * @param file
	 *            An instance of `java.io.File`.
	 * @throws Exception
	 *             thrown when the File instance does not reference an existing
	 *             file.
	 */
	private void setFileInstance(File file) throws Exception {
		if (!file.isFile()) {
			throw new Exception(
					"The provided file instance does not reference an existing file.");
		}

		this.file = file;
	}

	/**
	 * @param Map
	 *            the map that holds the word with its frequency in the file.
	 */
	private void setMap(Map<String, Integer> words) {
		if (words == null) {
			wordsAndCounts = new HashMap<String, Integer>();
		} else {
			this.wordsAndCounts = words;
		}
	}

	/**
	 * @return returns the total number of words for this particular file.
	 */
	public int GetNumberOfWords() {
		return this.numberOfWords;
	}
}
