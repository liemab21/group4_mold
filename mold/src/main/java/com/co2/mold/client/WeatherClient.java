package com.co2.mold.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "weather-service", url = "PLACEHOLDER")
public interface WeatherClient {
    @GetMapping("PLACEHOLDER")
    Float getCurrentHumidity(
            @PathVariable String time,
            @PathVariable String classroom
    );
}
