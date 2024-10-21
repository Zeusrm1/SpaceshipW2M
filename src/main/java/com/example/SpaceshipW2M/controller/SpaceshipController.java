package com.example.SpaceshipW2M.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.SpaceshipW2M.dto.SpaceshipDto;
import com.example.SpaceshipW2M.service.SpaceshipService;
import com.example.SpaceshipW2M.utils.ExceptionConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Spaceship API", description = "API for managing spaceships")
public class SpaceshipController {

	private final SpaceshipService spaceshipService;

	@Autowired
	public SpaceshipController(SpaceshipService spaceshipService) {
		this.spaceshipService = spaceshipService;
	}


	@PostMapping("/spaceship")
	@Operation(summary = "Save a spaceship", description = "Save a spaceship in the database")
	public ResponseEntity<SpaceshipDto> saveSpaceship(@RequestBody @Valid SpaceshipDto spaceshipDto) {
		if (spaceshipService.existsSpaceshipByName(spaceshipDto.getSpaceshipName())) {
			throw new IllegalArgumentException(ExceptionConstants.SPACESHIP_NAME_ALREADY_EXISTS);
		}
		spaceshipDto = spaceshipService.saveSpaceship(spaceshipDto);
		return new ResponseEntity<>(spaceshipDto, HttpStatus.CREATED);
	}

	@GetMapping("/spaceships")
	@Operation(summary = "Get all spaceships", description = "Get all spaceships from the database")
	public ResponseEntity<List<SpaceshipDto>> getAllSpaceships() {
		List<SpaceshipDto> spaceshipDtoList = spaceshipService.getAllSpaceships();
		if (spaceshipDtoList.isEmpty()) {
			throw new IllegalArgumentException(ExceptionConstants.SPACESHIP_NOT_FOUND);
		}
		return ResponseEntity.ok(spaceshipDtoList);
	}

	@GetMapping("/spaceship/{spaceshipId}")
	@Operation(summary = "Get a spaceship", description = "Get a spaceship by its id")
	public ResponseEntity<SpaceshipDto> getSpaceship(@PathVariable("spaceshipId") Long spaceshipId) {
		SpaceshipDto spaceshipDto = spaceshipService.getSpaceship(spaceshipId)
				.orElseThrow(() -> new IllegalArgumentException(ExceptionConstants.SPACESHIP_NOT_FOUND));
		return ResponseEntity.ok(spaceshipDto);
	}

	@GetMapping("/spaceship/name/{spaceshipName}")
	@Operation(summary = "Get a spaceship by name", description = "Get a spaceship by its name")
	public ResponseEntity<List<SpaceshipDto>> getSpaceshipByName(@Valid @PathVariable("spaceshipName") String spaceshipName) {
		List<SpaceshipDto> spaceshipDtoList = spaceshipService.getSpaceshipByName(spaceshipName);
		return ResponseEntity.ok(spaceshipDtoList);
	}

	@PutMapping("/spaceship/{spaceshipId}")
	@Operation(summary = "Update a spaceship", description = "Update a spaceship by its id")
	public ResponseEntity<SpaceshipDto> updateSpaceship(@PathVariable("spaceshipId") Long spaceshipId,
			@Valid @RequestBody SpaceshipDto spaceshipDto) {

		spaceshipService.getSpaceship(spaceshipId)
				.orElseThrow(() -> new IllegalArgumentException(ExceptionConstants.SPACESHIP_NOT_FOUND));
		if (spaceshipService.existsSpaceshipByName(spaceshipDto.getSpaceshipName())) {
			throw new IllegalArgumentException(ExceptionConstants.SPACESHIP_NAME_ALREADY_EXISTS);
		}

		SpaceshipDto updatedSpaceship = spaceshipService.updateSpaceship(spaceshipId, spaceshipDto.getSpaceshipName());
		return ResponseEntity.ok(updatedSpaceship);
	}

	@DeleteMapping("/spaceship/{spaceshipId}")
	@Operation(summary = "Delete a spaceship", description = "Delete a spaceship by its id")
	public ResponseEntity<Void> deleteSpaceship(@PathVariable("spaceshipId") Long spaceshipId) {
		spaceshipService.getSpaceship(spaceshipId)
				.orElseThrow(() -> new IllegalArgumentException(ExceptionConstants.SPACESHIP_NOT_FOUND));
		spaceshipService.deleteSpaceship(spaceshipId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/spaceships/paginated")
	@Operation(summary = "Get paginated spaceships", description = "Get paginated list of spaceships from the database")
	public ResponseEntity<PagedModel<EntityModel<SpaceshipDto>>> getSpaceshipsPaginated(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "7") int size,
			PagedResourcesAssembler<SpaceshipDto> assembler) {
		Page<SpaceshipDto> spaceshipPage = spaceshipService.getSpaceshipsPaginated(page, size);
		if (spaceshipPage.getTotalPages() < page) {
			throw new IllegalArgumentException(ExceptionConstants.PAGE_NOT_FOUND);
		}
		if (spaceshipPage.isEmpty()) {
			throw new IllegalArgumentException(ExceptionConstants.SPACESHIP_NOT_FOUND);
		}
		return ResponseEntity.ok(assembler.toModel(spaceshipPage));
	}
}