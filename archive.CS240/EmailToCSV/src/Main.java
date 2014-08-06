/*import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;*/




import java.io.File;
//import java.io.File;
//import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.PrintStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Scanner;
import java.util.regex.Matcher;

public class Main{

	public static void main(String[] args) throws IOException {
		//PrintStream realSystemOut = System.out;
		
		  //File file  = new File("T:\\logging.txt");
		  //PrintStream printStream = new PrintStream(new FileOutputStream(file));

		   // System.setOut(printStream);
	
		
	    //Path dir = Paths.get("C:\\Users\\kdevocht\\Dropbox\\Apps\\Attachments\\mary_wright@byu.edu");
	    File file = new File("E:\\Dropbox\\Apps\\Attachments\\mary_wright@byu.edu");
	    File [] files = file.listFiles();
	    FileWriter writer = new FileWriter("Y:\\emails.csv");
	    writer.append("Date");
	    writer.append(",");
	    writer.append("Email Address");
	    writer.append(",");
	    writer.append("Count");
	    writer.append("\n");
		for (File txt : files) 
		{
			if(txt.toString().endsWith(".txt")){
			    Scanner scnr = new Scanner( new FileReader(txt));

			    String date = "";
			    String email = "";
			    String count = "";
			    boolean proceed = false;
			    System.out.println("   Parsing file: "+txt);
			    while(scnr.hasNextLine())
			    {
			        String word = scnr.nextLine();
			        if(word.contains("This report was requested on "))
			        {
			        	word = word.trim();
			        	int beginDate = word.indexOf('/');
			        	int endDate = word.lastIndexOf('/');
			        	word = word.substring(beginDate-2, endDate+5);
			        	word = word.trim();
			            date = word;
			            proceed = true;
			        }
			        else if(proceed && word.contains("@"))
			        {
			        	word = word.trim();
			        	String [] words = word.split("- Sent to ");
			        	email = words[0].trim();
			        	int rec = words[1].indexOf("recipients");
			        	count = words[1].substring(0, rec);
			        	count = count.trim();
			        	
			    	    writer.append(date);
			    	    writer.append(",");
			    	    writer.append(email);
			    	    writer.append(",");
			    	    writer.append(count);
			    	    writer.append("\n");

			        }
			    }
			    scnr.close();

			}
			else{
				System.out.println("   Could not parse file: "+txt);
			}

		}
	    writer.flush();
	    writer.close();	

	}
	
	/*private static void parseEmail(String filename) throws Exception {
		if(filename.endsWith(".txt")){
		    Scanner scnr = new Scanner( new FileReader(txt));
		    FileWriter writer = new FileWriter("T:\\emails.csv");
		    writer.append("Date");
		    writer.append(",");
		    writer.append("Email Address");
		    writer.append(",");
		    writer.append("Count");
		    writer.append("\n");
		    String date = "";
		    String email = "";
		    String count = "";
		    boolean proceed = false;
		    System.out.println("   Parsing file: "+filename);
		    while(scnr.hasNextLine())
		    {
		        String word = scnr.nextLine();
		        if(word.contains("This report was requested on "))
		        {
		        	word = word.trim();
		        	int beginDate = word.indexOf('/');
		        	int endDate = word.lastIndexOf('/');
		        	word = word.substring(beginDate-1, endDate+5);
		        	word = word.trim();
		            date = word;
		            proceed = true;
		        }
		        else if(proceed && word.contains("@"))
		        {
		        	word = word.trim();
		        	String [] words = word.split("- Sent to ");
		        	email = words[0].trim();
		        	int rec = words[1].indexOf("recipients");
		        	count = words[1].substring(0, rec);
		        	count = count.trim();
		        	
		    	    writer.append(date);
		    	    writer.append(",");
		    	    writer.append(email);
		    	    writer.append(",");
		    	    writer.append(count);
		    	    writer.append("\n");

		        }
		    }
		    scnr.close();
		    writer.flush();
		    writer.close();	
		}
		else{
			System.out.println("   Could not parse file: "+filename);
		}

	  }*/
  }