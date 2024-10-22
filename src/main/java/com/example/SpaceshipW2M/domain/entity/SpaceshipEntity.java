package com.example.SpaceshipW2M.domain.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TBL_SPACESHIPS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpaceshipEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "spaceship_name")
	@NotBlank
	private String spaceshipName;

	public SpaceshipEntity(@NotBlank String spaceshipName) {
		this.spaceshipName = spaceshipName;
	}
}
