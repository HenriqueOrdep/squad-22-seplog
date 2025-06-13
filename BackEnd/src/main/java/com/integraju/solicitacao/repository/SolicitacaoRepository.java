package com.integraju.solicitacao.repository;

import com.integraju.solicitacao.model.Solicitacao;
import com.integraju.solicitacao.model.StatusRequisicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Integer> {

    List<Solicitacao> findByUsuarioId(Integer usuarioId);

    List<Solicitacao> findByUsuarioIdAndStatus(Integer usuarioId, StatusRequisicao status);

    List<Solicitacao> findByServicoSetorId(Integer setorId);

    List<Solicitacao> findByStatus(StatusRequisicao status);

    List<Solicitacao> findByServicoSetorIdAndStatus(Integer setorId, StatusRequisicao status);
}
