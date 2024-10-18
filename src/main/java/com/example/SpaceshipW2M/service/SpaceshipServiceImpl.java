package com.example.SpaceshipW2M.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.SpaceshipW2M.entity.SpaceshipEntity;
import com.example.SpaceshipW2M.repository.SpaceshipRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SpaceshipServiceImpl implements SpaceshipService {

	@Autowired
	private SpaceshipRepository spaceshipRepository;

	@CachePut(value = "spaceship", key = "#spaceship.id")
	public void saveSpaceship(SpaceshipEntity spaceship) {
		spaceshipRepository.save(spaceship);
	}

	@Cacheable(value = "spaceship", key = "#id")
	public Optional<SpaceshipEntity> getSpaceship(int id) {
		return spaceshipRepository.findById(id);
	}

	@Cacheable(value = "spaceship", key = "#spaceshipName")
	public List<SpaceshipEntity> getSpaceshipByName(String spaceshipName) {
		return spaceshipRepository.findBySpaceshipNameContainingIgnoreCase(spaceshipName);
	}

	public Page<SpaceshipEntity> getSpaceshipsPaginated(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return spaceshipRepository.findAll(pageable);
	}

	@Cacheable(value = "spaceship")
	public List<SpaceshipEntity> getAllSpaceships() {
		return spaceshipRepository.findAll();
	}

	@CachePut(value = "spaceship", key = "#spaceship.id")
	public void updateSpaceship(SpaceshipEntity spaceship) {
		spaceshipRepository.save(spaceship);
	}

	@CacheEvict(value = "spaceship", key = "#id")
	public void deleteSpaceship(int id) {
		spaceshipRepository.deleteById(id);
	}

	public boolean existsSpaceshipByName(@NotBlank String spaceshipName) {
		return spaceshipRepository.existsSpaceshipBySpaceshipName(spaceshipName);
	}

}
