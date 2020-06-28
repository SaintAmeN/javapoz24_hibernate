package com.sda.javapoz24.hib.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Grade implements IBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ClassSubject subject;

    private double grade; // wartość oceny

    @ManyToOne
    @ToString.Exclude
    private Student student;
}
