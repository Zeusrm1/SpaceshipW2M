package com.example.SpaceshipW2M.domain;

import com.example.SpaceshipW2M.domain.service.SpaceshipServiceImpl;
import com.example.SpaceshipW2M.infrastructures.dto.SpaceshipDto;
import com.example.SpaceshipW2M.domain.entity.SpaceshipEntity;
import com.example.SpaceshipW2M.infrastructures.repository.SpaceshipRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
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
	@DisplayName("Test save spaceship")
	void testSaveSpaceship() {
		when(spaceshipRepository.save(any(SpaceshipEntity.class))).thenReturn(spaceshipEntity);

		SpaceshipDto result = spaceshipService.saveSpaceship(spaceshipDto);

		assertEquals("Falcon", result.getSpaceshipName());
		verify(spaceshipRepository, times(1)).save(any(SpaceshipEntity.class));
		verify(spaceshipRepository).save(argThat(entity -> entity.getSpaceshipName().equals("Falcon")));

	}

	@Test
	@DisplayName("Test update spaceship")
	void testUpdateSpaceship() {
		when(spaceshipRepository.findById(1L)).thenReturn(Optional.of(spaceshipEntity));
		when(spaceshipRepository.save(any(SpaceshipEntity.class))).thenReturn(spaceshipEntity);

		SpaceshipDto result = spaceshipService.updateSpaceship(1L, "Falcon Updated");

		assertEquals("Falcon Updated", result.getSpaceshipName());

		verify(spaceshipRepository).findById(1L);
		verify(spaceshipRepository).save(any(SpaceshipEntity.class));
	}

	@Test
	@DisplayName("Test delete spaceship")
	void testDeleteSpaceship() {
		doNothing().when(spaceshipRepository).deleteById(1L);

		spaceshipService.deleteSpaceship(1L);

		verify(spaceshipRepository, times(1)).deleteById(1L);
		verifyNoMoreInteractions(spaceshipRepository);
	}

	@Test
	@DisplayName("Test exists spaceship by name")
	void testExistsSpaceshipByName() {
		when(spaceshipRepository.existsSpaceshipBySpaceshipName("Falcon")).thenReturn(true);

		boolean result = spaceshipService.existsSpaceshipByName("Falcon");

		assertTrue(result);
	}

	@Test
	@DisplayName("Test get spaceship")
	void testGetSpaceship() {
		when(spaceshipRepository.findById(1L)).thenReturn(Optional.of(spaceshipEntity));

		Optional<SpaceshipDto> result = spaceshipService.getSpaceship(1L);

		assertTrue(result.isPresent());
		assertEquals("Falcon", result.get().getSpaceshipName());

		when(spaceshipRepository.findById(2L)).thenReturn(Optional.empty());

		Optional<SpaceshipDto> notFoundResult = spaceshipService.getSpaceship(2L);

		assertFalse(notFoundResult.isPresent());
	}

	@Test
	@DisplayName("Test get all spaceships")
	void testGetAllSpaceships() {
		when(spaceshipRepository.findAll()).thenReturn(Collections.singletonList(spaceshipEntity));

		List<SpaceshipDto> result = spaceshipService.getAllSpaceships();

		assertEquals(1, result.size());
		assertEquals("Falcon", result.getFirst().getSpaceshipName());
	}

	@Test
	@DisplayName("Test get spaceship by name")
	void testGetSpaceshipByName() {
		when(spaceshipRepository.findBySpaceshipNameContainingIgnoreCase("Falcon")).thenReturn(Collections.singletonList(spaceshipEntity));

		List<SpaceshipDto> result = spaceshipService.getSpaceshipByName("Falcon");

		assertEquals(1, result.size());
		assertEquals("Falcon", result.getFirst().getSpaceshipName());
	}

	@Test
	@DisplayName("Test get spaceships paginated")
	void testGetSpaceshipsPaginated() {
		List<SpaceshipEntity> spaceshipEntities = Collections.singletonList(spaceshipEntity);
		Page<SpaceshipEntity> spaceshipPage = new PageImpl<>(spaceshipEntities);

		when(spaceshipRepository.findAll(any(Pageable.class))).thenReturn(spaceshipPage);

		Page<SpaceshipDto> result = spaceshipService.getSpaceshipsPaginated(0, 1);

		assertEquals(1, result.getContent().size());
		assertEquals("Falcon", result.getContent().get(0).getSpaceshipName());
	}

}
