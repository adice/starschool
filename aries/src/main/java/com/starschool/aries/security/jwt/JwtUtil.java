package com.starschool.aries.security.jwt;

import org.jose4j.json.JsonUtil;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

import java.security.PrivateKey;
import java.util.UUID;

/**
 * JwtUtil
 * <P>
 * JWT工具类
 * </p>
 *
 * @author adi
 * @since 2023/3/3 15:27
 */
public class JwtUtil {
    static String keyId = "1548c10fe1d94ff693c9ceb8acce3db1";
    static String pubilcKey = "{\"kty\":\"RSA\",\"kid\":\"1548c10fe1d94ff693c9ceb8acce3db1\",\"alg\":\"RS256\",\"n\":\"ntv92FC8E6PHXv1HPEUhmzzGJqEcH7ZrWty5g-PdGL_AB88xK32JDFb0AhxH8oUAgkKfcho_-ye4LXTmf9G0MKcrFKceM-j5E5GtaU84WgtynkljZn1JB527QDD_7zRCpAHu4bsG-3qL69uhOfuvol9oYKufU_Qx1tijs_jNl0D2K2BnUOgZXPS0ZrE0pC3RiTxD_68CGnPMa1ZNTSJO5KKLyTMVGkGcrKGUD266GZY24u3ZATxd-ZDEfBLvpebbwS9CmsOqhXvwXX0Ijf75Wd76QOjZbS9kLvJ259FtkP-mR-7nQdtZByuHtN_LFByIH6VQ_6dtIcBkoWGnMRkrJQ\",\"e\":\"AQAB\"}";
    static String privateKey = "{\"kty\":\"RSA\",\"kid\":\"1548c10fe1d94ff693c9ceb8acce3db1\",\"alg\":\"RS256\",\"n\":\"ntv92FC8E6PHXv1HPEUhmzzGJqEcH7ZrWty5g-PdGL_AB88xK32JDFb0AhxH8oUAgkKfcho_-ye4LXTmf9G0MKcrFKceM-j5E5GtaU84WgtynkljZn1JB527QDD_7zRCpAHu4bsG-3qL69uhOfuvol9oYKufU_Qx1tijs_jNl0D2K2BnUOgZXPS0ZrE0pC3RiTxD_68CGnPMa1ZNTSJO5KKLyTMVGkGcrKGUD266GZY24u3ZATxd-ZDEfBLvpebbwS9CmsOqhXvwXX0Ijf75Wd76QOjZbS9kLvJ259FtkP-mR-7nQdtZByuHtN_LFByIH6VQ_6dtIcBkoWGnMRkrJQ\",\"e\":\"AQAB\",\"d\":\"XNPReAd8ZCN_XO9hPyKOsB4yCoaMF_vfakvcVLowzZFRyvKTTZ2lPtyHNI6Ucrph1Br0QFnUEr94Ueg_5S6IhNwgy65XMKjV7KLxQFXke3crjFxybUHR-RyapA3YBMk5Hj5hwNsztJA81CP-G8dL7UB9tCVl4eteBM6S2i9QBoNE4cyZ2cPpf2bRpZipLlkrVyadVa6WcoKJ5_yXHQoD2gFKOZ2cXGo4JzNmM4MBkot9nTLNwLob-t6Yri-rLcvxesYzakP1EZKyln8a_XodH2V5ZW-uCBPb3yIGMhnA8A_-knBUs-ansQAzI66aHbken-Av50jEf4mve8RQO-Wd\",\"p\":\"t1wsHUKA4BiHE3hkJM2npu9YTZCM3fbuiLw3KDTPaPGbNx_tFOX5RfN4BfvB-b9EEGaqiD2YFzN-9mT5BoeP7vP5mN4DMd1OTfC-DKrpgIOsXuacZTR6JTIcfu6oLQ-0fRkHFuJ57QE6CNJrl6Fr3ZsgGQKW8NBS2wFTvXeL9pc\",\"q\":\"3csH61QxoNPEvoK6cI9EWMTSnAtenRqbWGUkoiqkQEP_GjWi0cj_BtcvAuYxLo6OMR1356IntpL03gI9gR4dGgb9i-CWsveTYeFKUL8p5LNqT_dSiQjDQDcpTd0vrUQw6PgJMTtMt7Og7uTwHJh61OVQC--EmBJIpt8K5r6UP6M\",\"dp\":\"CvHtkDGQD9Bm_7qH_8pm34gZ9UdToUxEINVbVYMRfmotobS-KikwIATQfUrx4T6RiYkXVNqYYYopYfvVAZbF-LWHiKB4AFn9epmMPqVVGt7BQ0jwvPnM8Lt1B_6m-0mctHrx-WO7c3JHTI-C7KIuFiRUXinRaL6Gs5v4q66fJDE\",\"dq\":\"1KtO36buo3EL8UU28qFdfuTTsg7w3SHbHSp2swzCTGEm5oHRqujZKT0Cdfw14peM3qmMBM6m19kX9R0ezoUKkJ9tVUe4GMluR-eXs89po0MqSJ01RfLiDdwWvfO-OJzvbSQQ5GUhDfCu-wMI_dJzzVpIo_k6Ifs-MMwkDtdlHZM\",\"qi\":\"mUoCq1ictNrpkgWmMw-0HjVvQ7r7ssTVtd3HoQylfmZPfFsoKAa9qf-IGpOQVHgiew01NQA10MFsIbZNFJfI5JqW-14Rwl8Tnn_YDFZ8yX92X5bnpEt7fhmAnOUwh--Gd-LJlefV3ocsmgt6xQfQqPlZHt2MPPj6sviisBt3hxQ\"}";

