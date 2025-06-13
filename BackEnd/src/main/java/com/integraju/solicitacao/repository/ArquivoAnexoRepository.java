package com.integraju.solicitacao.repository;

import com.integraju.solicitacao.model.ArquivoAnexo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArquivoAnexoRepository extends JpaRepository<ArquivoAnexo, Integer> {
    List<ArquivoAnexo> findBySolicitacaoId(Integer solicitacaoId);
}
