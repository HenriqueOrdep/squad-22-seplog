package com.integraju.solicitacao.repository;

import com.integraju.solicitacao.model.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LogsRepository extends JpaRepository<Logs, Integer> {
    List<Logs> findBySolicitacaoIdOrderByDataAsc(Integer solicitacaoId);
}
