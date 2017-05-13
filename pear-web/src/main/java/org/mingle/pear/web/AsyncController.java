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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * @author mingle
 */
@RequestMapping("/async")
@RestController
public class AsyncController {
    private static final int MAX = Integer.MAX_VALUE;

    @RequestMapping(value = "/sum", method = RequestMethod.GET)
    public Callable<Long> sum() {
        return () -> {
            long sum = 0;

            Thread.sleep(5000);

            for (int i = 0; i < MAX; i++)
                sum += i;

            return sum;
        };
    }

    public static void main(String[] args) throws InterruptedException {
        long sum = 0;

        Thread.sleep(5000);

        for (int i = 0; i < MAX; i++)
            sum += i;

        System.out.println(sum);
    }
}
