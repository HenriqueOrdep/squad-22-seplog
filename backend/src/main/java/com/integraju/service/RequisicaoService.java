package com.integraju.service;

import com.integraju.dto.FormularioRequisicaoDTO;
import com.integraju.dto.RequisicaoDTO;
import com.integraju.dto.DevolutivaDTO;
import com.integraju.model.FormularioRequisicao;
import com.integraju.model.LogsRequisicao;
import com.integraju.model.Requisicao;
import com.integraju.model.StatusRequisicao;
import com.integraju.model.TipoRequisicao;
import com.integraju.model.Usuario;
import com.integraju.repository.FormularioRequisicaoRepository;
import com.integraju.repository.LogsRequisicaoRepository;
import com.integraju.repository.RequisicaoRepository;
import com.integraju.repository.TipoRequisicaoRepository;
import com.integraju.repository.UsuarioRepository;
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
    private final TipoRequisicaoRepository tipoRequisicaoRepository;
    private final FormularioRequisicaoRepository formularioRequisicaoRepository;
    private final LogsRequisicaoRepository logsRequisicaoRepository;

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

        TipoRequisicao tipo = tipoRequisicaoRepository.findById(dto.getTipoRequisicaoId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de requisição não encontrado"));

        Requisicao requisicao = Requisicao.builder()
                .usuario(usuario)
                .tipoRequisicao(tipo)
                .status(StatusRequisicao.PENDENTE)
                .build();

        requisicao = requisicaoRepository.save(requisicao);

        if (dto.getFormulario() != null) {
            for (FormularioRequisicaoDTO campo : dto.getFormulario()) {
                formularioRequisicaoRepository.save(
                        FormularioRequisicao.builder()
                                .requisicao(requisicao)
                                .campoNome(campo.getCampoNome())
                                .valor(campo.getValor())
                                .build()
                );
            }
        }

        return requisicao;
    }

    @Transactional
    public Requisicao atualizar(Integer id, RequisicaoDTO dto) {
        Optional<Requisicao> optRequisicao = requisicaoRepository.findById(id);
        if (optRequisicao.isEmpty()) {
            return null;
        }

        Requisicao requisicaoExistente = optRequisicao.get();

        TipoRequisicao tipo = tipoRequisicaoRepository.findById(dto.getTipoRequisicaoId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de requisição não encontrado"));

        requisicaoExistente.setTipoRequisicao(tipo);
        requisicaoExistente.setStatus(StatusRequisicao.PENDENTE);

        return requisicaoRepository.save(requisicaoExistente);
    }

    @Transactional
    public void deletar(Integer id) {
        requisicaoRepository.deleteById(id);
    }

    @Transactional
    public Requisicao encaminharRequisicao(Integer id, Integer usuarioResponsavelId) {
        Requisicao requisicao = requisicaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Requisição não encontrada"));

        Usuario usuarioResponsavel = usuarioRepository.findById(usuarioResponsavelId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário responsável não encontrado"));

        StatusRequisicao statusAnterior = requisicao.getStatus();

        if (!statusAnterior.podeTransitarPara(StatusRequisicao.ENCAMINHADA)) {
            throw new IllegalArgumentException("Transição de status inválida de " + statusAnterior + " para ENCAMINHADA.");
        }

        requisicao.setStatus(StatusRequisicao.ENCAMINHADA);

        LogsRequisicao log = LogsRequisicao.builder()
                .requisicao(requisicao)
                .usuarioResponsavel(usuarioResponsavel)
                .statusAnterior(statusAnterior.name())
                .statusNovo(StatusRequisicao.ENCAMINHADA.name())
                .data(LocalDateTime.now())
                .comentario(null)
                .build();

        logsRequisicaoRepository.save(log);
        return requisicaoRepository.save(requisicao);
    }

    @Transactional
    public Requisicao enviarDevolutiva(DevolutivaDTO dto) {
        Requisicao requisicao = requisicaoRepository.findById(dto.getRequisicaoId())
                .orElseThrow(() -> new IllegalArgumentException("Requisição não encontrada"));

        Usuario analista = usuarioRepository.findById(dto.getUsuarioAnalistaId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário analista não encontrado"));

        StatusRequisicao novoStatus = StatusRequisicao.valueOf(dto.getNovoStatus());

        StatusRequisicao statusAnterior = requisicao.getStatus();

        if (!statusAnterior.podeTransitarPara(novoStatus)) {
            throw new IllegalArgumentException("Transição de status inválida de " + statusAnterior + " para " + novoStatus + ".");
        }

        requisicao.setStatus(novoStatus);

        LogsRequisicao log = LogsRequisicao.builder()
                .requisicao(requisicao)
                .usuarioResponsavel(analista)
                .statusAnterior(statusAnterior.name())
                .statusNovo(novoStatus.name())
                .comentario(dto.getComentario())
                .data(LocalDateTime.now())
                .build();

        logsRequisicaoRepository.save(log);

        return requisicaoRepository.save(requisicao);
    }


    public List<Requisicao> listarPorUsuarioEStatus(Integer usuarioId, StatusRequisicao status) {
        if (status != null) {
            return requisicaoRepository.findByUsuarioIdAndStatus(usuarioId, status);
        }
        return requisicaoRepository.findByUsuarioId(usuarioId);
    }
    public List<LogsRequisicao> listarLogsPorRequisicao(Integer requisicaoId) {
        return logsRequisicaoRepository.findByRequisicaoIdOrderByDataAsc(requisicaoId);
    }
}
