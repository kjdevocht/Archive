package server.persistance.file;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import server.model.game.IUser;
import server.model.game.User;
import server.persistance.IUsersDAO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 12/10/2014.
 */
public class FileUserDAO implements IUsersDAO {
	@Override
	public List<IUser> getUsers() {
		File folder = new File("../data/local/users");
		if (!folder.exists()) {
			folder.mkdirs();
		}
		List<IUser> users = loadFilesForFolder(folder);

		return users;
	}

	@Override
	public void saveUser(IUser user) {
		//System.out.println("Trying to save a user to file");
		XStream xStream = new XStream(new DomDriver());
		String xml = xStream.toXML(user);
		try {
			File tmp = new File("../data/local/users/"+user.getId()+".txt");
			if (!tmp.exists()) {
				tmp.createNewFile();
			}
			FileWriter fw = new FileWriter(tmp);
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


	private List<IUser> loadFilesForFolder(final File folder) {
		XStream xStream = new XStream(new DomDriver());
		List<IUser> users = new ArrayList<>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				loadFilesForFolder(fileEntry);
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
					User tmp = (User)xStream.fromXML(everything);
					users.add(tmp);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		return users;
	}
}