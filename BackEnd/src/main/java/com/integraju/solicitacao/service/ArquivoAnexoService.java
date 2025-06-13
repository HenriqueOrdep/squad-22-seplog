package com.integraju.solicitacao.service;

import com.integraju.solicitacao.dto.ArquivoAnexoDTO;
import com.integraju.solicitacao.model.ArquivoAnexo;
import com.integraju.solicitacao.model.Solicitacao;
import com.integraju.solicitacao.repository.ArquivoAnexoRepository;
import com.integraju.solicitacao.repository.SolicitacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArquivoAnexoService {

    private final ArquivoAnexoRepository arquivoRepo;
    private final SolicitacaoRepository solicitacaoRepo;

    public ArquivoAnexoDTO salvar(ArquivoAnexoDTO dto) {
        Solicitacao solicitacao = solicitacaoRepo.findById(dto.getSolicitacaoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Solicitação com ID " + dto.getSolicitacaoId() + " não encontrada."
                ));

        ArquivoAnexo arquivo = ArquivoAnexo.builder()
                .nomeArquivo(dto.getNomeArquivo())
                .caminhoArquivo(dto.getCaminhoArquivo())
                .solicitacao(solicitacao)
                .build();

        arquivo = arquivoRepo.save(arquivo);

        return ArquivoAnexoDTO.builder()
                .id(arquivo.getId())
                .nomeArquivo(arquivo.getNomeArquivo())
                .caminhoArquivo(arquivo.getCaminhoArquivo())
                .solicitacaoId(solicitacao.getId())
                .enviadoEm(arquivo.getEnviadoEm())
                .build();
    }

    public List<ArquivoAnexoDTO> listarPorSolicitacao(Integer solicitacaoId) {
        return arquivoRepo.findBySolicitacaoId(solicitacaoId)
                .stream()
                .map(arq -> ArquivoAnexoDTO.builder()
                        .id(arq.getId())
                        .nomeArquivo(arq.getNomeArquivo())
                        .caminhoArquivo(arq.getCaminhoArquivo())
                        .solicitacaoId(solicitacaoId)
                        .enviadoEm(arq.getEnviadoEm())
                        .build())
                .collect(Collectors.toList());
    }
}
