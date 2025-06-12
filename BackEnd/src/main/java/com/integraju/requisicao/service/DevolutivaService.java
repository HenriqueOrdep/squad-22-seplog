package com.integraju.requisicao.service;

import com.integraju.analista.model.Analista;
import com.integraju.analista.repository.AnalistaRepository;
import com.integraju.requisicao.dto.DevolutivaDTO;
import com.integraju.requisicao.model.Devolutiva;
import com.integraju.requisicao.model.Requisicao;
import com.integraju.requisicao.repository.DevolutivaRepository;
import com.integraju.requisicao.repository.RequisicaoRepository;
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
    private final RequisicaoRepository requisicaoRepo;
    private final AnalistaRepository analistaRepo;

    public DevolutivaDTO salvar(DevolutivaDTO dto) {
        Requisicao requisicao = requisicaoRepo.findById(dto.getRequisicaoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Requisição com ID " + dto.getRequisicaoId() + " não encontrada."));

        Analista analista = analistaRepo.findById(dto.getAnalistaId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Analista com ID " + dto.getAnalistaId() + " não encontrado."));

        Devolutiva devolutiva = Devolutiva.builder()
                .mensagem(dto.getMensagem())
                .requisicao(requisicao)
                .analista(analista)
                .build();

        devolutiva = devolutivaRepo.save(devolutiva);

        return DevolutivaDTO.builder()
                .id(devolutiva.getId())
                .mensagem(devolutiva.getMensagem())
                .requisicaoId(requisicao.getId())
                .analistaId(analista.getId())
                .criadoEm(devolutiva.getCriadoEm())
                .build();
    }

    public List<DevolutivaDTO> listarPorRequisicao(Integer requisicaoId) {
        return devolutivaRepo.findByRequisicaoId(requisicaoId)
                .stream()
                .map(dev -> DevolutivaDTO.builder()
                        .id(dev.getId())
                        .mensagem(dev.getMensagem())
                        .requisicaoId(requisicaoId)
                        .analistaId(dev.getAnalista().getId())
                        .criadoEm(dev.getCriadoEm())
                        .build())
                .collect(Collectors.toList());
    }
}
