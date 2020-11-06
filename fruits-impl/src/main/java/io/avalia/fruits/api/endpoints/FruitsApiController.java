package io.avalia.fruits.api.endpoints;

import io.avalia.fruits.api.FruitsApi;
import io.avalia.fruits.api.exceptions.ApiException;
import io.avalia.fruits.api.exceptions.NotFoundException;
import io.avalia.fruits.entities.FruitEntity;
import io.avalia.fruits.api.model.Fruit;
import io.avalia.fruits.repositories.FruitRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FruitsApiController implements FruitsApi {

    @Autowired
    FruitRepository fruitRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createFruit(@ApiParam(value = "", required = true) @Valid @RequestBody Fruit fruit) {
        FruitEntity newFruitEntity = toFruitEntity(fruit);
        fruitRepository.save(newFruitEntity);
        Long id = newFruitEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newFruitEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    public ResponseEntity<List<Fruit>> getFruits() {
        List<Fruit> fruits = new ArrayList<>();
        for (FruitEntity fruitEntity : fruitRepository.findAll()) {
            fruits.add(toFruit(fruitEntity));
        }
        return ResponseEntity.ok(fruits);
    }

    @Override
    public ResponseEntity<Fruit> getFruit(@ApiParam(value = "",required=true) @PathVariable("id") Integer id) {
        FruitEntity existingFruitEntity = fruitRepository.findById(Long.valueOf(id)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(toFruit(existingFruitEntity));
    }

    private FruitEntity toFruitEntity(Fruit fruit) {
        FruitEntity entity = new FruitEntity();
        entity.setColour(fruit.getColour());
        entity.setKind(fruit.getKind());
        entity.setWeight(fruit.getWeight());
        entity.setSize(fruit.getSize());
        entity.setExpirationDate(fruit.getExpirationDate());
        entity.setExpirationDateTime(fruit.getExpirationDateTime());
        return entity;
    }

    private Fruit toFruit(FruitEntity entity) {
        Fruit fruit = new Fruit();
        fruit.setColour(entity.getColour());
        fruit.setKind(entity.getKind());
        fruit.setWeight(entity.getWeight());
        fruit.setSize(entity.getSize());
        fruit.setExpirationDate(entity.getExpirationDate());
        OffsetDateTime dateTime = entity.getExpirationDateTime();
        fruit.setExpirationDateTime(entity.getExpirationDateTime());
        return fruit;
    }

}
