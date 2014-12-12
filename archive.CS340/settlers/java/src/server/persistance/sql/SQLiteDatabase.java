package server.persistance.sql;

import java.sql.*;

public class SQLiteDatabase {

    private Connection connection;
    private static final String driver = "org.sqlite.JDBC";
    private static final String dbDirectory = "../data/database/";
    private static final String dbFile = "catan.sqlite";
    private static final String dbTest = "catan-test.sqlite";
    private static final String connectionURL = "jdbc:sqlite:" + dbDirectory + dbFile;
    private static final String connectionTestURL = "jdbc:sqlite:" + dbDirectory + dbTest;


    public static void init() throws DatabaseException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("Could not load database driver", e);
        }
    }

    public SQLiteDatabase() {
        this.connection = null;
		try {
			init();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

	}

    public Connection getConnection() {
        return this.connection;
    }


    /**
     * opens a connection to the database and starts a transaction
     * @throws DatabaseException
     */
    public void startTransaction() throws DatabaseException {
        try {
            connection = DriverManager.getConnection(connectionURL);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DatabaseException("Could not connect to database. Make sure " +
                    dbFile + " is available in " + dbDirectory, e);
        }
    }

    /**
     * commits or rolls back the transaction and closes the connection
     * @param commit
     */
    public void endTransaction(boolean commit) {
        if (connection != null) {
            try {
                if (commit) {
                    connection.commit();
                } else {
                    connection.rollback();
                }
            } catch (SQLException e) {
                //System.out.println("Could not end transaction");
                e.printStackTrace();
            } finally {
                safeClose(connection);
                connection = null;
            }
        }
    }

    public void startTestTransaction() throws DatabaseException {
        try {
            connection = DriverManager.getConnection(connectionTestURL);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DatabaseException("Could not connect to database. Make sure " +
                    dbTest + " is available in ./" + dbDirectory, e);
        }
    }

    public static void safeClose(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ignored) {}
        }
    }

    public static void safeClose(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ignored) {}
        }
    }

    public static void safeClose(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ignored) {}
        }
    }

    public static void safeClose(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ignored) {}
        }
    }




}
