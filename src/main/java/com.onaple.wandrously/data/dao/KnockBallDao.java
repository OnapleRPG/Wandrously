package com.onaple.wandrously.data.dao;

import com.onaple.wandrously.Wandrously;
import com.onaple.wandrously.data.beans.KnockBallBean;
import com.onaple.wandrously.data.handlers.DatabaseHandler;

import javax.naming.ServiceUnavailableException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class KnockBallDao {
    private static String errorDatabasePrefix = "Error while connecting to database : ";

    /**
     * Generate database tables if they do not exist
     */
    public static void createTableIfNotExist() {
        String query = "CREATE TABLE IF NOT EXISTS knockBall (id INTEGER PRIMARY KEY, uuid VARCHAR(50), casterUuid VARCHAR(50))";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DatabaseHandler.getDatasource().getConnection();
            statement = connection.prepareStatement(query);
            statement.execute();
            statement.close();
        } catch (ServiceUnavailableException e) {
            Wandrously.getLogger().error(errorDatabasePrefix.concat(e.getMessage()));
        } catch (SQLException e) {
            Wandrously.getLogger().error("Error while creating knockball table : " + e.getMessage());
        } finally {
            closeConnection(connection, statement, null);
        }
    }

    /**
     * Add a knockball into database
     * @param knockBallBean Knockball spawned
     */
    public static void addKnockBall(KnockBallBean knockBallBean) {
        String query = "INSERT INTO knockBall (uuid, casterUuid) VALUES (?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DatabaseHandler.getDatasource().getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, knockBallBean.getUuid());
            statement.setString(2, knockBallBean.getCasterUuid());
            statement.execute();
            statement.close();
        } catch (ServiceUnavailableException e) {
            Wandrously.getLogger().error(errorDatabasePrefix.concat(e.getMessage()));
        } catch (SQLException e) {
            Wandrously.getLogger().error("Error while inserting knockball : " + e.getMessage());
        } finally {
            closeConnection(connection, statement, null);
        }
    }

    public static Optional<KnockBallBean> getKnockBallByUuid(String uuid) {
        String query = "SELECT id, uuid, casterUuid FROM knockBall WHERE uuid = ?";
        Optional<KnockBallBean> knockBallBeanOptional = Optional.empty();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            connection = DatabaseHandler.getDatasource().getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, uuid);
            results = statement.executeQuery();
            if (results.next()) {
                knockBallBeanOptional = Optional.of(new KnockBallBean(results.getInt("id"), results.getString("uuid"), results.getString("casterUuid")));
            }
            statement.close();
        } catch (ServiceUnavailableException e) {
            Wandrously.getLogger().error(errorDatabasePrefix.concat(e.getMessage()));
        } catch (SQLException e) {
            Wandrously.getLogger().error("Error while fetching knockball with uuid : " + e.getMessage());
        } finally {
            closeConnection(connection, statement, results);
        }
        return knockBallBeanOptional;
    }

    public static void deleteMonsterByUuid(String uuid) {
        String query = "DELETE FROM knockBall WHERE uuid = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DatabaseHandler.getDatasource().getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, uuid);
            statement.execute();
            statement.close();
        } catch (ServiceUnavailableException e) {
            Wandrously.getLogger().error(errorDatabasePrefix.concat(e.getMessage()));
        } catch (SQLException e) {
            Wandrously.getLogger().error("Error while deleting knockball : " + e.getMessage());
        } finally {
            closeConnection(connection, statement, null);
        }
    }

    /**
     * Close a database connection
     * @param connection Connection to close
     */
    private static void closeConnection(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                Wandrously.getLogger().error("Error while closing result set : " + e.getMessage());
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                Wandrously.getLogger().error("Error while closing statement : " + e.getMessage());
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                Wandrously.getLogger().error("Error while closing connection : " + e.getMessage());
            }
        }
    }
}
