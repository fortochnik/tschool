package tstore.service.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tstore.dao.UserDao;
import tstore.dao.impl.UserDaoImpl;
import tstore.model.OrderEntity;
import tstore.model.UserEntity;
import tstore.model.enums.Role;
import tstore.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mipan on 08.10.2016.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * Get user by credentials
     * @param login user login
     * @param password user's password
     * @return {@link UserEntity}
     */
    public UserEntity getUser(String login, String password) {
//        userDao.beginTransaction();
        UserEntity userEntity = userDao.findByCredential(login, password);
//        userDao.closeTransaction();
        return userEntity;
    }

    /**
     * Get {@link UserEntity} by login
     * @param login user login
     * @return {@link UserEntity}
     */
    public UserEntity getUser(String login) {
//        userDao.beginTransaction();
        UserEntity userEntity = userDao.findByLogin(login);
//        userDao.closeTransaction();
        return userEntity;
    }

    /**
     * Get {@link UserEntity} by id
     * @param id user id
     * @return {@link UserEntity}
     */
    public UserEntity getUserById(Integer id) {
//        userDao.beginTransaction();
        UserEntity userEntity = userDao.findById(UserEntity.class, id);
//        userDao.closeTransaction();
        Hibernate.initialize(userEntity);
        return userEntity;
    }

    /**
     * Create user
     * @param userEntity {@link UserEntity} for create
     */
    public void createUser(UserEntity userEntity) {
//        userDao.beginTransaction();
        userDao.persist(userEntity);
//        userDao.closeTransaction();
    }

    /**
     * Update user
     * @param userEntity {@link UserEntity} for update
     */
    public void update(UserEntity userEntity) {
//        userDao.beginTransaction();
        userDao.update(userEntity);
//        userDao.closeTransaction();
    }


    /**
     * Create user by id
     * @param  id user id
     * @return user
     */
    @Deprecated
    public UserEntity getFullUserById(Integer id) {
//        userDao.beginTransaction();
        UserEntity userEntity = userDao.findById(UserEntity.class, id);
        OrderEntity basketByUserId = new OrderServiceImpl().getBasketByUserId(id);
        Set setS = new HashSet();
        setS.add(basketByUserId);
        userEntity.setOrders(setS);
        userEntity.getOrders();
//        userDao.closeTransaction();
        return userEntity;
    }

    /**
     * Get top ten buying user
     * @return {@list UserEntity} list
     */
    public List getTopTenUser() {
//        userDao.beginTransaction();
        List topTenUser = userDao.getTopTenUser();
//        userDao.closeTransaction();
        return topTenUser;
    }


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void createUserSecurity(UserEntity user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_CLIENT);
        user.setName(user.getName());
        user.setSername(user.getSername());
        userDao.persist(user);
    }
}
