package com.sda.javapoz24.hib;

import com.sda.javapoz24.hib.database.StudentDao;
import com.sda.javapoz24.hib.model.Gender;
import com.sda.javapoz24.hib.model.Student;

import java.util.Scanner;

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
            } else if (command.startsWith("delete")) {  // delete 1
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
            }

        } while (!command.equalsIgnoreCase("quit"));
    }
}
