package tstore.service.impl;

import org.hibernate.Hibernate;
import tstore.dao.UserDao;
import tstore.dao.impl.UserDaoImpl;
import tstore.model.OrderEntity;
import tstore.model.UserEntity;
import tstore.service.UserService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mipan on 08.10.2016.
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    public UserEntity getUser(String login, String password) {
        userDao.beginTransaction();
        UserEntity userEntity = userDao.findByCredential(login, password);
        userDao.closeTransaction();
        return userEntity;
    }
    public UserEntity getUser(String login) {
        userDao.beginTransaction();
        UserEntity userEntity = userDao.findByLogin(login);
        userDao.closeTransaction();
        return userEntity;
    }

    public UserEntity getUserById(Integer id) {
        userDao.beginTransaction();
        UserEntity userEntity = userDao.findById(UserEntity.class, id);
//        Hibernate.initialize(userEntity.getOrders());
        userDao.closeTransaction();
        Hibernate.initialize(userEntity);
        return userEntity;
    }
    /*public UserEntity getUserById(String id) {
        return getUserById(Integer.valueOf(id));
    }
*/
    public void createUser(UserEntity userEntity) {
        userDao.beginTransaction();
        userDao.persist(userEntity);
        userDao.closeTransaction();
    }

    public void update(UserEntity userEntity) {
        userDao.beginTransaction();
        userDao.update(userEntity);
        userDao.closeTransaction();
    }

    public UserEntity getFullUserById(Integer id) {
        userDao.beginTransaction();
        UserEntity userEntity = userDao.findById(UserEntity.class, id);
        OrderEntity basketByUserId = new OrderServiceImpl().getBasketByUserId(id);
        Set setS = new HashSet();
        setS.add(basketByUserId);
        userEntity.setOrders(setS);
        userEntity.getOrders();
        userDao.closeTransaction();
        return userEntity;
    }
}
