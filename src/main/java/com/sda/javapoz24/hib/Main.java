package com.sda.javapoz24.hib;

import com.sda.javapoz24.hib.database.EntityDao;
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
        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            System.out.println("Podaj komendę [insert,list,delete,modify]");
            command = scanner.nextLine();

            if (command.startsWith("insert")) {         // insert Paweł Gaweł 20 true MALE
                handleStudentAdd(command);
            } else if (command.startsWith("list")) {    // list
                handleListStudents();
            } else if (command.startsWith("delete")) {  // delete 1
                handleDeleteStudent(command);
            } else if (command.startsWith("modify")) {  // modify mariusz kowalski 30 true MALE 1
                handleStudentUpdate(command);
            } else if (command.startsWith("gradeadd")){ // gradeadd 1 5.0 ENGLISH (dodaj ocenę studentowi o id 1)
                handleAddGradeToStudent(command);
            } else if(command.startsWith("gradelist")){ // gradelist 1 (wypisz oceny studenta o id 1)

            }else if(command.startsWith("gradedelete")){ // gradedelete 1 (usuń ocenę o identyfikatorze 1)
                handleGradeDelete(command);
            }else if(command.startsWith("gradeupdate")){ // gradeupdate 1 4.5 ENGLISH (aktualizuj ocenę o idenyfikatorze 1)
                handleGradeUpdate(command);
            }

        } while (!command.equalsIgnoreCase("quit"));
    }

    private static void handleGradeUpdate(String command) {
        EntityDao<Grade> gradeEntityDao = new EntityDao<>();
        String[] words = command.split(" ");

        Optional<Grade> gradeOptional = gradeEntityDao.findById(Long.parseLong(words[1]), Grade.class);
        if(gradeOptional.isPresent()) {
            Grade grade = gradeOptional.get();

            grade.setGrade(Double.parseDouble(words[2]));
            grade.setSubject(ClassSubject.valueOf(words[3]));

            gradeEntityDao.insertOrUpdate(grade);
            log.info("Update complete.");
        }else{
            log.info("Grade not found.");
        }
    }

    private static void handleGradeDelete(String command) {
        EntityDao<Grade> gradeEntityDao = new EntityDao<>();

        String[] words = command.split(" ");
        System.out.println("Success: " + gradeEntityDao.delete(Long.valueOf(words[1]), Grade.class));
    }

    private static void handleStudentUpdate(String command) {
        EntityDao<Student> studentEntityDao = new EntityDao<>();
        String[] words = command.split(" ");
        Student student = Student.builder()
                .firstName(words[1])
                .lastName(words[2])
                .age(Integer.parseInt(words[3]))
                .awarded(Boolean.parseBoolean(words[4]))
                .gender(Gender.valueOf(words[5].toUpperCase()))
                .id(Long.parseLong(words[6]))
                .build();

        studentEntityDao.insertOrUpdate(student);
    }

    private static void handleDeleteStudent(String command) {
        EntityDao<Student> studentEntityDao = new EntityDao<>();
        String[] words = command.split(" ");

        System.out.println("Success: " + studentEntityDao.delete(Long.valueOf(words[1]), Student.class));
    }

    private static void handleStudentAdd(String command) {
        EntityDao<Student> studentEntityDao = new EntityDao<>();

        String[] words = command.split(" ");
        Student student = Student.builder()
                .firstName(words[1])
                .lastName(words[2])
                .age(Integer.parseInt(words[3]))
                .awarded(Boolean.parseBoolean(words[4]))
                .gender(Gender.valueOf(words[5].toUpperCase()))
                .build();

        studentEntityDao.insertOrUpdate(student);
    }

    private static void handleListStudents() {
        EntityDao<Student> studentEntityDao = new EntityDao<>();
        List<Student> studentList = studentEntityDao.getAll(Student.class);

        log.info("List:");
        studentList.forEach(log::info);
    }

    private static void handleAddGradeToStudent(String command) {
        String[] words = command.split(" ");
        EntityDao<Grade> gradeEntityDao = new EntityDao<>();
        EntityDao<Student> studentEntityDao = new EntityDao<>();

        Optional<Student> studentOptional = studentEntityDao.findById(Long.parseLong(words[1]), Student.class);
        if(studentOptional.isPresent()) {
            Student student = studentOptional.get();

            Grade grade = Grade.builder()
                    .grade(Double.parseDouble(words[2]))
                    .subject(ClassSubject.valueOf(words[3]))
                    .student(student)
                    .build();

            gradeEntityDao.insertOrUpdate(grade);
        }
    }
}
