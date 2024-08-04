package util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseUtil {

    private static HikariDataSource hikariDataSource;

    static {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost/penyewaan_mobil");
        hikariConfig.setUsername("LENOVO");
        hikariConfig.setPassword("");

        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setIdleTimeout(60000);
        hikariConfig.setMaxLifetime(60 * 60 * 60000);

         hikariDataSource = new HikariDataSource(hikariConfig);
    }


    public static HikariDataSource getDataSource() {
        return hikariDataSource;
    }
}
