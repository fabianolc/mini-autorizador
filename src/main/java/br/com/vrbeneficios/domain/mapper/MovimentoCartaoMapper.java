package br.com.vrbeneficios.domain.mapper;

import br.com.vrbeneficios.domain.dto.MovimentoCartaoDto;
import br.com.vrbeneficios.domain.entity.MovimentoCartao;
import br.com.vrbeneficios.domain.request.MovimentoCartaoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * The interface Movimento cartao mapper.
 */
@Mapper(componentModel = "spring")
public interface MovimentoCartaoMapper {

    /**
     * Entity to dto movimento cartao dto.
     *
     * @param movimentoCartao the movimento cartao
     * @return the movimento cartao dto
     */
    MovimentoCartaoDto entityToDto(MovimentoCartao movimentoCartao);

    /**
     * Dto to entity movimento cartao.
     *
     * @param movimentoCartaoDto the movimento cartao dto
     * @return the movimento cartao
     */
    @Mapping(target = "cartao.movimentos", ignore = true)
    MovimentoCartao dtoToEntity(MovimentoCartaoDto movimentoCartaoDto);

    /**
     * Request to dto movimento cartao dto.
     *
     * @param movimentoCartaoRequest the movimento cartao request
     * @return the movimento cartao dto
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipoMovimento", ignore = true)
    @Mapping(target = "dataMovimento", ignore = true)
    MovimentoCartaoDto requestToDto(MovimentoCartaoRequest movimentoCartaoRequest);
}
