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
		this.setFileInstance(file);
	}

	public Path getPath()
	{
		return Paths.get(this.file.getAbsolutePath());
	}

	public Map<String, Integer> getWords() throws IOException
	{
		if (this.wordsAndCounts != null) return this.wordsAndCounts;

		this.wordsAndCounts = new HashMap<String, Integer>();

	
		   String content = new String(Files.readAllBytes(getPath()));
		   //System.out.println(content);
		   
		   content = content.replaceAll("[.,_:;+]", "");
		   content = content.replaceAll("([a-z]*\\d+[a-z]*)", "");
		   System.out.println(content);

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
