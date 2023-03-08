package br.com.lkt.service.impl;

import br.com.lkt.entity.ApplicationResponseBody;
import br.com.lkt.entity.Student;
import br.com.lkt.entity.Transaction;
import br.com.lkt.repository.StudentRepository;
import br.com.lkt.repository.TransactionRepository;
import br.com.lkt.service.LoaderService;
import br.com.lkt.utils.NameFormatter;
import jakarta.inject.Singleton;
import lombok.extern.log4j.Log4j2;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Log4j2
public class LoaderServiceImpl implements LoaderService {

    private final StudentRepository studentRepository;
    private final TransactionRepository transactionRepository;
    private final String logPrefix = "[Service] [Loader]";

    public LoaderServiceImpl(StudentRepository studentRepository, TransactionRepository transactionRepository) {
        this.studentRepository = studentRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional()
    @Override
    public ApplicationResponseBody loadFromCsv() throws IOException {
        try {
            log.info(logPrefix + " Reading students from file");
            List<Student> students = csvReaderStudent();
            log.info(logPrefix + " Student's file read successfully");

            log.info(logPrefix + " Reading transactions from file");
            List<Transaction> transactions = csvReaderTransaction(students);
            log.info(logPrefix + " Transaction's file read successfully");

            log.info(logPrefix + " Adding all student's to the database");
            studentRepository.saveAll(students);
            log.info(logPrefix + " Added all student's successfully");

            log.info(logPrefix + " Adding all transaction's to the database");
            transactionRepository.saveAll(transactions);
            log.info(logPrefix + " Added all transaction's successfully");

            return new ApplicationResponseBody(logPrefix + " Added all the students and transactions successfully", null);
        } catch (Exception e) {
            log.error(logPrefix + " " + e.getMessage());
            throw e;
        }
    }

    private List<Student> csvReaderStudent() throws IOException {
        List<Student> students = new ArrayList<>();

        BufferedReader csvReader = new BufferedReader(new FileReader("./files/students_list.csv"));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            Student student = new Student(Integer.parseInt(data[0]), NameFormatter.capitalizeName(data[1]));

            students.add(student);
        }
        csvReader.close();
        return students;
    }

    private List<Transaction> csvReaderTransaction(List<Student> students) throws IOException {
        List<Transaction> transactions = new ArrayList<>();

        BufferedReader csvReader = new BufferedReader(new FileReader("./files/transactions_list.csv"));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            Transaction transaction = new Transaction(Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2], Double.parseDouble(data[3]), data[4]);

            transaction.setStudent(
                    students.stream()
                            .filter(student -> transaction.getStudentRegistrationNumber().equals(student.getStudentRegistrationNumber()))
                            .findAny()
                            .orElse(null));

            transactions.add(transaction);
        }
        csvReader.close();
        return transactions;
    }
}
