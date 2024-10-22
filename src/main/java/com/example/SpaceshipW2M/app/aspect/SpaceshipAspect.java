package com.example.SpaceshipW2M.app.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SpaceshipAspect {

    private static final Logger logger = LoggerFactory.getLogger(SpaceshipAspect.class);
    @Before("execution(* com.example.SpaceshipW2M.controller.SpaceshipController.getSpaceship(..)) && args(spaceshipId)")
    public void logNegativeId(Long spaceshipId) {
        if (spaceshipId < 0) {
            logger.warn("Attempted to get a spaceship with a negative id: {}", spaceshipId);
        }
    }
}