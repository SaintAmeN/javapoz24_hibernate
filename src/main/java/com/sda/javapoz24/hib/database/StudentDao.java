package com.sda.javapoz24.hib.database;

import com.sda.javapoz24.hib.model.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.RollbackException;

public class StudentDao {
    public void insert(Student student) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Baza transakcyjna jest ACID.
            // Atomicity - transakcja jest niepodzielna
            // Consistency - spójność danych
            // Isolation - transakcje nie wpływają na siebie
            // Durability - wytrzymałość/odporność na błędy. Dane zostaną zapisane trwale na dysk.
            //
            // BEGIN OPERATIONS COMMIT
            transaction = session.beginTransaction();   // BEGIN

            // wstawienie rekrdu do bazy danych
            // SAVE - zapisz/wstaw    - jeśli brakuje ID (wartość null)
            // UPDATE - aktualizuj    - jeśli jest ID (jeśli jest błędne id, to wygeneruje błąd)
            session.saveOrUpdate(student);              // OERATIONS

            transaction.commit();                       // COMMIT
            // Pipeline poniżej - jeśli zdarzy się IllegalStateException lub RollbackException
        } catch (IllegalStateException | RollbackException ise) {
            System.err.println("Błąd wstawiania rekordu.");
            ise.printStackTrace();

            if(transaction != null) {
                transaction.rollback();
            }
        }
    }
}
