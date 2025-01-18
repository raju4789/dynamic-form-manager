package com.finclutech.dynamic_form_manager.services.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWTServiceImpl is a service class responsible for handling JWT (JSON Web Token) operations,
 * such as token generation, validation, and extraction of claims.
 */
@Service
@Slf4j
public class JWTServiceImpl implements JWTService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expirationTime}")
    private int expirationTime;

    /**
     * Extracts the username (subject) from the given JWT token.
     *
     * @param token the JWT token
     * @return the username extracted from the token
     */
    @Override
    public String extractUsername(String token) {
        log.debug("Extracting username from token: {}", token);
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the given JWT token using a claims resolver function.
     *
     * @param token          the JWT token
     * @param claimsResolver the function to resolve the desired claim
     * @param <T>            the type of the claim
     * @return the extracted claim
     */
    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        log.debug("Extracting claim from token: {}", token);
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token for the given user details without any additional claims.
     *
     * @param userDetails the user details
     * @return the generated JWT token
     */
    @Override
    public String generateToken(UserDetails userDetails) {
        log.info("Generating token for user: {}", userDetails.getUsername());
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token for the given user details with additional claims.
     *
     * @param extraClaims additional claims to include in the token
     * @param userDetails the user details
     * @return the generated JWT token
     */
    @Override
    public String generateToken(Map<String, String> extraClaims, UserDetails userDetails) {
        log.info("Generating token for user: {} with extra claims: {}", userDetails.getUsername(), extraClaims);
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates the given JWT token against the provided user details.
     *
     * @param token       the JWT token
     * @param userDetails the user details
     * @return true if the token is valid, false otherwise
     */
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        log.debug("Validating token for user: {}", userDetails.getUsername());
        final String username = extractUsername(token);
        boolean isValid = username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        log.info("Token validation result for user {}: {}", userDetails.getUsername(), isValid);
        return isValid;
    }

    /**
     * Checks if the given JWT token is expired.
     *
     * @param token the JWT token
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        log.debug("Checking if token is expired: {}", token);
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the given JWT token.
     *
     * @param token the JWT token
     * @return the expiration date
     */
    private Date extractExpiration(String token) {
        log.debug("Extracting expiration date from token: {}", token);
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the given JWT token.
     *
     * @param token the JWT token
     * @return the claims extracted from the token
     */
    private Claims extractAllClaims(String token) {
        log.debug("Extracting all claims from token: {}", token);
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retrieves the signing key used for signing and verifying JWT tokens.
     *
     * @return the signing key
     */
    private Key getSignInKey() {
        log.debug("Retrieving signing key for JWT operations");
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}