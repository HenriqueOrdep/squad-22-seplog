package com.integraju.requisicao.service;

import com.integraju.requisicao.dto.ArquivoRequisicaoDTO;
import com.integraju.requisicao.model.ArquivoRequisicao;
import com.integraju.requisicao.model.Requisicao;
import com.integraju.requisicao.repository.ArquivoRequisicaoRepository;
import com.integraju.requisicao.repository.RequisicaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArquivoRequisicaoService {

    private final ArquivoRequisicaoRepository arquivoRepo;
    private final RequisicaoRepository requisicaoRepo;

    public ArquivoRequisicaoDTO salvar(ArquivoRequisicaoDTO dto) {
        Requisicao requisicao = requisicaoRepo.findById(dto.getRequisicaoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Requisição com ID " + dto.getRequisicaoId() + " não encontrada."
                ));

        ArquivoRequisicao arquivo = ArquivoRequisicao.builder()
                .nomeArquivo(dto.getNomeArquivo())
                .caminhoArquivo(dto.getCaminhoArquivo())
                .requisicao(requisicao)
                .build();

        arquivo = arquivoRepo.save(arquivo);

        return ArquivoRequisicaoDTO.builder()
                .id(arquivo.getId())
                .nomeArquivo(arquivo.getNomeArquivo())
                .caminhoArquivo(arquivo.getCaminhoArquivo())
                .requisicaoId(requisicao.getId())
                .enviadoEm(arquivo.getEnviadoEm())
                .build();
    }

    public List<ArquivoRequisicaoDTO> listarPorRequisicao(Integer requisicaoId) {
        return arquivoRepo.findByRequisicaoId(requisicaoId)
                .stream()
                .map(arq -> ArquivoRequisicaoDTO.builder()
                        .id(arq.getId())
                        .nomeArquivo(arq.getNomeArquivo())
                        .caminhoArquivo(arq.getCaminhoArquivo())
                        .requisicaoId(requisicaoId)
                        .enviadoEm(arq.getEnviadoEm())
                        .build())
                .collect(Collectors.toList());
    }
}
