package com.integraju.repository;

import com.integraju.model.Requisicao;
import com.integraju.model.StatusRequisicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RequisicaoRepository extends JpaRepository<Requisicao, Integer> {
    List<Requisicao> findByUsuarioId(Integer usuarioId);

    List<Requisicao> findByUsuarioIdAndStatus(Integer usuarioId, StatusRequisicao status);
}
