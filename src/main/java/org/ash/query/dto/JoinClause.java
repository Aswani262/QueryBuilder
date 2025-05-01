package org.ash.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinClause {
    private String joinType;      // INNER, LEFT, etc.
    private String table;
    private String alias;
    private String onCondition;
}
