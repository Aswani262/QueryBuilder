package org.ash.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QueryBuilderResult {
    private String sql;
    private List<Object> parameters;
}
