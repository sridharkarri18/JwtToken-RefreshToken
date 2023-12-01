package Jwt.Security.Controller;

import Jwt.Security.Entity.AuthRequest;
import Jwt.Security.Entity.User;
import Jwt.Security.Repoistory.UserRepoistory;
import Jwt.Security.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Data")
public class AccessController {


    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String fetchData() {
        return "Ur Site has been hacked";
    }

    @GetMapping("/welcome")
    @PreAuthorize("hasAuthority('USER')")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }


}
