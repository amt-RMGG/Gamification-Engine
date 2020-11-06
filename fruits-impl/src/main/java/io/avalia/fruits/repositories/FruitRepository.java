package io.avalia.fruits.repositories;

import io.avalia.fruits.entities.FruitEntity;
import org.springframework.data.repository.CrudRepository;

public interface FruitRepository extends CrudRepository<FruitEntity, Long> {

}
