package com.integraju.service;

import com.integraju.dto.FormularioRequisicaoDTO;
import com.integraju.model.FormularioRequisicao;
import com.integraju.model.Requisicao;
import com.integraju.repository.FormularioRequisicaoRepository;
import com.integraju.repository.RequisicaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class FormularioRequisicaoService {

    private final FormularioRequisicaoRepository formularioRequisicaoRepository;
    private final RequisicaoRepository requisicaoRepository;

    public List<FormularioRequisicao> listarTodos() {
        return formularioRequisicaoRepository.findAll();
    }

    public FormularioRequisicao salvar(FormularioRequisicaoDTO dto) {
        Requisicao requisicao = requisicaoRepository.findById(dto.getRequisicaoId())
                .orElseThrow(() -> new IllegalArgumentException("Requisição não encontrada"));

        FormularioRequisicao formulario = FormularioRequisicao.builder()
                .requisicao(requisicao)
                .campoNome(dto.getCampoNome())
                .valor(dto.getValor())
                .build();

        return formularioRequisicaoRepository.save(formulario);
    }

    public FormularioRequisicao atualizar(Integer id, FormularioRequisicaoDTO dto) {
        Optional<FormularioRequisicao> optFormulario = formularioRequisicaoRepository.findById(id);
        if (optFormulario.isEmpty()) {
            return null;
        }

        FormularioRequisicao formularioExistente = optFormulario.get();

        Requisicao requisicao = requisicaoRepository.findById(dto.getRequisicaoId())
                .orElseThrow(() -> new IllegalArgumentException("Requisição não encontrada"));

        formularioExistente.setRequisicao(requisicao);
        formularioExistente.setCampoNome(dto.getCampoNome());
        formularioExistente.setValor(dto.getValor());

        return formularioRequisicaoRepository.save(formularioExistente);
    }

    public void deletar(Integer id) {
        formularioRequisicaoRepository.deleteById(id);
    }
}
