package com.example.SpaceshipW2M.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.SpaceshipW2M.dto.SpaceshipDto;
import com.example.SpaceshipW2M.entity.SpaceshipEntity;
import com.example.SpaceshipW2M.repository.SpaceshipRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SpaceshipServiceImpl implements SpaceshipService {

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SpaceshipServiceImpl.class);

    @CachePut(value = "spaceship", key = "#result.id")
    public SpaceshipDto saveSpaceship(SpaceshipDto spaceshipDto) {
        logger.info("Saving spaceship with name: {}", spaceshipDto.getSpaceshipName());
        SpaceshipEntity spaceshipEntity = convertToEntity(spaceshipDto);
        spaceshipRepository.save(spaceshipEntity);
        return convertToDto(spaceshipEntity);
    }

    @Cacheable(value = "spaceship", key = "#id")
    public Optional<SpaceshipDto> getSpaceship(Long id) {
        logger.info("Getting spaceship with id: {}", id);
        return spaceshipRepository.findById(id).map(this::convertToDto);
    }

    @Cacheable(value = "spaceship", key = "#spaceshipName")
    public List<SpaceshipDto> getSpaceshipByName(String spaceshipName) {
        logger.info("Getting spaceship with name: {}", spaceshipName);
        return spaceshipRepository.findBySpaceshipNameContainingIgnoreCase(spaceshipName)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "spaceship", key = "#page + #size")
    public Page<SpaceshipDto> getSpaceshipsPaginated(int page, int size) {
        logger.info("Getting spaceships with page: {} and size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return spaceshipRepository.findAll(pageable).map(this::convertToDto);
    }

    @Cacheable(value = "spaceship")
    public List<SpaceshipDto> getAllSpaceships() {
        logger.info("Getting all spaceships");
        return spaceshipRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @CachePut(value = "spaceship", key = "#id")
    public SpaceshipDto updateSpaceship(Long id, String spaceshipName) {
        logger.info("Updating spaceship with id: {}", id);
        SpaceshipEntity spaceshipEntity = spaceshipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Spaceship not found with id: " + id));
        spaceshipEntity.setSpaceshipName(spaceshipName);
        spaceshipRepository.save(spaceshipEntity);
        return convertToDto(spaceshipEntity);
    }

    @CacheEvict(value = "spaceship", key = "#id")
    public void deleteSpaceship(Long id) {
        logger.info("Deleting spaceship with id: {}", id);
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