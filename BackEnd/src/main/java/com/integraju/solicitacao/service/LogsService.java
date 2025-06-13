package com.integraju.solicitacao.service;

import com.integraju.analista.model.Analista;
import com.integraju.analista.repository.AnalistaRepository;
import com.integraju.solicitacao.dto.LogsDTO;
import com.integraju.solicitacao.model.Logs;
import com.integraju.solicitacao.model.Solicitacao;
import com.integraju.solicitacao.repository.LogsRepository;
import com.integraju.solicitacao.repository.SolicitacaoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogsService {

    private final LogsRepository logsRepository;
    private final SolicitacaoRepository solicitacaoRepository;
    private final AnalistaRepository analistaRepository;

    public List<Logs> listarPorSolicitacao(Integer solicitacaoId) {
        return logsRepository.findBySolicitacaoIdOrderByDataAsc(solicitacaoId);
    }

    public List<Logs> listarTodos() {
        return logsRepository.findAll();
    }

    public Logs salvar(@Valid LogsDTO dto) {
        Solicitacao solicitacao = solicitacaoRepository.findById(dto.getSolicitacaoId())
                .orElseThrow(() -> new IllegalArgumentException("Solicitação não encontrada"));

        Analista analista = analistaRepository.findById(dto.getAnalistaId())
                .orElseThrow(() -> new IllegalArgumentException("Analista não encontrado"));

        Logs log = Logs.builder()
                .solicitacao(solicitacao)
                .statusAnterior(dto.getStatusAnterior())
                .statusNovo(dto.getStatusNovo())
                .comentario(dto.getComentario())
                .analista(analista)
                .build();

        return logsRepository.save(log);
    }

    public Logs atualizar(Integer id, @Valid LogsDTO dto) {
        Logs log = logsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Log não encontrado"));

        Solicitacao solicitacao = solicitacaoRepository.findById(dto.getSolicitacaoId())
                .orElseThrow(() -> new IllegalArgumentException("Solicitação não encontrada"));

        Analista analista = analistaRepository.findById(dto.getAnalistaId())
                .orElseThrow(() -> new IllegalArgumentException("Analista não encontrado"));

        log.setSolicitacao(solicitacao);
        log.setStatusAnterior(dto.getStatusAnterior());
        log.setStatusNovo(dto.getStatusNovo());
        log.setComentario(dto.getComentario());
        log.setAnalista(analista);

        return logsRepository.save(log);
    }

    public void deletar(Integer id) {
        logsRepository.deleteById(id);
    }

    public void registrarLog(Solicitacao solicitacao, String statusAnterior, String statusNovo) {
        Logs log = Logs.builder()
                .solicitacao(solicitacao)
                .statusAnterior(statusAnterior)
                .statusNovo(statusNovo)
                .build();

        logsRepository.save(log);
    }

    public void registrarLog(Solicitacao solicitacao, Analista analista, String statusAnterior, String statusNovo, String comentario) {
        Logs log = Logs.builder()
                .solicitacao(solicitacao)
                .analista(analista)
                .statusAnterior(statusAnterior)
                .statusNovo(statusNovo)
                .comentario(comentario)
                .build();

        logsRepository.save(log);
    }
}
