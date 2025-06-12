package com.integraju.requisicao.dto;

import com.integraju.requisicao.model.StatusRequisicao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequisicaoDTO {

    private Integer id;

    @NotNull(message = "ID do usuário é obrigatório")
    private Integer usuarioId;

    @NotNull(message = "ID do serviço é obrigatório")
    private Integer servicoId;

    @NotNull(message = "ID do setor é obrigatório")
    private Integer setorId;

    @Size(max = 3000, message = "A descrição deve ter no máximo 3000 caracteres")
    private String descricao;

    private StatusRequisicao status;

    private LocalDateTime criadoEm;

    private LocalDateTime atualizadoEm;

    private FormularioRequisicaoDTO formulario;

    private List<ArquivoRequisicaoDTO> arquivos;

    private List<DevolutivaDTO> devolutivas;
}
