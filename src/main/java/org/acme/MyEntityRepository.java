package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@Transactional
@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MyEntityRepository {
  private final EntityManager entityManager;

  public void delete(final MyEntity entity) {
    entityManager.remove(entity);
  }

  public Optional<MyEntity> findByID(final UUID primaryKey) {
    return Optional.ofNullable(entityManager.find(MyEntity.class, primaryKey));
  }

  public void save(final MyEntity entity) {
    entityManager.persist(entity);
  }

  public void update(final MyEntity entity) {
    entityManager.merge(entity);
  }
}
