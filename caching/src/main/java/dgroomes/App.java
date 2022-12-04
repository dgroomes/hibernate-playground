package dgroomes;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Optional;

/**
 * An illustration of Hibernate's caching behavior. See the README for more information.
 */
public class App {

  private static final Logger rootLog = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);

  public static void main(String[] args) {
    // Purposely quiet down the Hibernate logs during the bootstrapping phase. The Hibernate framework has lots and lots
    // of log statements, which is great for debugging, but we're not interested in debugging the bootstrap phase. We're
    // interested in peering into Hibernate when it's executing an application query.
    rootLog.setLevel(Level.WARN);

    EntityManagerFactory entityManagerFactory = null;

    try {
      entityManagerFactory = boostrapJakartaPersistence();

      var sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);

      applySchema(sessionFactory);
      // The boostrap phase is over. Let's amp up the Hibernate logs so we can see Hibernate do it's thing when it
      // executes application queries.
      rootLog.setLevel(Level.TRACE);

      // Simulate user interactions. This is the interesting part.
      InteractionSimulator interactionSimulator = new InteractionSimulator(entityManagerFactory);
      interactionSimulator.querySameEntityTwiceBySameUser();
      interactionSimulator.querySameEntityByTwoUsers();

      // Quiet the logs again.
      rootLog.setLevel(Level.WARN);
    } finally {
      Optional.ofNullable(entityManagerFactory).ifPresent(EntityManagerFactory::close);
    }
  }

  /**
   * Do all the boilerplate things to "bootstrap" a Jakarta persistence system (which is implemented by Hibernate).
   * <p>
   * For more information, read the docs: <a href="https://docs.jboss.org/hibernate/orm/6.1/userguide/html_single/Hibernate_User_Guide.html#bootstrap-jpa">link</a>
   */
  private static EntityManagerFactory boostrapJakartaPersistence() {
    // Make sure Hibernate uses the logging sub-system of our choice: SLF4J.
    // For information, see https://stackoverflow.com/a/19488546
    System.setProperty("org.jboss.logging.provider", "slf4j");

    return Persistence.createEntityManagerFactory("hibernate-playground-criteria");
  }

  /**
   * Apply the database schema and add some test data.
   */
  private static void applySchema(SessionFactory sessionFactory) {
    try (var session = sessionFactory.openSession()) {
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
  }
}
