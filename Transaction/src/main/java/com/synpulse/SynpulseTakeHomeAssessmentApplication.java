package com.synpulse;

import com.synpulse.service.CurrencyService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableScheduling
@EnableSwagger2
@SpringBootApplication
public class SynpulseTakeHomeAssessmentApplication implements InitializingBean {
    @Autowired
    CurrencyService currencyService;

    public static void main(String[] args) {
        SpringApplication.run(SynpulseTakeHomeAssessmentApplication.class, args);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        currencyService.updateLatestCurrencyRate();
    }
}
