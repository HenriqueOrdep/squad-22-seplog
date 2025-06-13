package com.integraju.solicitacao.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitacaoDTO {

    private Integer id;

    @NotNull(message = "ID do serviço é obrigatório")
    private Integer servicoId;

    @Size(max = 3000, message = "A descrição deve ter no máximo 3000 caracteres")
    private String descricao;

    private LocalDateTime criadoEm;

    private LocalDateTime atualizadoEm;

    private List<ArquivoAnexoDTO> arquivos;

    private List<DevolutivaDTO> devolutivas;
}
