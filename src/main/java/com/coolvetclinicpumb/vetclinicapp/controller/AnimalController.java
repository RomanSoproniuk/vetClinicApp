package com.coolvetclinicpumb.vetclinicapp.controller;

import com.coolvetclinicpumb.vetclinicapp.dto.AnimalResponseDto;
import com.coolvetclinicpumb.vetclinicapp.dto.AnimalSearchParameters;
import com.coolvetclinicpumb.vetclinicapp.service.AnimalService;
import java.io.IOException;
import java.util.List;
import javax.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;

    @PostMapping("/files/uploads")
    public ResponseEntity<?> saveAnimals(@RequestParam("file") MultipartFile file)
            throws IOException, JAXBException {
        return animalService.saveAnimals(file);
    }

    @GetMapping("/search")
    public List<AnimalResponseDto> returnAnimalsByParams(
            AnimalSearchParameters animalSearchParameters,
            Pageable pageable) {
        return animalService.returnAnimalsByParams(animalSearchParameters, pageable);
    }
}
