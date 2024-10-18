package com.example.SpaceshipW2M.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Service;

import com.example.SpaceshipW2M.entity.SpaceshipEntity;
import com.example.SpaceshipW2M.repository.SpaceshipRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SpaceshipService {

	private SpaceshipRepository spaceshipRepository;

	public void saveSpaceship(SpaceshipEntity spaceship) {
		spaceshipRepository.save(spaceship);
	}

	public Optional<SpaceshipEntity> getSpaceship(int id) {
		return spaceshipRepository.findById(id);
	}

	public List<SpaceshipEntity> getSpaceshipByName(String spaceshipName) {
		return spaceshipRepository.findByNameContainingIgnoreCase(spaceshipName);
	}

	public List<SpaceshipEntity> getAllSpaceships() {
		return spaceshipRepository.findAll();
	}

	public void updateSpaceship(SpaceshipEntity spaceship) {
		spaceshipRepository.save(spaceship);
	}

	public void deleteSpaceship(int id) {
		spaceshipRepository.deleteById(id);
	}

	public boolean existsSpaceshipByName(@NotBlank String spaceshipName) {
		return spaceshipRepository.existsSpaceshipByName(spaceshipName);
	}

}
