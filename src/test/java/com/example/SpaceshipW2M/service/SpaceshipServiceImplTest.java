package com.example.SpaceshipW2M.service;

import com.example.SpaceshipW2M.dto.SpaceshipDto;
import com.example.SpaceshipW2M.entity.SpaceshipEntity;
import com.example.SpaceshipW2M.repository.SpaceshipRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpaceshipServiceImplTest {

	@Mock
	private SpaceshipRepository spaceshipRepository;

	@InjectMocks
	private SpaceshipServiceImpl spaceshipService;

	private SpaceshipEntity spaceshipEntity;
	private SpaceshipDto spaceshipDto;

	@BeforeEach
	void setUp() {
		spaceshipEntity = new SpaceshipEntity();
		spaceshipEntity.setId(1L);
		spaceshipEntity.setSpaceshipName("Falcon");

		spaceshipDto = new SpaceshipDto();
		spaceshipDto.setId(1L);
		spaceshipDto.setSpaceshipName("Falcon");
	}

	@Test
	void testGetAllSpaceships() {
		when(spaceshipRepository.findAll()).thenReturn(Collections.singletonList(spaceshipEntity));

		List<SpaceshipDto> result = spaceshipService.getAllSpaceships();

		assertEquals(1, result.size());
		assertEquals("Falcon", result.getFirst().getSpaceshipName());
	}

	@Test
	void testSaveSpaceship() {
		when(spaceshipRepository.save(any(SpaceshipEntity.class))).thenReturn(spaceshipEntity);

		SpaceshipDto result = spaceshipService.saveSpaceship(spaceshipDto);

		assertEquals("Falcon", result.getSpaceshipName());
	}

	@Test
	void testUpdateSpaceship() {
        lenient().when(spaceshipRepository.findById(1L)).thenReturn(Optional.of(spaceshipEntity));

        SpaceshipDto result = spaceshipService.updateSpaceship(1L, "Falcon Updated");

		assertEquals("Falcon Updated", result.getSpaceshipName());
	}

	@Test
	void testDeleteSpaceship() {
		doNothing().when(spaceshipRepository).deleteById(1L);

		spaceshipService.deleteSpaceship(1L);

		verify(spaceshipRepository, times(1)).deleteById(1L);
	}

	@Test
	void testExistsSpaceshipByName() {
		when(spaceshipRepository.existsSpaceshipBySpaceshipName("Falcon")).thenReturn(true);

		boolean result = spaceshipService.existsSpaceshipByName("Falcon");

		assertTrue(result);
	}

	@Test
	void testGetSpaceship() {
		when(spaceshipRepository.findById(1L)).thenReturn(Optional.of(spaceshipEntity));

		Optional<SpaceshipDto> result = spaceshipService.getSpaceship(1L);

		assertEquals("Falcon", result.get().getSpaceshipName());
	}

	@Test
	void testGetSpaceshipByName() {
		when(spaceshipRepository.findBySpaceshipNameContainingIgnoreCase("Falcon")).thenReturn(Collections.singletonList(spaceshipEntity));

		List<SpaceshipDto> result = spaceshipService.getSpaceshipByName("Falcon");

		assertEquals(1, result.size());
		assertEquals("Falcon", result.getFirst().getSpaceshipName());
	}

	@Test
	void testGetSpaceshipsPaginated() {
		List<SpaceshipEntity> spaceshipEntities = Collections.singletonList(spaceshipEntity);
		Page<SpaceshipEntity> spaceshipPage = new PageImpl<>(spaceshipEntities);

		when(spaceshipRepository.findAll(any(Pageable.class))).thenReturn(spaceshipPage);

		Page<SpaceshipDto> result = spaceshipService.getSpaceshipsPaginated(0, 1);

		assertEquals(1, result.getContent().size());
		assertEquals("Falcon", result.getContent().get(0).getSpaceshipName());
	}

}
