package com.coolvetclinicpumb.vetclinicapp.service;

import com.coolvetclinicpumb.vetclinicapp.model.Animal;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface CsvHandleService {
    List<Animal> handleCsvFileWithAnimals(MultipartFile file) throws IOException;
}
