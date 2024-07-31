package dgroomes.caching;

import dgroomes.caching.db.Observation;
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
   * Simulate a request by a user that does a look-up for an entity; and then does the same look-up a second time in the
   * same session.
   */
  public void findSameEntityTwiceSameSession() {
    EntityManager entityManager = null;
    try {
      entityManager = entityManagerFactory.createEntityManager();

      log.info("Executing a 'find' operation...");
      find(entityManager);

      log.info("Executing the same 'find' operation a second time...");
      find(entityManager);
    } finally {
      Optional.ofNullable(entityManager).ifPresent(EntityManager::close);
    }
  }

  /**
   * Simulate two user requests. The first request does a look-up for an entity. The second request occurs in a different
   * session and does a look-up for the same entity.
   */
  public void findSameEntityTwiceTwoSessions() {
    EntityManager entityManager = null;
    try {
      entityManager = entityManagerFactory.createEntityManager();
      log.info("Executing a 'find' operation for user 1 ...");
      find(entityManager);
    } finally {
      Optional.ofNullable(entityManager).ifPresent(EntityManager::close);
    }

    entityManager = null;
    try {
      entityManager = entityManagerFactory.createEntityManager();

      log.info("Executing the same 'find' operation for user 2 ...");
      find(entityManager);
    } finally {
      Optional.ofNullable(entityManager).ifPresent(EntityManager::close);
    }
  }

  private static void find(EntityManager entityManager) {
    Observation observation = entityManager.find(Observation.class, 1L);

    log.info("Found ...");
    log.info("Observation (id={}, type={}): {}", observation.getId(), observation.getType().getDescription(), observation.getObservation());
    log.info("");
  }
}
