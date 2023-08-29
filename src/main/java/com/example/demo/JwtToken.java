package com.example.demo;

import io.jsonwebtoken.*;
import jakarta.security.auth.message.AuthException;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtToken implements Serializable {

    private static final long EXPIRATION_TIME =  5 * 60 * 60 * 1000; //5hrs
    /**
     * JWT SECRET KEY
     */
    private static final String SECRET = "learn to dance in the rain";

    /**
     * 簽發JWT
     */
    public String generateToken(HashMap<String, String> userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put( "studentID", userDetails.get("studentID") );

        return Jwts.builder()
                .setClaims( claims )
                .setExpiration( new Date( Instant.now().toEpochMilli() + EXPIRATION_TIME  ) )
                .signWith( SignatureAlgorithm.HS512, SECRET )
                .compact();
    }

    /**
     * 驗證JWT
     */
    public void validateToken(String token, String studentID) throws AuthException {
        try {
            String user = (Jwts.parser()
                    .setSigningKey( SECRET )
                    .parseClaimsJws( token ).getBody().get("studentID")).toString();
            System.out.println("JWT : "+user);
            if (user != null && !user.equals(studentID)) {
                System.out.println("JWT token user mismatch.");
                throw new AuthException("JWT token user mismatch.");
            }
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature.");
            throw new AuthException("Invalid JWT signature.");
        }
        catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token.");
            throw new AuthException("Invalid JWT token.");
        }
        catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token");
            throw new AuthException("Expired JWT token");
        }
        catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token");
            throw new AuthException("Unsupported JWT token");
        }
        catch (IllegalArgumentException e) {
            System.out.println("JWT token compact of handler are invalid");
            throw new AuthException("JWT token compact of handler are invalid");
        }
    }
}
