package cs240Image;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.StringBuilder;
import java.util.Scanner;



public class ImageEditor {
	static String treatment = "";
	static CSImage image = null;
	static int blurNum = 0;
	
	
	
	public static void main(String [] args) throws IOException
	{
		if(args.length >= 3  && args.length <= 4)
		{
			ImageEditor imgedtr = new ImageEditor();
			String inputFile = args [0];
			String outputFile = args[1];
			treatment = args[2];
			if(treatment.equals("motionblur"))
			{
				if(args.length != 4)
				{
					System.out.println("You must include a number if you would like to use motion blur:  in-file.ppm out-file.ppm motion blur 10");
					System.exit(0);
				}
				blurNum = Integer.parseInt(args[3]);
				
				if(blurNum < 0)
				{
					System.out.println("You must use a number greater then 0 for motionblur");
					System.exit(0);
				}
				blurNum = Integer.parseInt(args[3]);
			}
				
				Scanner scnr = new Scanner( new FileReader(inputFile));
				BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
				StringBuilder output = new StringBuilder();
				String workingLine = "";
				try {
					workingLine = scnr.next();
					
					if(workingLine.equals("P3"))
					{
						workingLine = scnr.next();
						workingLine = imgedtr.commentCleaner(workingLine, scnr);
						int width = Integer.parseInt(workingLine);
						workingLine = scnr.next();
						workingLine = imgedtr.commentCleaner(workingLine, scnr);
						int height = Integer.parseInt(workingLine);
						workingLine = scnr.next();
						workingLine = imgedtr.commentCleaner(workingLine, scnr);
						int maxCV = Integer.parseInt(workingLine);
						
						imgedtr.image = new CSImage(width,height);
						output.append("P3\n" + width + " "  + height + "\n" + maxCV + "\n");
						int heightCount = 0;
						while(scnr.hasNext())
						{
							for(int c = 0; c<width; c++)
							{
								int red = scnr.nextInt();
								int green = scnr.nextInt();
								int blue = scnr.nextInt();

								Pixel tmp = new Pixel(red, green, blue);
								image.img[heightCount] [c] = tmp;
							}
							heightCount++;	
							
						}
						for(int r = 0; r<height; r++)
						{
							for(int c = 0; c<width; c++)
							{
								Pixel treat = imgedtr.treat(image.img[r] [c].red,image.img[r] [c].green,image.img[r] [c].blue, r, c, width);
								output.append(treat.red + "\n" + treat.green + "\n" + treat.blue + "\n");
							}
		
						}
		
						scnr.close();
						bw.write(output.toString());
						bw.flush();
						bw.close();
					}
					else
					{
						System.out.println("Wrong file format.  You must use .ppm");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		else
		{
			System.out.println("You must have an input file, output file and an Action: in-file.ppm out-file.ppm (grayscale|invert|emboss|motionblur motion-blur-length)");
		}
	}
	
	public String commentCleaner(String workingLine, Scanner scnr)
	{
		while(workingLine.contains("#"))
		{
			workingLine = scnr.nextLine();
			workingLine = scnr.next();
		}
		
		return workingLine;
	}
	
	public Pixel treat(int red, int green, int blue, int r, int c, int widthnum)
	{
		if(treatment.equals("invert"))
		{
			Pixel treated = invert(red, green, blue);
			return treated;
		}
		
		if(treatment.equals("grayscale"))
		{
			Pixel treated = grayscale(red, green, blue);
			return treated;
		}
		if(treatment.equals("emboss"))
		{
			Pixel treated = emboss(red, green, blue, r, c);
			return treated;
		}
		if(treatment.equals("motionblur"))
		{
			Pixel blur = blur(r, c, widthnum);
			return blur;
		}
		else
		{
			Pixel fake = new Pixel(0,0,0);
			return fake;
		}
		
		
	}
	
	public Pixel invert(int red, int green, int blue)
	{
		red = Math.abs(red-255);
		green = Math.abs(green-255);
		blue = Math.abs(blue-255);
		
		Pixel inverted = new Pixel(red, green, blue);
		
		return inverted;
	}
	
	public Pixel grayscale(int red, int green, int blue)
	{	
		int gray = (red+green+blue)/3;
		Pixel grayscale = new Pixel(gray,gray,gray);
		
		return grayscale;
		
		
	}
	
	public Pixel emboss(int red, int green, int blue, int r, int c)
	{
		int maxD = 0;
		int redDiff = 0;
		int greenDiff = 0;
		int blueDiff = 0;
		int v = 0;
		if(r != 0 && c != 0)
		{
			redDiff = red-image.img[r-1] [c-1].red;
			greenDiff = green-image.img[r-1] [c-1].green;
			blueDiff = blue-image.img[r-1] [c-1].blue;
			
			maxD = redDiff;
			if(Math.abs(greenDiff)>Math.abs(redDiff))
			{
				maxD = greenDiff;
			}
			if(Math.abs(blueDiff)>Math.abs(greenDiff) && Math.abs(blueDiff)>Math.abs(redDiff))
			{
				maxD = blueDiff;
			}
			
			v = 128+maxD;
			if(v > 255) 
				{
				v = 255;
				}
			if(v<0)
			{
				v = 0;
			}
		}
		else
		{
			v = 128;
		}
		Pixel emboss = new Pixel(v, v, v);
		
		return emboss;
	}
	
	public Pixel blur(int r, int c, int widthnum)
	{
		int y = 0;
		int redavg = 0;
		int greenavg = 0;
		int blueavg = 0;
		int avg = 0;

		if(c+blurNum > widthnum)
		{
			y = widthnum;
		}
		else
		{
			y = c+blurNum;
		}
		
		for(int j = c; j<y; j++)
		{
			redavg += image.img [r][j].red;
			greenavg += image.img [r] [j].green;
			blueavg += image.img [r] [j].blue;
			avg++;
		}
		redavg = redavg/avg;
		greenavg = greenavg/avg;
		blueavg = blueavg/avg;
		
		Pixel blur = new Pixel(redavg,greenavg,blueavg);
		
		return blur;
	}

}
