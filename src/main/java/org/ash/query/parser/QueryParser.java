package org.ash.query.parser;

public class QueryParser {

    public static String[] parseSort(String sortStr) {
        return sortStr != null && !sortStr.isEmpty()
                ? sortStr.split(",")
                : new String[]{"createdAt", "asc"};
    }
}
