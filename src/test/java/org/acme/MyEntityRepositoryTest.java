package org.acme;

import jakarta.inject.Inject;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;

@EnableAutoWeld
@AddBeanClasses(EntityManagerProducer.class)
final class MyEntityRepositoryTest {
  @Inject
  private MyEntityRepository repository;

  @Test
  void delete() {
    final var id = UUID.fromString("305127f3-0df4-4085-a7ea-0ae5e446050d");
    repository.delete(repository.findByID(id).orElseThrow(() -> new IllegalStateException("Entity must be present")));
    Assertions.assertThat(repository.findByID(id)).isEmpty();
  }

  @Test
  void findByID_WhenRecordDoesNotExist() {
    Assertions.assertThat(repository.findByID(UUID.randomUUID())).isEmpty();
  }

  @Test
  void findByID_WhenRecordExists() {
    Assertions.assertThat(repository.findByID(UUID.fromString("61df2cfa-465c-4336-8b1f-a0fcd15f022f")))
        .isPresent()
        .get()
        .usingRecursiveComparison()
        .isEqualTo(new MyEntity(UUID.fromString("61df2cfa-465c-4336-8b1f-a0fcd15f022f"), "Terran"));
  }

  @Test
  void save() {
    final var instance = new MyEntity(UUID.randomUUID(), "Another");
    repository.save(instance);
    Assertions.assertThat(repository.findByID(instance.getId()))
        .isPresent()
        .get()
        .hasNoNullFieldsOrProperties()
        .usingRecursiveComparison()
        .isEqualTo(instance);
  }

  @Test
  void update() {
    final var id = UUID.fromString("1590e402-059a-4558-8205-d9885984c7d5");
    Assertions.assertThat(repository.findByID(id)).hasValueSatisfying(it -> {
      Assertions.assertThat(it)
        .usingRecursiveComparison()
        .isEqualTo(new MyEntity(UUID.fromString("1590e402-059a-4558-8205-d9885984c7d5"), "Zerg"));
      // Update values
      it.setName("Don't You Dare");
      repository.update(it);
    });
    Assertions.assertThat(repository.findByID(id))
        .isPresent()
        .get()
        .hasNoNullFieldsOrProperties()
        .usingRecursiveComparison()
        .isEqualTo(new MyEntity(UUID.fromString("1590e402-059a-4558-8205-d9885984c7d5"), "Don't You Dare"));
  }

  @Test
  void saveFindAndUpdate() {
    final var instance = new MyEntity(UUID.randomUUID(), "ToBe");
    repository.save(instance);
    Assertions.assertThat(repository.findByID(instance.getId())).hasValueSatisfying(it -> {
      Assertions.assertThat(it)
          .hasNoNullFieldsOrProperties()
          .usingRecursiveComparison()
          .isEqualTo(new MyEntity(instance.getId(), "ToBe"));
      it.setName("!ToBe");
      repository.update(it);
    });
    Assertions.assertThat(repository.findByID(instance.getId()))
        .isPresent()
        .get()
        .hasNoNullFieldsOrProperties()
        .usingRecursiveComparison()
        .isEqualTo(new MyEntity(instance.getId(), "!ToBe"));
  }
}
