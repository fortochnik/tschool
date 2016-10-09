package tstore.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import tstore.dao.GenericDao;
import tstore.utils.HibernateSessionFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mipan on 02.10.2016.
 */
public class GenericDaoImpl<T, Id extends Serializable> implements GenericDao<T, Id> {

    private Session session;
    private Transaction currentTransaction;

    public void persist(T entity) {
        session.save(entity);
    }

    public void update(T entity) {
        session.update(entity);
    }

    public void delete(T entity) {
        session.delete(entity);
    }

    public void deleteAll(Class<T> clazz) {
        List<T> allEntities = findAll(clazz);
        for (T entity : allEntities) {
            delete(entity);
        }
    }

    public T findById(Class<T> clazz, Id id) {
        return (T)getCurrentSession().get(clazz.getName(), id);
    }

    public List<T> findAll(Class<T> clazz) {
        Query query = getCurrentSession().createQuery("from " + clazz.getName());
        List result = query.getResultList();
        return result;
    }

    public void beginTransaction() {
        session = getCurrentSession();
        currentTransaction = session.beginTransaction();
    }

    public void closeTransaction() {
        currentTransaction.commit();
        session.close();
    }

    protected Session getCurrentSession(){
        session = HibernateSessionFactory.getSessionFactory().openSession();
        return session;
    }
}
