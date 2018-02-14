package com.vmturbo.tetration.simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@Import({TetrationWebSecurityConfig.class, MVCConfig.class, TetrationConfig.class})
public class TetrationSimulator {
    /**
     * The main.
     *
     * @param args The arguments.
     * @throws Exception Just in case.
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(TetrationSimulator.class, args);
    }
}
