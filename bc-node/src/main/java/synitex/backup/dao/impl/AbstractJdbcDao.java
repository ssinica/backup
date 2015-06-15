package synitex.backup.dao.impl;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-sql
 * http://www.jooq.org/doc/3.6/manual-single-page/#getting-started
 */
public abstract class AbstractJdbcDao {

    private final DSLContext dsl;

    @Autowired
    public AbstractJdbcDao(DSLContext dsl) {
        this.dsl = dsl;
    }

    public DSLContext getDsl() {
        return dsl;
    }
}
