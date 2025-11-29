package com.jomeerkatz.springboot_backend_cc.mappers;

public interface Mapper <A, B> {

    B mapTo(A a);

    A mapFrom(B b);
}
