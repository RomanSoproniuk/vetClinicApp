package com.coolvetclinicpumb.vetclinicapp.service.implementations;

import com.coolvetclinicpumb.vetclinicapp.dto.AnimalResponseDto;
import com.coolvetclinicpumb.vetclinicapp.dto.AnimalSearchParameters;
import com.coolvetclinicpumb.vetclinicapp.exception.FileFormatNotSupported;
import com.coolvetclinicpumb.vetclinicapp.mapper.AnimalMapper;
import com.coolvetclinicpumb.vetclinicapp.model.Animal;
import com.coolvetclinicpumb.vetclinicapp.model.Category;
import com.coolvetclinicpumb.vetclinicapp.repository.AnimalRepository;
import com.coolvetclinicpumb.vetclinicapp.repository.CategoryRepository;
import com.coolvetclinicpumb.vetclinicapp.repository.criteriaquery.AnimalSpecificationBuilder;
import com.coolvetclinicpumb.vetclinicapp.service.AnimalService;
import com.coolvetclinicpumb.vetclinicapp.service.CsvHandleService;
import com.coolvetclinicpumb.vetclinicapp.service.XmlHandleService;
import java.io.IOException;
import java.util.List;
import javax.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {
    private static final byte FIRST_CATEGORY = 0;
    private static final byte SECOND_CATEGORY = 1;
    private static final byte THIRD_CATEGORY = 2;
    private static final byte FOURTH_CATEGORY = 3;
    private static final byte PRICE_TWENTY = 20;
    private static final byte PRICE_FORTY = 40;
    private static final byte PRICE_SIXTY = 60;
    private static final byte ZERO_PRICE = 0;
    private static final byte MINIMAL_WEIGHT = 1;
    private static final byte MINIMAL_LENGTH_SIZE_FOR_PARAMS = 2;
    private final CsvHandleService csvHandleService;
    private final CategoryRepository categoryRepository;
    private final AnimalRepository animalRepository;
    private final XmlHandleService xmlHandleService;
    private final AnimalMapper animalMapper;
    private final AnimalSpecificationBuilder animalSpecificationBuilder;

    @Override
    public ResponseEntity<?> saveAnimals(MultipartFile file) throws IOException, JAXBException {
        if (file.isEmpty()) {
            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
        }
        String contentType = file.getContentType();
        if (contentType != null && contentType.equals("text/csv")) {
            List<Animal> animals = csvHandleService.handleCsvFileWithAnimals(file);
            validateAndSaveAnimalsToDb(animals);
            return new ResponseEntity<>("CSV file uploaded successfully", HttpStatus.OK);
        }
        if (contentType != null && contentType.equals("application/xml")) {
            List<Animal> animals = xmlHandleService.handleXmlFileWithAnimal(file);
            validateAndSaveAnimalsToDb(animals);
            return new ResponseEntity<>("XML file uploaded successfully", HttpStatus.OK);
        }
        throw new FileFormatNotSupported("This file format is not yet supported for reading");
    }

    @Override
    public List<AnimalResponseDto> returnAnimalsByParams(
            AnimalSearchParameters animalSearchParameters,
            Pageable pageable) {
        Specification<Animal> animalSpecification = animalSpecificationBuilder
                .build(animalSearchParameters);
        return animalRepository.findAll(animalSpecification, pageable).stream()
                .map(animalMapper::toDto)
                .toList();
    }

    private void validateAndSaveAnimalsToDb(List<Animal> animals) {
        List<Category> allCategoryFromDb = categoryRepository.findAll();
        List<Animal> filterAnimals = animals.stream()
                .filter(animal -> ((animal.getName() != null
                        && animal.getName().length() > MINIMAL_LENGTH_SIZE_FOR_PARAMS))
                        && (animal.getType() != null
                        && animal.getType().length() > MINIMAL_LENGTH_SIZE_FOR_PARAMS)
                        && (animal.getSex() != null)
                        && (animal.getWeight() != null && animal.getWeight() > MINIMAL_WEIGHT)
                        && (animal.getCost() != null && animal.getCost() > ZERO_PRICE))
                .map(animal -> {
                    if (animal.getCost() <= PRICE_TWENTY) {
                        animal.setCategory(allCategoryFromDb.get(FIRST_CATEGORY));
                    } else if (animal.getCost() <= PRICE_FORTY) {
                        animal.setCategory(allCategoryFromDb.get(SECOND_CATEGORY));
                    } else if (animal.getCost() <= PRICE_SIXTY) {
                        animal.setCategory(allCategoryFromDb.get(THIRD_CATEGORY));
                    } else {
                        animal.setCategory(allCategoryFromDb.get(FOURTH_CATEGORY));
                    }
                    return animal;
                })
                .toList();
        animalRepository.saveAll(filterAnimals);
    }
}
