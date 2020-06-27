package com.sda.javapoz24.hib.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity // adnotacja znajdująca się nad każdą klasą która ma swoją tablę w bazie.
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

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

}
