package com.kamilisler.springwithkafka.controller;

import com.kamilisler.springwithkafka.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/kafka")
@ControllerAdvice
public class KafkaController {

    @Autowired
    private OperatorService operatorService;

    @GetMapping("/send/{packageId}")
    public ResponseEntity<String> sendSinglePackageToKafka(@PathVariable Long packageId) {

        return operatorService.sendPackageForKafka(packageId);
    }

    @GetMapping("/bootstrap")
    public ResponseEntity<String> sendAllPackagesToKafka() {

        return operatorService.bootstrapForKafka();
    }


}
