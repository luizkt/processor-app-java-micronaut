package br.com.lkt.controller;

import br.com.lkt.entity.ApplicationResponseBody;
import br.com.lkt.entity.Student;
import br.com.lkt.service.StudentService;
import br.com.lkt.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class StudentController {

    private final StudentService studentService;
    private final TransactionService transactionService;

    public StudentController(StudentService studentService, TransactionService transactionService){
        this.studentService = studentService;
        this.transactionService = transactionService;
    }

    @Post(value = "/students", produces="application/json", consumes="application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Add new student"),
            @ApiResponse(responseCode = "400", description = "Some field have wrong information"),
            @ApiResponse(responseCode = "500", description = "Some error occurred"),
    })
    public ApplicationResponseBody add(@Valid @Body Student student) {
        return studentService.add(student);
    }

    @Patch(value = "/students/{studentRegistrationNumber}", produces="application/json", consumes="application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update the student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update the student"),
            @ApiResponse(responseCode = "400", description = "Some field have wrong information"),
            @ApiResponse(responseCode = "500", description = "Some error occurred"),
    })
    public ApplicationResponseBody updateStudentByStudentRegistrationNumber(
            @Body Student studentUpdate,
            @PathVariable Integer studentRegistrationNumber
    ) throws JsonProcessingException {
        return studentService.updateStudentByStudentRegistrationNumber(studentUpdate,studentRegistrationNumber);
    }

    @Delete(value = "/students/{studentRegistrationNumber}", produces="application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete the student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete the student"),
            @ApiResponse(responseCode = "400", description = "Some field have wrong information"),
            @ApiResponse(responseCode = "500", description = "Some error occurred"),
    })
    public ApplicationResponseBody deleteStudentByStudentRegistrationNumber(@PathVariable Integer studentRegistrationNumber) throws JsonProcessingException {
        return studentService.deleteStudentByStudentRegistrationNumber(studentRegistrationNumber);
    }

    @Get(value = "/students")
    @ResponseBody
    @ApiOperation(value = "Search students matching name pattern")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found student by name"),
            @ApiResponse(responseCode = "204", description = "No student found by name"),
            @ApiResponse(responseCode = "400", description = "Some field have wrong information"),
            @ApiResponse(responseCode = "500", description = "Some error occurred"),
    })
    public ApplicationResponseBody findByName(
            @QueryValue String name,
            HttpServletResponse response
    ) throws JsonProcessingException {
        ApplicationResponseBody applicationResponse = studentService.findByName(name);

        if(applicationResponse.getData() != null)
            response.setStatus(HttpStatus.OK.getCode());
        else
            response.setStatus(HttpStatus.NO_CONTENT.getCode());

        return applicationResponse;
    }

    @Get(value = "/students/{studentRegistrationNumber}")
    @ResponseBody
    @ApiOperation(value = "Search for student student by registration number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search for student by registration number"),
            @ApiResponse(responseCode = "204", description = "No student found by registration number"),
            @ApiResponse(responseCode = "400", description = "Some field have wrong information"),
            @ApiResponse(responseCode = "500", description = "Some error occurred"),
    })
    public ApplicationResponseBody findByStudentRegistrationNumber(
            @PathVariable Integer studentRegistrationNumber,
            HttpServletResponse response
    ) throws JsonProcessingException {
        ApplicationResponseBody applicationResponse = studentService.findByStudentRegistrationNumber(studentRegistrationNumber);

        if(applicationResponse.getData() != null)
            response.setStatus(HttpStatus.OK.getCode());
        else
            response.setStatus(HttpStatus.NO_CONTENT.getCode());

        return applicationResponse;
    }

    @Get(value = "/students/{studentRegistrationNumber}/transactions", produces="application/json")
    @ResponseBody
    @ApiOperation(value = "Find all transactions by student registration number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all transactions from the student"),
            @ApiResponse(responseCode = "204", description = "No transactions found from the student"),
            @ApiResponse(responseCode = "400", description = "Some field have wrong information"),
            @ApiResponse(responseCode = "500", description = "Some error occurred"),
    })
    public ApplicationResponseBody findAllTransactionsFromStudent(
            HttpServletResponse response,
            @PathVariable Integer studentRegistrationNumber
    ) throws JsonProcessingException {
        ApplicationResponseBody applicationResponse = transactionService.findAllTransactionsFromStudent(studentRegistrationNumber);

        if(applicationResponse.getData() != null)
            response.setStatus(HttpStatus.OK.getCode());
        else
            response.setStatus(HttpStatus.NO_CONTENT.getCode());

        return applicationResponse;
    }

}
