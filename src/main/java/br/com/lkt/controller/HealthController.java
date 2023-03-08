package br.com.lkt.controller;

import br.com.lkt.service.HealthService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller
public class HealthController {
    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @Get
    public String healthCheck() {
        return healthService.healthCheck();
    }
}
