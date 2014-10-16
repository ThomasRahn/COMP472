package main.Filesystem;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by Alan Ly on 16-Oct-2014.
 */
public class Directory
{
	protected File file;

	/**
	 * @param path The absolute path to the target directory.
	 * @throws Exception Thrown when the specified path does not lead to a valid directory.
	 */
	public Directory(String path) throws Exception
	{
		this.file = this.makeFileInstance(path);
	}

	/**
	 * @param file An instance of `java.io.File` to use.
	 */
	public Directory(File file)
	{
		this.file = file;
	}

	/**
	 * Gets all files under this directory.
	 *
	 * @return An array of `java.io.File` instances.
	 */
	public File[] getFiles()
	{
		return this.file.listFiles();
	}

	/**
	 * Gets all files under this directory by filter.
	 *
	 * @param filter The filter that should be used to filter the directory listing.
	 * @return An array of `java.io.File` instances.
	 */
	public File[] getFiles(FileFilter filter)
	{
		return this.file.listFiles(filter);
	}

	/**
	 * Creates an instance of `java.io.File` based on the target path.
	 *
	 * @param path The absolute path to the target directory.
	 * @return An instance of `java.io.File`.
	 * @throws Exception Thrown when the specified path does not lead to a valid directory.
	 */
	private File makeFileInstance(String path) throws Exception
	{
		File file = new File(path);

		if (! file.isDirectory()) {
			throw new Exception("Specified path " + path + " is not a directory.");
		}

		return file;
	}
}
