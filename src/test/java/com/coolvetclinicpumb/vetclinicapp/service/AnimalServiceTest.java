package com.coolvetclinicpumb.vetclinicapp.service;

import com.coolvetclinicpumb.vetclinicapp.dto.AnimalResponseDto;
import com.coolvetclinicpumb.vetclinicapp.dto.AnimalSearchParameters;
import com.coolvetclinicpumb.vetclinicapp.dto.CategoryResponseDto;
import com.coolvetclinicpumb.vetclinicapp.exception.FileFormatNotSupported;
import com.coolvetclinicpumb.vetclinicapp.exception.FileIsEmptyException;
import com.coolvetclinicpumb.vetclinicapp.mapper.AnimalMapper;
import com.coolvetclinicpumb.vetclinicapp.model.Animal;
import com.coolvetclinicpumb.vetclinicapp.model.Category;
import com.coolvetclinicpumb.vetclinicapp.repository.AnimalRepository;
import com.coolvetclinicpumb.vetclinicapp.repository.CategoryRepository;
import com.coolvetclinicpumb.vetclinicapp.repository.criteriaquery.AnimalSpecificationBuilder;
import com.coolvetclinicpumb.vetclinicapp.service.implementations.AnimalServiceImpl;
import com.coolvetclinicpumb.vetclinicapp.service.implementations.CsvHandlerServiceImpl;
import com.coolvetclinicpumb.vetclinicapp.service.implementations.XmlHandleServiceImpl;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {
    @Mock
    private static AnimalSearchParameters animalSearchParameters;
    private static Specification<Animal> animalSpecification;
    private static List<Animal> animals;
    private static List<AnimalResponseDto> animalResponseDtos;
    private static List<Category> categories;
    private static Pageable pageable;
    private static Page<Animal> animalPage;
    private static Animal firstAnimal;
    private static Animal secondAnimal;
    private static Animal thirdAnimal;
    private static Animal fourthAnimal;
    private static Animal fifthAnimal;
    private static Animal sixthAnimal;
    private static Animal seventhAnimal;
    private static AnimalResponseDto firstAnimalDto;
    private static AnimalResponseDto secondAnimalDto;
    private static AnimalResponseDto thirdAnimalDto;
    private static AnimalResponseDto fourthAnimalDto;
    private static AnimalResponseDto fifthAnimalDto;
    private static AnimalResponseDto sixthAnimalDto;
    private static AnimalResponseDto seventhAnimalDto;
    private static Category firstCategory;
    private static Category secondCategory;
    private static Category thirdCategory;
    private static Category fourthCategory;
    private static CategoryResponseDto firthCategoryResponseDto;
    private static CategoryResponseDto secondCategoryResponseDto;
    private static CategoryResponseDto thirdCategoryResponseDto;
    private static CategoryResponseDto fourthCategoryResponseDto;
    @InjectMocks
    private AnimalServiceImpl animalService;
    @Mock
    private CsvHandlerServiceImpl csvHandlerService;
    @Mock
    private XmlHandleServiceImpl xmlHandleService;
    @Mock
    private AnimalMapper animalMapper;
    @Mock
    private AnimalSpecificationBuilder animalSpecificationBuilder;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private AnimalRepository animalRepository;

    @BeforeAll
    static void setUp() {
        short one = 1;
        firstCategory = new Category(one, Category.TypeCategory.FIRST);
        short two = 2;
        secondCategory = new Category(two, Category.TypeCategory.SECOND);
        short three = 3;
        thirdCategory = new Category(three, Category.TypeCategory.THIRD);
        short fourth = 4;
        fourthCategory = new Category(fourth, Category.TypeCategory.FOURTH);
        firthCategoryResponseDto = new CategoryResponseDto(one, "FIRST");
        secondCategoryResponseDto = new CategoryResponseDto(two, "SECOND");
        thirdCategoryResponseDto = new CategoryResponseDto(three, "THIRD");
        fourthCategoryResponseDto = new CategoryResponseDto(fourth, "FOURTH");
        categories = new ArrayList<>();
        categories.add(firstCategory);
        categories.add(secondCategory);
        categories.add(thirdCategory);
        categories.add(fourthCategory);
        firstAnimalDto = new AnimalResponseDto(1L, "Buddy", "cat", "FEMALE", 41, 78,
                fourthCategoryResponseDto);
        secondAnimalDto = new AnimalResponseDto(2L, "Duke", "cat", "MALE", 33, 108,
                fourthCategoryResponseDto);
        thirdAnimalDto = new AnimalResponseDto(3L, "Sadie", "cat", "MALE", 26, 27,
                secondCategoryResponseDto);
        fourthAnimalDto = new AnimalResponseDto(4L, "Leo", "cat", "FEMALE", 23, 82,
                fourthCategoryResponseDto);
        fifthAnimalDto = new AnimalResponseDto(5L, "Lola", "dog", "MALE", 35, 105,
                fourthCategoryResponseDto);
        sixthAnimalDto = new AnimalResponseDto(6L, "Bailey", "dog", "MALE", 42, 46,
                thirdCategoryResponseDto);
        seventhAnimalDto = new AnimalResponseDto(7L, "Loki", "cat", "FEMALE", 11, 87,
                fourthCategoryResponseDto);
        firstAnimal = new Animal(1L, "Buddy", "cat", Animal.Sex.FEMALE, 41, 78, fourthCategory);
        secondAnimal = new Animal(2L, "Duke", "cat", Animal.Sex.MALE, 33, 108, fourthCategory);
        thirdAnimal = new Animal(3L, "Sadie", "cat", Animal.Sex.MALE, 26, 27, secondCategory);
        fourthAnimal = new Animal(4L, "Leo", "cat", Animal.Sex.FEMALE, 23, 82, fourthCategory);
        fifthAnimal = new Animal(5L, "Lola", "dog", Animal.Sex.MALE, 35, 105, fourthCategory);
        sixthAnimal = new Animal(6L, "Bailey", "dog", Animal.Sex.MALE, 42, 46, thirdCategory);
        seventhAnimal = new Animal(7L, "Loki", "cat", Animal.Sex.FEMALE, 11, 87, fourthCategory);
        animalResponseDtos = new ArrayList<>();
        animalResponseDtos.add(firstAnimalDto);
        animalResponseDtos.add(secondAnimalDto);
        animalResponseDtos.add(thirdAnimalDto);
        animalResponseDtos.add(fourthAnimalDto);
        animalResponseDtos.add(fifthAnimalDto);
        animalResponseDtos.add(sixthAnimalDto);
        animalResponseDtos.add(seventhAnimalDto);
        animals = new ArrayList<>();
        animals.add(firstAnimal);
        animals.add(secondAnimal);
        animals.add(thirdAnimal);
        animals.add(fourthAnimal);
        animals.add(fifthAnimal);
        animals.add(sixthAnimal);
        animals.add(seventhAnimal);
        animalSearchParameters = new AnimalSearchParameters(new String[]{},
                new String[]{}, new String[]{});
        animalSpecification = new Specification<Animal>() {
            @Override
            public Predicate toPredicate(Root<Animal> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        pageable = PageRequest.of(0, 20);
        animalPage = new PageImpl<>(animals);
    }

    @Test
    @DisplayName("""
            Correct save animals to DB from CSV file
            """)
    public void saveFromCsvFile_CorrectSaveToDb_Success() throws IOException, JAXBException {
        //given
        FileInputStream inputFile = new FileInputStream("testfiles/animals.csv");
        MockMultipartFile fileCsv = new MockMultipartFile("animals.csv",
                "animal.csv",
                "text/csv",
                inputFile);
        Mockito.when(csvHandlerService.handleCsvFileWithAnimals(fileCsv)).thenReturn(animals);
        Mockito.when(categoryRepository.findAll()).thenReturn(categories);
        Mockito.when(animalRepository.saveAll(animals)).thenReturn(animals);
        ResponseEntity<?> expected = new ResponseEntity<>(animals, HttpStatus.OK);
        //when
        ResponseEntity<?> actual = animalService.saveAnimals(fileCsv);
        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Correct save animals to DB from XML file
            """)
    public void saveFromXmlFile_CorrectSaveToDb_Success() throws IOException, JAXBException {
        //given
        FileInputStream inputFile = new FileInputStream("testfiles/animals.xml");
        MockMultipartFile fileXml = new MockMultipartFile("animals.xml",
                "animal.xml",
                "application/xml",
                inputFile);
        Mockito.when(xmlHandleService.handleXmlFileWithAnimal(fileXml)).thenReturn(animals);
        Mockito.when(categoryRepository.findAll()).thenReturn(categories);
        Mockito.when(animalRepository.saveAll(animals)).thenReturn(animals);
        ResponseEntity<?> expected = new ResponseEntity<>(animals, HttpStatus.OK);
        //when
        ResponseEntity<?> actual = animalService.saveAnimals(fileXml);
        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Try to save empty file
            """)
    public void saveEmptyFile_ReturnEmptyFileMessage_ThrowMessage()
            throws IOException, JAXBException {
        //given
        ResponseEntity<?> expected = new ResponseEntity<>(
                new FileIsEmptyException("The file is empty"), HttpStatus.BAD_REQUEST);
        FileInputStream inputFile = new FileInputStream("testfiles/animalempty.csv");
        MockMultipartFile fileCsv = new MockMultipartFile("animalempty.csv",
                "animalempty.csv",
                "text/csv",
                inputFile);
        //when
        ResponseEntity<?> actual = animalService.saveAnimals(fileCsv);
        //then
        Assertions.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    @DisplayName("""
            Try to save not supported format file
            """)
    public void saveFileUnsupportedFormat_ThrowException_Success() throws IOException {
        //given
        FileInputStream inputFile = new FileInputStream("testfiles/animals.yaml");
        MockMultipartFile fileYaml = new MockMultipartFile("animals.yaml",
                "animals.yaml",
                "application/yaml ",
                inputFile);
        //then
        Assertions.assertThrows(FileFormatNotSupported.class,
                () -> animalService.saveAnimals(fileYaml));
    }

    @Test
    @DisplayName("""
            Search rentals by parameter
            """)
    public void searchRentalsByParams_ReturnCorrectRental_Success() {
        //given
        Mockito.when(animalSpecificationBuilder.build(animalSearchParameters))
                .thenReturn(animalSpecification);
        Mockito.when(animalRepository.findAll(animalSpecification, pageable))
                .thenReturn(animalPage);
        Mockito.when(animalMapper.toDto(firstAnimal)).thenReturn(firstAnimalDto);
        Mockito.when(animalMapper.toDto(secondAnimal)).thenReturn(secondAnimalDto);
        Mockito.when(animalMapper.toDto(thirdAnimal)).thenReturn(thirdAnimalDto);
        Mockito.when(animalMapper.toDto(fourthAnimal)).thenReturn(fourthAnimalDto);
        Mockito.when(animalMapper.toDto(fifthAnimal)).thenReturn(firstAnimalDto);
        Mockito.when(animalMapper.toDto(sixthAnimal)).thenReturn(sixthAnimalDto);
        Mockito.when(animalMapper.toDto(seventhAnimal)).thenReturn(seventhAnimalDto);
        List<AnimalResponseDto> expected = animalResponseDtos;
        //when
        List<AnimalResponseDto> actual
                = animalService.returnAnimalsByParams(animalSearchParameters, pageable);
        //then
        Assertions.assertEquals(expected.size(), actual.size());
        EqualsBuilder.reflectionEquals(expected.get(0), actual.get(0));
        EqualsBuilder.reflectionEquals(expected.get(1), actual.get(1));
        EqualsBuilder.reflectionEquals(expected.get(2), actual.get(2));
        EqualsBuilder.reflectionEquals(expected.get(3), actual.get(3));
        EqualsBuilder.reflectionEquals(expected.get(4), actual.get(4));
        EqualsBuilder.reflectionEquals(expected.get(5), actual.get(5));
        EqualsBuilder.reflectionEquals(expected.get(6), actual.get(6));
    }
}
