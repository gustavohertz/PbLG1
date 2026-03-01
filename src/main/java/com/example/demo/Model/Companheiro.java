package com.example.demo.Model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Companheiro {

    @NotBlank(message = "O nome do companheiro é obrigatório")
    private String nome;

    @NotNull(message = "A espécie é obrigatória")
    private Especies especie;

    @Min(value = 0, message = "Lealdade mínima é 0")
    @Max(value = 100, message = "Lealdade máxima é 100")
    private int lealdade;


    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Especies getEspecie() { return especie; }
    public void setEspecie(Especies especie) { this.especie = especie; }

    public int getLealdade() { return lealdade; }
    public void setLealdade(int lealdade) { this.lealdade = lealdade; }
}