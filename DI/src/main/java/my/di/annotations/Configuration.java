package my.di.annotations;

import java.lang.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: Hanna
 * Date: 11/10/13
 * Time: 6:39 PM
 * To change this template use File | Settings | File Templates.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {

    String value() default "";

}
