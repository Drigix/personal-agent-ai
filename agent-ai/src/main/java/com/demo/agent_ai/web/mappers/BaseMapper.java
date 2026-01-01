package com.demo.agent_ai.web.mappers;

import java.util.List;

public interface BaseMapper<D,M> {

    D toDocument(M src);

    M toModel(D src);

    List<D> toDocument(List<M> src);

    List<M> toModel(List<D> src);
}
