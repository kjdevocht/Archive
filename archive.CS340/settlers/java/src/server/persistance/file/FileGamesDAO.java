package server.persistance.file;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import server.command.ICommand;
import server.model.ServerModel;
import server.model.game.GameModel;
import server.model.game.IGameModel;
import server.persistance.IGamesDAO;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Created by kdevocht on 12/10/2014.
 */
public class FileGamesDAO implements IGamesDAO{

	private int numCommands;
	public FileGamesDAO(int numCommands){
		this.numCommands = numCommands;
	}
	@Override
	public void loadGames() {
		File folder = new File("../data/local/games/");
		if (!folder.exists()) {
			folder.mkdirs();
		}
		List<IGameModel> games = loadGames(folder);
		ServerModel.getSingleton().loadGames(games);
		for(IGameModel game :games)
		{
			runCommands(game.getId());
		}

	}

	@Override
	public void storeCommand(int gameID, ICommand command) {
		XStream xStream = new XStream(new DomDriver());
		IGameModel test = ServerModel.getSingleton().getGames().get(gameID);
		if(numCommands == test.getCommandsExecuted()){
            //System.out.println("Saving model instead of command");
            IGameModel game = ServerModel.getSingleton().getGames().get(gameID);
			String xml = xStream.toXML(game);
			try {
				File folder = new File("../data/local/games/");
				if (!folder.exists()) {
					folder.mkdirs();
				}
				File tmp = new File("../data/local/games/"+gameID+".txt");
				test.setCommandsExecuted(test.getCommandsExecuted()+1);
				if (!tmp.exists()) {
					tmp.createNewFile();
				}
				FileWriter fw = new FileWriter(tmp.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(xml);
				bw.close();
			} catch (FileNotFoundException e) {
				//System.out.println("Could not find file");
				e.printStackTrace();
			} catch (IOException e) {
				//System.out.println("Something went wrong");
				e.printStackTrace();
			}
			test.setCommandsExecuted(0);
			File folder = new File("../data/local/commands/"+gameID);
			if (folder.exists()) {
				clearCommands("../data/local/commands/"+gameID);
			}


		}
		else
		{

			String xml = xStream.toXML(command);
			try {
				File folder = new File("../data/local/commands/"+gameID);
				if (!folder.exists()) {
					folder.mkdirs();
				}
				File tmp = new File("../data/local/commands/"+gameID+"/"+test.getCommandsExecuted()+".txt");
				test.setCommandsExecuted(test.getCommandsExecuted()+1);
				if (!tmp.exists()) {
					tmp.createNewFile();
				}
				FileWriter fw = new FileWriter(tmp.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(xml);
				bw.close();
			} catch (FileNotFoundException e) {
				//System.out.println("Could not find file");
				e.printStackTrace();
			} catch (IOException e) {
				//System.out.println("Something went wrong");
				e.printStackTrace();
			}
		}
	}

	@Override
	public int getNumCommands() {
		return numCommands;
	}

	@Override
	public void setNumCommands(int numCommands) {

	}

	private void clearCommands(String path)
	{
		Path dir = Paths.get(path);
		try {
			Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(Path file,BasicFileAttributes attrs) throws IOException {
					//System.out.println("Deleting file: " + file);
					Files.delete(file);
					return CONTINUE;
				}


			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void runCommands(int gameId){
		File folder = new File("../data/local/commands/"+gameId+"/");
		if (!folder.exists()) {
			folder.mkdirs();
		}
		List<ICommand> commands = loadCommands(folder);
		for(ICommand command : commands){
			command.execute();
		}
	}

	private List<ICommand> loadCommands(final File folder){
		XStream xStream = new XStream(new DomDriver());
		List<ICommand> commands = new ArrayList<>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				loadGames(fileEntry);
			} else {
				try {
                    //System.out.println("File name: "+fileEntry.getName());
                    BufferedReader br = new BufferedReader(new FileReader(fileEntry.getPath()));
					StringBuilder sb = new StringBuilder();
					String line = br.readLine();

					while (line != null) {
						sb.append(line);
						sb.append(System.lineSeparator());
						line = br.readLine();
					}
					String everything = sb.toString();
					ICommand tmp = (ICommand)xStream.fromXML(everything);
					commands.add(tmp);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return commands;
	}
	private List<IGameModel> loadGames(final File folder) {
		XStream xStream = new XStream(new DomDriver());
		List<IGameModel> games = new ArrayList<>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				loadGames(fileEntry);
			} else {
				try {
					BufferedReader br = new BufferedReader(new FileReader(fileEntry.getPath()));
					StringBuilder sb = new StringBuilder();
					String line = br.readLine();

					while (line != null) {
						sb.append(line);
						sb.append(System.lineSeparator());
						line = br.readLine();
					}
					String everything = sb.toString();
					GameModel tmp = (GameModel)xStream.fromXML(everything);
					games.add(tmp);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
        Collections.sort(games, new Comparator<IGameModel>() {
            @Override
            public int compare(IGameModel o1, IGameModel o2) {
                if(o1.getId() == o2.getId())
                    return 0;
                if(o1.getId() > o2.getId())
                    return 1;
                return -1;
            }
        });
		return games;
	}
}
