package br.com.vrbeneficios.domain.mapper;

import br.com.vrbeneficios.domain.dto.CartaoDto;
import br.com.vrbeneficios.domain.entity.Cartao;
import br.com.vrbeneficios.domain.request.CartaoRequest;
import br.com.vrbeneficios.domain.response.CartaoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * The interface Cartao mapper.
 */
@Mapper(componentModel = "spring")
public interface CartaoMapper {

    /**
     * Entity to dto cartao dto.
     *
     * @param cartao the cartao
     * @return the cartao dto
     */
    CartaoDto entityToDto(Cartao cartao);

    /**
     * Dto to entity cartao.
     *
     * @param cartaoDto the cartao dto
     * @return the cartao
     */
    Cartao dtoToEntity(CartaoDto cartaoDto);

    /**
     * Request to dto cartao dto.
     *
     * @param cartaoRequest the cartao request
     * @return the cartao dto
     */
    @Mapping(target = "saldo", ignore = true)
    CartaoDto requestToDto(CartaoRequest cartaoRequest);

    /**
     * Dto to response cartao response.
     *
     * @param cartaoDto the cartao dto
     * @return the cartao response
     */
    CartaoResponse dtoToResponse(CartaoDto cartaoDto);

    /**
     * Entity to response cartao response.
     *
     * @param cartao the cartao
     * @return the cartao response
     */
    CartaoResponse entityToResponse(Cartao cartao);
}
