package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MyEntityRepository {
  private final EntityManager entityManager;

  public void delete(final MyEntity entity) {
    var transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.remove(entity);
      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
    }
  }

  public Optional<MyEntity> findByID(final UUID primaryKey) {
    try {
      return Optional.ofNullable(entityManager.find(MyEntity.class, primaryKey));
    } catch (Exception e) {
      /* Nevermind */
    }
    return Optional.empty();
  }

  public void save(final MyEntity entity) {
    var transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(entity);
      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
    }
  }

  public void update(final MyEntity entity) {
    var transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.merge(entity);
      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
    }
  }
}
