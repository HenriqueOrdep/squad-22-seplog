package com.integraju.requisicao.repository;

import com.integraju.requisicao.model.ArquivoRequisicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArquivoRequisicaoRepository extends JpaRepository<ArquivoRequisicao, Integer> {
    List<ArquivoRequisicao> findByRequisicaoId(Integer requisicaoId);
}
