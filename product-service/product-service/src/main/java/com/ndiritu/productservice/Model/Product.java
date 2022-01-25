package com.ndiritu.productservice.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
