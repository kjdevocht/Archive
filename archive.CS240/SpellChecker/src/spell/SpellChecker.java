package spell;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import spell.SpellCorrector.NoSimilarWordFoundException;
import spell.Trie.Node;

@SuppressWarnings("unused")
public class SpellChecker implements SpellCorrector{
	
	Trie words = new Words();
	int mostInstancesOfAWord = 0;
	int depth = 0;
	Set <String> edit1 = new TreeSet<String>();
	Set <String> edit2 = new TreeSet<String>();

	public void useDictionary(String dictionaryFileName) throws IOException
	{
		Scanner scnr = new Scanner( new FileReader(dictionaryFileName));
		while(scnr.hasNext())//Gets each word regardless of white space in file
		{
			String word = scnr.next();
			if(isAlpha(word)) //makes sure that there are only letters in the word
			{
				words.add(word.toLowerCase()); //sets it to lowercase for continuity
			}
		}
		scnr.close();//make sure to close the scanner
		
	}
	
	public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException
	{
		mostInstancesOfAWord = 0;
		Node word = words.find(inputWord);//See if the word is already in the dictionary
		String sug = null;
		if(word == null)
		{
			depth = 1;//Run the for edit functions to see if a word is one edit away
			runEdits(inputWord);
			sug = searchSetOfEditWords(sug);

			if(sug == null)//nothing was found one edit away.  Try two
			{
				depth = 2;
				for(String input :edit1)//create a new set of words that is one edit away from the set of words that was one edit away from the original
				{
					runEdits(input);
				}
				
				sug = searchSetOfEditWords(sug);
				if(sug == null)
				{
					NoSimilarWordFoundException fail = new NoSimilarWordFoundException();
					throw fail;
				}
				else
				{
					return sug;
				}
			}
			else
			{
				return sug;
			}
		}
		else
		{
			return inputWord;
		}
	}
	
	private String searchSetOfEditWords(String sug)
	{
		Set <String> currentSet = new TreeSet<String>();
		switch(depth) //see which set to run
		{
			case 1 : currentSet = edit1;
				break;
			case 2 : currentSet = edit2;
				break;
		}
		
		for(String find : currentSet)
		{
			Node nullTest = new WordNode();
			nullTest = words.find(find);
			if(nullTest != null && nullTest.getValue()>mostInstancesOfAWord)//Test to see if the modified word is in the dictionary and if it has the must number of occurances in the document
			{
				sug = find;
				mostInstancesOfAWord = nullTest.getValue();
			}	
		}
		return sug;
	}
	
	private void runEdits(String testWord)
	{
		deletion(testWord);
		transposition(testWord);
		alteration(testWord);
		insertion(testWord);
	}
	
	
	
	private void deletion(String word)
	{
		String find = "";
		String [] wordArray = word.split("(?!^)");
		for(int i = 0; i<wordArray.length; i++)//loop through every letter in word
		{
			StringBuilder deleteBuilder = new StringBuilder();
			for(int h = 0; h<wordArray.length; h++)//loop through every letter in word and insert it into deleteBuilder as long as h != i  so on the first loop over the word "test" it would create "est".  On the second loop it would create "tst"
			{
				if(h !=i)
				{
					deleteBuilder.append(wordArray [h]);
				}
			}
			find = deleteBuilder.toString();
			if(depth == 1)
			{
				edit1.add(find);
			}
			else
			{
				edit2.add(find);
			}
		}
	}
	
	private void transposition(String word)
	{
		String find = "";;
		String [] wordArray = word.split("(?!^)");
		for(int i = 0; i<wordArray.length-1; i++)//just like deletion except only loop length-1
		{
			StringBuilder transposBuilder = new StringBuilder();
			for(int h = 0; h<wordArray.length; h++)
			{
				if(h !=i)
				{
					transposBuilder.append(wordArray [h]);
				}
			}
			transposBuilder.insert(i+1, wordArray[i]);//this compensates for the length-1 and always to switch the letters
			find = transposBuilder.toString();
			if(depth == 1)
			{
				edit1.add(find);
			}
			else
			{
				edit2.add(find);
			}
		}
	}
	
	private void alteration(String word)
	{
		String find = "";
		for(int i = 0; i<word.length(); i++)//loops through ever letter in word
		{
			StringBuilder test = new StringBuilder(word);
			for(int b = 0; b<25; b++)//loops through every letter in the alphabet
			{
				test.delete(i, i+1);//delete the letter at i
				test.insert(i, index(b));//insert the new letter at i based off of b
				find = test.toString();
				if(depth == 1)
				{
					edit1.add(find);
				}
				else
				{
					edit2.add(find);
				}
			}
		}
	}
	
	private void insertion(String word)
	{
		String find = "";
		for(int i = 0; i<=word.length(); i++)//loops through ever letter in word
		{
			StringBuilder test = new StringBuilder(word);
			for(int b = 0; b<25; b++)//loops through every letter in the alphabet
			{
				test.insert(i, index(b));//inserts a new letter into the word
				find = test.toString();//convert it back to a string
				test.delete(i, i+1);//remove the letter you just added from you StringBuilder
				if(depth == 1)
				{
					edit1.add(find);
				}
				else
				{
					edit2.add(find);
				}
			}
		}
	}
	
	private String index(int letter)
	{
		String index = "";
		char temp = (char) (letter+97);
		index = Character.toString(temp);
		return index;
	}
	
	private boolean isAlpha(String word)
	{
	    return word.matches("[a-zA-Z]+");
	}
}
