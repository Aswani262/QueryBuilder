package org.ash.query.dto;

import lombok.Data;
import java.util.List;

@Data
public class ConditionDTO {
    private String type; // AND, OR, NOT, or null for simple
    private String field;
    private String operator;
    private Object value;
    private List<ConditionDTO> conditions; // nested children
}
