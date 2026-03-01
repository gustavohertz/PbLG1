package com.example.demo.Exception;

import java.util.List;

public class ErroResposta {
    private String mensagem;
    private List<String> detalhes;

    public ErroResposta(String mensagem, List<String> detalhes) {
        this.mensagem = mensagem;
        this.detalhes = detalhes;
    }

    public String getMensagem() { return mensagem; }
    public List<String> getDetalhes() { return detalhes; }
}