package com.sda.javapoz24.hib;

import com.sda.javapoz24.hib.database.StudentDao;
import com.sda.javapoz24.hib.model.ClassSubject;
import com.sda.javapoz24.hib.model.Gender;
import com.sda.javapoz24.hib.model.Grade;
import com.sda.javapoz24.hib.model.Student;
import lombok.extern.log4j.Log4j;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Log4j
public class Main {
    public static void main(String[] args) {
        StudentDao dao = new StudentDao();
        Scanner scanner = new Scanner(System.in);

        String command;

        do {
            System.out.println("Podaj komendę [insert,list,delete,modify]");
            command = scanner.nextLine();

            if (command.startsWith("insert")) {         // insert Paweł Gaweł 20 true MALE
                String[] words = command.split(" ");
                Student student = Student.builder()
                        .firstName(words[1])
                        .lastName(words[2])
                        .age(Integer.parseInt(words[3]))
                        .awarded(Boolean.parseBoolean(words[4]))
                        .gender(Gender.valueOf(words[5].toUpperCase()))
                        .build();

                dao.insertOrUpdate(student);
            } else if (command.startsWith("list")) {    // list
                List<Student> studentList = dao.getAll();

                log.info("List:");
                studentList.forEach(log::info);
            } else if (command.startsWith("delete")) {  // delete 1
                String[] words = command.split(" ");

                System.out.println("Success: " + dao.deleteStudent(Long.valueOf(words[1])));
            } else if (command.startsWith("modify")) {  // modify mariusz kowalski 30 true MALE 1
                String[] words = command.split(" ");
                Student student = Student.builder()
                        .firstName(words[1])
                        .lastName(words[2])
                        .age(Integer.parseInt(words[3]))
                        .awarded(Boolean.parseBoolean(words[4]))
                        .gender(Gender.valueOf(words[5].toUpperCase()))
                        .id(Long.parseLong(words[6]))
                        .build();

                dao.insertOrUpdate(student);
            } else if (command.startsWith("gradeadd")){ // gradeadd 1 5.0 ENGLISH
                handleAddGradeToStudent(dao, command);
            }

        } while (!command.equalsIgnoreCase("quit"));
    }

    private static void handleAddGradeToStudent(StudentDao dao, String command) {
        String[] words = command.split(" ");

        Optional<Student> studentOptional = dao.findById(Long.parseLong(words[1]));
        if(studentOptional.isPresent()) {
            Student student = studentOptional.get();

            Grade grade = Grade.builder()
                    .grade(Double.parseDouble(words[2]))
                    .subject(ClassSubject.valueOf(words[3]))
                    .student(student)
                    .build();

            // TODO: przekazujemy do DAO do zapisu
        }

    }
}
