package com.kamilisler.springwithkafka.servicetest;

import com.kamilisler.springwithkafka.entity.Package;
import com.kamilisler.springwithkafka.model.MappedPackage;
import com.kamilisler.springwithkafka.repository.PackageRepository;
import com.kamilisler.springwithkafka.service.PreparePackageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PreparePackageTest {

    @InjectMocks
    private PreparePackageService preparePackageService;
    @Mock
    private PackageRepository packageRepository;

    @DisplayName("call bootstrap with empty list, expect return emptylist")
    @Test
    public void should_return_null_when_none_package() {
        // Arrange
        // that means if we call find method of repository in our service, should be return empty list
        given(packageRepository.findAll()).willReturn(Collections.emptyList());
        // Act
        List<MappedPackage> packagesFound = preparePackageService.getAllPackages();
        // Assert
        Assertions.assertEquals(Collections.emptyList(), packagesFound);
    }

    // we create this method because we want to create date same format as given
    private Date createDateWithGivenFormat(String date) {
        SimpleDateFormat givenFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        try {
            return givenFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @DisplayName("sending package that is completed ,expecting result not null and MappedPackage class")
    @Test
    public void should_return_one_completedpackage() {


        // Arrange
        Package myPackage = new Package(19604181L, 123972783L, 50002010395213L, 20002011575015L,
                20000000004103L, 999000020443388L, "REGULAR",
                277, new Date(2021 - 11 - 13), createDateWithGivenFormat("2021-11-13 10:47:52.675248"), createDateWithGivenFormat("2021-11-13 10:47:52.675248"), createDateWithGivenFormat("2021-11-13 10:47:52.828692"),
                createDateWithGivenFormat("2021-11-13 10:48:37.032078"), createDateWithGivenFormat("2021-11-13 10:49:50.278087"), createDateWithGivenFormat("2021-11-13 11:05:56.861614"),
                createDateWithGivenFormat("2021-11-13 11:05:58.045640"), createDateWithGivenFormat("2021-11-13 11:40:15.314340"), createDateWithGivenFormat("2021-11-13 11:40:15.314340"),
                null, true, false,
                null, "COMPLETED", null);
        // Arrange
        //we ask for package from the  repository within service and we don't know if we'll  get or not.
        given(packageRepository.findById(anyLong())).willReturn(Optional.of(myPackage));

        // Act
        // result must not  be null because we have myPackage in repository and its not cancelled.
        MappedPackage resultPackage = preparePackageService.getSinglePackage(myPackage.getId());
        // Assert
        Assertions.assertNotNull(resultPackage);
        Assertions.assertEquals(MappedPackage.class, resultPackage.getClass());

    }

    @DisplayName("send cancelled single package,expect return null")
    @Test
    public void should_return_null_cancelled_package() {
        // Arrange
        Package cancelledPackage = new Package(3L, 333333L, 333333L, 333333L, 333333L, 333333L, "SMALL",
                120, new Date(2021 - 11 - 15), createDateWithGivenFormat("2022-11-15 08:47:52.675248"), createDateWithGivenFormat("2022-11-15 08:47:52.675248"),
                createDateWithGivenFormat("2022-11-15 08:57:52.828692"), null, null, null, null,
                null, createDateWithGivenFormat("2022-11-15 08:58:52.828692"), createDateWithGivenFormat("2022-11-15 08:58:52.828692"),
                true, true, "yanlış sipariş", "CANCELLED", null);
        // we will ask for package from repository within service, and we don't know if we'll get or not
        given(packageRepository.findById(anyLong())).willReturn(Optional.of(cancelledPackage));

        // Act
        // there is package in repository but test package must be null because our service is filtering cancelled packages.
        MappedPackage testPackage = preparePackageService.getSinglePackage(cancelledPackage.getId());
        // Assert
        Assertions.assertNull(testPackage);
    }

    @DisplayName("call bootstrap, except filter cancelled packages")
    @Test
    public void should_filter_cancelled_package_in_bootstrap() {
        // Arrange
        Package package1 = new Package(3L, 333333L, 333333L, 333333L, 333333L, 333333L, "SMALL",
                120, new Date(2021 - 11 - 15), createDateWithGivenFormat("2022-11-15 08:47:52.675248"), createDateWithGivenFormat("2022-11-15 08:47:52.675248"),
                createDateWithGivenFormat("2022-11-15 08:57:52.828692"), null, null, null, null,
                null, createDateWithGivenFormat("2022-11-15 08:58:52.828692"), createDateWithGivenFormat("2022-11-15 08:58:52.828692"),
                true, true, "yanlış sipariş", "CANCELLED", null);
        Package package2 = new Package(4L, 444444L, 444444L, 444444L, 444444L, 444444L,
                "BIG", 345, new Date(2021 - 11 - 15), createDateWithGivenFormat("2022-11-15 10:30:00.000000"),
                createDateWithGivenFormat("2022-11-15 10:30:00.000000"), createDateWithGivenFormat("2022-11-15 11:05:00.000000"),
                createDateWithGivenFormat("2022-11-15 11:10:00.000000"), createDateWithGivenFormat("2022-11-15 11:12:00.000000"),
                createDateWithGivenFormat("2022-11-15 11:20:00.000000"), createDateWithGivenFormat("2022-11-15 11:25:00.000000"), null, createDateWithGivenFormat("2022-11-15 11:30:00.000000"),
                createDateWithGivenFormat("2022-11-15 11:30:00.000000"), true, true, "vazgeçtim", "CANCELLED", null);
        Package package3 = new Package(7L, 777777L, 777777L, 777777L, 7L, 777777L, "REGULAR",
                130, new Date(2021 - 11 - 15), createDateWithGivenFormat("2022-11-15 10:50:12.758476"), createDateWithGivenFormat("2022-11-15 10:50:12.758476"), createDateWithGivenFormat("2022-11-15 10:55:12.758476"),
                createDateWithGivenFormat("2022-11-15 11:00:12.758476"), createDateWithGivenFormat("2022-11-15 11:05:12.758476"), createDateWithGivenFormat("2022-11-15 11:10:12.758476"), createDateWithGivenFormat("2022-11-15 11:15:12.758476"), createDateWithGivenFormat("2022-11-15 11:30:12.758476"),
                createDateWithGivenFormat("2022-11-15 11:30:12.758476"), null, true, false, null, "COMPLETED", null);
        Package package4 = new Package(8L, 888888L, 888888L, 888888L, 888888L, 888888L, "BIG",
                180, new Date(2021 - 11 - 15), createDateWithGivenFormat("2022-11-15 07:00:00.123456"), createDateWithGivenFormat("2022-11-15 07:00:00.123456"), createDateWithGivenFormat("2022-11-15 09:00:00.123456"), createDateWithGivenFormat("2022-11-15 09:10:00.123456"),
                createDateWithGivenFormat("2022-11-15 09:20:00.123456"), createDateWithGivenFormat("2022-11-15 09:32:00.123456"), createDateWithGivenFormat("2022-11-15 09:35:00.000000"), createDateWithGivenFormat("2022-11-15 12:35:00.000000"),
                createDateWithGivenFormat("2022-11-15 12:35:00.000000"), null, true, false, null, "COMPLETED", true);
        Package package5 = new Package(5L, 555555L, 555555L, 555555L, 555555L, 555555L, "SMALL",
                90, new Date(2021 - 11 - 15), createDateWithGivenFormat("2022-11-15 10:45:12.758476"), createDateWithGivenFormat("2022-11-15 10:45:12.758476"), createDateWithGivenFormat("2022-11-15 10:55:12.758476"),
                createDateWithGivenFormat("2022-11-15 10:57:12.758476"), createDateWithGivenFormat("2022-11-15 10:58:12.758476"), createDateWithGivenFormat("2022-11-15 11:00:12.758476"), createDateWithGivenFormat("2022-11-15 11:05:12.758476"),
                null, createDateWithGivenFormat("2022-11-15 11:05:12.758476"), null, true, false,
                null, "IN DELIVERY", null);


        // there is 2 cancelled packages and 3 completed packages.
        // we expect that service give us 3 packages that is not cancelled.
        //Arrange
        // we assume that our repository return all of 6 packages
        given(packageRepository.findAll()).willReturn(List.of(package1, package2, package3, package4, package5));
        // Act
        // our service must filter 2 packages of them
        List<MappedPackage> testPackage = preparePackageService.getAllPackages();

        // Assert
        // 3 packages remain without cancelled
        Assertions.assertEquals(3, testPackage.size());
    }

    @DisplayName("sending package that in delivery,expecting result not null and MappedPackage class")
    @Test
    public void should_return_one_indeliverypackage() {


        // Arrange
        Package inDeliveryPackage = new Package(5L, 555555L, 555555L, 555555L, 555555L, 555555L, "SMALL",
                90, new Date(2021 - 11 - 15), createDateWithGivenFormat("2022-11-15 10:45:12.758476"), createDateWithGivenFormat("2022-11-15 10:45:12.758476"), createDateWithGivenFormat("2022-11-15 10:55:12.758476"),
                createDateWithGivenFormat("2022-11-15 10:57:12.758476"), createDateWithGivenFormat("2022-11-15 10:58:12.758476"), createDateWithGivenFormat("2022-11-15 11:00:12.758476"), createDateWithGivenFormat("2022-11-15 11:05:12.758476"),
                null, createDateWithGivenFormat("2022-11-15 11:05:12.758476"), null, true, false,
                null, "IN DELIVERY", null);
        // Arrange
        //we ask for package from the  repository within service and we don't know if we'll  get or not.
        given(packageRepository.findById(anyLong())).willReturn(Optional.of(inDeliveryPackage));

        // Act.
        MappedPackage resultPackage = preparePackageService.getSinglePackage(inDeliveryPackage.getId());
        // Assert
        Assertions.assertNotNull(resultPackage);
        Assertions.assertEquals(MappedPackage.class, resultPackage.getClass());

    }

}
