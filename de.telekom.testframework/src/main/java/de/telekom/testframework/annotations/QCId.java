package de.telekom.testframework.annotations;

import static de.telekom.testframework.annotations.QCState.Unknown;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Target;

@Retention(SOURCE)
@Documented
@Target(TYPE)
public @interface QCId {

    String value();

    QCState state() default Unknown;
}
