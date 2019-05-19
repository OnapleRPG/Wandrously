package com.onaple.wandrously.data.handlers;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.sql.SqlService;

import javax.naming.ServiceUnavailableException;
import javax.sql.DataSource;
import java.sql.SQLException;

public class DatabaseHandler {
    private static String JDBC_URL = "jdbc:sqlite:./wandrously.db";

    private static SqlService sqlService;

    public static DataSource getDatasource() throws SQLException, ServiceUnavailableException {
        if (sqlService == null) {
            sqlService = Sponge.getServiceManager().provide(SqlService.class).orElseThrow(() -> new ServiceUnavailableException("Sponge service manager not provided."));
        }
        return sqlService.getDataSource(JDBC_URL);
    }
}
