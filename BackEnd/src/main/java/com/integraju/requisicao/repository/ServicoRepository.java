package com.integraju.requisicao.repository;

import com.integraju.requisicao.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Integer> {

    List<Servico> findByAtivoTrue();
}
