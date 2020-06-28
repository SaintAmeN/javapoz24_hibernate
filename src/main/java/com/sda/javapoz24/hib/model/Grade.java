package com.sda.javapoz24.hib.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @CreationTimestamp
    private LocalDateTime created; // ustawi się przy tworzeniu obiektu

    @UpdateTimestamp
    private LocalDateTime updated; // ustawi się przy MODYFIKACJI

    @ManyToOne
    @ToString.Exclude
    private Student student;

    // Jeśli macie ManyToMany po dwóch stronach
    // to mapped by wstawiacie po przeciwnej stronie, do tej po której
    // będziecie dodawać

    // Opcja 1:
    // Nauczyciel ManyToMany (mappedBy)
    // Student    ManyToMany (tworząc studenta, dodaje mu nauczyciela i zapisuję studenta)

    // Opcja 2:
    // Nauczyciel ManyToMany (tworząc Nauczyciela, dodaje mu studenta i zapisuję nauczyciela)
    // Student    ManyToMany (mappedBy)


}
