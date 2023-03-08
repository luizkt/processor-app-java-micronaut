package br.com.lkt.service.impl;

import br.com.lkt.entity.ApplicationResponseBody;
import br.com.lkt.entity.Student;
import br.com.lkt.exception.BusinessException;
import br.com.lkt.repository.StudentRepository;
import br.com.lkt.service.StudentService;
import br.com.lkt.utils.NameFormatter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Singleton;
import lombok.extern.log4j.Log4j2;

import javax.transaction.Transactional;
import java.util.List;

import static br.com.lkt.utils.Message.*;

@Singleton
@Log4j2
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final String logPrefix = "[Service] [Student]";

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    @Override
    public ApplicationResponseBody add(Student student) throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();

            log.info(logPrefix + " Adding student");

            student.setName(NameFormatter.capitalizeName(student.getName()));

            if (studentRepository.existsById(student.getStudentRegistrationNumber()))
                throw new BusinessException(HttpStatus.BAD_REQUEST, STUDENT_ALREADY_REGISTERED);

            log.debug(logPrefix + " Adding student: " + mapper.writeValueAsString(student));

            studentRepository.save(student);

            return new ApplicationResponseBody(STUDENT_ADDED_SUCCESSFULLY, student);
        } catch (BusinessException e) {
            log.error(logPrefix + " " + e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(logPrefix + " " + e.getMessage());
            throw e;
        }
    }

    @Transactional
    @Override
    public ApplicationResponseBody updateStudentByStudentRegistrationNumber(Student studentUpdate, Integer studentRegistrationNumber) throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();

            log.info(logPrefix + " Updating student");
            log.debug(logPrefix + " Updating student [" + studentRegistrationNumber + "] with: " + mapper.writeValueAsString(studentUpdate));

            Student studentDatabase = studentRepository.findByStudentRegistrationNumber(studentRegistrationNumber);

            if(studentDatabase == null)
                throw new BusinessException(HttpStatus.BAD_REQUEST, STUDENT_DOES_NOT_EXIST);

            log.info(logPrefix + " Student found");

            studentDatabase.setName(studentUpdate.getName() == null || studentUpdate.getName().isEmpty()
                    ? NameFormatter.capitalizeName(studentDatabase.getName())
                    : NameFormatter.capitalizeName(studentUpdate.getName()));

            log.debug(logPrefix + " Updating student: " + mapper.writeValueAsString(studentDatabase));

            studentRepository.save(studentDatabase);

            return new ApplicationResponseBody(STUDENT_UPDATED_SUCCESSFULLY, studentDatabase);
        } catch (BusinessException e) {
            log.error(logPrefix + " " + e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(logPrefix + " " + e.getMessage());
            throw e;
        }
    }

    @Transactional
    @Override
    public ApplicationResponseBody deleteStudentByStudentRegistrationNumber(Integer studentRegistrationNumber) throws JsonProcessingException {
        try {
            log.info(logPrefix + " Deleting student by registration number");
            log.debug(logPrefix + " Deleting student registration number [" + studentRegistrationNumber + "]");

            Student student = studentRepository.findByStudentRegistrationNumber(studentRegistrationNumber);

            if(student == null)
                throw new BusinessException(HttpStatus.BAD_REQUEST, STUDENT_DOES_NOT_EXIST);

            ObjectMapper mapper = new ObjectMapper();

            log.debug(logPrefix + " Deleting student: " + mapper.writeValueAsString(student));

            studentRepository.deleteById(student.getStudentRegistrationNumber());

            return new ApplicationResponseBody(STUDENT_DELETED_SUCCESSFULLY, student);
        } catch (BusinessException e) {
            log.error(logPrefix + " " + e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(logPrefix + " " + e.getMessage());
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ApplicationResponseBody findByName(String name) throws JsonProcessingException {
        try{
            log.info(logPrefix + " Searching student by name");
            log.debug(logPrefix + " Searching for student name [" + name + "]");
            List<Student> students = studentRepository.findByName(name);

            if(students.size() == 0)
                return new ApplicationResponseBody(STUDENT_NOT_FOUND, null);

            return new ApplicationResponseBody(FOUND_STUDENT_NAME, students);
        } catch (Exception e) {
            log.error(logPrefix + " " + e.getMessage());
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ApplicationResponseBody findByStudentRegistrationNumber(Integer studentRegistrationNumber) throws JsonProcessingException {
        try {
            log.info(logPrefix + " Searching for student by registration number");
            log.debug(logPrefix + " Searching for student registration number [" + studentRegistrationNumber + "]");
            Student student = studentRepository.findByStudentRegistrationNumber(studentRegistrationNumber);

            if(student == null)
                return new ApplicationResponseBody(STUDENT_NOT_FOUND, null);

            return new ApplicationResponseBody(FOUND_STUDENT_REGISTRATION_NUMBER, student);
        } catch (Exception e) {
            log.error(logPrefix + " " + e.getMessage());
            throw e;
        }
    }

}
