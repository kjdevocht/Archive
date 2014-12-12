package server.persistance.file;

import server.persistance.PersistanceProvider;

/**
 * Created by curtis on 12/10/14.
 */
public class FilePersistanceProvider extends PersistanceProvider {
    public FilePersistanceProvider() {
        this.userDAO = new FileUserDAO();
        this.gameDAO = new FileGamesDAO(PersistanceProvider.numCommands);
    }
}
