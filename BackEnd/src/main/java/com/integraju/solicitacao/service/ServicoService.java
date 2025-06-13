package com.integraju.solicitacao.service;

import com.integraju.solicitacao.dto.ServicoDTO;
import com.integraju.solicitacao.model.Servico;
import com.integraju.analista.model.Setor;
import com.integraju.solicitacao.repository.ServicoRepository;
import com.integraju.analista.repository.SetorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final SetorRepository setorRepository;

    public List<Servico> listarTodos() {
        return servicoRepository.findByAtivoTrue();
    }

    public Servico buscarPorId(Integer id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado"));
    }

    public Servico salvar(ServicoDTO dto) {
        Setor setor = setorRepository.findById(dto.getSetorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Setor não encontrado"));

        Servico servico = Servico.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .setor(setor)
                .ativo(dto.getAtivo() != null ? dto.getAtivo() : true)
                .build();

        return servicoRepository.save(servico);
    }

    public Servico atualizar(Integer id, ServicoDTO dto) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado"));

        Setor setor = setorRepository.findById(dto.getSetorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Setor não encontrado"));

        servico.setNome(dto.getNome());
        servico.setDescricao(dto.getDescricao());
        servico.setSetor(setor);
        servico.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : servico.getAtivo());

        return servicoRepository.save(servico);
    }

    public void deletar(Integer id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado"));

        servico.setAtivo(false);
        servicoRepository.save(servico);
    }
}
