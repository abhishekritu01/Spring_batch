package com.spring.spring_batch_processing.controller;

import com.spring.spring_batch_processing.model.Student;
import com.spring.spring_batch_processing.reposiotry.StudentRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class StudentController {

    public StudentRepository studentRepository;

    public static final String TEMP_STORAGE = "/Users/bhavikajha/Desktop/batch-file/";

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @PostMapping("/importCsv")
    public void importCsvToDb(@RequestParam("file") MultipartFile multipartFile) {
        try {
            if (multipartFile.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }

            // Extracting file name
            String originalFilename = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            // Saving file to temporary storage
            File fileToImport = new File(TEMP_STORAGE + originalFilename);
            multipartFile.transferTo(fileToImport);

            // Running the job
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("fullPathFileName", fileToImport.getAbsolutePath())
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);

            // Optionally, delete the file from temp storage after processing
            // fileToImport.delete();

        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

}
