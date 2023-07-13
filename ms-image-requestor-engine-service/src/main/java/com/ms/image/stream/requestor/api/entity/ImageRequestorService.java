package com.ms.image.stream.requestor.api.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.ZonedDateTime;
/**
 * JPA Schema class
 *
 * @author Shreejit Panchal
 * @version 1.0
 * @date 11/07/2023
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image_service")
public class ImageRequestorService {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private BigInteger Id;

    @Column(name = "image_id")
    private String imageId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "image_filename")
    private String imageFileName;

    @Column(name = "image_type")
    private String imageType;

    @Column(name = "image_stream_status")
    private String imageStreamStatus;

    @Column(name = "image_stream_desc")
    private String imageStreamDesc;

    @Column(name = "correlation_id")
    private String correlationId;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "request_datetime")
    private ZonedDateTime requestDateTime;

    @Column(name = "created_datetime")
    private ZonedDateTime createdDateTime;

    @Column(name = "modified_datetime")
    private ZonedDateTime modifiedDateTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String modifiedBy;
}
