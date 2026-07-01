package com.agent_ai_users.web.mappers;

import java.util.List;

public interface BaseMapper<E,D> {

    E toEntity(D src);

    D toDto(E src);

    List<E> toEntity(List<D> src);

    List<D> toDto(List<E> src);
}
