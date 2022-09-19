
package com.chen.blog.core.account.domain.model;

import com.chen.blog.core.sharedkernel.ddd.Identifiable;
import com.chen.blog.core.sharedkernel.ddd.Identifier;
import com.chen.blog.core.sharedkernel.ddd.annotation.ValueObject;
import com.chen.blog.core.sharedkernel.idgenerator.IdGenerators;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/6 16:10
 */
@ValueObject
@EqualsAndHashCode(of = {"id"})
@ToString
@Setter(AccessLevel.PRIVATE)
public class AccountId implements Identifiable<Long>, Identifier {
    /**
     * id
     */
    private Long id;

    private AccountId(Long id) {
        setId(id);
    }

    public static AccountId of(Long id) {
        Preconditions.checkNotNull(id);
        return new AccountId(id);
    }

    public static AccountId generateId() {
        return AccountId.of(IdGenerators.current().generateId());
    }

    @Override
    public Long getId() {
        return id;
    }
}
