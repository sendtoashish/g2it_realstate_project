package com.g2it.realestate.utils;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Resources using this annotation will be protected.
 * It means that anyone can access that resource need
 * to be signed in.
 */

@Retention(RUNTIME)
@Target(METHOD)
public @interface Secured {}
