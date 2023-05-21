package io.github.ichocomilk.lightshop.commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.ichocomilk.lightshop.commands.PrincipalCommand;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SubCommand {

    int argsNeed() default 1;
    String argsError() default "Error executing this command";
    String identifier();
    Class<? extends PrincipalCommand> principalCommand();
}