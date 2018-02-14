package com.vmturbo.tetration.simulator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vmturbo.tetration.simulator.controller.TetrationController;
import com.vmturbo.tetration.simulator.service.TetrationService;

@Configuration
public class TetrationConfig {
    /**
     * Constructs the service bean.
     *
     * @return The service bean.
     */
    @Bean
    public TetrationService tetrationService() {
        return new TetrationService();
    }

    /**
     * Constructs the Tetration controller.
     *
     * @return The Tetration controller.
     */
    @Bean
    public TetrationController tetrationController() {
        return new TetrationController(tetrationService());
    }
}
