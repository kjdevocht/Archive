package server.persistance.sql;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import server.Server;
import server.command.ICommand;
import server.model.ServerModel;
import server.model.game.GameModel;
import server.model.game.IGameModel;
import server.persistance.IGamesDAO;
import server.persistance.Serializer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class SQLiteGameDAO implements IGamesDAO {

    private SQLiteDatabase database;
    private int numCommands;

    public SQLiteGameDAO(SQLiteDatabase database, int numCommands) {
        this.database = database;
        this.numCommands = numCommands;
    }

    @Override
    public void loadGames() {
        List<IGameModel> games = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            database.startTransaction();
            String query = "select * from games";
            stmt = database.getConnection().prepareStatement(query);
            rs = stmt.executeQuery();
            XStream xStream = new XStream(new DomDriver());

            while (rs.next()) {
                Integer gameID = rs.getInt(1);
                String serializedModel = rs.getString(2);
                GameModel model = (GameModel)xStream.fromXML(serializedModel);
                games.add(model);
            }
            Collections.sort(games, new Comparator<IGameModel>() {
                @Override
                public int compare(IGameModel o1, IGameModel o2) {
                    if (o1.getId() == o2.getId())
                        return 0;
                    if (o1.getId() > o2.getId())
                        return 1;
                    return -1;
                }
            });
            ServerModel.getSingleton().loadGames(games);
            for(IGameModel game :games)
            {
                runCommands(game.getId());
            }
        } catch (SQLException e) {
            database.endTransaction(false);
            //System.out.println("Error Reading SQLite Database: ");
            e.printStackTrace();
        } catch (DatabaseException e) {
            database.endTransaction(false);
            //System.out.println("Error in Database: ");
            e.printStackTrace();
        } finally {
            SQLiteDatabase.safeClose(rs);
            SQLiteDatabase.safeClose(stmt);
        }


        database.endTransaction(true);
        //return games;
    }

    private void runCommands(int gameID) {
        List<ICommand> commands = loadCommands(gameID);
        for(ICommand command : commands){
            command.execute();
        }
    }

    private List<ICommand> loadCommands(int gameID) {
        XStream xStream = new XStream(new DomDriver());
        List<ICommand> commands = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "select * from commands where game_id = ?";
            stmt = database.getConnection().prepareStatement(query);
            stmt.setInt(1, gameID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Integer command_id = rs.getInt(1);
                Integer game_id = rs.getInt(2);
                String serializedCommand = rs.getString(3);
                ICommand command = (ICommand)xStream.fromXML(serializedCommand);
                commands.add(command);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commands;
    }

    @Override
    public void storeCommand(int gameID, ICommand command) {
        IGameModel test = ServerModel.getSingleton().getGames().get(gameID);
        //System.out.println("Num of Commands: " +numCommands);
        //System.out.println("Commands Executed: " +test.getCommandsExecuted());
        if(numCommands == test.getCommandsExecuted()){
            try {
                saveGameModel(gameID);
                test.setCommandsExecuted(0);
            } catch (DatabaseException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
        {
            String commandXML = Serializer.serializeObject(command);
            PreparedStatement stmt = null;

            try {
                database.startTransaction();
                String query = "insert into commands (id, game_id, command) values (?, ?, ?)";
                stmt = database.getConnection().prepareStatement(query);
                stmt.setInt(1, test.getCommandsExecuted());
                stmt.setInt(2, gameID);
                stmt.setString(3, commandXML);
                if(stmt.executeUpdate() != 1) {
                    throw new DatabaseException("Could not update user");
                }
            } catch (DatabaseException | SQLException e) {
                database.endTransaction(false);
                e.printStackTrace();
            } finally {
                SQLiteDatabase.safeClose(stmt);
            }
            database.endTransaction(true);
            test.setCommandsExecuted(test.getCommandsExecuted()+1);
        }

    }

    private void saveGameModel(int gameID) throws DatabaseException, SQLException {
        //System.out.println("Saving Game Model");
        IGameModel gameModel = ServerModel.getSingleton().getGames().get(gameID);
/*        if(gameID < 0 || gameID >= ServerModel.getSingleton().getGames().size())
            return;*/

        String model = Serializer.serializeObject(gameModel);
        PreparedStatement stmt = null;
        try {
            database.startTransaction();
            String delete = "delete from games where id=?";
            stmt = database.getConnection().prepareStatement(delete);
            stmt.setInt(1,gameID);
            if(stmt.executeUpdate() != 1) {
                //System.out.println("Game does not exist");
            }
            String query = "insert into games (id, game) values (?, ?)";
            //System.out.println("Created query");
            stmt = database.getConnection().prepareStatement(query);
            //System.out.println("Created connection");
            //System.out.println("gameID: "+gameID);
            stmt.setInt(1, gameID);
            stmt.setString(2, model);
            if(stmt.executeUpdate() != 1) {
                //System.out.println("Could not save game");
                throw new DatabaseException("Could not save game");
            }
            clearCommands(gameID);
        } catch (DatabaseException | SQLException e) {
            //System.out.println("Could not save game 2");
            database.endTransaction(false);
            throw e;
        } finally {
            //System.out.println("Closing connection");
            SQLiteDatabase.safeClose(stmt);
        }
        database.endTransaction(true);
    }

    private void clearCommands(int gameId) throws SQLException, DatabaseException {
        //System.out.println("Clearing Commands");
        PreparedStatement stmt = null;
        String query = "delete from commands where game_id=?";
        stmt = database.getConnection().prepareStatement(query);
        stmt.setInt(1,gameId);
        if(stmt.executeUpdate() != 1) {
            //System.out.println("Could not delete commands");
        }
    }

    @Override
    public int getNumCommands() {
        return numCommands;
    }

    @Override
    public void setNumCommands(int numCommands) {
        this.numCommands = numCommands;
    }


}
