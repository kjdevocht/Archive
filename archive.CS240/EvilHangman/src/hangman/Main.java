package hangman;

import hangman.EvilHangmanGame.GuessAlreadyMadeException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;


public class Main {
	@SuppressWarnings("serial")
	public static class TooManyLettersException extends Exception {
	}
	@SuppressWarnings("serial")
	public static class NotAlphaException extends Exception {
	}
	public static void main(String[] args) throws IOException {
		
		String FileName = args[0];
		int wordLength = Integer.parseInt(args[1]);
		int numGuesses = Integer.parseInt(args[2]);
		
		
		myEHG game = new myEHG();
		game.startGame(new File(FileName), wordLength);
		Scanner usrIn = new Scanner(System.in);
		String input = "";
		char guess = 0;
		int i = 0;
		String testPattern = game.pattern;

		int numLeft = numGuesses;
	
		System.out.println("You may only guess letters\n");
		System.out.println("You have " + numLeft + " Guesses left");
		System.out.print("Used letters:");
		System.out.println("\nWord: " + game.pattern);
		System.out.print("Enter guess:");
		while(i<numGuesses)
		{

			try
			{
				input = usrIn.nextLine();
				if(input.length() == 1)
				{
					guess = input.charAt(0);
					boolean alphaTest = isAlpha(Character.toString(guess));
					if(!alphaTest)
					{
						NotAlphaException fail = new NotAlphaException();
						try {
							throw fail;
						} catch (NotAlphaException e) {
							System.out.println("You may only guess letters\n");
							System.out.println("You have " + numLeft + " Guesses left");
							System.out.print("Used letters:");
							for(String s : game.guesses)
							{
								System.out.print(s + " ");
							}
							System.out.println("\nWord: " + game.pattern);
							System.out.print("Enter guess:");
							continue;
						}
					}
				}
				else
				{
					TooManyLettersException fail = new TooManyLettersException();
					throw fail;
				}
				try 
				{
					Set<String> possibleWords = game.makeGuess(guess);
					numLeft--;
					if(!(game.pattern.contains("-")))
					{
						System.out.println("You Win !");
						System.out.println("The word was: " + game.pattern);
						break;
					}
					else if(testPattern.equals(game.pattern))
					{
						System.out.println("Sorry there are no " + guess + "'s");
					}
					else
					{
						if(game.leastLetters == 1)
						{
							System.out.println("Yes, there is 1" + guess);
						}
						else
						{
							System.out.println("Yes, there are " + game.leastLetters + " " + guess+"'s");
						}
					}
					if(numLeft>0)
					{
						System.out.println("\nYou have " + numLeft + " Guesses left");
						System.out.print("Used letters:");
						for(String s : game.guesses)
						{
							System.out.print(s + " ");
						}
						System.out.println("\nWord: " + game.pattern);
						System.out.print("Enter guess:");
					}
					else
					{
						System.out.println("You lose!");
						String first = possibleWords.iterator().next();
						System.out.println("The word was " + first);
					}

					i++;
				}
				catch(GuessAlreadyMadeException fail)
				{
					System.out.println("You already guessed that letter guess again\n");
					System.out.println("You have " + numLeft + " Guesses left");
					System.out.print("Used letters:");
					for(String s : game.guesses)
					{
						System.out.print(s + " ");
					}
					System.out.println("\nWord: " + game.pattern);
					System.out.print("Enter guess:");
				}
			}

			catch(TooManyLettersException fail)
			{
				System.out.println("You may only guess one letter at a time\n");
				System.out.println("You have " + numLeft + " Guesses left");
				System.out.print("Used letters:");
				for(String s : game.guesses)
				{
					System.out.print(s + " ");
				}
				System.out.println("\nWord: " + game.pattern);
				System.out.print("Enter guess:");
			}

		}
		usrIn.close();
	}
	
	static private boolean isAlpha(String word)
	{
	    return word.matches("[a-zA-Z]+");
	}
}
