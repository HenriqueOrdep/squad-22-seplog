package com.integraju.service;

import com.integraju.dto.FormularioTemplateDTO;
import com.integraju.model.FormularioTemplate;
import com.integraju.model.TipoRequisicao;
import com.integraju.repository.FormularioTemplateRepository;
import com.integraju.repository.TipoRequisicaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FormularioTemplateService {

    private final FormularioTemplateRepository formularioTemplateRepository;
    private final TipoRequisicaoRepository tipoRequisicaoRepository;

    public List<FormularioTemplate> listarTodos() {
        return formularioTemplateRepository.findAll();
    }

    public FormularioTemplate salvar(FormularioTemplateDTO dto) {
        TipoRequisicao tipo = tipoRequisicaoRepository.findById(dto.getTipoRequisicaoId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de Requisição não encontrado"));

        FormularioTemplate template = FormularioTemplate.builder()
                .tipoRequisicao(tipo)
                .campoNome(dto.getCampoNome())
                .campoTipo(dto.getCampoTipo())
                .campoOpcoes(dto.getCampoOpcoes())
                .obrigatorio(dto.isObrigatorio())
                .ordem(dto.getOrdem())
                .build();

        return formularioTemplateRepository.save(template);
    }

    public FormularioTemplate atualizar(Integer id, FormularioTemplateDTO dto) {
        return formularioTemplateRepository.findById(id).map(templateExistente -> {
            TipoRequisicao tipo = tipoRequisicaoRepository.findById(dto.getTipoRequisicaoId())
                    .orElseThrow(() -> new IllegalArgumentException("Tipo de Requisição não encontrado"));

            templateExistente.setTipoRequisicao(tipo);
            templateExistente.setCampoNome(dto.getCampoNome());
            templateExistente.setCampoTipo(dto.getCampoTipo());
            templateExistente.setCampoOpcoes(dto.getCampoOpcoes());
            templateExistente.setObrigatorio(dto.isObrigatorio());
            templateExistente.setOrdem(dto.getOrdem());

            return formularioTemplateRepository.save(templateExistente);
        }).orElse(null);
    }

    public void deletar(Integer id) {
        formularioTemplateRepository.deleteById(id);
    }
}
