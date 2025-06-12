package com.integraju.requisicao.service;

import com.integraju.analista.model.Analista;
import com.integraju.analista.model.Setor;
import com.integraju.analista.repository.AnalistaRepository;
import com.integraju.analista.repository.SetorRepository;
import com.integraju.requisicao.dto.FormularioRequisicaoDTO;
import com.integraju.requisicao.dto.RequisicaoDTO;
import com.integraju.requisicao.model.*;
import com.integraju.requisicao.repository.*;
import com.integraju.usuario.model.Usuario;
import com.integraju.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequisicaoService {

    private final RequisicaoRepository requisicaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ServicoRepository servicoRepository;
    private final SetorRepository setorRepository;
    private final FormularioRequisicaoRepository formularioRequisicaoRepository;
    private final LogsRequisicaoService logsRequisicaoService;
    private final AnalistaRepository analistaRepository;

    public List<Requisicao> listarTodos() {
        return requisicaoRepository.findAll();
    }

    public Optional<Requisicao> buscarPorId(Integer id) {
        return requisicaoRepository.findById(id);
    }

    @Transactional
    public Requisicao salvar(RequisicaoDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Servico servico = servicoRepository.findById(dto.getServicoId())
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));

        Setor setor = servico.getSetor();

        Requisicao requisicao = Requisicao.builder()
                .usuario(usuario)
                .servico(servico)
                .setor(setor)
                .descricao(dto.getDescricao())
                .status(StatusRequisicao.RECEBIDA)
                .build();

        requisicao = requisicaoRepository.save(requisicao);

        if (dto.getFormulario() != null) {
            FormularioRequisicaoDTO campo = dto.getFormulario();
            FormularioRequisicao entidade = FormularioRequisicao.builder()
                    .requisicao(requisicao)
                    .campoNome(campo.getCampoNome())
                    .valor(campo.getValor())
                    .build();
            formularioRequisicaoRepository.save(entidade);
        }

        logsRequisicaoService.registrarLog(
                requisicao,
                null,
                StatusRequisicao.RECEBIDA.name()
        );

        return requisicao;
    }

    @Transactional
    public Requisicao atualizarStatus(Integer requisicaoId, Integer analistaId, StatusRequisicao novoStatus, String comentario) {
        Requisicao requisicao = requisicaoRepository.findById(requisicaoId)
                .orElseThrow(() -> new IllegalArgumentException("Requisição não encontrada"));

        Analista analista = analistaRepository.findById(analistaId)
                .orElseThrow(() -> new IllegalArgumentException("Analista não encontrado"));

        StatusRequisicao statusAnterior = requisicao.getStatus();

        if (!statusAnterior.podeTransitarPara(novoStatus)) {
            throw new IllegalArgumentException("Transição de status inválida de " + statusAnterior + " para " + novoStatus);
        }

        requisicao.setStatus(novoStatus);
        requisicao.setAtualizadoEm(LocalDateTime.now());

        logsRequisicaoService.registrarLog(
                requisicao,
                analista,
                statusAnterior.name(),
                novoStatus.name(),
                comentario
        );

        return requisicaoRepository.save(requisicao);
    }

    public List<Requisicao> listarPorUsuario(Integer usuarioId) {
        return requisicaoRepository.findByUsuarioId(usuarioId);
    }

    public List<Requisicao> listarPorSetor(Integer setorId) {
        return requisicaoRepository.findBySetorId(setorId);
    }

    public List<Requisicao> listarPorStatus(StatusRequisicao status) {
        return requisicaoRepository.findByStatus(status);
    }

    public List<LogsRequisicao> listarLogsPorRequisicao(Integer requisicaoId) {
        return logsRequisicaoService.listarPorRequisicao(requisicaoId);
    }
}
