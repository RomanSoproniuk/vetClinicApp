package com.coolvetclinicpumb.vetclinicapp.service.implementations;

import com.coolvetclinicpumb.vetclinicapp.model.Animal;
import com.coolvetclinicpumb.vetclinicapp.service.CsvHandleService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CsvHandlerServiceImpl implements CsvHandleService {

    @Override
    public List<Animal> handleCsvFileWithAnimals(MultipartFile file) throws IOException {
        Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        CsvToBean<Animal> csvToBean = new CsvToBeanBuilder<Animal>(reader)
                .withType(Animal.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        return csvToBean.parse();
    }
}
