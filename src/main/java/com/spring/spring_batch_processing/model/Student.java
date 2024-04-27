package com.spring.spring_batch_processing.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "students_data")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {

//    id,firstName,lastName,email,gender,contactNo,country,dob

    @Id
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;

    private String contactNo;

    private String country;

    private String dob;


}
