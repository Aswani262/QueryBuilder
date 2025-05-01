package org.ash.query.condition;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CompositeCondition implements Condition {
    public enum Type { AND, OR }
    private Type type;
    private List<Condition> conditions;
}
