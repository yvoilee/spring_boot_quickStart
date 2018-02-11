package ai.leo.quickstart.Service.Impl;

import ai.leo.quickstart.Service.DataService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl implements DataService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${file.path_root:'/data/'}")
    private String PATH_PREFIX;
    @Value("${sys.mix_radix:2}")
    private int RADIX;


    public int getRadix() {
        return this.RADIX;
    }

    public void setRadix(int radix) {
        this.RADIX = radix;
    }

    private JSONObject jsonParse(String line) {
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.parseObject(line);
        } catch (Exception e) {
            return null;
        }
        return jsonObject;
    }
}
