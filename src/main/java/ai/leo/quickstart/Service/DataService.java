package ai.leo.quickstart.Service;

import com.alibaba.fastjson.JSONArray;

/**
 * 训练数据合并功能接口
 *
 * @Author: yvoilee
 * @Email: yvoilee@gmail.com
 * @Date: 2017-12-06  16:12
 */
public interface DataService {

    int getRadix();

    void setRadix(int radix);
}
