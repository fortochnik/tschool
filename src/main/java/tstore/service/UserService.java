package tstore.service;

import tstore.model.UserEntity;

/**
 * Created by mipan on 08.10.2016.
 */
public interface UserService {

    UserEntity getUser(String username, String password);
    UserEntity getUser(String login);
    void createUser(UserEntity userEntity);
}
