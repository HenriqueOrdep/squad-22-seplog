package com.integraju.service;

import com.integraju.dto.LogsRequisicaoDTO;
import com.integraju.model.LogsRequisicao;
import com.integraju.model.Requisicao;
import com.integraju.model.Usuario;
import com.integraju.repository.LogsRequisicaoRepository;
import com.integraju.repository.RequisicaoRepository;
import com.integraju.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LogsRequisicaoService {

    private final LogsRequisicaoRepository logsRequisicaoRepository;
    private final RequisicaoRepository requisicaoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<LogsRequisicao> listarPorRequisicao(Integer requisicaoId) {
        return logsRequisicaoRepository.findByRequisicaoIdOrderByDataAsc(requisicaoId);
    }


    public List<LogsRequisicao> listarTodos() {
        return logsRequisicaoRepository.findAll();
    }

    public LogsRequisicao salvar(LogsRequisicaoDTO dto) {
        Requisicao requisicao = requisicaoRepository.findById(dto.getRequisicaoId())
                .orElseThrow(() -> new IllegalArgumentException("Requisição não encontrada"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioResponsavel())
                .orElseThrow(() -> new IllegalArgumentException("Usuário responsável não encontrado"));

        LogsRequisicao log = LogsRequisicao.builder()
                .requisicao(requisicao)
                .statusAnterior(dto.getStatusAnterior())
                .statusNovo(dto.getStatusNovo())
                .usuarioResponsavel(usuario)
                .build();

        return logsRequisicaoRepository.save(log);
    }

    public LogsRequisicao atualizar(Integer id, LogsRequisicaoDTO dto) {
        Optional<LogsRequisicao> optLog = logsRequisicaoRepository.findById(id);
        if (optLog.isEmpty()) {
            return null;
        }

        LogsRequisicao logExistente = optLog.get();

        Requisicao requisicao = requisicaoRepository.findById(dto.getRequisicaoId())
                .orElseThrow(() -> new IllegalArgumentException("Requisição não encontrada"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioResponsavel())
                .orElseThrow(() -> new IllegalArgumentException("Usuário responsável não encontrado"));

        logExistente.setRequisicao(requisicao);
        logExistente.setStatusAnterior(dto.getStatusAnterior());
        logExistente.setStatusNovo(dto.getStatusNovo());
        logExistente.setUsuarioResponsavel(usuario);

        return logsRequisicaoRepository.save(logExistente);
    }

    public void deletar(Integer id) {
        logsRequisicaoRepository.deleteById(id);
    }
}
