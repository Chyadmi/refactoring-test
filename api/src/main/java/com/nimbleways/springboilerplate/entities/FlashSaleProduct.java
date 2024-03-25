package com.nimbleways.springboilerplate.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "flash_sale_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleProduct extends Product {

    @Column(name = "flash_sale_start_date")
    private LocalDate flashSaleStartDate;

    @Column(name = "flash_sale_end_date")
    private LocalDate flashSaleEndDate;

    @Column(name = "flash_sale_max_quantity")
    private Integer flashSaleMaxQuantity;

    @Column(name = "flash_sale_quantity_sold")
    private Integer flashSaleQuantitySold = 0;
}
