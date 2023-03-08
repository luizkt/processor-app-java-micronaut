package br.com.lkt.service.impl;

import br.com.lkt.service.HealthService;
import jakarta.inject.Singleton;

@Singleton
public class HealthServiceImpl implements HealthService {
    @Override
    public String healthCheck() {
        return "UP";
    }
}
