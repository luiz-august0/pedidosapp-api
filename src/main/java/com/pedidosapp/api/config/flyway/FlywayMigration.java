package com.pedidosapp.api.config.flyway;

import com.pedidosapp.api.config.multitenancy.TenantContext;
import com.pedidosapp.api.model.entities.User;
import com.pedidosapp.api.model.enums.EnumUserRole;
import com.pedidosapp.api.repository.UserRepository;
import com.pedidosapp.api.service.UserService;
import com.pedidosapp.api.utils.Utils;
import jakarta.annotation.PostConstruct;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class FlywayMigration {
    @Autowired
    DataSource dataSource;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void migrate() {
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

        flywayMigrate("public");

        for (String schema : schemas) {
            TenantContext.clear();

            flywayMigrate(schema);

            TenantContext.setCurrentTenant(schema);
            User user = userRepository.findByLogin("admin");

            if (Utils.isEmpty(user)) {
                String encryptedPassword = new BCryptPasswordEncoder().encode(schema + "123logar");
                user = new User("admin", encryptedPassword, EnumUserRole.ADMIN);

                userRepository.save(user);
            }
        }
    }

    private void flywayMigrate(String schema) {
        Flyway.configure()
                .dataSource(dataSource)
                .schemas(schema)
                .locations(schema.equals("public") ? "classpath:db/migration/public" : "classpath:db/migration/tenant")
                .load()
                .migrate();
    }
}