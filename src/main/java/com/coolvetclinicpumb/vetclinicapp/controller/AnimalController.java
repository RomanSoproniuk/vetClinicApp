package com.coolvetclinicpumb.vetclinicapp.controller;

import com.coolvetclinicpumb.vetclinicapp.dto.AnimalResponseDto;
import com.coolvetclinicpumb.vetclinicapp.dto.AnimalSearchParameters;
import com.coolvetclinicpumb.vetclinicapp.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import javax.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequiredArgsConstructor
@Tag(name = "Animal API", description = """
        In this API, you can perform certain actions on animals, for example,
         add them to the database using a certain file format, or receive
          certain animals by dynamic request
            """)
public class AnimalController {
    private final AnimalService animalService;

    @Operation(summary = "Save the animals to the database from the file", description = """ 
        With the help of this endpoint, you can save animals to the database, note that you
         must specify all fields, otherwise the animal without all fields will not be saved.
          Also, at the moment, this API supports two types of files for saving - XML and CSS.
           Maybe in the future the number of supported files for saving animals will increase =)
            """)
    @PostMapping("/files/uploads")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> saveAnimals(@RequestParam("file") MultipartFile file)
            throws IOException, JAXBException {
        return animalService.saveAnimals(file);
    }

    @Operation(summary = "Get animals according to certain parameters", description = """ 
       When using this endpoint, you can get certain animals by entering the parameters
        you are interested in, if no animals are found according to these parameters,
         you will get an empty list.
            """)
    @GetMapping("/search")
    public List<AnimalResponseDto> returnAnimalsByParams(
            AnimalSearchParameters animalSearchParameters,
            Pageable pageable) {
        return animalService.returnAnimalsByParams(animalSearchParameters, pageable);
    }
}
