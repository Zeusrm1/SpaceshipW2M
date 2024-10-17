package com.example.SpaceshipW2M.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SpaceshipDto {

	@NotBlank
	private String name;

}
