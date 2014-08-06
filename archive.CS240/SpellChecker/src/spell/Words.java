package spell;



public class Words implements Trie{
	int wordCount = 0;
	int nodeCount = 1;
	WordNode root = new WordNode();

	private int index(String letter)
	{
		int index = 0;
		index = letter.charAt(0);
		index = index-97;
		return index;
	}
	
	private WordNode addWord(WordNode current, String [] word, int i)
	{
		if(i == word.length)
		{	
			if(current.count == 0)
			{
				wordCount++;
			}
			current.count++;
		}
		else
		{
			if(current.nodes [index(word[i])] == null)
			{
				nodeCount++;
				current.nodeCount++;
				WordNode temp = new WordNode();
				
				current.nodes [index(word[i])] = temp;
				addWord(temp, word, i+1);
			}
			else
			{
				addWord(current.nodes[index(word[i])], word, i+1);
			}
		}
	return current;
	}
	
	private WordNode findNode(WordNode current, String [] word, int i)
	{
		WordNode temp = current;
		if(i == word.length && temp.count>0)
		{	
			return temp;
		}
		else
		{
			if(i == word.length)
			{
				return null;
			}
			if(temp.nodes [index(word[i])] == null)
			{
				return null;
			}
			else
			{
				temp = findNode(temp.nodes[index(word[i])], word, i+1);
			}
		}
		
		return temp;
	}
	
	@Override
	public void add(String word) 
	{
		String [] wordArray = word.split("(?!^)");
		addWord(root, wordArray, 0);
	}

	@Override
	public Node find(String word) 
	{
		String [] wordArray = word.split("(?!^)");
		return findNode(root, wordArray, 0);
	}

	@Override
	public int getWordCount() 
	{
		return wordCount;
	}

	@Override
	public int getNodeCount()
	{
		return nodeCount;
	}
	
	@Override
	public String toString()
	{
		StringBuilder results = new StringBuilder();
		StringBuilder tempS = new StringBuilder();
		results = realToString(root, results, tempS);
		String print = results.toString(); 
		return print;
	}
	
	private StringBuilder realToString(WordNode current, StringBuilder results, StringBuilder tempS) 
	{
		for(int i = 0; i<26; i++)
		{
			if(current.nodes[i] != null)
			{
				tempS.append(indexR(i));
				WordNode temp = current.nodes[i];
				if(temp.count>0)
				{
					results.append(tempS.toString() + " " +  temp.count + "\n");
				}
				
				realToString(temp, results, tempS);
			}
		}
		int length = tempS.length();
		if(length !=0)
		{
			tempS.deleteCharAt(length-1);
		}
		return results;
	}
	
	@Override
	public int hashCode() 
	{
		return (root.hashCode() << 3) ^ (wordCount + nodeCount);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this.hashCode() == o.hashCode())
		{
			return true;
		}
		else
		{
			if(o.getClass() == this.getClass())
			{
				Words other = (Words) o;
				return trieCheck(this.root, other.root, false);
			}
			else
			{
				return false;
			}

		}
	}
	
	private boolean trieCheck(WordNode current, WordNode other, boolean same)
	{
		for(int i = 0; i<26; i++)
		{
			if(current.nodes[i] == null && other.nodes [i] == null)
			{
				same =  true;
			}
			else if(current.nodes[i].count == other.nodes[i].count)
			{
				WordNode tmpCur = current.nodes [i];
				WordNode tmpOth = other.nodes [i];
				same =  trieCheck(tmpCur, tmpOth, same);
			}
			else
			{
				same = false;
			}
		}
		return same;
	}
	
	private String indexR(int letter)
	{
		String index = "";
		char temp = (char) (letter+97);
		index = Character.toString(temp);
		return index;
	}

}
