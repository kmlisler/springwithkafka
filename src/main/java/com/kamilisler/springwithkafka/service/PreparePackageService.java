package com.kamilisler.springwithkafka.service;

import com.kamilisler.springwithkafka.entity.Package;
import com.kamilisler.springwithkafka.model.MappedPackage;
import com.kamilisler.springwithkafka.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@EnableJpaRepositories("com.kamilisler.springwithkafka.repository")
public class PreparePackageService {
    @Autowired
    PackageRepository packageRepository;

    public MappedPackage getSinglePackage(Long id){

        Optional<Package> thePackage = packageRepository.findById(id);
        return thePackage.map(this::processPackage).orElse(null);
        /*
        if (thePackage.isPresent()){
            return processPackage(thePackage.get());
        }
        return null;
         */
    }


    public MappedPackage processPackage(Package samplePackage){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");

        MappedPackage theMappedPackage = new MappedPackage();
        
        theMappedPackage.setId(samplePackage.getId());
        theMappedPackage.setCreatedAt(dateFormat.format(samplePackage.getCreated_at()));
        theMappedPackage.setLastUpdatedAt(dateFormat.format(samplePackage.getLast_updated_at()));

        Integer collectionDuration = calculateDuration(samplePackage.getCreated_at(),samplePackage.getCollected_at());
        theMappedPackage.setCollectionDuration(collectionDuration);

        Integer deliveryDuration = calculateDuration(samplePackage.getIn_delivery_at(),samplePackage.getCompleted_at());
        theMappedPackage.setDeliveryDuration(deliveryDuration);

        theMappedPackage.setEta(samplePackage.getEta());

        Integer leadTime = calculateDuration(samplePackage.getWaiting_for_assignment_at(),samplePackage.getCompleted_at()); // start: created_at olabilir mi ?
        theMappedPackage.setLeadTime(leadTime);

        theMappedPackage.setOrderInTime(leadTime <= theMappedPackage.getEta());
        
        return theMappedPackage;
    }
    public Integer calculateDuration(Date startDate,Date endDate){
        long diff = endDate.getTime() - startDate.getTime();
        long result = TimeUnit.MILLISECONDS.toMinutes(diff);

        return (int)result;
    }


}
