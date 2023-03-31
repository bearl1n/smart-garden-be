package com.gmv.smartgardenbe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "relay")
public class Relay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean isEnabled;
    private String name;

    /** Order number in IOT (1,2,3,4)*/
    private Integer number;
    @ManyToOne
    @JoinColumn(name="device_id", nullable=false)
    private Device device;

}
