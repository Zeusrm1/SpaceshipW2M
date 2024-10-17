package com.example.SpaceshipW2M.service;

import java.util.Optional;

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

	public void updateSpaceship(SpaceshipEntity spaceship) {
		spaceshipRepository.save(spaceship);
	}

	public void deleteSpaceship(int id) {
		spaceshipRepository.deleteById(id);
	}


}
