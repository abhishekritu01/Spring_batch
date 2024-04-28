package com.spring.spring_batch_processing.config;

import com.spring.spring_batch_processing.model.Student;
import com.spring.spring_batch_processing.reposiotry.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class StudentWriter implements ItemWriter<Student> {

    @Autowired
    private StudentRepository studentRepository;


    @Override
    public void write(Chunk<? extends Student> chunk) throws Exception {
        System.out.println("Thread Name : -"+  Thread.currentThread().getName());
        studentRepository.saveAll(chunk);
    }


}
