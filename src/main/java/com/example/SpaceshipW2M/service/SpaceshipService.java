package com.example.SpaceshipW2M.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.SpaceshipW2M.dto.SpaceshipDto;

public interface SpaceshipService {

	SpaceshipDto saveSpaceship(SpaceshipDto spaceshipDto);

	Optional<SpaceshipDto> getSpaceship(Long id);

	List<SpaceshipDto> getSpaceshipByName(String spaceshipName);

	List<SpaceshipDto> getAllSpaceships();

	SpaceshipDto updateSpaceship(Long id, String spaceshipName);

	void deleteSpaceship(Long id);

	boolean existsSpaceshipByName(String spaceshipName);

	Page<SpaceshipDto> getSpaceshipsPaginated(int page, int size);
}