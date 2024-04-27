package com.spring.spring_batch_processing.config;

import com.spring.spring_batch_processing.model.Student;
import org.springframework.batch.item.ItemProcessor;

public class StudentProcessor implements ItemProcessor<Student, Student> {

    @Override
    public Student process(Student student) throws Exception {
//        return student;
        return student.getCountry().equals("China") ? student : null;
    }

}
