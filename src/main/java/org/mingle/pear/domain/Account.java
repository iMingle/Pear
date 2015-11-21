package org.mingle.pear.domain;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.activerecord.RooJpaActiveRecord;

import javax.validation.constraints.NotNull;

import lombok.Data;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public @Data class Account {

    @NotNull
    private String name;

    private int age;
}
