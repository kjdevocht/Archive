package spell;

public class WordNode implements Trie.Node
{
	WordNode [] nodes = new WordNode [26];
	int nodeCount = 1;
	int count = 0;
	
	@Override
	public int getValue() 
	{
		return count;
	}
	
	public int hashcode()
	{
		return (count+nodeCount) << 5;
	}
	

}
