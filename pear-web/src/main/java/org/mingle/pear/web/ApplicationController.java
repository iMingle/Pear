/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@RequestMapping("/")
@Controller
@EnableAutoConfiguration
public class ApplicationController {

    @RequestMapping(value = {"/uncaughtException"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String uncaughtException() {
        return "errors/uncaughtException";
    }

    @RequestMapping(value = {"/resourceNotFound"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String resourceNotFound() {
        return "errors/resourceNotFound";
    }

    @RequestMapping(value = {"/dataAccessFailure"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String dataAccessFailure() {
        return "errors/dataAccessFailure";
    }
}
