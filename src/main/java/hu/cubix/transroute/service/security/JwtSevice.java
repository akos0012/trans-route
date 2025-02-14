package hu.cubix.transroute.service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import hu.cubix.transroute.config.HrConfigProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;


@Service
public class JwtSevice {

    private static final String AUTH = "auth";
    private static Algorithm algorithm;
    private static String ISSUER;


    @Autowired
    private HrConfigProperties conf;

    @PostConstruct
    public void init() {
        ISSUER = conf.getJwtData().getIssuer();
        try {
            Method method = Algorithm.class.getMethod(conf.getJwtData().getAlg(), String.class);
            algorithm = (Algorithm) method.invoke(Algorithm.class, conf.getJwtData().getSecret());
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public String createJwt(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withArrayClaim(AUTH, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .withExpiresAt(new Date(System.currentTimeMillis() + conf.getJwtData().getDuration().toMillis()))
                .withIssuer(ISSUER)
                .sign(algorithm);
    }

    public UserDetails parseJwt(String jwtToken) {
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build()
                .verify(jwtToken);

        List<SimpleGrantedAuthority> authorities = decodedJWT.getClaim(AUTH).asList(String.class).stream()
                .map(SimpleGrantedAuthority::new).toList();

        return new User(decodedJWT.getSubject(), "dummy", authorities);
    }
}
