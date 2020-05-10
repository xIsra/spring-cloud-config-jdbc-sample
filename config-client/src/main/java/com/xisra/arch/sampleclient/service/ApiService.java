package com.xisra.arch.sampleclient.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class ApiService {

    @Value("${test.greeting:TEST}")
    @Getter
    private String greetingMessage;

}
