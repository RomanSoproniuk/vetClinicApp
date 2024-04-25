package com.coolvetclinicpumb.vetclinicapp.dto;

import com.coolvetclinicpumb.vetclinicapp.model.Animal;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Setter;

@Setter
@XmlRootElement(name = "animals")
public class XmlAnimalDto {
    private List<Animal> animalsList;

    @XmlElement(name = "animal")
    public List<Animal> getAnimalsList() {
        return animalsList;
    }
}
