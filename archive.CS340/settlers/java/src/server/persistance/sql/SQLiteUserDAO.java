package server.persistance.sql;

import server.model.game.IUser;
import server.model.game.User;
import server.persistance.IUsersDAO;

import java.sql.*;
import java.util.*;

/**
 * @author Mitch on 12/8/14.
 */
public class SQLiteUserDAO implements IUsersDAO {

    private SQLiteDatabase database;

    public SQLiteUserDAO(SQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public List<IUser> getUsers() {
        List<IUser> users = new ArrayList<IUser>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            database.startTransaction();
            String query = "select * from users";

            stmt = database.getConnection().prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Integer userID = rs.getInt(1);
                String username = rs.getString(2);
                String password = rs.getString(3);
                IUser user = new User();
                user.setId(userID);
                user.setName(username);
                user.setPassword(password);
                users.add(user);
            }
        } catch (SQLException e) {
            database.endTransaction(false);
            System.out.println("Error Reading SQLite Database: ");
            e.printStackTrace();
        } catch (DatabaseException e) {
            database.endTransaction(false);
            System.out.println("Error in Database: ");
            e.printStackTrace();
        } finally {
            SQLiteDatabase.safeClose(rs);
            SQLiteDatabase.safeClose(stmt);
        }
        database.endTransaction(true);
        return users;
    }

    @Override
    public void saveUser(IUser user) {
        PreparedStatement stmt = null;
        try {
            database.startTransaction();
            String query = "insert into users (user_id, username, password) values (?, ?, ?)";

            stmt = database.getConnection().prepareStatement(query);
            stmt.setInt(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getPassword());
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
    }


}
