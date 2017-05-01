package org.mingle.pear.mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mingle.pear.App;
import org.mingle.pear.domain.Account;
import org.mingle.pear.domain.mapper.AccountMapper;
import org.mingle.pear.util.Sex;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.atomic.LongAdder;

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

    @Test
    public void insert() {
        Random random = new Random();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            Account account = new Account();
            account.setAge(random.nextInt(100));
            String name = UUID.randomUUID().toString();
            account.setName(name);
            account.setSex(Sex.values()[random.nextInt(3)]);
            account.setVersion(1);
            account.setEmail(name + "@yeah.net");
            accountMapper.insertAccount(account);
        }

        System.out.println(System.currentTimeMillis() - start);
    }

    public static void main(String[] args) {
        String[] str = new String[]{
                "a", "b"
        };

        List<? extends Integer> list = new ArrayList<>();
        list.add(null);
        System.out.println(list.size());
        System.out.println(Arrays.toString(list.toArray()));

        Map<String, Long> map = new HashMap<>();
        map.forEach((key, value) -> {

        });

        LongAdder longAdder = new LongAdder();
        for (int i = 0; i < 10; i++)
            longAdder.increment();
        System.out.println(longAdder.sum());
    }
}
