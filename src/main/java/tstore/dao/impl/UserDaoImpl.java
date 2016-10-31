package tstore.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import tstore.dao.UserDao;
import tstore.model.UserEntity;
import tstore.model.enums.BasketOrderState;
import tstore.utils.SessionAttributes;

import javax.persistence.NoResultException;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by mipan on 02.10.2016.
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<UserEntity, Integer> implements UserDao {
    private UserEntity user;
    final static Logger logger = Logger.getLogger(UserDaoImpl.class);

    public UserEntity findByCredential(String login, String password) {

        String hql = "from UserEntity where email = :login and  password = :password";
        Query query = getCurrentSession().createQuery(hql).setParameter("login", login).setParameter("password", password);

        try {
            List list = query.list();
            user = (UserEntity) list.get(0);
            if (list.size()>1){
                throw new NonUniqueResultException(list.size());
            }
        } catch (IndexOutOfBoundsException e) {
            logger.info(MessageFormat.format("No result UserDaoImpl by : {0} - {1} UserEntity ", login, password), e);

            return null;
        } catch (NonUniqueResultException e) {
            logger.error(MessageFormat.format("Non Unique UserDaoImpl by : {0} - {1} UserEntity ", login, password), e);
        }

        return user;
    }

    public UserEntity findByLogin(String login) {

        String hql = "from UserEntity where email = :login";
        Query query = getCurrentSession().createQuery(hql).setParameter("login", login);

        try {
            List list = query.list();
            user = (UserEntity) list.get(0);
            if (list.size()>1){
                throw new NonUniqueResultException(list.size());
            }
        } catch (IndexOutOfBoundsException e) {
            logger.info(MessageFormat.format("No result UserDaoImpl by : {0} UserEntity ", login), e);
            return null;
        } catch (NonUniqueResultException e) {
            logger.error(MessageFormat.format("Non Unique UserDaoImpl by : {0} UserEntity ", login), e);
        }

        return user;
    }

    public List getTopTenUser() {
        String hql = "select client, count(client) as quantity from OrderEntity as orders inner join orders.client as client where orders.state != :basket group by client order by quantity desc";
        Query query = getCurrentSession().createQuery(hql).setParameter("basket", BasketOrderState.BASKET).setMaxResults(10);
        List list = query.list();
        return list;
    }
}
