package com.example.SpaceshipW2M.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpaceshipW2M.dto.SpaceshipDto;
import com.example.SpaceshipW2M.entity.SpaceshipEntity;
import com.example.SpaceshipW2M.service.SpaceshipService;
import com.example.SpaceshipW2M.utils.ExceptionConstants;

import ch.qos.logback.core.util.StringUtil;

@RestController
public class SpaceshipController {

	private final SpaceshipService spaceshipService;

	@Autowired
	public SpaceshipController(SpaceshipService spaceshipService) {
		this.spaceshipService = spaceshipService;
	}

	@PostMapping("/spaceship")
	public void saveSpaceship(@RequestBody SpaceshipDto spaceshipDto) {
		if (StringUtil.isNullOrEmpty(spaceshipDto.getSpaceshipName())) {
			throw new IllegalArgumentException(ExceptionConstants.SPACESHIP_NAME_REQUIRED);
		}
		if (spaceshipService.existsSpaceshipByName(spaceshipDto.getSpaceshipName())) {
			throw new IllegalArgumentException(ExceptionConstants.SPACESHIP_NAME_ALREADY_EXISTS);
		}
		//Creamos la entidad de la nave espacial y guardamos
		SpaceshipEntity spaceshipEntity = new SpaceshipEntity(spaceshipDto.getSpaceshipName());
		spaceshipService.saveSpaceship(spaceshipEntity);
	}

	@GetMapping("/spaceships")
	public ResponseEntity<List<SpaceshipEntity>> getAllSpaceships() {
		List<SpaceshipEntity> spaceshipEntityList = spaceshipService.getAllSpaceships();
		if (spaceshipEntityList.isEmpty()) {
			throw new IllegalArgumentException(ExceptionConstants.SPACESHIP_NOT_FOUND);
		}
		return ResponseEntity.ok(spaceshipEntityList);
	}

	@GetMapping("/spaceship/{spaceshipId}")
	public ResponseEntity<SpaceshipEntity> getSpaceship(@PathVariable("spaceshipId") int spaceshipId) {
		if (spaceshipService.getSpaceship(spaceshipId).isEmpty()) {
			throw new IllegalArgumentException(ExceptionConstants.SPACESHIP_NOT_FOUND);
		}
		SpaceshipEntity spaceshipEntity = spaceshipService.getSpaceship(spaceshipId).get();
		return ResponseEntity.ok(spaceshipEntity);
	}

	@GetMapping("/spaceship/{spaceshipName}")
	public ResponseEntity<List<SpaceshipEntity>> getSpaceshipByName(@PathVariable("spaceshipName") String spaceshipName) {
		if (StringUtil.isNullOrEmpty(spaceshipName)) {
			throw new IllegalArgumentException(ExceptionConstants.SPACESHIP_NAME_REQUIRED);
		}
		if (!spaceshipService.existsSpaceshipByName(spaceshipName)) {
			throw new IllegalArgumentException(ExceptionConstants.SPACESHIP_NAME_NOT_FOUND);
		}
		List<SpaceshipEntity> spaceshipEntityList = spaceshipService.getSpaceshipByName(spaceshipName);
		return ResponseEntity.ok(spaceshipEntityList);
	}

	@PostMapping("/spaceship/{spaceshipId}")
	public void updateSpaceship(@PathVariable("spaceshipId") int spaceshipId, @RequestBody SpaceshipDto spaceshipDto) {
		if (spaceshipService.getSpaceship(spaceshipId).isEmpty()) {
			throw new IllegalArgumentException(ExceptionConstants.SPACESHIP_NOT_FOUND);
		}
		if (StringUtil.isNullOrEmpty(spaceshipDto.getSpaceshipName())) {
			throw new IllegalArgumentException(ExceptionConstants.SPACESHIP_NAME_REQUIRED);
		}
		if (spaceshipService.existsSpaceshipByName(spaceshipDto.getSpaceshipName())) {
			throw new IllegalArgumentException(ExceptionConstants.SPACESHIP_NAME_ALREADY_EXISTS);
		}
		SpaceshipEntity spaceshipEntity = spaceshipService.getSpaceship(spaceshipId).get();
		spaceshipEntity.setSpaceshipName(spaceshipDto.getSpaceshipName());
		spaceshipService.updateSpaceship(spaceshipEntity);
	}

	@DeleteMapping("/spaceship/{spaceshipId}")
	public void deleteSpaceship(@PathVariable("spaceshipId") int spaceshipId) {
		if (spaceshipService.getSpaceship(spaceshipId).isEmpty()) {
			throw new IllegalArgumentException(ExceptionConstants.SPACESHIP_NOT_FOUND);
		}
		spaceshipService.deleteSpaceship(spaceshipId);
	}



}
