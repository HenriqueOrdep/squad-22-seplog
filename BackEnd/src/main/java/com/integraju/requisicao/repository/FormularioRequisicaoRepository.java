package com.integraju.requisicao.repository;

import com.integraju.requisicao.model.FormularioRequisicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormularioRequisicaoRepository extends JpaRepository<FormularioRequisicao, Integer> {
    List<FormularioRequisicao> findByRequisicaoId(Integer requisicaoId);
}
