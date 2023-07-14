package com.ms.image.stream.requestor.api.repository;

import com.ms.image.stream.requestor.api.entity.ImageRequestorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface ImageRequestorServiceRepository extends JpaRepository<ImageRequestorService, BigInteger> {
    @Query(value = "SELECT * FROM image_service where transaction_id=?1", nativeQuery = true)
    Optional<ImageRequestorService> findImageByTransactionId(String transcationId);

}
