package com.kamilisler.springwithkafka.service;

import com.kamilisler.springwithkafka.entity.Package;
import com.kamilisler.springwithkafka.model.MappedPackage;
import com.kamilisler.springwithkafka.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class PreparePackageService {
    @Autowired
    PackageRepository packageRepository;

    public MappedPackage getSinglePackage(Long id) {

        Optional<Package> thePackage = packageRepository.findById(id);
        // filter et cancalled'a göre
        //return thePackage.map(this::processPackage).orElse(null); // or else throw vs.


        /*
         * cancelled package'ların filtrelenmesi burada yapıldı.
         * çok büyük ölçekli verilerde filtreleme repository'den çekme sırasında yapılsa performans açısından
         * kullanmayacağımız verileri repository'den çekmemek adına daha iyi olabilirdi fakat bu projede uygulanmadı.
         *
         * */
        if (thePackage.isPresent() && !thePackage.get().getCancelled()) {

            return processPackage(thePackage.get());
        }
        return null;
    }
    public List<MappedPackage> getAllPackages(){

        List<Package> allPackages = packageRepository.findAll();
        return allPackages.stream().filter(thePackage -> !thePackage.getCancelled()).map(this::processPackage).collect(Collectors.toList());
    }

    private MappedPackage processPackage(Package samplePackage) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");

        MappedPackage theMappedPackage = new MappedPackage();

        theMappedPackage.setId(samplePackage.getId());
        // we assume that created_at not null
        theMappedPackage.setCreatedAt(dateFormat.format(samplePackage.getCreated_at()));
        // if created_at not null, last update at has the same value for beggining and not null.
        theMappedPackage.setLastUpdatedAt(dateFormat.format(samplePackage.getLast_updated_at()));

        // the package may be just created or cancelled befare collect,so not collected yet.  we do this control because if collected_at null, we get an error.
        if (Objects.isNull(samplePackage.getCollected())) {
            theMappedPackage.setCollectionDuration(null);
        } else {
            Integer collectionDuration = calculateDuration(samplePackage.getCreated_at(), samplePackage.getCollected_at());
            theMappedPackage.setCollectionDuration(collectionDuration);
        }
        // the package may be cancelled before delivery or not completed yet. in this situation deliveryDuration must be null.
        if (Objects.isNull(samplePackage.getIn_delivery_at()) || Objects.isNull(samplePackage.getCompleted_at())) {
            theMappedPackage.setDeliveryDuration(null);
        } else {
            Integer deliveryDuration = calculateDuration(samplePackage.getIn_delivery_at(), samplePackage.getCompleted_at());
            theMappedPackage.setDeliveryDuration(deliveryDuration);
        }

        // eta is not null, static value
        theMappedPackage.setEta(samplePackage.getEta());
        // the package may be not completed yet or cancelled. this means leadtime is null.
        // leadtime is null means ve dont complate the package and orderintime must be null.
        if (Objects.isNull(samplePackage.getCompleted_at())) {
            theMappedPackage.setLeadTime(null);
            theMappedPackage.setOrderInTime(null);
        } else {
            Integer leadTime = calculateDuration(samplePackage.getCreated_at(), samplePackage.getCompleted_at());
            theMappedPackage.setLeadTime(leadTime);
            theMappedPackage.setOrderInTime(leadTime <= theMappedPackage.getEta());
        }


        return theMappedPackage;
    }
    public Integer calculateDuration(Date startDate,Date endDate){
        long diff = endDate.getTime() - startDate.getTime();
        long result = TimeUnit.MILLISECONDS.toMinutes(diff);

        return (int)result;
    }


}
