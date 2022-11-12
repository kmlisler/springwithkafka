package com.kamilisler.springwithkafka.controller;


import com.kamilisler.springwithkafka.SpringwithkafkaApplication;
import com.kamilisler.springwithkafka.model.MappedPackage;
import com.kamilisler.springwithkafka.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    private final ProducerService producer;

    @Autowired
    KafkaController(ProducerService producer) {
        this.producer = producer;
    }

    @GetMapping("/send/{packageId}")
    public String sendSinglePackageToKafka(@PathVariable Long packageId) {

        MappedPackage myPackage = new MappedPackage(packageId,"test","test",0,0,0,0,true);

        this.producer.sendMessage(myPackage);

        return "Single package is sent. sent package id : " + packageId;
    }
    //TODO: sistemdeki MappedPackage'ların hepsini kafkaya gönder
    @GetMapping("/bootstrap")
    public String sendAllPackagesToKafka() {
        // this.producer.sendMessage("");
        return "All packages sent to kafka." ;
    }

}
