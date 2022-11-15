package com.kamilisler.springwithkafka.controller;
import com.kamilisler.springwithkafka.entity.Package;
import com.kamilisler.springwithkafka.model.MappedPackage;
import com.kamilisler.springwithkafka.service.PreparePackageService;
import com.kamilisler.springwithkafka.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    private final ProducerService producer;
    @Autowired
    private PreparePackageService preparePackageService;

    public KafkaController(ProducerService producer) {
        this.producer = producer;
    }

    @GetMapping("/send/{packageId}")
    public String sendSinglePackageToKafka(@PathVariable Long packageId) {

        MappedPackage myPackage = preparePackageService.getSinglePackage(packageId);
        if (myPackage != null){
            this.producer.sendMessage(myPackage);
            return "Single package is sent. sent package id : " + packageId;
        }
        return "Package id is not found : " + packageId;


    }
    //TODO: sistemdeki MappedPackage'ların hepsini kafkaya gönder
    @GetMapping("/bootstrap")
    public String sendAllPackagesToKafka() {
        // this.producer.sendMessage("");

        return "All packages sent to kafka." ;
    }

    @GetMapping("/packages")
    public List<Package> getAllComments() {
        //return packageRepository.findAll();
        return null;
    }


}
