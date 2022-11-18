package com.kamilisler.springwithkafka.service;

import com.kamilisler.springwithkafka.model.MappedPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

// this service will oparate other services for controller
@Service
public class OperatorService {
    @Autowired
    ProducerService producerService;
    @Autowired
    PreparePackageService preparePackageService;

    public ResponseEntity<String> sendPackageForKafka(Long packageId) {
        try {
            MappedPackage myPackage = preparePackageService.getSinglePackage(packageId);

            if (myPackage != null) {
                this.producerService.sendMessage(myPackage);
                return new ResponseEntity<>("Single package is sent to kafka . Sent package id : " + packageId, HttpStatus.OK);
            }
            return new ResponseEntity<>("Package id is not found or package is cancelled: " + packageId, HttpStatus.BAD_REQUEST);
        } catch (Exception exc) {
            return new ResponseEntity<>("An unexpected error is occurred :" + exc.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> bootstrapForKafka() {
        try {
            List<MappedPackage> list = preparePackageService.getAllPackages();
            this.producerService.sendMessageForBootstrap(list);
            return new ResponseEntity<>("All packages sent to kafka.", HttpStatus.OK);
        } catch (Exception exc) {
            return new ResponseEntity<>("An unexpected error is occurred " + exc.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
