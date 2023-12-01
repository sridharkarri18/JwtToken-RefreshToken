package Jwt.Security.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="refreshtokeninfo")
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int refreshid;

    private String token;

    private Instant expirationtime;

    @OneToOne
    @JoinColumn(name = "userid",referencedColumnName = "id")
    private User user;
}
