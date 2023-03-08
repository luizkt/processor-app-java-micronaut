package br.com.lkt.service;

import br.com.lkt.entity.ApplicationResponseBody;

import java.io.IOException;

public interface LoaderService {
    ApplicationResponseBody loadFromCsv() throws IOException;
}
