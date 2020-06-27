package com.sda.javapoz24.hib.database;

import com.sda.javapoz24.hib.model.Student;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    public void insertOrUpdate(Student student) {
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

    public List<Student> getAll(){
        List<Student> list = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // narzędzie/klasa do tworzenia zapytania oraz kreowania kryteriów (kaluzula where)
            // WHERE ?
            CriteriaBuilder cb = session.getCriteriaBuilder();

            // klasa reprezentująca zapytanie bazodanowe. typ generyczny to typ obiektu którego się spodziewamy w wyniku.
            // SELECT ?
            CriteriaQuery<Student> query = cb.createQuery(Student.class);

            // Z jakiej tabeli wykonujemy zapytanie
            // FROM ?
            Root<Student> table = query.from(Student.class);

            // sormułowanie zapytania do tabeli
            // poniżej zapytanie o studenta o id = 1
//            query.select(table).where(cb.equal(table.get("id"), 1));

            // poniżej zapytanie select * bez klauzuli where.
            query.select(table); // wykonaj zapytanie o (query) z tabeli (table)


//            query.select(table)
//                    .where(
//                            cb.and(
//                                    cb.between(table.get("age"), 20, 30),
//                                    cb.equal(table.get("lastName"), "Kowalski"))
//                    );

            // wynik jest listą
            List<Student> results = session.createQuery(query).list();

            list.addAll(results); // dodaje wszystkie wyniki.

        }catch (HibernateException he){
            System.err.println("Listing error.");
            he.printStackTrace();
        }
        // mimo potencjalnego wystąpienia błędu, zawsze zwrócę liste (w najgorszym przypadku pustą)
        return list;
    }
}
