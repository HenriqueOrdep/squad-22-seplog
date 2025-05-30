package com.integraju.service;

import com.integraju.dto.SetorDTO;
import com.integraju.model.Setor;
import com.integraju.repository.SetorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Setor não encontrado com id: " + id));
        return toDTO(setor);
    }

    public SetorDTO salvar(SetorDTO dto) {
        Setor setor = toEntity(dto);
        setor = setorRepository.save(setor);
        return toDTO(setor);
    }

    public SetorDTO atualizar(Integer id, SetorDTO dto) {
        Optional<Setor> optionalSetor = setorRepository.findById(id);
        if (optionalSetor.isEmpty()) {
            return null;
        }

        Setor setorExistente = optionalSetor.get();
        setorExistente.setNome(dto.getNome());

        setorExistente = setorRepository.save(setorExistente);
        return toDTO(setorExistente);
    }

    public void deletar(Integer id) {
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
