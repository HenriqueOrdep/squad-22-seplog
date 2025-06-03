package com.integraju.model;

public enum StatusRequisicao {
    PENDENTE,
    EM_ANALISE,
    DEVOLUTIVA,
    FINALIZADA,
    ENCAMINHADA;

    public boolean podeTransitarPara(StatusRequisicao novoStatus) {
        switch (this) {
            case PENDENTE:
                return novoStatus == EM_ANALISE || novoStatus == ENCAMINHADA;
            case EM_ANALISE:
                return novoStatus == DEVOLUTIVA || novoStatus == FINALIZADA;
            case DEVOLUTIVA:
                return novoStatus == FINALIZADA;
            case FINALIZADA:
                return false;
            case ENCAMINHADA:
                return novoStatus == EM_ANALISE || novoStatus == FINALIZADA;
            default:
                return false;
        }
    }
}