    public static final long TOKEN_EXPIRATION_TIME = 30;

    /**
     * 生成包含username的JWT
     *
     * @param userName 用户名
     * @return JWT字符串
     */
    public static String createToken(String userName) {
        JwtClaims jwtClaims = new JwtClaims();
        jwtClaims.setSubject("ariestoken");
        jwtClaims.setIssuer("com.starrysky");
        jwtClaims.setIssuedAtToNow();
        jwtClaims.setAudience("com.starrysky.*");
        jwtClaims.setGeneratedJwtId();
        jwtClaims.setClaim("username", userName);
        jwtClaims.setExpirationTimeMinutesInTheFuture(TOKEN_EXPIRATION_TIME);

        JsonWebSignature jws = new JsonWebSignature();
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
        jws.setKeyIdHeaderValue(keyId);
        jws.setPayload(jwtClaims.toJson());
        try {
            PrivateKey privateKeyObject = new RsaJsonWebKey(JsonUtil.parseJson(privateKey)).getPrivateKey();
            jws.setKey(privateKeyObject);
            return jws.getCompactSerialization();
        } catch (JoseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证token
     *
     * @param token 客户端token信息
     * @return 用户id
     */
    public static String verifyToken(String token) throws InvalidJwtException, JoseException {
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setMaxFutureValidityInMinutes(525600)
                .setAllowedClockSkewInSeconds(30)
                .setRequireSubject()
                .setExpectedIssuer("com.starrysky")
                .setExpectedAudience("com.starrysky.*")
                .setVerificationKey(new RsaJsonWebKey(JsonUtil.parseJson(pubilcKey)).getPublicKey())
                .build();
        JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
        if (jwtClaims != null) {
            return jwtClaims.getClaimValue("username").toString();
        } else {
            return null;
        }
    }

    public static void createKeyPair() {
        keyId = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            RsaJsonWebKey jwk = RsaJwkGenerator.generateJwk(2048);
            jwk.setKeyId(keyId);
            jwk.setAlgorithm(AlgorithmIdentifiers.RSA_USING_SHA256);
            pubilcKey = jwk.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY);
            privateKey = jwk.toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);
            System.out.println("keyId:" + keyId);
            System.out.println("publicKey" + pubilcKey);
            System.out.println("privateKey" + privateKey);
        } catch (JoseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createKeyPair();
    }
}
