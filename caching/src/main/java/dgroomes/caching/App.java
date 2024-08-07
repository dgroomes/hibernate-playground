package dgroomes.caching;

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

  private static final Logger hibernateLog = (Logger) LoggerFactory.getLogger("org.hibernate");
  private static final org.slf4j.Logger log = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) {
    // Purposely quiet down the Hibernate logs during the bootstrapping phase. The Hibernate framework has lots and lots
    // of log statements, which is great for debugging, but we're not interested in debugging the bootstrap phase. We're
    // interested in peering into Hibernate when it's executing an application query.
    hibernateLog.setLevel(Level.WARN);

    EntityManagerFactory emf = null;
    try {
      emf = bootstrapJakartaPersistence("without-second-level-caching");

      var sessionFactory = emf.unwrap(SessionFactory.class);

      applySchema(sessionFactory);
      // The bootstrap phase is over. Let's amp up the Hibernate logs so we can see Hibernate do it's thing when it
      // executes application queries.
      hibernateLog.setLevel(Level.TRACE);

      // Simulate user interactions. This is the interesting part.
      InteractionSimulator interactionSimulator = new InteractionSimulator(emf);
      log.info("Scenario 1: 'first-level cache hit'");
      interactionSimulator.findSameEntityTwiceSameSession();
      log.info("Scenario 2: 'first-level cache miss'");
      interactionSimulator.findSameEntityTwiceTwoSessions();

      // Quiet the logs again.
      hibernateLog.setLevel(Level.WARN);
    } finally {
      Optional.ofNullable(emf).ifPresent(EntityManagerFactory::close);
    }

    // Now, repeat a similar experiment but wire a second-level cache into the Hibernate software machinery.
    emf = null;
    try {
      emf = bootstrapJakartaPersistence("with-second-level-caching");

      var sessionFactory = emf.unwrap(SessionFactory.class);

      applySchema(sessionFactory);
      hibernateLog.setLevel(Level.TRACE);

      InteractionSimulator interactionSimulator = new InteractionSimulator(emf);
      log.info("Scenario 3: 'second-level cache hit'");
      interactionSimulator.findSameEntityTwiceTwoSessions();

      hibernateLog.setLevel(Level.WARN);
    } finally {
      Optional.ofNullable(emf).ifPresent(EntityManagerFactory::close);
    }
  }

  /**
   * Do all the boilerplate things to "bootstrap" a Jakarta persistence system (which is implemented by Hibernate).
   * <p>
   * For more information, read the docs: <a href="https://docs.jboss.org/hibernate/orm/6.1/userguide/html_single/Hibernate_User_Guide.html#bootstrap-jpa">link</a>
   */
  private static EntityManagerFactory bootstrapJakartaPersistence(String persistenceUnitName) {
    // Make sure Hibernate uses the logging sub-system of our choice: SLF4J.
    // For information, see https://stackoverflow.com/a/19488546
    System.setProperty("org.jboss.logging.provider", "slf4j");

    return Persistence.createEntityManagerFactory(persistenceUnitName);
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
