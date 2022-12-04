package org.yunhongmin.shop.service;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CircuitService {
    @HystrixCommand(
            commandKey = "CircuitService.circuit",
            fallbackMethod = "defaultCircuit",
            commandProperties = {
                    @HystrixProperty(
                            name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(
                            name="circuitBreaker.requestVolumeThreshold", value="5"),
                    @HystrixProperty(
                            name="circuitBreaker.errorThresholdPercentage", value="50"),
                    @HystrixProperty(
                            name="circuitBreaker.sleepWindowInMilliseconds", value="7000"),
                    @HystrixProperty(
                            name="metrics.rollingStats.timeInMilliseconds", value="10000")
            }
    )
    public void circuit() {
        throw new IllegalArgumentException("circuit breaker test");
    }

    public void defaultCircuit(Throwable e) {
        log.warn(e.toString());
        log.warn("circuit breaker works!");
    }
}
