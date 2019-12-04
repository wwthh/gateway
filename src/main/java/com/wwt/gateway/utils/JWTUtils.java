package com.wwt.gateway.utils;

import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWTUtils
 */
public class JWTUtils {
    private static final String ISSUER = "wwt";
    private static final String SUBJECT = "user_token";
    private static final String KEY = "0(vw3bi%y=_UPFfJ@XYqS$y29kt1RvYR+!Oo_(N4xlHdKBGP+5R7%JrcdcYLv+xQBdici)NyG^i08D-NHDWA_tB0TxGAtAmNrWL2k+YAQOqr@S7E_7s)!%*2w#W#Rm8ti^2UKO)9N@iYiO#PWYcA_L#d-^OfOOOJcr%4ya-D$SDGpLGSpG964M)zT*l8h^mt7ANFx80K";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static final long ttlMillis = 2 * 60 * 3600 * 1000; // 设置token两小时过期

    public static String generateJWT(String userId) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(KEY);
        long nowMillis = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder().setIssuer(ISSUER).setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(nowMillis + ttlMillis)).claim("userId", userId).setSubject(SUBJECT)
                .signWith(SIGNATURE_ALGORITHM, new SecretKeySpec(apiKeySecretBytes, SIGNATURE_ALGORITHM.getJcaName()));
        return builder.compact();
    }

    public static String getUserIdFromJWT(String jwt){
        if(jwt==null) return null;
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(KEY)).parseClaimsJws(jwt).getBody();
        if(claims.getExpiration().before(new Date())) {
            return null;
        }
        return claims.get("userId", String.class);
    }

}