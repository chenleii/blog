package com.chen.blog.infrastructure.persistence.repository.mongodb;

import com.chen.blog.infrastructure.persistence.repository.dataobject.AccountDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/21 15:43
 */
public interface AccountMongoRepository extends MongoRepository<AccountDO, Long> {


    /**
     * 根据手机号查询
     *
     * @param phoneNo 手机号
     * @return 账户
     */
    Optional<AccountDO> findByPhoneNo(String phoneNo);

}
