package com.example.SpaceshipW2M.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class SpaceshipDto {

	@NotBlank
    private Long id;

	@NotBlank
    private String spaceshipName;

    @JsonCreator
    public SpaceshipDto(@JsonProperty("id") Long id,
                        @JsonProperty("spaceshipName") String spaceshipName) {
        this.id = id;
        this.spaceshipName = spaceshipName;
    }

	public SpaceshipDto() {

	}
}