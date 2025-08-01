package org.example.persistence.migration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.exception.LiquibaseException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MigrationStrategy {

    private final Connection connection;

    @SuppressWarnings("deprecation")
    public void executeMigration() {
        var originalOut = System.out;
        var originalErr = System.err;

        try (var fos = new FileOutputStream("Liquibase.log");
             PrintStream ps = new PrintStream(fos)) {
            
            System.setOut(ps);
            System.setErr(ps);

            // Create JdbcConnection (doesn't need to be closed as it wraps the provided connection)
            var jdbcConnection = new JdbcConnection(connection);
            
            // Create and properly close Liquibase instance
            try (Liquibase liquibase = new Liquibase(
                    "db/changelog/db.changelog-master.yaml",
                    new ClassLoaderResourceAccessor(),
                    jdbcConnection)) {
                
                // Use non-deprecated update method
                liquibase.update(new Contexts(), new LabelExpression());

                
            } catch (LiquibaseException e) {
                e.printStackTrace(ps);
            }

        } catch (IOException e) {
            e.printStackTrace(originalErr); // Use original error stream since we might not have the file stream
        } finally {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
    }
}
