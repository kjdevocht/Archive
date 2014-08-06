package listem;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Counter extends Search implements LineCounter{
	
	int lineCount = 0;
	File current = null;
	@Override
	public Map<File, Integer> countLines(File directory,String fileSelectionPattern, boolean recursive) 
	{
		HashMap<File, Integer> total = new HashMap<File, Integer>();
		Pattern pfile = Pattern.compile(fileSelectionPattern);
		boolean goodFile = false;
		
		ArrayList<File> allFiles = new ArrayList<File>();
		allFiles = proccessRecursion(directory, pfile, allFiles, recursive);

		for(File file: allFiles)
		{
			goodFile = proccessFile(file,pfile);
			if(goodFile)
			{
				total.put(file, lineCount);
				lineCount = 0;
			}
		}
		return total;
	}
	
	public void proccessLine(String search)
	{
		lineCount++;
	}
}
