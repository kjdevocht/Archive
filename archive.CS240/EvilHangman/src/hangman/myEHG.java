package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class myEHG implements EvilHangmanGame{
	private TreeSet<String> currentDictionary;
	public TreeSet<String> guesses;
	public String pattern;
	public int leastLetters;

	public myEHG()
	{
		
	}
	public void startGame(File dictionary, int wordLength)
	{
		currentDictionary = new TreeSet<String>();
		guesses = new TreeSet<String>();
		StringBuilder blankPattern = new StringBuilder();
		for(int i = 0; i<wordLength; i++)
		{
			blankPattern.append("-");
		}
		pattern = blankPattern.toString();
		Scanner scnr;
		try {
			scnr = new Scanner( new FileReader(dictionary));
			while(scnr.hasNext())
			{
				String word = scnr.next();
				if(isAlpha(word) && word.length() == wordLength)
				{
					currentDictionary.add(word.toLowerCase());
				}
			}
			scnr.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found please choose a new file");
			System.exit(1);
		}
	}
	private boolean isAlpha(String word)
	{
	    return word.matches("[a-zA-Z]+");
	}
	
	private String makePattern(String word, char guessedLetter)
	{
		StringBuilder tempPattern = new StringBuilder();
		for(int i = 0; i<word.length(); i++)
		{
			if(word.charAt(i) == guessedLetter)
			{
				tempPattern.append(word.charAt(i));
			}
			else if(pattern.charAt(i) != '-')
			{
				tempPattern.append(pattern.charAt(i));
			}
			else
			{
				tempPattern.append("-");
			}
		}
		
		return tempPattern.toString();
	}
	
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException
	{
		String guessString = Character.toString(guess).toLowerCase();
		if(!(guesses.contains(guessString)))
		{
			guesses.add(guessString);
			HashMap<String, TreeSet<String>> possibleSets = new HashMap<String, TreeSet<String>>();
			for(String word : currentDictionary)
			{
				String testPattern = makePattern(word, guess);
				if(possibleSets.containsKey(testPattern))
				{
					TreeSet<String> temp  = possibleSets.get(testPattern);
					possibleSets.remove(testPattern);
					temp.add(word);
					possibleSets.put(testPattern, temp);
				}
				else
				{
					TreeSet<String> temp = new TreeSet<String>();
					temp.add(word);
					possibleSets.put(testPattern, temp);
				}
			}
			int largestSetSize = 0;
			String largestSetKey = "";
			boolean moreThanOne = false;
			for (Entry<String, TreeSet<String>> entry : possibleSets.entrySet()) 
			{
			    String key = entry.getKey();
			    TreeSet<String> value = entry.getValue();
			    if(largestSetSize<value.size())
			    {
			    	largestSetSize = value.size();
			    	largestSetKey = key;
			    }
			    else if(largestSetSize == value.size())
			    {
			    	moreThanOne = true;
			    }
			}
			if(!moreThanOne)
			{
				pattern = largestSetKey;
				currentDictionary = possibleSets.get(largestSetKey);
			}
			else//This means that there are more then one set that are the same size
			{
				HashMap<String, TreeSet<String>> setsOfSameLength = new HashMap<String, TreeSet<String>>();
				for (Entry<String, TreeSet<String>> entry : possibleSets.entrySet())
				{
				    String key = entry.getKey();
				    TreeSet<String> value = entry.getValue();
					if(value.size() == largestSetSize)
					{
						setsOfSameLength.put(key, value);
					}
				}
				testForNoLetters(setsOfSameLength, guessString);
			}
		}
		else
		{
			GuessAlreadyMadeException fail = new GuessAlreadyMadeException();
			throw fail;
		}
		
		return currentDictionary;
	}
	

	
	private void testForNoLetters(HashMap<String, TreeSet<String>> setsOfSameLength, String guessString)
	{	
		TreeSet<String> temp = null;
		for (String key : setsOfSameLength.keySet()) 
		{
		    if(!(key.contains(guessString)))//This means that this Pattern does not contain the guessed letter
		    {
		    	pattern = key;
		    	currentDictionary = setsOfSameLength.get(key);
		    }

		}
		if(temp == null)//This means all setsOfSameLength contain the guessed letter
		{
			testForFewestLetters(setsOfSameLength, guessString);
		}
	}
	
	private void testForFewestLetters(HashMap<String, TreeSet<String>> setsOfSameLength, String guessString)
	{
		leastLetters = 0;
		String smallestLetterKey = "";
		int [] arrayNumberOfLetters = new int [setsOfSameLength.size()];
		boolean multiplePossibilities = false;
		int it = 0;
		for (String key : setsOfSameLength.keySet()) 
		{
			int guessedLetter = 0;
		    for (char ch : key.toCharArray())
		    {
		    	
			    if(ch == guessString.charAt(0))
			    {
			    	guessedLetter++;
			    }
		    }
		    arrayNumberOfLetters[it] = guessedLetter;
		    if(leastLetters>guessedLetter)
		    {
		    	leastLetters = guessedLetter;
		    	smallestLetterKey = key;
		    }
		    else if(leastLetters == guessedLetter)
		    {
		    	multiplePossibilities = true;
		    }
		    it++;
		}
		
		if(!multiplePossibilities)
		{
			pattern = smallestLetterKey;
			TreeSet<String> temp = new TreeSet<String>(setsOfSameLength.get(smallestLetterKey));
	    	currentDictionary = temp;
		}
		else //this means some of the Sets in setsOfSameLength have the same number of guessed letters
		{
			HashMap<String, TreeSet<String>> setsOfFewestLetters = new HashMap<String, TreeSet<String>>();
			int i = 0;
			for (Entry<String, TreeSet<String>> entry : setsOfSameLength.entrySet())
			{
			    String key = entry.getKey();
			    TreeSet<String> value = entry.getValue();
				if(arrayNumberOfLetters[i] == leastLetters)
				{
					setsOfFewestLetters.put(key, value);
				}
				i++;
			}
			testForRightMostLetter(setsOfFewestLetters ,guessString);
		}
	}
	
	private void testForRightMostLetter(HashMap<String, TreeSet<String>> setsOfFewestLetters, String guessString)
	{
		String currentBest = "";
		for(String key : setsOfFewestLetters.keySet())
		{
			if(currentBest.equals(""))
			{
				currentBest = key;
				continue;
			}
			for(int i = key.length()-1; i>0;  i--)
			{
				if(currentBest.charAt(i) == guessString.charAt(0) && key.charAt(i) != guessString.charAt(0))
				{
					break;
				}
				else if(currentBest.charAt(i) != guessString.charAt(0) && key.charAt(i) == guessString.charAt(0))
				{
					currentBest = key;
					break;
				}
				else
				{
					
				}
			}

		}
		pattern = currentBest;
		TreeSet<String> temp = new TreeSet<String>(setsOfFewestLetters.get(currentBest));
    	currentDictionary = temp;
	}
}
