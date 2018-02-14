package com.vmturbo.tetration.simulator.controller;

import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vmturbo.tetration.simulator.service.TetrationService;

/**
 * The tetration controller.
 */
@RestController
@RequestMapping("/openapi/v1")
public class TetrationController {
    /**
     * The tetration service.
     */
    private final TetrationService service_;

    /**
     * Constructs the controller.
     *
     * @param service The service.
     */
    public TetrationController(final TetrationService service) {
        service_ = Objects.requireNonNull(service);
    }

    /**
     * Returns simulated response.
     *
     * @return The simulated response.
     */
    @RequestMapping(path = "/flowsearch",
                    method = RequestMethod.POST,
                    consumes = {MediaType.APPLICATION_JSON_VALUE},
                    produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<String> flowSearch(@RequestBody final @NotNull String request) {
        return new ResponseEntity<>(service_.flowSearch(request), HttpStatus.OK);
    }
}
