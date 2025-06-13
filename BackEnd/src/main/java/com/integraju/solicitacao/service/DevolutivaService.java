package com.integraju.solicitacao.service;

import com.integraju.analista.model.Analista;
import com.integraju.analista.repository.AnalistaRepository;
import com.integraju.solicitacao.dto.DevolutivaDTO;
import com.integraju.solicitacao.model.Devolutiva;
import com.integraju.solicitacao.model.Solicitacao;
import com.integraju.solicitacao.repository.DevolutivaRepository;
import com.integraju.solicitacao.repository.SolicitacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DevolutivaService {

    private final DevolutivaRepository devolutivaRepo;
    private final SolicitacaoRepository solicitacaoRepo;
    private final AnalistaRepository analistaRepo;

    public DevolutivaDTO salvar(DevolutivaDTO dto) {
        Solicitacao solicitacao = solicitacaoRepo.findById(dto.getSolicitacaoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Solicitação com ID " + dto.getSolicitacaoId() + " não encontrada."));

        Analista analista = analistaRepo.findById(dto.getAnalistaId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Analista com ID " + dto.getAnalistaId() + " não encontrado."));

        Devolutiva devolutiva = Devolutiva.builder()
                .mensagem(dto.getMensagem())
                .solicitacao(solicitacao)
                .analista(analista)
                .build();

        devolutiva = devolutivaRepo.save(devolutiva);

        return DevolutivaDTO.builder()
                .id(devolutiva.getId())
                .mensagem(devolutiva.getMensagem())
                .solicitacaoId(solicitacao.getId())
                .analistaId(analista.getId())
                .criadoEm(devolutiva.getCriadoEm())
                .build();
    }

    public List<DevolutivaDTO> listarPorSolicitacao(Integer solicitacaoId) {
        return devolutivaRepo.findBySolicitacaoId(solicitacaoId)
                .stream()
                .map(dev -> DevolutivaDTO.builder()
                        .id(dev.getId())
                        .mensagem(dev.getMensagem())
                        .solicitacaoId(solicitacaoId)
                        .analistaId(dev.getAnalista().getId())
                        .criadoEm(dev.getCriadoEm())
                        .build())
                .collect(Collectors.toList());
    }
}