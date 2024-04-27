package com.spring.spring_batch_processing.reposiotry;

import com.spring.spring_batch_processing.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {


}
