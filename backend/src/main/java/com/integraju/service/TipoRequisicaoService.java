package com.integraju.service;

import com.integraju.dto.TipoRequisicaoDTO;
import com.integraju.model.Servico;
import com.integraju.model.TipoRequisicao;
import com.integraju.repository.ServicoRepository;
import com.integraju.repository.TipoRequisicaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TipoRequisicaoService {

    private final TipoRequisicaoRepository tipoRequisicaoRepository;
    private final ServicoRepository servicoRepository;

    public List<TipoRequisicao> listarTodos() {
        return tipoRequisicaoRepository.findAll();
    }

    public Optional<TipoRequisicao> buscarPorId(Integer id) {
        return tipoRequisicaoRepository.findById(id);
    }

    public TipoRequisicao salvar(TipoRequisicaoDTO dto) {
        Servico servico = servicoRepository.findById(dto.getServicoId())
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));

        TipoRequisicao tipo = TipoRequisicao.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .servico(servico)
                .build();

        return tipoRequisicaoRepository.save(tipo);
    }

    public TipoRequisicao atualizar(Integer id, TipoRequisicaoDTO dto) {
        return tipoRequisicaoRepository.findById(id).map(tipoExistente -> {
            Servico servico = servicoRepository.findById(dto.getServicoId())
                    .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));

            tipoExistente.setNome(dto.getNome());
            tipoExistente.setDescricao(dto.getDescricao());
            tipoExistente.setServico(servico);

            return tipoRequisicaoRepository.save(tipoExistente);
        }).orElse(null);
    }

    public void deletar(Integer id) {
        tipoRequisicaoRepository.deleteById(id);
    }
}
