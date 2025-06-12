package com.co2.mold.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "co2-service", url = "PLACEHOLDER")
public interface Co2Client {
    @GetMapping("PLACEHOLDER")
    Float getCurrentCo2(
            @PathVariable String time,
            @PathVariable String classroom
    );
}