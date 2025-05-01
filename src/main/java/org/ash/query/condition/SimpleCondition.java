package org.ash.query.condition;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleCondition implements Condition {
    private String field;
    private String operator;
    private Object value;
    private String alias; // optional
}
