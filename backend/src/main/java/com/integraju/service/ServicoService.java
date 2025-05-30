package com.integraju.service;

import com.integraju.dto.ServicoDTO;
import com.integraju.model.Servico;
import com.integraju.model.Setor;
import com.integraju.repository.ServicoRepository;
import com.integraju.repository.SetorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final SetorRepository setorRepository;

    public List<Servico> listarTodos() {
        return servicoRepository.findAll();
    }

    public Optional<Servico> buscarPorId(Integer id) {
        return servicoRepository.findById(id);
    }

    public Servico salvar(ServicoDTO dto) {
        Setor setor = setorRepository.findById(dto.getSetorId())
                .orElseThrow(() -> new IllegalArgumentException("Setor não encontrado"));

        Servico servico = Servico.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .setor(setor)
                .build();

        return servicoRepository.save(servico);
    }

    public Servico atualizar(Integer id, ServicoDTO dto) {
        return servicoRepository.findById(id).map(servicoExistente -> {
            Setor setor = setorRepository.findById(dto.getSetorId())
                    .orElseThrow(() -> new IllegalArgumentException("Setor não encontrado"));

            servicoExistente.setNome(dto.getNome());
            servicoExistente.setDescricao(dto.getDescricao());
            servicoExistente.setSetor(setor);

            return servicoRepository.save(servicoExistente);
        }).orElse(null);
    }

    public void deletar(Integer id) {
        servicoRepository.deleteById(id);
    }
}
