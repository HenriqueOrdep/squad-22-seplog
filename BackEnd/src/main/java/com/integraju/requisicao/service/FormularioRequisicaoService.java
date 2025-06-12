package com.integraju.requisicao.service;

import com.integraju.requisicao.dto.FormularioRequisicaoDTO;
import com.integraju.requisicao.model.FormularioRequisicao;
import com.integraju.requisicao.model.Requisicao;
import com.integraju.requisicao.repository.FormularioRequisicaoRepository;
import com.integraju.requisicao.repository.RequisicaoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormularioRequisicaoService {

    private final FormularioRequisicaoRepository formularioRequisicaoRepository;
    private final RequisicaoRepository requisicaoRepository;

    public List<FormularioRequisicao> listarTodos() {
        return formularioRequisicaoRepository.findAll();
    }

    public List<FormularioRequisicao> listarPorRequisicao(Integer requisicaoId) {
        return formularioRequisicaoRepository.findByRequisicaoId(requisicaoId);
    }

    public FormularioRequisicao salvar(@Valid FormularioRequisicaoDTO dto) {
        Requisicao requisicao = requisicaoRepository.findById(dto.getRequisicaoId())
                .orElseThrow(() -> new EntityNotFoundException("Requisição não encontrada"));

        FormularioRequisicao formulario = FormularioRequisicao.builder()
                .requisicao(requisicao)
                .campoNome(dto.getCampoNome())
                .valor(dto.getValor())
                .build();

        return formularioRequisicaoRepository.save(formulario);
    }

    public FormularioRequisicao atualizar(Integer id, @Valid FormularioRequisicaoDTO dto) {
        FormularioRequisicao existente = formularioRequisicaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Formulário não encontrado"));

        Requisicao requisicao = requisicaoRepository.findById(dto.getRequisicaoId())
                .orElseThrow(() -> new EntityNotFoundException("Requisição não encontrada"));

        existente.setRequisicao(requisicao);
        existente.setCampoNome(dto.getCampoNome());
        existente.setValor(dto.getValor());

        return formularioRequisicaoRepository.save(existente);
    }

    public void deletar(Integer id) {
        formularioRequisicaoRepository.deleteById(id);
    }
}
