package dgroomes;

import dgroomes.db.Observation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Simulate user interactions with the application.
 * <p>
 * This class concerns itself with simulating user behavior. Ultimately we want to exercise Hibernate's caching behavior.
 * This class should not concern itself with the bootstrapping of the database or the {@link jakarta.persistence.EntityManager}.
 */
public class InteractionSimulator {

  private static final Logger log = LoggerFactory.getLogger(InteractionSimulator.class);

  private final EntityManagerFactory entityManagerFactory;

  public InteractionSimulator(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  /**
   * Simulate a request by a user that queries for the same entity twice.
   */
  public void queryEntityTwice() {
    log.info("[queryEntityTwice]");

    EntityManager entityManager = null;
    try {
      entityManager = entityManagerFactory.createEntityManager();

      log.info("Executing a query...");
      // Will Hibernate cache the results? We will see...
      query(entityManager);

      log.info("Execute the same query a second time...");
      // Is there any entity data from the previous query in Hibernate's caching system? Will Hibernate use this cached
      // data? We're in the same session so first-level should work. Remember, an EntityManager represents a session.
      query(entityManager);
    } finally {
      Optional.ofNullable(entityManager).ifPresent(EntityManager::close);
    }
  }

  private static void query(EntityManager entityManager) {
    Observation observation = entityManager.find(Observation.class, 1L);

    log.info("The query found ...");
    log.info("Observation (id={}, type={}): {}", observation.getId(), observation.getType().getDescription(), observation.getObservation());
    log.info("");
  }
}
