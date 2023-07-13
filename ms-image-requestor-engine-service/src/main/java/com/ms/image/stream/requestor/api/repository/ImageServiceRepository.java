package com.ms.image.stream.requestor.api.repository;

import com.ms.image.stream.requestor.api.entity.ImageRequestorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface ImageServiceRepository extends JpaRepository<ImageRequestorService, BigInteger> {

//    @Query(value = "SELECT * FROM order_service where order_id=?1", nativeQuery = true)
//    Optional<ImageRequestorService> findCustomerByOrderId(String orderId);
//
//    @Query(value = "SELECT * FROM order_service where UPPER(customer_name)=?1", nativeQuery = true)
//    List<ImageRequestorService> findCustomerByCustomerName(String customer_name);
//
//    @Query(value = "SELECT * FROM order_service", nativeQuery = true)
//    List<ImageRequestorService> findAllCustomer();
}
