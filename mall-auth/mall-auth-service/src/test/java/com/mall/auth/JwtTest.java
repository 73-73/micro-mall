package com.mall.auth;

import com.mall.common.pojo.UserInfo;
import com.mall.common.utils.JwtUtils;
import com.mall.common.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author pan
 * @create 2020-05-01-11:38
 */
public class JwtTest {

    private static final String pubKeyPath = "F:\\\\graduation-project\\\\rsa\\\\rsa.pub";

    private static final String priKeyPath = "F:\\\\graduation-project\\\\rsa\\\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    /**
     * 生成公钥和私钥（需要把before方法先注释掉）
     * @throws Exception
     */
    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "ncu-mall");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    /**
     * 生成token信息，需要先读取公私钥，把before注释放开来
     * @throws Exception
     */
    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack",1), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU4ODMwNDgxN30.QqcPHhVRps51-_okTURJEPsXGxA605iMyvTjetobkN9g24DN0LNQJRuM0vLAWnRw9a1VlcvJnbeiLsla_6w6P4RaLQMo-boBN7JmL_A-5Dq6lPT3tkGxMMJ1e3KG_QaG3whelg4n-0WsIV42XFMy1bEsuJvzFxENJ2pZZsfARXY";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}
