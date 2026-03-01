package com.example.demo.repository;

import com.example.demo.Model.Aventureiro;
import com.example.demo.Model.Classes;
import com.example.demo.Model.Companheiro;
import com.example.demo.Model.Especies;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.Optional;

@Repository
public class GuildaRepository {

    private final List<Aventureiro> aventureiros = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final Random random = new Random();

    public GuildaRepository() {
        popularDadosIniciais();
    }

    private void popularDadosIniciais() {
        String[] nomesSeed = {"Ragnar", "Lyra", "Kael", "Thrain", "Serafina", "Eldrin"};
        Classes[] classes = Classes.values();

        for (int i = 0; i < 100; i++) {
            Aventureiro a = new Aventureiro();
            a.setId(idGenerator.getAndIncrement());
            a.setNome(nomesSeed[random.nextInt(nomesSeed.length)] + " " + (i + 1));
            a.setClasse(classes[random.nextInt(classes.length)]);
            a.setNivel(random.nextInt(50) + 1);
            a.setAtivo(true);

            if (i % 5 == 0) {
                Companheiro c = new Companheiro();
                c.setNome("Fiel " + i);
                c.setEspecie(Especies.values()[random.nextInt(Especies.values().length)]);
                c.setLealdade(random.nextInt(101));
                a.setCompanheiro(c);
            }

            aventureiros.add(a);
        }
    }

    public List<Aventureiro> findAll() {
        return new ArrayList<>(aventureiros);
    }

    public Optional<Aventureiro> findById(Long id) {
        return aventureiros.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
    }

    public List<Aventureiro> findByFilters(Classes classe, Boolean ativo, Integer nivelMinimo) {
        return aventureiros.stream()
                .filter(a -> (classe == null || a.getClasse().equals(classe)))
                .filter(a -> (ativo == null || a.isAtivo() == ativo))
                .filter(a -> (nivelMinimo == null || a.getNivel() >= nivelMinimo))
                .sorted((a1, a2) -> a1.getId().compareTo(a2.getId())) // Ordenação obrigatória por ID
                .collect(Collectors.toList());
    }

    public Aventureiro update(Aventureiro aventureiroExistente, Aventureiro novosDados) {
        aventureiroExistente.setNome(novosDados.getNome());
        aventureiroExistente.setClasse(novosDados.getClasse());
        aventureiroExistente.setNivel(novosDados.getNivel());
        return aventureiroExistente;
    }

    public Aventureiro save(Aventureiro novoAventureiro) {
        novoAventureiro.setId(idGenerator.getAndIncrement());
        novoAventureiro.setAtivo(true);
        novoAventureiro.setCompanheiro(null);
        aventureiros.add(novoAventureiro);
        return novoAventureiro;
    }

    public void updateCompanheiro(Long aventureiroId, Companheiro novoCompanheiro) {
        findById(aventureiroId).ifPresent(a -> a.setCompanheiro(novoCompanheiro));
    }
}