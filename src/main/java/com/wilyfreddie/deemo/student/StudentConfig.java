package com.wilyfreddie.deemo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Locale;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student ivan = new Student(
                    "Ivan Garan",
                    "garanivanfrederic@gmail.com",
                    LocalDate.of(2001, Month.NOVEMBER, 5)
            );
            Student michi = new Student(
                    "Michael Businos",
                    "michaelanthony.businos@honeywell.com",
                    LocalDate.of(2000, Month.MAY, 11)
            );

            studentRepository.saveAll(
                    List.of(ivan, michi)
            );
        };
    }
}
