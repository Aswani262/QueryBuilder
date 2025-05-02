package org.ash.query.dto;

import lombok.Data;

import java.util.List;

@Data
public class QueryRequestDTO {
    private String baseTable = "users";
    private String baseAlias = "u";
    private List<String> selectFields;
    private String sort;
    private int page = 0;
    private int size = 10;

    private String search;
    private List<String> searchColumns;

    private ConditionDTO filter;
}
