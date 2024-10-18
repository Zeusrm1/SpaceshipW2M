package com.example.SpaceshipW2M.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.SpaceshipW2M.entity.SpaceshipEntity;

public interface SpaceshipService {

	void saveSpaceship(SpaceshipEntity spaceship);

	Optional<SpaceshipEntity> getSpaceship(int id);

	List<SpaceshipEntity> getSpaceshipByName(String spaceshipName);

	List<SpaceshipEntity> getAllSpaceships();

	void updateSpaceship(SpaceshipEntity spaceship);

	void deleteSpaceship(int id);

	boolean existsSpaceshipByName(String spaceshipName);

	Page<SpaceshipEntity> getSpaceshipsPaginated(int page, int size);
}