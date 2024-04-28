package com.spring.spring_batch_processing.config;

import com.spring.spring_batch_processing.model.Student;
import com.spring.spring_batch_processing.partition.StudentRangePartation;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@AllArgsConstructor
public class SpringBatchConfig {

    //    private final StudentRepository studentRepository;
    private final StudentWriter studentWriter;

    @Bean
    public FlatFileItemReader<Student> reader() {
        FlatFileItemReader<Student> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("students.csv"));
        reader.setName("CSV-Reader");
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper());
        return reader;
    }

    private LineMapper<Student> lineMapper() {
        DefaultLineMapper<Student> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();

        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob");

        BeanWrapperFieldSetMapper<Student> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Student.class);
        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public StudentProcessor processor() {
        return new StudentProcessor();
    }

//    @Bean
//    public RepositoryItemWriter<Student> writer() {
//        RepositoryItemWriter<Student> writer = new RepositoryItemWriter<>();
//        writer.setRepository(studentRepository);
//        writer.setMethodName("save");
//        return writer;
//    }

    @Bean
    public StudentRangePartation partitioner() {
        return new StudentRangePartation();
    }


    @Bean
    public Step childStep(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager
    ) {
        return new StepBuilder("step1", jobRepository)
                .<Student, Student>chunk(250, platformTransactionManager)
                .reader(reader())
                .processor(processor())
                .writer(studentWriter)
//                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("importCsv", jobRepository)
                .flow(childStep(jobRepository, platformTransactionManager))
                .end()
                .build();
    }

    //task executor
    @Bean
    protected TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.setQueueCapacity(4);
        return taskExecutor;
    }


     //partition handler
    @Bean
    public TaskExecutorPartitionHandler partitionHandler(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();
        partitionHandler.setGridSize(4);
        partitionHandler.setStep(childStep(jobRepository, platformTransactionManager));
        partitionHandler.setTaskExecutor(taskExecutor());
        return partitionHandler;
    }

    //steps
    @Bean
    public Step parentStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("parentStep", jobRepository)
                .partitioner("childStep", partitioner())
                .partitionHandler(partitionHandler(jobRepository, platformTransactionManager))
                .build();
    }


}
