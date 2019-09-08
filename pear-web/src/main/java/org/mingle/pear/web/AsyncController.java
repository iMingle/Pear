/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mingle.pear.web;

import org.mingle.pear.dao.AccountDao;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author mingle
 */
@RequestMapping("/async")
@RestController
public class AsyncController {
    private static final Executor executor = Executors.newWorkStealingPool(2000);

    @Resource private AccountDao accountDao;

    @GetMapping(value = "/sync")
    public Long sync() throws InterruptedException {
        Thread.sleep(1000);

        try {
            accountDao.getById(1L);
        } catch (MyBatisSystemException e) {
            e.printStackTrace();
        }

        return 1L;
    }

    @GetMapping(value = "/sum")
    public Callable<Long> sum() {
        return () -> {
            Thread.sleep(1000);

            return 1L;
        };
    }

    @GetMapping(value = "/deferred")
    public DeferredResult<Long> deferred() {
        DeferredResult<Long> deferredResult = new DeferredResult<>();

        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // ignore
            }

            return 1L;
        }, executor).whenComplete((result, throwable) -> deferredResult.setResult(result));

        return deferredResult;
    }
}
