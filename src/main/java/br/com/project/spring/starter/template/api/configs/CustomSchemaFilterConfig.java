package br.com.project.spring.starter.template.api.configs;

import org.hibernate.boot.model.relational.Namespace;
import org.hibernate.boot.model.relational.Sequence;
import org.hibernate.mapping.Table;
import org.hibernate.tool.schema.spi.SchemaFilter;

public class CustomSchemaFilterConfig implements SchemaFilter {
    public static final CustomSchemaFilterConfig INSTANCE = new CustomSchemaFilterConfig();

    @Override
    public boolean includeNamespace(Namespace namespace) {
        return true;
    }

    @Override
    public boolean includeTable(Table table) {
        return table.getSchema().equals("public");
    }

    @Override
    public boolean includeSequence(Sequence sequence) {
        return true;
    }
}
