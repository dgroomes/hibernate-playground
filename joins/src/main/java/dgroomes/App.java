package dgroomes;

import dgroomes.db.Observation;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

      queryWithHql(session);
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
   * Query the database using Hibernate HQL (Hibernate Query Language)
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
   * NOT YET IMPLEMENTED
   *
   * Query the database using Hibernates Criteria API.
   */
  //  private static void queryWithCriteria() {
  //    CriteriaBuilder cb = em.getCriteriaBuilder();
  //    CriteriaQuery<Observation> cq = cb.createQuery(Observation.class);
  //    Root<Observation> o = cq.from(Observation.class);
  //
  //    o.fetch("country",JoinType.INNER);
  //
  //    em.createQuery(cq.select(o)).getResultList();
  //  }
}
