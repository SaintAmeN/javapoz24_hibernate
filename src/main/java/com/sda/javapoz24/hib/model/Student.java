package com.sda.javapoz24.hib.model;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity // adnotacja znajdująca się nad każdą klasą która ma swoją tablę w bazie.
@Getter
@Setter
@EqualsAndHashCode
@ToString//(of = {"firstName", "lastName"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student implements IBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    // IDENTITY - dopisanie auto_increment w bazie danych - przez co identyfikatory biorą się z bazy
    //
    // SEQUENCE
    // TABLE
    // W obu powyższych przypadkach stworzona zostanie tabela która przechowuje następny identyfikator
    // NIE MA AUTO_INCREMENT NA TABELI!!!
    private Long id;

//    @Column
    private String firstName;

//    @Column
    private String lastName;

//    @Column
    private int age;

    @Column(columnDefinition = "BOOLEAN DEFAULT 1")
    private boolean awarded;

//    @Column
    // ordinal = liczbowo
    // string = tekstowo
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private Set<Grade> gradeSet = new HashSet<>();

    // Nie powinniśmy tworzyć relacji (więcej niż jednej) w której relacja oparta jest o kolekcję List
    // Hibernate nie pozwoli zdefiniować dwóch relacji List, szczególnie z typem EAGER

    // WAŻNE ! UMIEŚCIĆ W NAWIASIE
    // WAŻNE ! Typ obiektowy (Double) a nie 'double'
    @Formula(value = "(select avg(grade.grade) from grade where grade.student_id=id)")
    private Double average;
}
