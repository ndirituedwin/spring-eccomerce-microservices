package com.ndiritu.productservice.Dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
//@AllArgsConstructor
//@NoArgsConstructor
public class ProductDto {

    private Long id;
    @NotNull
    @NotBlank(message = "Product name cannot be blank")
    @Column(unique = true)
    private String name;
    @NotNull()
    @NotBlank(message = "Product description cannot be blank")
    private String description;
    private BigDecimal price;
    private Instant createdAt;
    private String message;
}
