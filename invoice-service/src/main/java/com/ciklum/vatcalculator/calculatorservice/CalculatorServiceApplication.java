package com.ciklum.vatcalculator.calculatorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CalculatorServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(CalculatorServiceApplication.class, args);
  }

}
