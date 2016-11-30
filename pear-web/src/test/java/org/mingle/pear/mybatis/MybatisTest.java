package org.mingle.pear.mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mingle.pear.App;
import org.mingle.pear.domain.mapper.AccountMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

/**
 * Created by mingle on 2016/11/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class MybatisTest {
    @Inject private AccountMapper accountMapper;

    @Test
    public void testSelect() {
        System.out.println(accountMapper.getAccount(8L));
    }
}
