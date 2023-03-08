package br.com.lkt.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "STUDENT", uniqueConstraints = { @UniqueConstraint(columnNames = "STUDENT_REGISTRATION_NUMBER") })
@Serdeable
public class Student implements Serializable {

    public Student(){}

    public Student(Integer studentRegistrationNumber, String name){
        this.studentRegistrationNumber = studentRegistrationNumber;
        this.name = name;
    }

    @Id
    @Column(name = "STUDENT_REGISTRATION_NUMBER", unique = true, nullable = false)
    @JsonProperty("student_registration_number")
    @NotNull
    private Integer studentRegistrationNumber;

    @Column(name = "NAME")
    @JsonProperty("name")
    @NotNull
    private String name;

    public Integer getStudentRegistrationNumber() {
        return studentRegistrationNumber;
    }

    public void setStudentRegistrationNumber(Integer studentRegistrationNumber) {
        this.studentRegistrationNumber = studentRegistrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
