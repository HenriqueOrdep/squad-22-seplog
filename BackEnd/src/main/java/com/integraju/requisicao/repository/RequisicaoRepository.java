package com.integraju.requisicao.repository;

import com.integraju.requisicao.model.Requisicao;
import com.integraju.requisicao.model.StatusRequisicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequisicaoRepository extends JpaRepository<Requisicao, Integer> {

    List<Requisicao> findByUsuarioId(Integer usuarioId);

    List<Requisicao> findByUsuarioIdAndStatus(Integer usuarioId, StatusRequisicao status);

    List<Requisicao> findBySetorId(Integer setorId);

    List<Requisicao> findByStatus(StatusRequisicao status);

    List<Requisicao> findBySetorIdAndStatus(Integer setorId, StatusRequisicao status);
}
