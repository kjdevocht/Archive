package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Search {
	
	public boolean proccessFile(File file, Pattern pfile)
	{
		Matcher match = pfile.matcher(file.getName());
		boolean goodFile = false;
        if (file.isFile() && match.matches()) 
        {
        	try 
        	{
				Scanner scnr = new Scanner( new FileReader(file.getPath()));
				
        		while(scnr.hasNextLine())
        		{
        			String search = scnr.nextLine();
        			proccessLine(search);
        		}
        		scnr.close();
				
				
			} catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
        	goodFile = true;
        }
        return goodFile;
	}
	
	public ArrayList<File> proccessRecursion(File directory,  Pattern pfile, ArrayList<File> allFiles, boolean recursive) 
	{
		File[] files = directory.listFiles();
		for (File file : files) 
		{
			Matcher match = pfile.matcher(file.getName());
	        if(file.isDirectory() && recursive) {
	        	proccessRecursion(file, pfile, allFiles,recursive);
	        }
	        if(match.matches() && file.isFile())
	        {
	        	allFiles.add(file);
	        }
		}
		
		return allFiles;
	}
	
	public abstract void proccessLine(String search);

	

}
