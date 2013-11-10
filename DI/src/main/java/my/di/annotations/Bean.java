package my.di.annotations;/*
* Bean.java
*
* Copyright (c) 2013 Teamnet. All Rights Reserved.
*
* This source file may not be copied, modified or redistributed,
* in whole or in part, in any form or for any reason, without the express
* written consent of Teamnet.
*/

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface Bean {

    String value() default "";

}
