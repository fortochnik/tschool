package tstore.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tstore.dao.UserDao;
import tstore.model.UserEntity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by mipan on 24.11.2016.
 */
public class UserServiceImplTest {
    private UserServiceImplUnitTest userServiceImplUnitTest;


    @Before
    public void setUp() throws Exception {
        userServiceImplUnitTest = new UserServiceImplUnitTest();
    }

    @Test
    public void testGetUser() throws Exception {
        userServiceImplUnitTest.getUser("login", "Pa$$w0rd");
        Mockito.verify(userServiceImplUnitTest.getUserDao(), Mockito.atLeastOnce()).findByCredential("login", "Pa$$w0rd");
    }

    @Test
    public void testCreateUserSecurity() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("Pa$$w0rd");
        userServiceImplUnitTest.createUserSecurity(userEntity);
        Mockito.verify(userServiceImplUnitTest.getBCryptPasswordEncoder(), Mockito.atLeastOnce()).encode("Pa$$w0rd");
        Mockito.verify(userServiceImplUnitTest.getUserDao(), Mockito.atLeastOnce()).persist(userEntity);
    }

    private class UserServiceImplUnitTest extends UserServiceImpl {
        public UserServiceImplUnitTest() {
            super();
            userDao = mock(UserDao.class);
            bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
        }
        public UserDao getUserDao(){
            return userDao;
        }
        public BCryptPasswordEncoder getBCryptPasswordEncoder(){
            return bCryptPasswordEncoder;
        }

    }
}