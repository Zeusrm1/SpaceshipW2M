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
}