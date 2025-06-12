package com.integraju.requisicao.repository;

import com.integraju.requisicao.model.Devolutiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevolutivaRepository extends JpaRepository<Devolutiva, Integer> {
    List<Devolutiva> findByRequisicaoId(Integer requisicaoId);
}
