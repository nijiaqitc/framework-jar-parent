package com.njq.common.util.grab;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

public class HtmlDecodeUtil {

    public static String decodeHtml(String html, String fileJs, String fun) throws Exception{
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        // 执行指定脚本
        FileReader reader = new FileReader(fileJs);
        engine.eval(reader);
        if (engine instanceof Invocable) {
            // 调用merge方法，并传入两个参数
            Invocable invoke = (Invocable) engine;
            // 调用了js的fun方法
            String c = (String) invoke.invokeFunction(fun, html);
            return c;
        }
        return "";
    }
}
