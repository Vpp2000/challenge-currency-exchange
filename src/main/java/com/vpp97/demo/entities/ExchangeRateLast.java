package com.vpp97.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

@Entity
@Table(
        name = "exchange_rate_last",
        uniqueConstraints = @UniqueConstraint(columnNames = {"currency_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeRateLast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(scale = 4, columnDefinition = "decimal(10,4)")
    private BigDecimal rate;

    @OneToOne()
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

}
