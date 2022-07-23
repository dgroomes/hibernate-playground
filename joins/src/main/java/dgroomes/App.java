package dgroomes;

import dgroomes.db.Observation;
import dgroomes.db.Observation_;
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
 * A Hibernate ORM example program showcasing joins. See the README for more information.
 */
public class App {

  private static final Logger log = LoggerFactory.getLogger(App.class);
  public static final String PERSISTENCE_UNIT_NAME = "hibernate-playground-joins";

  public static void main(String[] args) {
    EntityManagerFactory entityManagerFactory = boostrapJakartaPersistence();

    try (var sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
         var session = sessionFactory.openSession()) {

      applySchema(session);
      queryWithHql(session);
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
   * Why bother with a {@link EntityManagerFactory}? Well, I was happy with {@link SessionFactory} but it turns out that
   * using features like the latest Criteria API only works this way. And I really want to use the Criteria API because
   * it seems like the only way to get "fetch joins" to work. And I really don't want the dreaded "N+1 selects" problem.
   * <p>
   * In my opinion, this is on the high end of highly abstracted APIs. It's interesting that you can create a
   * {@link EntityManagerFactory} and then unwrap it into a {@link SessionFactory} but you can't create a {@link Metadata}
   * and then further bootstrap it into a {@link EntityManagerFactory}. I'm surprised I need a `persistence.xml` file
   * because haven't we been in the "annotations are better than XML" Java world since like 2012? Anyway, XML is actually
   * pretty nice in Intellij because Intellij is so smart about the XML config files for the big libraries frameworks.
   * The auto-complete is nice.
   */
  private static EntityManagerFactory boostrapJakartaPersistence() {
    // Make sure Hibernate uses the logging sub-system of our choice: SLF4J.
    // For information, see https://stackoverflow.com/a/19488546
    System.setProperty("org.jboss.logging.provider", "slf4j");

    return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
  }

  /**
   * Apply the database schema and add some test data;
   */
  private static void applySchema(Session session) {
    session.doWork(connection -> {
      try (var statement = connection.createStatement()) {

        statement.execute(Util.readClasspathResource("/schema/1-create-table-observation-types.ddl"));
        statement.execute(Util.readClasspathResource("/schema/2-create-table-observations.ddl"));
        statement.execute(Util.readClasspathResource("/schema/3-sample-observation-types.sql"));
        statement.execute(Util.readClasspathResource("/schema/4-sample-observations.sql"));
      } catch (SQLException e) {
        throw new IllegalStateException("Unexpected error while applying the database schema", e);
      }
    });
  }

  /**
   * Query the database using Hibernate HQL (Hibernate Query Language),
   *
   * When you enable SQL statement logging, you'll notice that Hibernate issues three 'select' statements to the database.
   * The first is to the 'observations' table, but then there are two more to the 'observation_types' table which are
   * used to get the 'description' column. This is kind of annoying because it slows down the application, makes the
   * logs noisy and creates more points of failure (e.g. what if the first request is fine, but the second or third requests
   * fail?).
   */
  private static void queryWithHql(Session session) {
    var observations = session.createQuery("select o from Observation o join fetch ObservationType ot", Observation.class).list();
    log.info("[Query using HQL] Found results...");
    for (var observation : observations) {
      log.info("Observation (id={}, type={}): {}", observation.getId(), observation.getType().getDescription(), observation.getObservation());
    }
    log.info("");
  }

  /**
   * Query the database using Hibernate's Criteria API.
   *
   * The Criteria API (the Jakarta-based Criteria API, not the original Hibernate-based one which it replaces) is powerful.
   * It offers type safety and other options like fetch types. This comes at cost though. The procedural Java code that
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

    log.info("[Query using HQL] Found results...");
    for (var observation : observations) {
      log.info("Observation (id={}, type={}): {}", observation.getId(), observation.getType().getDescription(), observation.getObservation());
    }
    log.info("");
  }
}
