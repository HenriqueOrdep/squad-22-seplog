package com.integraju.solicitacao.repository;

import com.integraju.solicitacao.model.Devolutiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevolutivaRepository extends JpaRepository<Devolutiva, Integer> {
    List<Devolutiva> findBySolicitacaoId(Integer requisicaoId);
}

