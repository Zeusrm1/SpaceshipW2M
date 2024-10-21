package com.example.SpaceshipW2M.service;

import com.example.SpaceshipW2M.dto.SpaceshipDto;
import com.example.SpaceshipW2M.entity.SpaceshipEntity;
import com.example.SpaceshipW2M.repository.SpaceshipRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class SpaceshipServiceImplIntegrationTest {

	@Autowired
	private SpaceshipServiceImpl spaceshipService;

	@Autowired
	private SpaceshipRepository spaceshipRepository;

	@Test
	void testSaveAndRetrieveSpaceship() {
		SpaceshipDto spaceshipDto = new SpaceshipDto();
		spaceshipDto.setSpaceshipName("Enterprise");

		SpaceshipDto savedSpaceship = spaceshipService.saveSpaceship(spaceshipDto);
		Optional<SpaceshipDto> retrievedSpaceship = spaceshipService.getSpaceship(savedSpaceship.getId());

		assertTrue(retrievedSpaceship.isPresent());
		assertEquals("Enterprise", retrievedSpaceship.get().getSpaceshipName());
	}

	@Test
	void testUpdateSpaceship() {
		SpaceshipDto spaceshipDto = new SpaceshipDto();
		spaceshipDto.setSpaceshipName("Voyager");

		SpaceshipDto savedSpaceship = spaceshipService.saveSpaceship(spaceshipDto);
		SpaceshipDto updatedSpaceship = spaceshipService.updateSpaceship(savedSpaceship.getId(), "Voyager Updated");

		assertEquals("Voyager Updated", updatedSpaceship.getSpaceshipName());
	}

	@Test
	void testDeleteSpaceship() {
		SpaceshipDto spaceshipDto = new SpaceshipDto();
		spaceshipDto.setSpaceshipName("Discovery");

		SpaceshipDto savedSpaceship = spaceshipService.saveSpaceship(spaceshipDto);
		spaceshipService.deleteSpaceship(savedSpaceship.getId());

		Optional<SpaceshipEntity> deletedSpaceship = spaceshipRepository.findById(savedSpaceship.getId());
		assertTrue(deletedSpaceship.isEmpty());
	}

    @Test
    void testDeleteSpaceshipNotFound() {
        spaceshipService.deleteSpaceship(99999L);

        Optional<SpaceshipEntity> deletedSpaceship = spaceshipRepository.findById(99999L);
        assertTrue(deletedSpaceship.isEmpty());
    }

	@Test
	void testGetAllSpaceships() {
		SpaceshipDto spaceshipDto1 = new SpaceshipDto();
		spaceshipDto1.setSpaceshipName("x-wing");

		SpaceshipDto spaceshipDto2 = new SpaceshipDto();
		spaceshipDto2.setSpaceshipName("tie-fighter");

		spaceshipService.saveSpaceship(spaceshipDto1);
		spaceshipService.saveSpaceship(spaceshipDto2);

		assertEquals(2, spaceshipService.getAllSpaceships().size());
	}

	@Test
	void testGetSpaceshipByName() {
		SpaceshipDto spaceshipDto1 = new SpaceshipDto();
		spaceshipDto1.setSpaceshipName("Nostromo");

		SpaceshipDto spaceshipDto2 = new SpaceshipDto();
		spaceshipDto2.setSpaceshipName("Rocinante");

		spaceshipService.saveSpaceship(spaceshipDto1);
		spaceshipService.saveSpaceship(spaceshipDto2);

		assertEquals(1, spaceshipService.getSpaceshipByName("Nostromo").size());
	}

    @Test
    void testExistsSpaceshipByName() {
        SpaceshipDto spaceshipDto = new SpaceshipDto();
        spaceshipDto.setSpaceshipName("Falcon");

        spaceshipService.saveSpaceship(spaceshipDto);
        boolean existsSpaceship = spaceshipService.existsSpaceshipByName("Falcon");

        assertTrue(existsSpaceship);
    }

	@Test
	void testGetSpaceshipNotFound() {
		Optional<SpaceshipDto> spaceship = spaceshipService.getSpaceship(99999L);

		assertTrue(spaceship.isEmpty());
	}

	@Test
	void testGetSpaceshipsPaginated() {
		SpaceshipDto spaceshipDto1 = new SpaceshipDto();
		spaceshipDto1.setSpaceshipName("Sulaco");

		SpaceshipDto spaceshipDto2 = new SpaceshipDto();
		spaceshipDto2.setSpaceshipName("Red Dwarf");

		spaceshipService.saveSpaceship(spaceshipDto1);
		spaceshipService.saveSpaceship(spaceshipDto2);

		int totalSpaceships = spaceshipService.getAllSpaceships().size();

		assertEquals(totalSpaceships, spaceshipService.getSpaceshipsPaginated(0, totalSpaceships).getTotalElements());
	}

}