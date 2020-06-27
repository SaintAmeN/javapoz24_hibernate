package com.sda.javapoz24.hib.database;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

// SINGLETON
public class HibernateUtil {
    private static final SessionFactory SESSION_FACTORY;

    //    Sekcja static - wywołuje się po starcie aplikacji. wykonuje się jeden raz
    static {
        // pobieramy konfigurację i ustawiamy SESSION_FACTORY;
        try {
            // ta konfiguracja ładuje plik hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("/hibernate.cfg.xml"); // nie muszę tego wpisywać, domyślnie ładuje ten plik

            SESSION_FACTORY = configuration.buildSessionFactory();
        } catch (HibernateException hibernateException) {
            System.err.println("Błąd inicjalizacji sesji.");
            hibernateException.printStackTrace(); // wypisz na ekran
            throw hibernateException; // rzuć wyjątek wyżej
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
