package com.integraju.requisicao.service;

import com.integraju.analista.model.Analista;
import com.integraju.analista.repository.AnalistaRepository;
import com.integraju.requisicao.dto.LogsRequisicaoDTO;
import com.integraju.requisicao.model.LogsRequisicao;
import com.integraju.requisicao.model.Requisicao;
import com.integraju.requisicao.repository.LogsRequisicaoRepository;
import com.integraju.requisicao.repository.RequisicaoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogsRequisicaoService {

    private final LogsRequisicaoRepository logsRequisicaoRepository;
    private final RequisicaoRepository requisicaoRepository;
    private final AnalistaRepository analistaRepository;

    public List<LogsRequisicao> listarPorRequisicao(Integer requisicaoId) {
        return logsRequisicaoRepository.findByRequisicaoIdOrderByDataAsc(requisicaoId);
    }

    public List<LogsRequisicao> listarTodos() {
        return logsRequisicaoRepository.findAll();
    }

    public LogsRequisicao salvar(@Valid LogsRequisicaoDTO dto) {
        Requisicao requisicao = requisicaoRepository.findById(dto.getRequisicaoId())
                .orElseThrow(() -> new IllegalArgumentException("Requisição não encontrada"));

        Analista analista = analistaRepository.findById(dto.getAnalistaId())
                .orElseThrow(() -> new IllegalArgumentException("Analista não encontrado"));

        LogsRequisicao log = LogsRequisicao.builder()
                .requisicao(requisicao)
                .statusAnterior(dto.getStatusAnterior())
                .statusNovo(dto.getStatusNovo())
                .comentario(dto.getComentario())
                .analista(analista)
                .build();

        return logsRequisicaoRepository.save(log);
    }

    public LogsRequisicao atualizar(Integer id, @Valid LogsRequisicaoDTO dto) {
        LogsRequisicao log = logsRequisicaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Log não encontrado"));

        Requisicao requisicao = requisicaoRepository.findById(dto.getRequisicaoId())
                .orElseThrow(() -> new IllegalArgumentException("Requisição não encontrada"));

        Analista analista = analistaRepository.findById(dto.getAnalistaId())
                .orElseThrow(() -> new IllegalArgumentException("Analista não encontrado"));

        log.setRequisicao(requisicao);
        log.setStatusAnterior(dto.getStatusAnterior());
        log.setStatusNovo(dto.getStatusNovo());
        log.setComentario(dto.getComentario());
        log.setAnalista(analista);

        return logsRequisicaoRepository.save(log);
    }

    public void deletar(Integer id) {
        logsRequisicaoRepository.deleteById(id);
    }

    public void registrarLog(Requisicao requisicao, String statusAnterior, String statusNovo) {
        LogsRequisicao log = LogsRequisicao.builder()
                .requisicao(requisicao)
                .statusAnterior(statusAnterior)
                .statusNovo(statusNovo)
                .build();

        logsRequisicaoRepository.save(log);
    }

    public void registrarLog(Requisicao requisicao, Analista analista, String statusAnterior, String statusNovo, String comentario) {
        LogsRequisicao log = LogsRequisicao.builder()
                .requisicao(requisicao)
                .analista(analista)
                .statusAnterior(statusAnterior)
                .statusNovo(statusNovo)
                .comentario(comentario)
                .build();

        logsRequisicaoRepository.save(log);
    }
}
