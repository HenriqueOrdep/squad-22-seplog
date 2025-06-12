package com.integraju.requisicao.model;

public enum StatusRequisicao {
    RECEBIDA,
    QUALIFICADA,
    EM_ANALISE,
    DEVOLUTIVA,
    INCOMPLETA,
    FINALIZADA;

    public boolean podeTransitarPara(StatusRequisicao novoStatus) {
        return switch (this) {
            case RECEBIDA -> novoStatus == QUALIFICADA || novoStatus == INCOMPLETA;
            case QUALIFICADA -> novoStatus == EM_ANALISE || novoStatus == INCOMPLETA;
            case EM_ANALISE -> novoStatus == DEVOLUTIVA || novoStatus == FINALIZADA || novoStatus == INCOMPLETA;
            case DEVOLUTIVA -> novoStatus == EM_ANALISE || novoStatus == FINALIZADA;
            case INCOMPLETA -> novoStatus == RECEBIDA;
            case FINALIZADA -> false;
        };
    }
}
