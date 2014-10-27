package main.Filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alan Ly
 * @author Thomas Rahn
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
	 * @param words
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
		// Read the entirety of our file into a single String instance.
		String content = new String(Files.readAllBytes(this.getPath()));

		/**
		 * Handle replies.
		 *
		 * When a user replies to another email, the original email is included
		 * and quoted with angle brackets '>'. This regex operation takes care
		 * of that and ensure that words aren't broken.
		 */
		content = content.replaceAll("(\\n*> )", "");

		/**
		 * Filters out invalid characters.
		 *
		 * Find,
		 * - Characters that are **not** (alphanumerical, spaces, or apostrophes)
		 * - Words with numbers within them.
		 * - Words less than 3 characters in length.
		 * and replace with a single space character.
		 */
		content = content.replaceAll("([^a-zA-Z0-9 '])|(\\w*\\d\\w*)|(\\b\\w{1,3}\\b)", " ");

		/**
		 * Final filtering.
		 *
		 * This regex operation filters out any remaining issues with apostrophes.
		 */
		content = content.replaceAll("(\\s'+)|('+(?=\\s))|('{2,})", " ");

		/**
		 * Split out words in string by whitespace runs.
		 */
		String[] words = content.split("\\s+");

		/**
		 * Iterate over our `words` array and update our counters.
		 */
		for (String word : words) {
			if (word.isEmpty()) continue;

			// Normalize the string: make it lowercase
			word = word.toLowerCase();

			// Get the current count of this word; if it's new then 1.
			Integer currentCount = this.wordsAndCounts.get(word);
			int updatedCount = (currentCount == null) ? 1 : currentCount + 1;

			// Update our counters
			this.numberOfWords++;
			this.wordsAndCounts.put(word, updatedCount);
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
	 * @param words
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
