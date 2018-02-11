package ai.leo.quickstart.controller;

import ai.leo.quickstart.Service.DataService;
import ai.leo.quickstart.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yvoilee
 * @Email: yvoilee@gmail.com
 * @Date: 2017-12-06  14:47
 */
//@Configuration
//@PropertySource("classpath:application-dev.yml")
@RestController
public class TestController {
    @Autowired
    private DataService mergeDataService;
    @Value("${file.path_root}")
    private String url;
    private static Logger logger = LoggerFactory.getLogger(TestController.class);
    @RequestMapping("/test1")
    public R test1(){
        System.out.println(mergeDataService.getRadix());
        return R.ok().put("url",url).put("radix",mergeDataService.getRadix());
    }

    @RequestMapping("/test2")
    public R test2(){
        mergeDataService.setRadix(100);
        return R.ok().put("radix",100);
    }
}
