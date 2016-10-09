package tstore.dao.impl;

import org.hibernate.NonUniqueResultException;
import org.hibernate.query.Query;
import tstore.dao.UserDao;
import tstore.model.UserEntity;
import tstore.utils.HibernateSessionFactory;

import javax.persistence.NoResultException;
import java.text.MessageFormat;

/**
 * Created by mipan on 02.10.2016.
 */
public class UserDaoImpl extends GenericDaoImpl<UserEntity, Integer> implements UserDao {
    private  UserEntity user;

    public UserEntity findByCredential(String login, String password) {

        String hql = "from UserEntity where email = :login and  password = :password";
        Query query = getCurrentSession().createQuery(hql).setParameter("login", login).setParameter("password", password);

        try {
            user = (UserEntity) query.getSingleResult();
        }
         catch(NoResultException e) {
            return null;
        }
        catch (NonUniqueResultException e)
        {
//            todo Change exception (?)
//            todo add logging
        }

        return user;
    }

    public UserEntity findByLogin(String login) {

        String hql = "from UserEntity where email = :login";
        Query query = getCurrentSession().createQuery(hql).setParameter("login", login);

        try {
            user = (UserEntity) query.getSingleResult();
        }
        catch(NoResultException e) {
            return null;
        }
        catch (NonUniqueResultException e)
        {
//            todo Change exception (?)
//            todo add logging
        }

        return user;
    }
}
