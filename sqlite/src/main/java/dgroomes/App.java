package dgroomes;

import dgroomes.db.Observation;
import org.hibernate.Session;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connect Hibernate to a SQLite database. See the README for more information.
 */
public class App {

  private static final Logger log = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) {
    Metadata metadata = boostrapHibernate();

    try (var sessionFactory = metadata.buildSessionFactory();
         var session = sessionFactory.openSession()) {

      queryWithHql(session);
    }
  }

  /**
   * Do all the boilerplate things to "bootstrap" Hibernate. The returned {@link Metadata} should be used to create a
   * {@link org.hibernate.SessionFactory}.
   */
  private static Metadata boostrapHibernate() {
    // Make sure Hibernate uses the logging sub-system of our choice: SLF4J.
    // For information, see https://stackoverflow.com/a/19488546
    System.setProperty("org.jboss.logging.provider", "slf4j");

    StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .applySetting("hibernate.dialect", org.hibernate.community.dialect.SQLiteDialect.class.getName())
            .applySetting("hibernate.connection.driver_class", org.sqlite.JDBC.class.getName())
            .applySetting("hibernate.connection.url", "jdbc:sqlite:observations.db")
            .build();

    return new MetadataSources(registry)
            .addPackage(Observation.class.getPackage()) // doesn't have any effect
            .addAnnotatedClass(Observation.class)
            .buildMetadata();
  }

  /**
   * Query the database using Hibernate HQL (Hibernate Query Language)
   */
  private static void queryWithHql(Session session) {
    var observations = session.createQuery("select o from Observation o", Observation.class).list();
    log.info("[Query using HQL] Found results...");
    for (var observation : observations) {
      log.info("Observation ({}): {}", observation.getId(), observation.getObservation());
    }
    log.info("");
  }
}
