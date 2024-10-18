package com.example.SpaceshipW2M.controller.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SpaceshipAspect {

	@Before("execution(* com.example.SpaceshipW2M.controller.SpaceshipController.getSpaceship(..)) && args(spaceshipId)")
	public void logNegativeId(int spaceshipId) {
		if (spaceshipId < 0) {
			System.out.println("Negative id: " + spaceshipId);
		}
	}
}
