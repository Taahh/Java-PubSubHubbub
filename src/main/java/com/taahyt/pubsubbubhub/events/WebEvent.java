package com.taahyt.pubsubbubhub.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WebEvent
{

    /**
     * Just slap this on a method if you want it to be included when an event is called
     */

}
