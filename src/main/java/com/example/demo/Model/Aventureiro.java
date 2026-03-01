package com.example.demo.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Aventureiro {

    private Long id;

    @NotBlank(message = "O nome é obrigatório e não pode ser vazio")
    private String nome;

    @NotNull(message = "A classe é obrigatória")
    private Classes classe; // Verifique se o seu Enum chama "Classes" ou "Classe"

    @Min(value = 1, message = "O nível deve ser maior ou igual a 1")
    private int nivel;

    private boolean ativo;

    private Companheiro companheiro;

    // Construtor Padrão
    public Aventureiro() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Classes getClasse() { return classe; }
    public void setClasse(Classes classe) { this.classe = classe; }

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public Companheiro getCompanheiro() { return companheiro; }
    public void setCompanheiro(Companheiro companheiro) { this.companheiro = companheiro; }
}