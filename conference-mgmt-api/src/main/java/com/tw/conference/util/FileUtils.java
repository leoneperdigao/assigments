/**
 * 
 */
package com.tw.conference.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Util class to handle with files
 * 
 * @author leone
 *
 */
public class FileUtils {
	
	/**
	 * Routine to transform a input file into a bufferedReader based on context
	 * 
	 * @param resourceFile
	 * @param context
	 * @return BufferedReader instance
	 */
	public static BufferedReader getBufferedReader(String resourceFile, Object context) {
		InputStream inputStream = context.getClass().getResourceAsStream(resourceFile);
		return new BufferedReader(new InputStreamReader(inputStream));
	}
}
