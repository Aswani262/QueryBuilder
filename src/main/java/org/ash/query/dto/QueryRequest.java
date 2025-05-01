package org.ash.query.dto;


import lombok.Data;
import org.ash.query.condition.Condition;

import java.util.List;

@Data
public class QueryRequest {
    private String baseTable = "entity";
    private String baseAlias = "e";

    private List<String> selectFields;
    private List<JoinClause> joins;

    private Condition whereCondition;

    private String search;
    private List<String> searchColumns;

    private String sort; // e.g., created_at,desc
    private Integer page = 0;
    private Integer size = 20;
}
