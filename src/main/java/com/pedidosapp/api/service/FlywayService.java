package com.pedidosapp.api.service;

import com.pedidosapp.api.config.multitenancy.TenantContext;
import com.pedidosapp.api.constants.FlywayConstants;
import com.pedidosapp.api.model.entities.User;
import com.pedidosapp.api.model.enums.EnumUserRole;
import com.pedidosapp.api.repository.UserRepository;
import com.pedidosapp.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlywayService {

    @Autowired
    private DataSource dataSource;

    private final UserRepository userRepository;

    public void doMigrations() {
        List<String> schemas = new ArrayList<>();

        try {
            ResultSet resultSet = dataSource.getConnection().createStatement().executeQuery(
                    " select schema_name as schema from information_schema.schemata " +
                            " where schema_name not in ('information_schema', 'public') and substring(schema_name, 1, 2) <> 'pg' "
            );

            while (resultSet.next()) schemas.add(resultSet.getString(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        migrateAndRepair("public");

        for (String schema : schemas) {
            TenantContext.clear();

            migrateAndRepair(schema);

            TenantContext.setCurrentTenant(schema);
            User user = userRepository.findByLogin(FlywayConstants.DEFAULT_USER);

            if (Utils.isEmpty(user)) {
                String encryptedPassword = new BCryptPasswordEncoder().encode(schema + FlywayConstants.DEFAULT_PASSWORD);
                user = new User(FlywayConstants.DEFAULT_USER, encryptedPassword, EnumUserRole.ADMIN);

                userRepository.save(user);
            }
        }
    }

    private void migrateAndRepair(String schema) {
        repair(schema);
        migrate(schema);
    }

    private Flyway configure(String schema) {
        return Flyway.configure()
                .dataSource(dataSource)
                .schemas(schema)
                .locations(schema.equals("public") ? FlywayConstants.PUBLIC_CLASSPATH : FlywayConstants.TENANT_CLASSPATH)
                .load();
    }

    private void migrate(String schema) {
        configure(schema).migrate();
    }

    private void repair(String schema) {
        configure(schema).repair();
    }

}