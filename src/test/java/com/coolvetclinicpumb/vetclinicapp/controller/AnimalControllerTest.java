package com.coolvetclinicpumb.vetclinicapp.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.coolvetclinicpumb.vetclinicapp.dto.AnimalResponseDto;
import com.coolvetclinicpumb.vetclinicapp.dto.CategoryResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnimalControllerTest {
    private static AnimalResponseDto animalResponseDto;
    private static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setUp(
            @Autowired WebApplicationContext applicationContext
    ) {
        short categoryId = 1;
        animalResponseDto = new AnimalResponseDto(4L, "Mike", "dog", "MALE",
                19, 18, new CategoryResponseDto(categoryId, "FIRST"));
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("""
            Correct save animal to DB from file
            """)
    @WithMockUser(username = "admin@gmail.com", roles = {"MANAGER"})
    @Sql(scripts = {"classpath:database/animals/delete-all-from-animals.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveAnimalToDb_CorrectSaveAnimal_Success() throws Exception {
        //given
        int lengthActual = 7;
        byte[] fileBytes;
        FileInputStream inputFile = new FileInputStream("testfiles/animals.xml");
        fileBytes = inputFile.readAllBytes();
        MockMultipartFile fileXml
                = new MockMultipartFile("file", "animals.xml", "application/xml", fileBytes);
        //when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/files/uploads")
                        .file(fileXml)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andReturn();
        //then
        AnimalResponseDto[] actual
                = objectMapper.readValue(result.getResponse()
                .getContentAsString(), AnimalResponseDto[].class);
        int lengthExpected = actual.length;
        Assertions.assertEquals(lengthExpected, lengthActual);
    }

    @Test
    @DisplayName("""
            Return correct animals from DB by params
            """)
    @WithMockUser(username = "admin@gmail.com", roles = {"MANAGER"})
    @Sql(scripts = {"classpath:database/animals/add-five-animals-to-db.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:database/animals/delete-all-from-animals.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void searchAnimals_ReturnCorrectAnimalsByParamsFemaleCat_Success() throws Exception {
        //given
        AnimalResponseDto expected = animalResponseDto;
        String queryString = "?sex=male&types=dog&categories=first";
        //when
        MvcResult result = mockMvc.perform(get("/search" + queryString)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //then
        List<AnimalResponseDto> actualList
                = Arrays.stream(objectMapper.readValue(result.getResponse()
                .getContentAsString(), AnimalResponseDto[].class)).toList();
        AnimalResponseDto actual = actualList.get(0);
        Assertions.assertEquals(expected.sex(), actual.sex());
        Assertions.assertEquals(expected.type(), actual.type());
    }
}
