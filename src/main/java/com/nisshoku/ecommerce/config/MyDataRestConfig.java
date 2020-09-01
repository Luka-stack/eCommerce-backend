package com.nisshoku.ecommerce.config;

import com.nisshoku.ecommerce.entity.Country;
import com.nisshoku.ecommerce.entity.Product;
import com.nisshoku.ecommerce.entity.ProductCategory;
import com.nisshoku.ecommerce.entity.State;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private final EntityManager entityManager;

    public MyDataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

        HttpMethod[] theUnsupportedActions = {HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT};

        disableHttpMethods(Product.class, config, theUnsupportedActions);
        disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);
        disableHttpMethods(Country.class, config, theUnsupportedActions);
        disableHttpMethods(State.class, config, theUnsupportedActions);

        exposeIds(config);
    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config,
                                    HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions)));
    }

    private void exposeIds(RepositoryRestConfiguration config) {

        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        List<Class> entityClasses = new ArrayList<>();

        for (EntityType tempEntity : entities) {
            entityClasses.add(tempEntity.getJavaType());
        }

        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
