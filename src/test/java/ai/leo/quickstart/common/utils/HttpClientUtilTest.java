package ai.leo.quickstart.common.utils;

import org.junit.Test;

/**
 * @Author: yvoilee
 * @Email: yvoilee@gmail.com
 * @Date: 2017-12-06  15:51
 */
public class HttpClientUtilTest {
    @Test
    public void doGet() throws Exception {
        String result  = HttpClientUtil.doPost("http://gitlab.fitme.ai/api/v3/session?login=lyh&password=Fitme!23");
        System.out.println(result);
    }

}