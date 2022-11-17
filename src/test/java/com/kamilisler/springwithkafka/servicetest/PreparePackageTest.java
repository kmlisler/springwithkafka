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

    @Test
    public void should_return_one_mappedpackage() {
        // Arrange
        Package myPackage = new Package(5L, 2L, 3L, 4L, 5L, 6L, "REGULAR",
                120, new Date(), new Date(), new Date(), new Date(), new Date(), new Date(),
                new Date(), new Date(), new Date(), new Date(), new Date(), true, false,
                "", "COMPLETED", null);
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

    @Test
    public void should_return_null_cancelled_package() {
        // Arrange
        Package myPackage = new Package(1L, 2L, 3L, 4L, 5L, 6L, "REGULAR",
                120, new Date(), new Date(), new Date(), new Date(), new Date(), new Date(),
                new Date(), new Date(), new Date(), new Date(), new Date(), true, true,
                "", "CANCELLED", null);
        // we will ask for package from repository within service, and we don't know if we'll get or not
        given(packageRepository.findById(anyLong())).willReturn(Optional.of(myPackage));

        // Act
        // there is package in repository but test package must be null because our service is filtering cancelled packages.
        MappedPackage testPackage = preparePackageService.getSinglePackage(myPackage.getId());
        // Assert
        Assertions.assertNull(testPackage);
    }

    @Test
    public void should_return_null_nonexistent_package() {
        // Arrange
        Package myPackage = new Package(2L, 2L, 3L, 4L, 5L, 6L, "REGULAR",
                120, new Date(), new Date(), new Date(), new Date(), new Date(), new Date(),
                new Date(), new Date(), new Date(), new Date(), new Date(), true, true,
                "", "COMPLETED", null);
        // we are adding one cancelled package to repository?
        given(packageRepository.findById(anyLong())).willReturn(Optional.of(myPackage));

        // Act
        // result must be null because we dont have package with id "1".
        MappedPackage testPackage = preparePackageService.getSinglePackage(1L);
        // Assert
        Assertions.assertNull(testPackage);
    }


    @DisplayName("Bootstrap method should filter cancalled packages")
    @Test
    public void should_filter_cancelled_package_in_bootstrap() {
        // Arrange
        Package package1 = new Package(6L, 7L, 8L, 9L, 10L, 11L, "REGULAR",
                120, new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(),
                new Date(), new Date(), true, true, "", "CANCELLED", null);
        Package package2 = new Package(2L, 3L, 4L, 5L, 6L, 7L, "REGULAR",
                345, new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(),
                new Date(), new Date(), true, true, "", "CANCELLED", null);
        Package package3 = new Package(3L, 4L, 5L, 6L, 7L, 8L, "REGULAR",
                127, new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(),
                new Date(), new Date(), true, false, "", "COMPLETED", null);
        Package package4 = new Package(4L, 5L, 6L, 7L, 8L, 9L, "REGULAR",
                210, new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(),
                new Date(), new Date(), true, false, "", "COMPLETED", null);
        Package package5 = new Package(5L, 6L, 7L, 8L, 9L, 10L, "REGULAR",
                150, new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(),
                new Date(), new Date(), true, false, "", "COMPLETED", null);
        Package package6 = new Package(1L, 1L, 12L, 13L, 13L, 11L, "REGULAR",
                143, new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(),
                new Date(), new Date(), true, false, "", "COMPLETED", null);

        // there is 2 cancelled packages and 4 completed packages.
        // we expect that service give us 4 packages that is not cancelled.
        //Arrange
        // we assume that our repository return all of 6 packages
        given(packageRepository.findAll()).willReturn(List.of(package1, package2, package3, package4, package5, package6));
        // Act
        // our service must filter 2 packages of them
        List<MappedPackage> testPackage = preparePackageService.getAllPackages();

        // Assert
        Assertions.assertEquals(4, testPackage.size());


    }

}
