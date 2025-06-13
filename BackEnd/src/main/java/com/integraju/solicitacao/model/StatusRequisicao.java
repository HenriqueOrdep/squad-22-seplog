package com.integraju.solicitacao.model;


public enum StatusRequisicao {
    RECEBIDA("Recebida"),
    QUALIFICADA("Qualificada"),
    EM_ANALISE("Em Análise"),
    DEVOLUTIVA("Devolutiva"),
    INCOMPLETA("Incompleta"),
    FINALIZADA("Finalizada");

    private final String descricao;

    StatusRequisicao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean podeTransitarPara(StatusRequisicao novoStatus) {
        return switch (this) {
            case RECEBIDA     -> novoStatus == QUALIFICADA || novoStatus == INCOMPLETA;
            case QUALIFICADA  -> novoStatus == EM_ANALISE || novoStatus == INCOMPLETA;
            case EM_ANALISE   -> novoStatus == DEVOLUTIVA || novoStatus == FINALIZADA || novoStatus == INCOMPLETA;
            case DEVOLUTIVA   -> novoStatus == EM_ANALISE || novoStatus == FINALIZADA;
            case INCOMPLETA   -> novoStatus == RECEBIDA;
            case FINALIZADA   -> false;
        };
    }
}

