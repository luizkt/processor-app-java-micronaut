package br.com.lkt.repository;

import br.com.lkt.entity.Student;
import br.com.lkt.entity.Transaction;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    @Query("FROM Transaction t WHERE t.student = :studentRegistrationNumber")
    List<Transaction> findByStudentRegistrationNumber(Student studentRegistrationNumber);

    @Query("FROM Transaction t WHERE t.transactionId = :transactionId")
    Transaction findByTransactionId(Integer transactionId);
}
