package dgroomes.criteria;

import dgroomes.criteria.db.Observation;
import dgroomes.criteria.db.Observation_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * An illustration of the Criteria API within the Jakarta Persistence API. See the README for more information.
 */
public class App {

  private static final Logger log = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) {
    EntityManagerFactory entityManagerFactory = bootstrapJakartaPersistence();

    try (var sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
         var session = sessionFactory.openSession()) {

      applySchema(session);
      queryWithCriteria(session);
    }
  }

  /**
   * Do all the boilerplate things to "bootstrap" a Jakarta persistence system (which is implemented by Hibernate).
   * <p>
   * For more information, read the docs: <a href="https://docs.jboss.org/hibernate/orm/6.1/userguide/html_single/Hibernate_User_Guide.html#bootstrap-jpa">link</a>
   * <p>
   * Most of the boilerplate is configuration in the 'META-INF/persistence.xml' configuration file. Study it.
   * <p>
   * In my opinion, this is on the high end of highly abstracted APIs. It's interesting that you can create a
   * {@link EntityManagerFactory} and then unwrap it into a {@link SessionFactory} but you can't create a {@link Metadata}
   * and then further bootstrap it into a {@link EntityManagerFactory}. I'm surprised I need a `persistence.xml` file
   * because haven't we been in the "annotations are better than XML" Java world since like 2012? Anyway, XML is actually
   * pretty nice in Intellij because Intellij is so smart about the XML config files for the big libraries and frameworks.
   * The auto-complete is nice.
   */
  private static EntityManagerFactory bootstrapJakartaPersistence() {
    // Make sure Hibernate uses the logging sub-system of our choice: SLF4J.
    // For information, see https://stackoverflow.com/a/19488546
    System.setProperty("org.jboss.logging.provider", "slf4j");

    return Persistence.createEntityManagerFactory("hibernate-playground-criteria");
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
   * Query the database using Hibernate's Criteria API.
   * <p>
   * The Criteria API (the Jakarta-based Criteria API, not the original Hibernate-based one which it replaces) is powerful.
   * It offers type safety and other options like fetch types. This comes at a cost though. The procedural Java code that
   * it takes to use the Criteria API is not as naturally expressive as the SQL queries we have come to learn and love.
   * That's a trade-off.
   */
  private static void queryWithCriteria(EntityManager entityManager) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Observation> criteria = builder.createQuery(Observation.class);

    Root<Observation> root = criteria.from(Observation.class);
    criteria.select(root);

    // This configuration is powerful. This ensures that Hibernate generates one monolithic SQL query to
    // fetch all columns from the "observations" table and all columns from the "observation_types" table.
    // In other words, there are no follow-up 'select' statements sent to the database. These follow-up select statements
    // are known as the "N + 1 selects" problem.
    root.fetch(Observation_.type);

    TypedQuery<Observation> query = entityManager.createQuery(criteria);
    List<Observation> observations = query.getResultList();

    log.info("The Criteria-based query found results...");
    for (var observation : observations) {
      log.info("Observation (id={}, type={}): {}", observation.getId(), observation.getType().getDescription(), observation.getObservation());
    }
    log.info("");
  }
}
