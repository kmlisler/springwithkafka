package com.kamilisler.springwithkafka.service;

import com.kamilisler.springwithkafka.model.MappedPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// this service will oparate other services for controller
@Service
public class OperatorService {
    @Autowired
    ProducerService producerService;
    @Autowired
    PreparePackageService preparePackageService;

    public String sendPackageForKafka(Long packageId) {
        MappedPackage myPackage = preparePackageService.getSinglePackage(packageId);

        if (myPackage != null) {
            this.producerService.sendMessage(myPackage);
            return "Single package is sent to kafka . Sent package id : " + packageId;
        }
        return "Package id is not found : " + packageId;
    }

    public String bootstrapForKafka() {
        List<MappedPackage> list = preparePackageService.getAllPackages();
        this.producerService.sendMessageForBootstrap(list);
        return "All packages sent to kafka.";
    }

}
