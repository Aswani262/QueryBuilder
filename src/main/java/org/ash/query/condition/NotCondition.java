package org.ash.query.condition;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotCondition implements Condition {
    private Condition condition;
}
