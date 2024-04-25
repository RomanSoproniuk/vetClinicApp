package com.coolvetclinicpumb.vetclinicapp.service.implementations;

import com.coolvetclinicpumb.vetclinicapp.dto.XmlAnimalDto;
import com.coolvetclinicpumb.vetclinicapp.model.Animal;
import com.coolvetclinicpumb.vetclinicapp.service.XmlHandleService;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class XmlHandleServiceImpl implements XmlHandleService {

    @Override
    public List<Animal> handleXmlFileWithAnimal(MultipartFile file)
            throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlAnimalDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        XmlAnimalDto xmlAnimalDto = (XmlAnimalDto) unmarshaller
                .unmarshal(new StringReader(new String(file.getBytes())));
        return xmlAnimalDto.getAnimalsList();
    }
}
