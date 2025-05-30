package com.integraju.repository;

import com.integraju.model.LogsRequisicao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface LogsRequisicaoRepository extends JpaRepository<LogsRequisicao, Integer> {
    List<LogsRequisicao> findByRequisicaoIdOrderByDataAsc(Integer requisicaoId);
}

