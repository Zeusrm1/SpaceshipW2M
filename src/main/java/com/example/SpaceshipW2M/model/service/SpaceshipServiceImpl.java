package com.example.SpaceshipW2M.model.service;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.SpaceshipW2M.model.dto.SpaceshipDto;
import com.example.SpaceshipW2M.model.entity.SpaceshipEntity;
import com.example.SpaceshipW2M.model.repository.SpaceshipRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceshipServiceImpl implements SpaceshipService {

    @Autowired
    private SpaceshipRepository spaceshipRepository;


    @CachePut(value = "spaceship", key = "#result.id")
    public SpaceshipDto saveSpaceship(SpaceshipDto spaceshipDto) {
        log.info("Saving spaceship with name: {}", spaceshipDto.getSpaceshipName());
        SpaceshipEntity spaceshipEntity = this.convertToEntity(spaceshipDto);
        spaceshipEntity = spaceshipRepository.save(spaceshipEntity);
        return convertToDto(spaceshipEntity);
    }

    @Cacheable(value = "spaceship", key = "#id")
    public Optional<SpaceshipDto> getSpaceship(Long id) {
        log.info("Getting spaceship with id: {}", id);
        return spaceshipRepository.findById(id).map(this::convertToDto);
    }

    @Cacheable(value = "spaceship", key = "#spaceshipName")
    public List<SpaceshipDto> getSpaceshipByName(String spaceshipName) {
        log.info("Getting spaceship with name: {}", spaceshipName);
        return spaceshipRepository.findBySpaceshipNameContainingIgnoreCase(spaceshipName)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Cacheable(value = "spaceship", key = "#page + #size")
    public Page<SpaceshipDto> getSpaceshipsPaginated(int page, int size) {
        log.info("Getting spaceships with page: {} and size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return spaceshipRepository.findAll(pageable).map(this::convertToDto);
    }

    @Cacheable(value = "spaceship")
    public List<SpaceshipDto> getAllSpaceships() {
        log.info("Getting all spaceships");
        return spaceshipRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @CachePut(value = "spaceship", key = "#id")
    public SpaceshipDto updateSpaceship(Long id, String spaceshipName) {
        log.info("Updating spaceship with id: {}", id);
        SpaceshipEntity spaceshipEntity = spaceshipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Spaceship not found with id: " + id));
        spaceshipEntity.setSpaceshipName(spaceshipName);
        spaceshipRepository.save(spaceshipEntity);
        return convertToDto(spaceshipEntity);
    }

    @CacheEvict(value = "spaceship", key = "#id")
    public void deleteSpaceship(Long id) {
        log.info("Deleting spaceship with id: {}", id);
        spaceshipRepository.deleteById(id);
    }

    public boolean existsSpaceshipByName(String spaceshipName) {
        return spaceshipRepository.existsSpaceshipBySpaceshipName(spaceshipName);
    }

    private SpaceshipDto convertToDto(SpaceshipEntity spaceshipEntity) {
        return new SpaceshipDto(spaceshipEntity.getId(), spaceshipEntity.getSpaceshipName());
    }

    private SpaceshipEntity convertToEntity(SpaceshipDto spaceshipDto) {
        return new SpaceshipEntity(spaceshipDto.getSpaceshipName());
    }
}