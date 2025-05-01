package org.ash.query.evaluator;



import org.ash.query.condition.CompositeCondition;
import org.ash.query.condition.Condition;
import org.ash.query.condition.NotCondition;
import org.ash.query.condition.SimpleCondition;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;

public class ConditionEvaluator<T> {

    private final Condition rootCondition;

    public ConditionEvaluator(Condition rootCondition) {
        this.rootCondition = rootCondition;
    }

    public Predicate<T> toPredicate() {
        return obj -> evaluate(rootCondition, obj);
    }

    public boolean evaluate(Condition condition, T obj) {
        if (condition instanceof SimpleCondition sc) {
            Object fieldValue = getFieldValue(obj, sc.getField());
            return applyOperator(fieldValue, sc.getValue(), sc.getOperator());

        } else if (condition instanceof CompositeCondition cc) {
            return switch (cc.getType()) {
                case AND -> cc.getConditions().stream().allMatch(c -> evaluate(c, obj));
                case OR  -> cc.getConditions().stream().anyMatch(c -> evaluate(c, obj));
            };

        } else if (condition instanceof NotCondition nc) {
            return !evaluate(nc.getCondition(), obj);
        }

        throw new UnsupportedOperationException("Unsupported condition: " + condition.getClass());
    }

    private Object getFieldValue(Object obj, String fieldName) {
        try {
            Field field = findField(obj.getClass(), fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException("Unable to read field " + fieldName, e);
        }
    }

    private Field findField(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignore) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }

    private boolean applyOperator(Object actual, Object expected, String operator) {
        if (actual == null) return false;

        return switch (operator) {
            case "=" -> actual.equals(expected);
            case "!=" -> !actual.equals(expected);
            case ">" -> compare(actual, expected) > 0;
            case "<" -> compare(actual, expected) < 0;
            case ">=" -> compare(actual, expected) >= 0;
            case "<=" -> compare(actual, expected) <= 0;
            case "LIKE" -> String.valueOf(actual).toLowerCase()
                .contains(String.valueOf(expected).toLowerCase().replace("%", ""));
            default -> throw new UnsupportedOperationException("Unsupported operator: " + operator);
        };
    }

    @SuppressWarnings("unchecked")
    private int compare(Object a, Object b) {
        if (a instanceof Comparable<?> && b instanceof Comparable<?>) {
            return ((Comparable<Object>) a).compareTo(b);
        }
        throw new IllegalArgumentException("Cannot compare: " + a + " and " + b);
    }
}
