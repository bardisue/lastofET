package com.cnusw.everytown.user.jwt;

import com.cnusw.everytown.user.dto.TokenDto;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
@Component
@Slf4j
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth"; // 추후에 권한을 얻기 위한 킬 사용될 문자열
    private static final String BEARER_TYPE = "bearer"; // 토큰의 인증 타입을 bearer로 설정
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30분

    private final Key key; // 토큰을 암호화 하는데 사용

    // application.properties에서 선언한 값들을 가져와 해당 변수에 대입
    public TokenProvider(@Value("${spring.jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰 생성
    public TokenDto generateTokenDto(Authentication authentication) {
        // Authentication 객체에 담긴 사용자의 정보들을 꺼내와서 string으로 형변환
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        // 토큰 만료시간 설정
        Date tokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        System.out.println(tokenExpiresIn);

        // Access Token 생성
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName()) // payload "sub" : "name"
                .claim(AUTHORITIES_KEY, authorities) // payload "auth" : "ROLE_USER"
                .setExpiration(tokenExpiresIn) // payload "exp" : 토큰 만료 시간
                .signWith(key, SignatureAlgorithm.HS512) // header "alg" : "HS512"
                .compact();

        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .tokenExpiresIn(tokenExpiresIn.getTime())
                .build();

    }
    public Authentication getAuthentication(String accessToken) {
        // string 형태의 토큰을 claims 형태로 생성
        Claims claims = parseClaims(accessToken);

        if(claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        // UserDetails(spring security에서 유저의 정보를 담는 인터페이스) 객체를 만들어 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 jwt 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 jwt 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 jwt 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("잘못된 형식의 jwt 토큰입니다.");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
