package Jwt.Security.Service;

import Jwt.Security.Entity.RefreshToken;
import Jwt.Security.Repoistory.RefreshRepoistory;
import Jwt.Security.Repoistory.UserRepoistory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshService {

    @Autowired
    private RefreshRepoistory refreshRepoistory;

    @Autowired
    private UserRepoistory userRepoistory;

    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepoistory.findByusername(username).get())
                .token(UUID.randomUUID().toString())
                .expirationtime(Instant.now().plusMillis(60000000))//100
                .build();
        return refreshRepoistory.save(refreshToken);

    }

    public Optional<RefreshToken> searchRefreshToken(String token) {
        return refreshRepoistory.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpirationtime().compareTo(Instant.now()) > 0) {
            refreshRepoistory.delete(refreshToken);
            throw new RuntimeException("Refresh Token Expired!");
        }
        return refreshToken;


    }


}
