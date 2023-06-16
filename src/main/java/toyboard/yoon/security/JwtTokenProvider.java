package toyboard.yoon.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import toyboard.yoon.security.dto.LoginResponse;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private String secretKey = "asdfsadfqwerwqrweqasfsadfqwerqwerzxvxzvxcqwerqwsdfsxczvzxcfdsafwerwq";

    // 토큰 유효시간 30분
    private final long tokenValidTime = 30 * 60 * 1000L;

    private final MemberDetailsService memberDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public LoginResponse createToken(Long userId, String roles) {

        Claims claims = Jwts.claims();// JWT payload 에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
        claims.put("userId", userId);
        claims.put("roles", "USER"); // 정보는 key / value 쌍으로 저장된다.

        Date now = new Date();

        String accessToken =  Jwts.builder()
                .setSubject("subssubsub")
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
//                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now.getTime() + tokenValidTime * 10))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return LoginResponse.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {

        Claims claims = parseClaims(accessToken);

        if (claims.get("roles") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("roles").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

//        UserDetails userDetails = memberDetailsService.loadUserByUsername(getUserEmail(accessToken));
        log.info("authorities={}", authorities);
        log.info("claims.toString()={}", claims.toString());
        log.info("claims.getSubject()={}", claims.getSubject());
        UserDetails userDetails = new User("asd", "", authorities);
        log.info("userDetails.getAuthorities={}", userDetails.getAuthorities());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Long getMemberId(HttpServletRequest request) {

        String accessToken = resolveToken(request);
        log.info("resolveToken 성공");

        Claims claims = parseClaims(accessToken);
        log.info("claims parse 성공");

        if (claims.get("userId") == null) {
            log.info("userId가 null??");
            throw new RuntimeException("id 정보가 없는 토큰입니다.");
        }

        return claims.get("userId", Long.class);
    }

    // 토큰에서 회원 정보 추출
//    public String getUserEmail(String token) {
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
//    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            log.info("Bearer 떼는거 성공");
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken);
            return true;
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
//            throw new JwtException("유효하지 않은 토큰입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
//            throw new JwtException("기한이 만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
//            throw new JwtException("지원하지 않는 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
//            throw new JwtException("claims 정보가 비어있습니다.");
        } catch (SignatureException e){
            log.info("JWT signature does not match locally computed signature.", e);
//            throw new JwtException("JWT 서명이 로컬로 산정된 서명과 일치하지 않습니다.");
        }

        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            log.info("parseClaims 들어옴");
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
