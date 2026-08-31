package br.com.rafaellbarros.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {

    private String nome;
    private int idade;
    private char sexo;
    private String matricula;

}
