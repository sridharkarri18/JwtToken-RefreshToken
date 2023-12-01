package Jwt.Security.Service;

import Jwt.Security.Entity.User;
import Jwt.Security.Repoistory.UserRepoistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepoistory userRepoistory;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepoistory.findByusername(username).orElseThrow(() -> new RuntimeException("Username not found"));

        return user;

    }
}

