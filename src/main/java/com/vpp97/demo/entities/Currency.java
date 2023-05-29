package com.vpp97.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "currency", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueName", columnNames = {"name"}),
        @UniqueConstraint(name = "UniqueCode", columnNames = {"code"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    private String name;
    private String code;

    @OneToOne(mappedBy = "currency", cascade = CascadeType.ALL)
    @JsonIgnore
    private ExchangeRateLast exchangeRateLast;
}
