package cn.succy.shiro.util;

import cn.succy.shiro.entity.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

/**
 * @author Succy
 * @date 2017-11-08 16:37
 **/
@Component
public class PasswordHelper  {
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private static final String ALGORITHM_NAME = "md5";
    private static final int HASH_ITERATIONS = 2;

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }


    public void encryptPassword(User user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPassword = new SimpleHash(
                ALGORITHM_NAME,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                HASH_ITERATIONS).toHex();

        user.setPassword(newPassword);
    }
}
