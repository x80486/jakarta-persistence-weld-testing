package org.acme;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

@Singleton
public class EntityManagerProducer {
  @Produces
  @Dependent
  public EntityManager entityManager() {
    return Persistence.createEntityManagerFactory("test").createEntityManager();
  }

  public void close(@Disposes final EntityManager entityManager) {
    if (entityManager != null && entityManager.isOpen()) {
      entityManager.close();
    }
  }
}
