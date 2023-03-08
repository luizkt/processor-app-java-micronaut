package br.com.lkt.service;

import br.com.lkt.entity.ApplicationResponseBody;
import br.com.lkt.entity.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface TransactionService {
    ApplicationResponseBody add(Transaction transaction) throws JsonProcessingException;
    ApplicationResponseBody findAllTransactionsFromStudent(Integer studentRegistrationNumber) throws JsonProcessingException;
    ApplicationResponseBody deleteTransactionById(Integer transactionId) throws JsonProcessingException;
}
