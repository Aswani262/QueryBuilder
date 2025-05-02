package org.ash.query.condition;



import org.ash.query.dto.ConditionDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ConditionMapper {

    public static Condition toCondition(ConditionDTO dto, String alias) {
        if (dto == null) return null;

        if (dto.getType() == null) {
            // Simple condition
            return new SimpleCondition(dto.getField(), dto.getOperator(), dto.getValue(), alias);
        }

        switch (dto.getType().toUpperCase()) {
            case "AND", "OR" -> {
                CompositeCondition.Type type = CompositeCondition.Type.valueOf(dto.getType().toUpperCase());
                List<Condition> children = dto.getConditions().stream()
                        .map(child -> toCondition(child, alias))
                        .collect(Collectors.toList());
                return new CompositeCondition(type, children);
            }
            case "NOT" -> {
                return new NotCondition(toCondition(dto.getConditions().get(0), alias));
            }
            default -> throw new IllegalArgumentException("Unsupported condition type: " + dto.getType());
        }
    }
}
