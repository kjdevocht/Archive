package listem;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Finder extends Search implements Grep{
	
	
	Pattern inFile = null;
	ArrayList<String> matches = new ArrayList<String>();
	File current = null;

	@Override
	public Map<File, List<String>> grep(File directory,
			String fileSelectionPattern, String substringSelectionPattern,
			boolean recursive) {
		
		HashMap<File, List<String>> total = new HashMap<File, List<String>>();
		inFile = Pattern.compile(substringSelectionPattern);
		Pattern pfile = Pattern.compile(fileSelectionPattern);
		boolean goodFile = false;
		ArrayList<File> allFiles = new ArrayList<File>();

		allFiles = proccessRecursion(directory, pfile, allFiles, recursive);
		
		for(File file: allFiles)
		{
			goodFile = proccessFile(file,pfile);
			if(goodFile && matches.size()>0)
			{
				ArrayList<String> temp = new ArrayList<String>(matches);
				total.put(file, temp);
				matches.clear();
			}
		}

		
		return total;
	}
	
	public void proccessLine(String search)
	{
		Matcher matchString = inFile.matcher(search);
		
		if(matchString.find())
		{
			matches.add(search);
		}
	}

}
