package rcp.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class FileUtilities {
	public static void insertInFile(File file, int lineNumber, String line)
			throws IOException {
		// temp file
		File outFile = new File("$$$$$$$$.tmp");

		// input
		FileInputStream fis = new FileInputStream(file);
		BufferedReader in = new BufferedReader(new InputStreamReader(fis));

		// output
		FileOutputStream fos = new FileOutputStream(outFile);
		PrintWriter out = new PrintWriter(fos);

		String thisLine = "";
		int i = 1;
		while ((thisLine = in.readLine()) != null) {
			if (i == lineNumber) {
				out.print(line);
			}
			out.println(thisLine);
			i++;
		}
		out.flush();
		out.close();
		in.close();

		file.delete();
		outFile.renameTo(file);
	}

	public static void insertInFile(File file, String keyStart, String insert)
			throws IOException {
		// temp file
		File outFile = new File(file.getParent()+ SystemConstants.PATH_SEPARATOR + "$$$$$$$$.tmp");

		// input
		FileInputStream fis = new FileInputStream(file);
		BufferedReader in = new BufferedReader(new InputStreamReader(fis));

		// output
		FileOutputStream fos = new FileOutputStream(outFile);
		PrintWriter out = new PrintWriter(fos);

		String line = "";
		while ((line = in.readLine()) != null) {
			if (line.trim().startsWith(keyStart.trim())) {
				out.print(insert);
			}
			out.println(line);
		}
		out.flush();
		out.close();
		in.close();

		file.delete();
		outFile.renameTo(file);
	}
}
