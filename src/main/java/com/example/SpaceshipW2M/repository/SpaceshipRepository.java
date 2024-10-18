package com.example.SpaceshipW2M.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpaceshipW2M.entity.SpaceshipEntity;

@Repository
public interface SpaceshipRepository extends JpaRepository<SpaceshipEntity, Integer> {

	List<SpaceshipEntity> findBySpaceshipNameContainingIgnoreCase(String spaceshipName);

	boolean existsSpaceshipBySpaceshipName(String spaceshipName);

}
