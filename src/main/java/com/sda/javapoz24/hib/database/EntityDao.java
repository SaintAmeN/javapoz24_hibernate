package com.sda.javapoz24.hib.database;

import com.sda.javapoz24.hib.model.IBaseEntity;
import com.sda.javapoz24.hib.model.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.RollbackException;

// Repository
public class EntityDao<T extends IBaseEntity> {

    public void insertOrUpdate(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.saveOrUpdate(entity);

            transaction.commit();
        } catch (IllegalStateException | RollbackException ise) {
            System.err.println("Błąd wstawiania rekordu.");
            ise.printStackTrace();

            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
