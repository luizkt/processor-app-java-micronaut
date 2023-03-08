package br.com.lkt.repository;

import br.com.lkt.entity.Student;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {
	@Query("FROM Student s WHERE s.name LIKE %:name%")
	List<Student> findByName(String name);

	@Query("FROM Student s WHERE s.studentRegistrationNumber = :studentRegistrationNumber")
	Student findByStudentRegistrationNumber(Integer studentRegistrationNumber);
}
