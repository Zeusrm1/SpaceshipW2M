package com.example.SpaceshipW2M.controller;

import com.example.SpaceshipW2M.dto.SpaceshipDto;
import com.example.SpaceshipW2M.entity.SpaceshipEntity;
import com.example.SpaceshipW2M.repository.SpaceshipRepository;
import com.example.SpaceshipW2M.utils.ExceptionConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

import jakarta.transaction.Transactional;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SpaceshipControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private SpaceshipRepository spaceshipRepository;

	@BeforeEach
	void setUp() {
		spaceshipRepository.deleteAll();
	}

	// Add the credentials as constants
	private static final String USERNAME = "user";
	private static final String PASSWORD = "password";


	private SpaceshipEntity convertToEntity(SpaceshipDto dto) {
		SpaceshipEntity entity = new SpaceshipEntity();
		entity.setSpaceshipName(dto.getSpaceshipName());
		return entity;
	}

	@Test
	void testCreateSpaceship() throws Exception {
		SpaceshipDto spaceshipDto = new SpaceshipDto();
		spaceshipDto.setSpaceshipName("Enterprise");

		mockMvc.perform(post("/spaceship")
						.with(httpBasic(USERNAME, PASSWORD))
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(spaceshipDto)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.spaceshipName").value("Enterprise"));
	}

	@Test
	void testGetSpaceshipById() throws Exception {
		SpaceshipDto spaceshipDto = new SpaceshipDto();
		spaceshipDto.setSpaceshipName("Imperial Star Destroyer 2");
		SpaceshipEntity spaceshipEntity = spaceshipRepository.save(convertToEntity(spaceshipDto));
		spaceshipDto.setId(spaceshipEntity.getId());

		mockMvc.perform(get("/spaceship/{id}", spaceshipDto.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.spaceshipName").value("Imperial Star Destroyer 2"));
	}

	@Test
	void testUpdateSpaceship() throws Exception {
		SpaceshipDto spaceshipDto = new SpaceshipDto();
		spaceshipDto.setSpaceshipName("Voyager");
		SpaceshipEntity spaceshipEntity = spaceshipRepository.save(convertToEntity(spaceshipDto));
		spaceshipDto.setId(spaceshipEntity.getId());

		spaceshipDto.setSpaceshipName("Voyager Updated");

		mockMvc.perform(put("/spaceship/{id}", spaceshipDto.getId())
						.with(httpBasic(USERNAME, PASSWORD))
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(spaceshipDto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.spaceshipName").value("Voyager Updated"));
	}

	@Test
	void testDeleteSpaceship() throws Exception {
		SpaceshipDto spaceshipDto = new SpaceshipDto();
		spaceshipDto.setSpaceshipName("Discovery");
		SpaceshipEntity spaceshipEntity = spaceshipRepository.save(convertToEntity(spaceshipDto));
		spaceshipDto.setId(spaceshipEntity.getId());

		mockMvc.perform(delete("/spaceship/{id}", spaceshipDto.getId())
						.with(httpBasic(USERNAME, PASSWORD)))
				.andExpect(status().isNoContent());
	}

    @Test
    void testGetAllSpaceships() throws Exception {
        SpaceshipDto spaceshipDto1 = new SpaceshipDto();
        spaceshipDto1.setSpaceshipName("Enterprise");
        SpaceshipEntity spaceshipEntity1 = spaceshipRepository.save(convertToEntity(spaceshipDto1));
        spaceshipDto1.setId(spaceshipEntity1.getId());

        SpaceshipDto spaceshipDto2 = new SpaceshipDto();
        spaceshipDto2.setSpaceshipName("Imperial Star Destroyer");
        SpaceshipEntity spaceshipEntity2 = spaceshipRepository.save(convertToEntity(spaceshipDto2));
        spaceshipDto2.setId(spaceshipEntity2.getId());

        mockMvc.perform(get("/spaceships")
						.with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].spaceshipName").value("Enterprise"))
                .andExpect(jsonPath("$[1].spaceshipName").value("Imperial Star Destroyer"));
    }

    @Test
    void testGetSpaceshipByName() throws Exception {
        SpaceshipDto spaceshipDto1 = new SpaceshipDto();
        spaceshipDto1.setSpaceshipName("Enterprise");
        SpaceshipEntity spaceshipEntity1 = spaceshipRepository.save(convertToEntity(spaceshipDto1));
        spaceshipDto1.setId(spaceshipEntity1.getId());

        SpaceshipDto spaceshipDto2 = new SpaceshipDto();
        spaceshipDto2.setSpaceshipName("Imperial Star Destroyer");
        SpaceshipEntity spaceshipEntity2 = spaceshipRepository.save(convertToEntity(spaceshipDto2));
        spaceshipDto2.setId(spaceshipEntity2.getId());

        mockMvc.perform(get("/spaceship/name/{name}", "Enterprise")
						.with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].spaceshipName").value("Enterprise"));
    }

    @Test
    void testGetSpaceshipsPaginated() throws Exception {
        SpaceshipDto spaceshipDto1 = new SpaceshipDto();
        spaceshipDto1.setSpaceshipName("Enterprise");
        SpaceshipEntity spaceshipEntity1 = spaceshipRepository.save(convertToEntity(spaceshipDto1));
        spaceshipDto1.setId(spaceshipEntity1.getId());

        SpaceshipDto spaceshipDto2 = new SpaceshipDto();
        spaceshipDto2.setSpaceshipName("Imperial Star Destroyer");
        SpaceshipEntity spaceshipEntity2 = spaceshipRepository.save(convertToEntity(spaceshipDto2));
        spaceshipDto2.setId(spaceshipEntity2.getId());

        mockMvc.perform(get("/spaceships/paginated")
						.with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.spaceshipDtoList[0].spaceshipName").value("Enterprise"))
                .andExpect(jsonPath("$._embedded.spaceshipDtoList[1].spaceshipName").value("Imperial Star Destroyer"));
    }

    @Test
    void testSaveSpaceshipWithEmptyName() throws Exception {
        SpaceshipDto spaceshipDto = new SpaceshipDto();
        spaceshipDto.setSpaceshipName(""); // Empty name

        mockMvc.perform(post("/spaceship")
						.with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(spaceshipDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ExceptionConstants.SPACESHIP_NAME_REQUIRED));
    }

    @Test
    void testSaveSpaceshipAlreadyExists() throws Exception {
        SpaceshipDto spaceshipDto = new SpaceshipDto();
        spaceshipDto.setSpaceshipName("Enterprise");
        spaceshipRepository.save(convertToEntity(spaceshipDto)); // Save first

        mockMvc.perform(post("/spaceship")
						.with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(spaceshipDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ExceptionConstants.SPACESHIP_NAME_ALREADY_EXISTS));
    }

    @Test
    void testGetAllSpaceshipsWhenNoneExist() throws Exception {
        mockMvc.perform(get("/spaceships")
						.with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ExceptionConstants.SPACESHIP_NOT_FOUND));
    }


    @Test
    void testGetSpaceshipByIdNotFound() throws Exception {
        mockMvc.perform(get("/spaceship/{id}", 99999L)
						.with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ExceptionConstants.SPACESHIP_NOT_FOUND));
    }

    @Test
    void testUpdateSpaceshipWithEmptyName() throws Exception {
        SpaceshipDto spaceshipDto = new SpaceshipDto();
        spaceshipDto.setSpaceshipName("Enterprise");
        SpaceshipEntity spaceshipEntity = spaceshipRepository.save(convertToEntity(spaceshipDto));
        spaceshipDto.setId(spaceshipEntity.getId());

        spaceshipDto.setSpaceshipName("");

        mockMvc.perform(put("/spaceship/{id}", spaceshipDto.getId())
						.with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(spaceshipDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ExceptionConstants.SPACESHIP_NAME_REQUIRED));
    }

    @Test
    void testDeleteSpaceshipNotFound() throws Exception {
        mockMvc.perform(delete("/spaceship/{id}", 99999L)
						.with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ExceptionConstants.SPACESHIP_NOT_FOUND));
    }

    @Test
    void testGetSpaceshipsPaginatedOutOfBounds() throws Exception {
        mockMvc.perform(get("/spaceships/paginated?page=99999&size=7")
						.with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ExceptionConstants.PAGE_NOT_FOUND));
    }

}
