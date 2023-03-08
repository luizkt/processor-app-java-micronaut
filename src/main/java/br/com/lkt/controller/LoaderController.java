package br.com.lkt.controller;

import br.com.lkt.entity.ApplicationResponseBody;
import br.com.lkt.service.LoaderService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.io.IOException;

@Controller
public class LoaderController {

    private final LoaderService loaderService;

    public LoaderController(LoaderService loaderService) {
        this.loaderService = loaderService;
    }

    @Post(value = "/loader/load_from_csv", produces="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add students and transactions from csv files")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Add students and transactions from csv files"),
            @ApiResponse(responseCode = "400", description = "Some field have wrong information"),
            @ApiResponse(responseCode = "500", description = "Some error occurred"),
    })
    public ApplicationResponseBody loadFromCsv() throws IOException {
        return loaderService.loadFromCsv();
    }
}
