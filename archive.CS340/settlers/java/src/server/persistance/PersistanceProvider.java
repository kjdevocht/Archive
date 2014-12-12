package server.persistance;

import server.persistance.sql.SQLitePersistanceProvider;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by kdevocht on 12/8/2014.
 */
public class PersistanceProvider implements IPersistanceProvider {

	protected IGamesDAO gameDAO;
    protected IUsersDAO userDAO;
	public static IPersistanceProvider singleton;
	private static String daoType = null;
	public static int numCommands;

    public PersistanceProvider() {
    }

    public static void init(String type, int numCommands){
		PersistanceProvider.daoType = type;
		PersistanceProvider.numCommands = numCommands;
	}

	public static IPersistanceProvider getSingleton() {
		if (singleton == null) {
			if(PersistanceProvider.daoType != null)
			{
                if(daoType.equals("fileJar.jar")) {
                    singleton = PersistanceProvider.loadJar("fileJar.jar", "server.persistance.file.FilePersistanceProvider");//new PersistanceProvider(PersistanceProvider.daoType);
                }
				else if(daoType.equals("sqliteJar.jar"))
				{
					singleton = PersistanceProvider.loadJar("sqliteJar.jar", "server.persistance.sql.SQLitePersistanceProvider");
				}

			}
		}
		return singleton;
	}
	private PersistanceProvider(String type) {

	}


    private static IPersistanceProvider loadJar(String jarfile, String classname) {
        try{
            Path path = Paths.get(jarfile);
            URL url = path.toUri().toURL();
            System.out.println("URL: " + url);

            URL[] classLoaderUrls = new URL[]{url};
            URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);
            IPersistanceProvider factory = (IPersistanceProvider)urlClassLoader.loadClass(classname).newInstance();
            urlClassLoader.close();
            return factory;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	@Override
	public IGamesDAO getGamesDAO() {
		return gameDAO;
	}

	@Override
	public IUsersDAO getUsersDAO() {
		return userDAO;
	}
}
