package com.team.bookstore.Services;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.team.bookstore.Dtos.Requests.IntrospectRequest;
import com.team.bookstore.Dtos.Requests.LogoutRequest;
import com.team.bookstore.Dtos.Requests.RefreshTokenRequest;
import com.team.bookstore.Dtos.Requests.UsernameLoginRequest;
import com.team.bookstore.Dtos.Responses.AuthenticationResponse;
import com.team.bookstore.Dtos.Responses.IntrospectResponse;
import com.team.bookstore.Entities.InvalidatedToken;
import com.team.bookstore.Entities.User;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Repositories.InvalidatedTokenRepository;
import com.team.bookstore.Repositories.UserRepository;
import lombok.experimental.NonFinal;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.security.sasl.AuthenticationException;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@Log4j2
public class AuthenticationService {
    @Autowired
    UserRepository  userRepository;
    @Autowired
    PasswordEncoder            passwordEncoder;
    @Autowired
    InvalidatedTokenRepository invalidatedTokenRepository;
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;
    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    public AuthenticationResponse authenticate(UsernameLoginRequest request) throws AuthenticationException {
        String      username  = request.getUsername();
        String     password  = request.getPassword();
        if (!userRepository.existsByUsername(username)) {
            throw new UsernameNotFoundException("User " + username + " is not exist!");
        }
        User user = userRepository.findUsersByUsername(username);
        boolean isAuthenticate = passwordEncoder.matches(password,
                user.getPassword());
        if (!isAuthenticate) {
            throw new AuthenticationException("Wrong password!");
        }
        user.setToken(GenerateToken(user));
        userRepository.save(user);
        return AuthenticationResponse.builder()
                .username(username)
                .token(user.getToken())
                .build();
    }

    public String GenerateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("com.authentication")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope",CreateScope(user))
                .build();
        Payload   payload   = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader,payload);
        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        }
        catch(JOSEException e){
            throw new RuntimeException(e);
        }

    }
    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                .toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new ApplicationException(ErrorCodes.UN_AUTHENTICATED);

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new ApplicationException(ErrorCodes.UN_AUTHENTICATED);

        return signedJWT;
    }
    public IntrospectResponse introspect(IntrospectRequest request)
            throws JOSEException, ParseException {
        String token = request.getToken();
        boolean isAuthenticated = true;

        try {
            verifyToken(token, false);
        } catch (Exception e) {
            isAuthenticated = false;
            log.info(e);
            throw new ApplicationException(ErrorCodes.UN_AUTHENTICATED);
        }
        return IntrospectResponse.builder().isAuthenticated(isAuthenticated).build();
    }

    private String CreateScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getUser_role())){
            user.getUser_role().forEach(user_role -> {
                stringJoiner.add(user_role.getRole().getRolename());
                user_role.getRole().getRole_permission().forEach(role_permission -> {
                    stringJoiner.add(role_permission.getPermission().getPermissionname());
                });
            });
        }
        return stringJoiner.toString();
    }
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(request.getToken(), true);

        String jit = signedJWT.getJWTClaimsSet().getSubject();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = new InvalidatedToken(jit,
                expiryTime);
        invalidatedTokenRepository.save(invalidatedToken);

        String username = signedJWT.getJWTClaimsSet().getSubject();
        if(!userRepository.existsByUsername(username)){
            throw new ApplicationException(ErrorCodes.UN_AUTHENTICATED);
        }
        User user = userRepository.findUsersByUsername(username);
        String token = GenerateToken(user);
        return AuthenticationResponse.builder().username(username).token(token).build();
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            SignedJWT signToken = verifyToken(request.getToken(), false);
            String jit = signToken.getJWTClaimsSet().getSubject();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedToken =
                    InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.IS_EXPIRED);
        }
    }
}
