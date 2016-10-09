package tstore.dao;

import tstore.model.UserEntity;

/**
 * Created by mipan on 02.10.2016.
 */
public interface UserDao extends GenericDao<UserEntity, Integer> {
    UserEntity findByCredential(String login, String password);
    UserEntity findByLogin(String login);
}
