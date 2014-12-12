package server.persistance.sql;

/**
 * Created by mitch10e on 12/8/14.
 */
/*public class PersistanceProvider implements IPersistanceProvider {

    private SQLiteDatabase database;
    private IGamesDAO gameDAO;
    private IUsersDAO userDAO;
    private static int numCommands;
    public static PersistanceProvider singleton;
    private static String daoType = null;

    public static void init(String type, int numCommands){
        PersistanceProvider.daoType = type;
        PersistanceProvider.numCommands = numCommands;
    }

    public static PersistanceProvider getSingleton() {
        if (singleton == null) {
                singleton = new PersistanceProvider(PersistanceProvider.numCommands);
        }
        return singleton;
    }

    public PersistanceProvider(int numCommands) {
        this.database = new SQLiteDatabase();
        this.gameDAO = new SQLiteGameDAO(database, numCommands);
        this.userDAO = new SQLiteUserDAO(database);
    }

    @Override
    public IGamesDAO getGamesDAO() {
        return this.gameDAO;
    }

    @Override
    public IUsersDAO getUsersDAO() {
        return this.userDAO;
    }



}*/


import server.persistance.PersistanceProvider;

/**
 * Created by curtis on 12/10/14.
 */
public class SQLitePersistanceProvider extends server.persistance.PersistanceProvider {

	public SQLitePersistanceProvider() {
		SQLiteDatabase database = new SQLiteDatabase();
		this.userDAO = new SQLiteUserDAO(database);
		this.gameDAO = new SQLiteGameDAO(database, PersistanceProvider.numCommands);
	}
}
