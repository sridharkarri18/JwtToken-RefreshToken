package Jwt.Security.Controller;

import Jwt.Security.Entity.*;
import Jwt.Security.Repoistory.RefreshRepoistory;
import Jwt.Security.Repoistory.UserRepoistory;
import Jwt.Security.Service.JwtService;
import Jwt.Security.Service.RefreshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserRepoistory userRepoistory;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private RefreshRepoistory refreshRepoistory;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired

    private RefreshService refreshService;
    @Autowired
    private UserDetailsService userDetailsService;


    @PostMapping("/new")
    //2 creating a user
    public User createUser(@RequestBody User user) {
        User user1 = new User();

        user1.setUsername(user.getUsername());
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setRoles(user.getRoles());
        return userRepoistory.save(user1);
    }


    @PostMapping("/login")
    public AuthResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        UserDetails userDetails=userDetailsService.loadUserByUsername(authRequest.getUsername());


        if (userDetails != null && passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())) {
            RefreshToken refreshToken=refreshService.createRefreshToken(authRequest.getUsername());
            return AuthResponse.builder()
                    .accesstoken(jwtService.generateToken(authRequest.getUsername()))
                    .refreshtoken(refreshToken.getToken())
                    .build();

        } else {
            throw new BadCredentialsException("BadCredentials");
        }
    }

    @PostMapping("/refresh")
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshService.searchRefreshToken(refreshTokenRequest.getToken())
                .map(refreshService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user.getUsername());
                    return AuthResponse.builder()
                            .accesstoken(accessToken)
                            .refreshtoken(refreshTokenRequest.getToken())
                            .build();
                }).orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }




}
