package dgroomes;

import dgroomes.db.Observation;
import dgroomes.db.ObservationType;
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
 * An illustration of Hibernate's *Hibernate Query Language*. See the README for more information.
 */
public class App {

  private static final Logger log = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) {
    Metadata metadata = bootstrapHibernate();

    try (var sessionFactory = metadata.buildSessionFactory();
         var session = sessionFactory.openSession()) {

      applySchema(session);
      queryWithHql(session);
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
            .applySetting("hibernate.format_sql", true)
            .applySetting("hibernate.highlight_sql", true)
            .applySetting("hibernate.use_sql_comments", true)
            .build();

    return new MetadataSources(registry)
            .addAnnotatedClass(Observation.class)
            .addAnnotatedClass(ObservationType.class)
            .buildMetadata();
  }

  /**
   * Apply the database schema and add some test data.
   */
  private static void applySchema(Session session) {
    session.doWork(connection -> {
      try (var statement = connection.createStatement()) {

        statement.execute(Util.readClasspathResource("/schema/1-create-table-observation-types.ddl"));
        statement.execute(Util.readClasspathResource("/schema/2-create-table-observations.ddl"));
        statement.execute(Util.readClasspathResource("/schema/3-sample-observation-types.sql"));
        statement.execute(Util.readClasspathResource("/schema/4-sample-observations.sql"));
      } catch (SQLException e) {
        throw new IllegalStateException("Unexpected error while applying the database schema and sample data", e);
      }
    });
  }

  /**
   * Query the database using Hibernate HQL (Hibernate Query Language),
   * <p>
   * When you enable SQL statement logging, you'll notice that Hibernate issues three 'select' statements to the database.
   * The first is to the 'observations' table, but then there are two more to the 'observation_types' table which are
   * used to get the 'description' column. This is kind of annoying because it slows down the application, makes the
   * logs noisy and creates more points of failure (e.g. what if the first request is fine, but the second or third requests
   * fail?).
   * <p>
   * This pattern of extra fetches is known as the "n + 1 selects" problem. There are options to mitigate it, but they come with
   * trade-offs. One option is to use a "join fetch" in the HQL query. This will cause Hibernate to issue a single SQL
   * statement to the database.
   */
  private static void queryWithHql(Session session) {
    var observations = session.createQuery("select o from Observation o", Observation.class).list();

    // Alternatively, we might issue the following similar query that does a "join fetch" to fetch both the 'observations'
    // and 'observation_types' data in a single SQL statement: "select o from Observation o join fetch o.type".

    log.info("The HQL query found results...");
    for (var observation : observations) {
      log.info("Observation (id={}, type={}): {}", observation.getId(), observation.getType().getDescription(), observation.getObservation());
    }
    log.info("");
  }
}
