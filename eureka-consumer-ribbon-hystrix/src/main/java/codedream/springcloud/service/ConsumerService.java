package codedream.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author alice
 * @time 2023-06-02 11:02
 **/
@Service
public class ConsumerService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallback")
    public String consumer() {
        return restTemplate.getForObject("http://eureka-client/dc", String.class);
    }
    public String consumerLay() {
        return restTemplate.getForObject("http://eureka-client/dc", String.class);
    }

    /**
     * 可以看到服务提供方输出了原本要返回的结果，但是由于返回前延迟了5秒，而服务消费方触发了服务请求超时异常，
     * 服务消费者就通过HystrixCommand注解中指定的降级逻辑进行执行，因此该请求的结果返回了fallback。
     * 这样的机制，对自身服务起到了基础的保护，同时还为异常情况提供了自动的服务降级切换机制。
     * */
    public String fallback() {
        return "fallback";
    }
}
