package com.sda.javapoz24.hib;

import com.sda.javapoz24.hib.database.HibernateUtil;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory factory = HibernateUtil.getSessionFactory();

    }
}
