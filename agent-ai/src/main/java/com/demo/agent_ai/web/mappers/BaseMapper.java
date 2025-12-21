package com.demo.agent_ai.web.mappers;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BaseMapper<D,M> {

    D toDocument(M src);

    M toModel(D src);

    List<D> toDocument(List<M> src);

    List<M> toModel(List<D> src);
}
