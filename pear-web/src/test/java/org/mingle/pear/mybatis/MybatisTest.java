package org.mingle.pear.mybatis;

import org.junit.Test;
import org.mingle.pear.BaseTest;
import org.mingle.pear.dao.AccountDao;
import org.mingle.pear.domain.Account;
import org.mingle.pear.dto.AccountQueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Random;
import java.util.UUID;

/**
 * Created by mingle on 2016/11/28.
 */
public class MybatisTest extends BaseTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject private AccountDao accountDao;

    @Test
    public void testSelect() {
        logger.info("account: {}", accountDao.getById(1L));
    }

    @Test
    public void testQuery() {
        AccountQueryParam queryParam = new AccountQueryParam();
        queryParam.setAge(25);
        logger.info("account: {}", accountDao.query(queryParam));
    }

    @Test
    public void testInsert() {
        Account account = new Account();
        account.setAge(10);
        String name = "test";
        account.setName(name);
        account.setSex(2);
        account.setVersion(1);
        account.setEmail(name + "@yeah.net");
        accountDao.insert(account);
    }

    @Test
    public void testUpdate() {
        Account account = new Account();
        account.setId(10L);
        account.setAge(16);
        String name = "test1";
        account.setName(name);
        account.setSex(1);
        account.setVersion(2);
        account.setEmail(name + "@yeah.net");
        accountDao.update(account);
    }

    @Test
    public void testDelete() {
        accountDao.delete(20L);
    }

    @Test
    public void multiInsert() {
        Random random = new Random();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Account account = new Account();
            account.setAge(random.nextInt(100));
            String name = UUID.randomUUID().toString();
            account.setName(name);
            account.setSex(random.nextInt(3));
            account.setVersion(1);
            account.setEmail(name + "@yeah.net");
            accountDao.insert(account);
        }

        System.out.println(System.currentTimeMillis() - start);
    }
}
