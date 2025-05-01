package org.ash.query.builder;



import org.ash.query.condition.CompositeCondition;
import org.ash.query.condition.Condition;
import org.ash.query.condition.NotCondition;
import org.ash.query.condition.SimpleCondition;

import java.util.ArrayList;
import java.util.List;

public class ConditionSqlBuilder {

    public static class SqlWithParams {
        public final String sql;
        public final List<Object> params;

        public SqlWithParams(String sql, List<Object> params) {
            this.sql = sql;
            this.params = params;
        }
    }

    public static SqlWithParams build(Condition condition) {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();
        buildInternal(condition, sql, params);
        return new SqlWithParams(sql.toString(), params);
    }

    private static void buildInternal(Condition condition, StringBuilder sql, List<Object> params) {
        if (condition instanceof SimpleCondition sc) {
            String alias = (sc.getAlias() != null ? sc.getAlias() + "." : "");
            sql.append(alias).append(sc.getField()).append(" ").append(sc.getOperator()).append(" ?");
            params.add(sc.getValue());

        } else if (condition instanceof CompositeCondition cc) {
            sql.append("(");
            for (int i = 0; i < cc.getConditions().size(); i++) {
                if (i > 0) sql.append(" ").append(cc.getType()).append(" ");
                buildInternal(cc.getConditions().get(i), sql, params);
            }
            sql.append(")");

        } else if (condition instanceof NotCondition nc) {
            sql.append("NOT (");
            buildInternal(nc.getCondition(), sql, params);
            sql.append(")");
        }
    }
}
