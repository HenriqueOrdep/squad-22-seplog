package com.integraju.analista.service;

import com.integraju.analista.dto.SetorDTO;
import com.integraju.analista.model.Setor;
import com.integraju.analista.repository.SetorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SetorService {

    private final SetorRepository setorRepository;

    public List<SetorDTO> listarTodos() {
        return setorRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public SetorDTO buscarPorId(Integer id) {
        return toDTO(buscarEntidadePorId(id));
    }

    public Setor buscarEntidadePorId(Integer id) {
        return setorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Setor não encontrado com id: " + id));
    }

    public SetorDTO salvar(SetorDTO dto) {
        Setor setor = toEntity(dto);
        setor = setorRepository.save(setor);
        return toDTO(setor);
    }

    @Transactional
    public SetorDTO atualizar(Integer id, SetorDTO dto) {
        Setor setorExistente = buscarEntidadePorId(id);
        setorExistente.setNome(dto.getNome());
        return toDTO(setorExistente);
    }

    public void deletar(Integer id) {
        if (!setorRepository.existsById(id)) {
            throw new IllegalArgumentException("Setor não encontrado com id: " + id);
        }
        setorRepository.deleteById(id);
    }

    private SetorDTO toDTO(Setor setor) {
        return SetorDTO.builder()
                .id(setor.getId())
                .nome(setor.getNome())
                .build();
    }

    private Setor toEntity(SetorDTO dto) {
        return Setor.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .build();
    }
}
