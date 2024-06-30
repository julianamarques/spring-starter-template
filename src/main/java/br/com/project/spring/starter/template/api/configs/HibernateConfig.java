package br.com.project.spring.starter.template.api.configs;

import org.hibernate.tool.schema.internal.DefaultSchemaFilter;
import org.hibernate.tool.schema.spi.SchemaFilter;
import org.hibernate.tool.schema.spi.SchemaFilterProvider;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig implements SchemaFilterProvider {
    @Override
    public SchemaFilter getCreateFilter() {
        return DefaultSchemaFilter.INSTANCE;
    }

    @Override
    public SchemaFilter getDropFilter() {
        return DefaultSchemaFilter.INSTANCE;
    }

    @Override
    public SchemaFilter getTruncatorFilter() {
        return DefaultSchemaFilter.INSTANCE;
    }

    @Override
    public SchemaFilter getMigrateFilter() {
        return DefaultSchemaFilter.INSTANCE;
    }

    @Override
    public SchemaFilter getValidateFilter() {
        return CustomSchemaFilterConfig.INSTANCE;
    }
}
