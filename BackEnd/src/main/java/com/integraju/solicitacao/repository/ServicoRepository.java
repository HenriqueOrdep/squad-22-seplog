package com.integraju.solicitacao.repository;

import com.integraju.solicitacao.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Integer> {

    List<Servico> findByAtivoTrue();
}
