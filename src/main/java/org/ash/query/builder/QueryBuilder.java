package org.ash.query.builder;


import org.ash.query.dto.QueryRequest;
import org.ash.query.parser.QueryParser;

public class QueryBuilder {
    public static QueryBuilderResult build(QueryRequest request) {
        String[] sort = QueryParser.parseSort(request.getSort());

        return SqlQueryBuilder.buildQuery(
            request.getBaseTable(),
            request.getBaseAlias(),
            request.getSelectFields(),
            request.getJoins(),
            request.getWhereCondition(),
            request.getSearchColumns(),
            request.getSearch(),
            sort,
            request.getPage(),
            request.getSize()
        );
    }
}
