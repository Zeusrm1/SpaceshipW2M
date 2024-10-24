package com.example.SpaceshipW2M.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.SpaceshipW2M.controller.utils.ControllerExceptionUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
public class SpaceshipDto {

	@NotNull(message = ControllerExceptionUtils.SPACESHIP_ID_REQUIRED)
    private Long id;

	@NotBlank(message = ControllerExceptionUtils.SPACESHIP_NAME_REQUIRED)
    private String spaceshipName;

    @JsonCreator
    public SpaceshipDto(@JsonProperty("id") Long id,
                        @JsonProperty("spaceshipName") String spaceshipName) {
        this.id = id;
        this.spaceshipName = spaceshipName;
    }

}