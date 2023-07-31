package com.sword.framework.graaljs;

import org.graalvm.polyglot.Context;

/**
 * GraalJs上下文池
 * Created by shujian.ou 2023/5/25 12:46
 */
public class ContextPool {

    private static final ThreadLocal<Context> contexts = ThreadLocal.withInitial(() -> Context.newBuilder("js").build());

    public static Context acquire() {
        return contexts.get();
    }

}
