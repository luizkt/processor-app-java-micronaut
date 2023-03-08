package br.com.lkt.controller;

import br.com.lkt.entity.ApplicationResponseBody;
import br.com.lkt.entity.Transaction;
import br.com.lkt.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.validation.Valid;

@Controller
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Post(path = "/transactions", produces="application/json", consumes="application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new transaction for the student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Add new transaction"),
            @ApiResponse(responseCode = "400", description = "Some field have wrong information"),
            @ApiResponse(responseCode = "500", description = "Some error occurred"),
    })
    public ApplicationResponseBody add(@Valid @Body Transaction transaction) throws JsonProcessingException {
        return transactionService.add(transaction);
    }

    @RequestMapping(path = "/transactions/{transactionId}", method = RequestMethod.DELETE, produces="application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete the transaction by transaction ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete the transaction"),
            @ApiResponse(responseCode = "400", description = "Some field have wrong information"),
            @ApiResponse(responseCode = "500", description = "Some error occurred"),
    })
    public ApplicationResponseBody deleteTransactionById(@PathVariable Integer transactionId) throws JsonProcessingException {
        return transactionService.deleteTransactionById(transactionId);
    }
}
