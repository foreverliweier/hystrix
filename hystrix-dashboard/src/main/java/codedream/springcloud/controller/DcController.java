package codedream.springcloud.controller;

import codedream.springcloud.service.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author alice
 * @time 2023-05-17 15:08
 **/
@RestController
public class DcController {
    @Autowired
    RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(DcController.class);
    @Autowired
    ConsumerService consumerService;

    /**
     * Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端负载均衡的工具。
     * 它是一个基于HTTP和TCP的客户端负载均衡器。
     * 它可以通过在客户端中配置ribbonServerList来设置服务端列表去轮询访问以达到均衡负载的作用。
     * 当Ribbon与Eureka联合使用时，ribbonServerList会被DiscoveryEnabledNIWSServerList重写，扩展成从Eureka注册中心中获取服务实例列表。
     * 同时它也会用NIWSDiscoveryPing来取代IPing，它将职责委托给Eureka来确定服务端是否已经启动。
     * 而当Ribbon与Consul联合使用时，ribbonServerList会被ConsulServerList来扩展成从Consul获取服务实例列表。
     * 同时由ConsulPing来作为IPing接口的实现。
     * 我们在使用Spring Cloud Ribbon的时候，不论是与Eureka还是Consul结合，都会在引入Spring Cloud Eureka或Spring Cloud Consul依赖的时候通过自动化配置来加载上述所说的配置内容，
     * 所以我们可以快速在Spring Cloud中实现服务间调用的负载均衡。
     * */
    @GetMapping("/consumer")
    public String consumer() {
        //这里请求的host位置并没有使用一个具体的IP地址和端口的形式，而是采用了服务名的方式组成
        //因为Spring Cloud Ribbon有一个拦截器，它能够在这里进行实际调用的时候，自动的去选取服务实例，
        //并将实际要请求的IP地址和端口替换这里的服务名，从而完成服务接口的调用。
        LOGGER.info("consumer");
        return consumerService.consumer();
    }


    @GetMapping("/consumerl")
    public String consumerLay() {
        //这里请求的host位置并没有使用一个具体的IP地址和端口的形式，而是采用了服务名的方式组成
        //因为Spring Cloud Ribbon有一个拦截器，它能够在这里进行实际调用的时候，自动的去选取服务实例，
        //并将实际要请求的IP地址和端口替换这里的服务名，从而完成服务接口的调用。
        LOGGER.info("consumerLay");
        return consumerService.consumerLay();
    }

}
