package com.integraju.solicitacao.service;

import com.integraju.analista.model.Analista;
import com.integraju.analista.repository.AnalistaRepository;
import com.integraju.solicitacao.dto.SolicitacaoDTO;
import com.integraju.solicitacao.model.*;
import com.integraju.solicitacao.repository.*;
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
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ServicoRepository servicoRepository;
    private final LogsService logsService;
    private final AnalistaRepository analistaRepository;

    public List<Solicitacao> listarTodos() {
        return solicitacaoRepository.findAll();
    }

    public Optional<Solicitacao> buscarPorId(Integer id) {
        return solicitacaoRepository.findById(id);
    }

    @Transactional
    public Solicitacao salvar(SolicitacaoDTO dto, String cpfUsuario) {
        Usuario usuario = usuarioRepository.findByCpfOuCnpj(cpfUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Servico servico = servicoRepository.findById(dto.getServicoId())
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));

        if (!servico.getAtivo()) {
            throw new IllegalArgumentException("Serviço inativo. Não é possível criar solicitação.");
        }

        Solicitacao solicitacao = Solicitacao.builder()
                .usuario(usuario)
                .servico(servico)
                .descricao(dto.getDescricao())
                .status(StatusRequisicao.RECEBIDA)
                .build();

        solicitacao = solicitacaoRepository.save(solicitacao);

        logsService.registrarLog(
                solicitacao,
                null,
                StatusRequisicao.RECEBIDA.name()
        );

        return solicitacao;
    }

    @Transactional
    public Solicitacao atualizarStatus(Integer solicitacaoId, Integer analistaId, StatusRequisicao novoStatus, String comentario) {
        Solicitacao solicitacao = solicitacaoRepository.findById(solicitacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitação não encontrada"));

        Analista analista = analistaRepository.findById(analistaId)
                .orElseThrow(() -> new IllegalArgumentException("Analista não encontrado"));

        StatusRequisicao statusAnterior = solicitacao.getStatus();

        if (!statusAnterior.podeTransitarPara(novoStatus)) {
            throw new IllegalArgumentException("Transição de status inválida de " + statusAnterior + " para " + novoStatus);
        }

        solicitacao.setStatus(novoStatus);
        solicitacao.setAtualizadoEm(LocalDateTime.now());

        if (statusAnterior == StatusRequisicao.RECEBIDA && novoStatus == StatusRequisicao.QUALIFICADA) {
            solicitacao.setAnalistaResponsavel(analista);
        }

        logsService.registrarLog(
                solicitacao,
                analista,
                statusAnterior.name(),
                novoStatus.name(),
                comentario
        );

        return solicitacaoRepository.save(solicitacao);
    }

    public List<Solicitacao> listarPorUsuario(Integer usuarioId) {
        return solicitacaoRepository.findByUsuarioId(usuarioId);
    }

    public List<Solicitacao> listarPorSetor(Integer setorId) {
        return solicitacaoRepository.findByServicoSetorId(setorId);
    }

    public List<Solicitacao> listarPorStatus(StatusRequisicao status) {
        return solicitacaoRepository.findByStatus(status);
    }

    public List<Logs> listarLogsPorSolicitacao(Integer solicitacaoId) {
        return logsService.listarPorSolicitacao(solicitacaoId);
    }
}
