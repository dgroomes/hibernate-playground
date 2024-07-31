package dgroomes;

import org.h2.Driver;
import org.hibernate.Session;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * A Hibernate ORM example program. See the README for more information.
 */
public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    private Session session;

    public static void main(String[] args) {
        new App().execute();
    }

    private void execute() {
        var metadata = bootstrapHibernate();
        try (var sessionFactory = metadata.buildSessionFactory();
             var session = sessionFactory.openSession()) {

            this.session = session;
            applySchema();
            insertObservation();
            queryWithHql();
            queryWithSql();
        }
    }

    /**
     * Do all the boilerplate things to "bootstrap" Hibernate. The returned {@link Metadata} should be used to create a
     * {@link org.hibernate.SessionFactory}.
     */
    private static Metadata bootstrapHibernate() {
        // Make sure Hibernate uses the logging sub-system of our choice: SLF4J.
        // For information, see https://stackoverflow.com/a/19488546
        System.setProperty("org.jboss.logging.provider", "slf4j");

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.driver_class", Driver.class.getName())
                .applySetting("hibernate.connection.url", "jdbc:h2:mem:hibernateplayground")
                .build();

        return new MetadataSources(registry)
                .addAnnotatedClass(Observation.class)
                .buildMetadata();
    }

    /**
     * Apply the database schema and add some test data.
     */
    private void applySchema() {
        session.doWork(connection -> {
            try (var statement = connection.createStatement()) {

                statement.execute(Util.readClasspathResource("/1-observations-schema.sql"));
                statement.execute(Util.readClasspathResource("/2-observations-data.sql"));
            } catch (SQLException e) {
                throw new IllegalStateException("Unexpected error while applying the database schema and sample data", e);
            }
        });
    }

    private void insertObservation() {
        var newObservation = new Observation();
        newObservation.setObservation("Programming is fun");
        var transaction = session.beginTransaction();
        session.persist(newObservation);
        transaction.commit();
        log.info("Inserted a new observation and it was auto-assigned the ID: {}", newObservation.getId());
    }

    /**
     * Query the database using Hibernate HQL (Hibernate Query Language)
     */
    private void queryWithHql() {
        var observations = session.createQuery("select o from Observation o", Observation.class).list();
        log.info("The HQL query found results...");
        for (var observation : observations) {
            log.info("Observation ({}): {}", observation.getId(), observation.getObservation());
        }
        log.info("");
    }

    /**
     * Query the database using SQL. Sometimes this is referred to as "raw SQL" to emphasize that it was authored directly
     * by a person instead of generated dynamically by an ORM tool. In Hibernate, this is called a "native query".
     */
    private void queryWithSql() {
        var observations = session.createNativeQuery("select * from observations", Observation.class).list();
        log.info("The hand-written SQL query found results...");
        for (var observation : observations) {
            log.info("Observation ({}): {}", observation.getId(), observation.getObservation());
        }
        log.info("");
    }
}
