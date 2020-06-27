package com.sda.javapoz24.hib;

import com.sda.javapoz24.hib.database.HibernateUtil;
import com.sda.javapoz24.hib.database.StudentDao;
import org.hibernate.SessionFactory;

import java.util.List;
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

            } else if (command.startsWith("list")) {    // list
            } else if (command.startsWith("delete")) {  // delete 1
            } else if (command.startsWith("modify")) {  // modify mariusz kowalski 30 true MALE 1
            }

        } while (!command.equalsIgnoreCase("quit"));
    }
}
