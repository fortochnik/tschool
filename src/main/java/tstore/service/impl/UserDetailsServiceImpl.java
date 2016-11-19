package tstore.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tstore.dao.UserDao;
import tstore.model.UserEntity;

import java.util.HashSet;
import java.util.Set;


public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserDao userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByLogin(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
//        for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().name()));
//        }

        User user1 = new User(user.getEmail(), user.getPassword(), grantedAuthorities);
        return user1;
    }
}
