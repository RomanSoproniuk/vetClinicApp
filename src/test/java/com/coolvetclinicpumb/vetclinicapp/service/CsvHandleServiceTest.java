package com.coolvetclinicpumb.vetclinicapp.service;

import com.coolvetclinicpumb.vetclinicapp.model.Animal;
import com.coolvetclinicpumb.vetclinicapp.model.Category;
import com.coolvetclinicpumb.vetclinicapp.service.implementations.CsvHandlerServiceImpl;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
public class CsvHandleServiceTest {
    private static Category firstCategory;
    private static Category secondCategory;
    private static Category thirdCategory;
    private static Category fourthCategory;
    private static Animal firstAnimal;
    private static Animal secondAnimal;
    private static Animal thirdAnimal;
    private static Animal fourthAnimal;
    private static Animal fifthAnimal;
    private static Animal sixthAnimal;
    private static Animal seventhAnimal;
    private static List<Animal> animals;
    @InjectMocks
    private CsvHandlerServiceImpl csvHandleService;

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
        firstAnimal = new Animal(1L, "Buddy", "cat", Animal.Sex.FEMALE, 41, 78, fourthCategory);
        secondAnimal = new Animal(2L, "Duke", "cat", Animal.Sex.MALE, 33, 108, fourthCategory);
        thirdAnimal = new Animal(3L, "Sadie", "cat", Animal.Sex.MALE, 26, 27, secondCategory);
        fourthAnimal = new Animal(4L, "Leo", "cat", Animal.Sex.FEMALE, 23, 82, fourthCategory);
        fifthAnimal = new Animal(5L, "Lola", "dog", Animal.Sex.MALE, 35, 105, fourthCategory);
        sixthAnimal = new Animal(6L, "Bailey", "dog", Animal.Sex.MALE, 42, 46, thirdCategory);
        seventhAnimal = new Animal(7L, "Loki", "cat", Animal.Sex.FEMALE, 11, 87, fourthCategory);
        animals = new ArrayList<>();
        animals.add(firstAnimal);
        animals.add(secondAnimal);
        animals.add(thirdAnimal);
        animals.add(fourthAnimal);
        animals.add(fifthAnimal);
        animals.add(sixthAnimal);
        animals.add(seventhAnimal);
    }

    @Test
    @DisplayName("""
            Correct read animals from CSV file
            """)
    public void saveFromCsvFile_CorrectSaveToDb_Success() throws IOException {
        //given
        List<Animal> expected = animals;
        FileInputStream inputFile = new FileInputStream("testfiles/animals.csv");
        MockMultipartFile fileCsv = new MockMultipartFile("animals.csv",
                "animal.csv",
                "text/csv",
                inputFile);
        //when
        List<Animal> actual = csvHandleService.handleCsvFileWithAnimals(fileCsv);
        //then
        Assertions.assertEquals(expected.size(), actual.size());
    }
}
