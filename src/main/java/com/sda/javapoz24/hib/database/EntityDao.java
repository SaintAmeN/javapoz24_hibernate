package com.sda.javapoz24.hib.database;

import com.sda.javapoz24.hib.model.IBaseEntity;
import com.sda.javapoz24.hib.model.Student;
import com.sda.javapoz24.hib.model.StudentShortInfo;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<T> findById(Long id, Class<T> tClass) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> query = cb.createQuery(tClass);
            Root<T> table = query.from(tClass);

            query.select(table).where(cb.equal(table.get("id"), id));

            T entity = session.createQuery(query).getSingleResult();

            return Optional.ofNullable(entity);
        } catch (PersistenceException he) {
            System.err.println("Listing error.");
            he.printStackTrace();
        }
        return Optional.empty();
    }

    public List<T> getAll(Class<T> tClass) {
        List<T> list = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> query = cb.createQuery(tClass);
            Root<T> table = query.from(tClass);

            query.select(table);
            list.addAll(session.createQuery(query).list());

        } catch (HibernateException he) {
            System.err.println("Listing error.");
            he.printStackTrace();
        }
        return list;
    }

//    public List<StudentShortInfo> getAllShortInfo(Class<T> tClass) {
//        List<StudentShortInfo> list = new ArrayList<>();
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            CriteriaBuilder cb = session.getCriteriaBuilder();
//            CriteriaQuery<StudentShortInfo> query = cb.createQuery(StudentShortInfo.class);
//            Root<Student> table = query.from(Student.class);
//
//            query.select(cb.construct(StudentShortInfo.class, table.get("firstName"), table.get("lastName")));
//            list.addAll(session.createQuery(query).list());
//
//        } catch (HibernateException he) {
//            System.err.println("Listing error.");
//            he.printStackTrace();
//        }
//        return list;
//    }
}
