package org.ash.query.builder;



import org.ash.query.condition.Condition;
import org.ash.query.dto.JoinClause;

import java.util.ArrayList;
import java.util.List;

public class SqlQueryBuilder {

    public static QueryBuilderResult buildQuery(
        String baseTable,
        String baseAlias,
        List<String> selectFields,
        List<JoinClause> joins,
        Condition condition,
        List<String> searchColumns,
        String search,
        String[] sort,
        int page,
        int size
    ) {
        List<Object> params = new ArrayList<>();

        String select = (selectFields != null && !selectFields.isEmpty())
                ? String.join(", ", selectFields)
                : baseAlias + ".*";

        StringBuilder sql = new StringBuilder("SELECT ")
                .append(select)
                .append(" FROM ").append(baseTable).append(" ").append(baseAlias).append(" ");

        if (joins != null) {
            for (JoinClause join : joins) {
                sql.append(join.getJoinType()).append(" JOIN ").append(join.getTable());
                if (join.getAlias() != null) {
                    sql.append(" ").append(join.getAlias());
                }
                sql.append(" ON ").append(join.getOnCondition()).append(" ");
            }
        }

        // WHERE condition
        if (condition != null) {
            ConditionSqlBuilder.SqlWithParams where = ConditionSqlBuilder.build(condition);
            sql.append("WHERE ").append(where.sql).append(" ");
            params.addAll(where.params);
        }

        // ORDER BY
        sql.append("ORDER BY ").append(baseAlias).append(".").append(sort[0])
           .append(" ").append(sort.length > 1 ? sort[1].toUpperCase() : "ASC").append(" ");

        // Pagination
        sql.append("LIMIT ? OFFSET ?");
        params.add(size);
        params.add(page * size);

        return new QueryBuilderResult(sql.toString().trim(), params);
    }
}
