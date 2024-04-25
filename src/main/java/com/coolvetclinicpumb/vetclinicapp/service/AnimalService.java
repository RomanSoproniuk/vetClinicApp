package com.coolvetclinicpumb.vetclinicapp.service;

import com.coolvetclinicpumb.vetclinicapp.dto.AnimalResponseDto;
import com.coolvetclinicpumb.vetclinicapp.dto.AnimalSearchParameters;
import java.io.IOException;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AnimalService {
    ResponseEntity<?> saveAnimals(MultipartFile animalsFile) throws IOException, JAXBException;

    List<AnimalResponseDto> returnAnimalsByParams(AnimalSearchParameters animalSearchParameters,
                                                  Pageable pageable);
}
