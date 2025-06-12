package com.integraju.requisicao.repository;

import com.integraju.requisicao.model.FormularioTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FormularioTemplateRepository extends JpaRepository<FormularioTemplate, Integer> {
    List<FormularioTemplate> findByServicoIdOrderByOrdemAsc(Integer servicoId);
}
