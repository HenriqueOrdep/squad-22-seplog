package com.integraju.requisicao.service;

import com.integraju.requisicao.dto.FormularioTemplateDTO;
import com.integraju.requisicao.model.FormularioTemplate;
import com.integraju.requisicao.model.Servico;
import com.integraju.requisicao.repository.FormularioTemplateRepository;
import com.integraju.requisicao.repository.ServicoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormularioTemplateService {

    private final FormularioTemplateRepository formularioTemplateRepository;
    private final ServicoRepository servicoRepository;

    public List<FormularioTemplate> listarTodos() {
        return formularioTemplateRepository.findAll();
    }

    public List<FormularioTemplate> listarPorServico(Integer servicoId) {
        return formularioTemplateRepository.findByServicoIdOrderByOrdemAsc(servicoId);
    }

    public FormularioTemplate salvar(@Valid FormularioTemplateDTO dto) {
        Servico servico = servicoRepository.findById(dto.getServicoId())
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado"));

        FormularioTemplate template = FormularioTemplate.builder()
                .servico(servico)
                .campoNome(dto.getCampoNome())
                .campoTipo(dto.getCampoTipo())
                .campoOpcoes(dto.getCampoOpcoes())
                .obrigatorio(dto.isObrigatorio())
                .ordem(dto.getOrdem())
                .build();

        return formularioTemplateRepository.save(template);
    }

    public FormularioTemplate atualizar(Integer id, @Valid FormularioTemplateDTO dto) {
        return formularioTemplateRepository.findById(id).map(template -> {
            Servico servico = servicoRepository.findById(dto.getServicoId())
                    .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado"));

            template.setServico(servico);
            template.setCampoNome(dto.getCampoNome());
            template.setCampoTipo(dto.getCampoTipo());
            template.setCampoOpcoes(dto.getCampoOpcoes());
            template.setObrigatorio(dto.isObrigatorio());
            template.setOrdem(dto.getOrdem());

            return formularioTemplateRepository.save(template);
        }).orElseThrow(() -> new EntityNotFoundException("Campo de formulário não encontrado"));
    }

    public void deletar(Integer id) {
        formularioTemplateRepository.deleteById(id);
    }
}
