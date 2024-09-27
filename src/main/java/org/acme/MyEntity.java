package org.acme;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entities")
public class MyEntity {
  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name = "name")
  private String name;

  @PrePersist
  void prePersist() {
    id = UUID.randomUUID();
  }
}
