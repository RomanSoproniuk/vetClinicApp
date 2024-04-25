package com.coolvetclinicpumb.vetclinicapp.service;

import com.coolvetclinicpumb.vetclinicapp.model.Animal;
import java.io.IOException;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.springframework.web.multipart.MultipartFile;

public interface XmlHandleService {
    List<Animal> handleXmlFileWithAnimal(MultipartFile file) throws JAXBException, IOException;
}
