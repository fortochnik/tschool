package tstore.service.impl;

import tstore.dao.UserDao;
import tstore.dao.impl.UserDaoImpl;
import tstore.model.UserEntity;
import tstore.service.UserService;

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
        userDao.closeTransaction();
        return userEntity;
    }

    public void createUser(UserEntity userEntity) {
        userDao.beginTransaction();
        userDao.persist(userEntity);
        userDao.closeTransaction();
    }
}
