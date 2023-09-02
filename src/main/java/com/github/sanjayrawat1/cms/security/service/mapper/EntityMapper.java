package com.github.sanjayrawat1.cms.security.service.mapper;

import java.util.List;

/**
 * @author Sanjay Singh Rawat
 */
public interface EntityMapper<D, E> {
    E toEntity(D dto);

    List<E> toEntity(List<D> dtos);

    D toDto(E entity);

    List<D> toDto(List<E> entities);

    void update(E entity, D dto);

    void partialUpdate(E entity, D dto);
}
