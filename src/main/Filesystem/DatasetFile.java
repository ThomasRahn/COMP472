package main.Filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by Alan Ly on 16-Oct-2014.
 */
public class DatasetFile
{
	private File file;
	private int numberOfWords = 0;
	private Map<String, Integer> wordsAndCounts;

	/**
	 * @param file A `java.io.File` instance.
	 * @throws Exception Thrown when the specified instance does not reference an existing file.
	 */
	public DatasetFile(File file) throws Exception
	{
		this.loadFileInstance(file);
	}

	public Path getPath()
	{
		return Paths.get(this.file.getAbsolutePath());
	}

	public Stream<String> getLines() throws IOException
	{
		return Files.lines(this.getPath(), StandardCharsets.US_ASCII);
	}

	public Map<String, Integer> getWords()
	{
		if (this.wordsAndCounts != null) return this.wordsAndCounts;

		this.wordsAndCounts = new HashMap<String, Integer>();

		/**
		 * @todo Collect all lines into a single string instance.
		 */

		/**
		 * @todo Filter out words in line via regular expression.
		 */

		/**
		 * @todo For each word in the string, keep a tally of how many words
		 * there are, plus keep a count of how many instances of each unique
		 * word there are for this file.
		 */

		return this.wordsAndCounts;
	}

	/**
	 * Load the specified File instance.
	 *
	 * @param file An instance of `java.io.File`.
	 * @throws Exception thrown when the File instance does not reference an existing file.
	 */
	private void setFileInstance(File file) throws Exception
	{
		if (! file.isFile()) {
			throw new Exception("The provided file instance does not reference an existing file.");
		}

		this.file = file;
	}
}
