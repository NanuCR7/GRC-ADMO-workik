package com.example.grc.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "master_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MasterData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category", length = 100, nullable = false)
    private String category; // Risk Type, Control Type, etc.

    @Column(name = "value", length = 200, nullable = false)
    private String value;
}
