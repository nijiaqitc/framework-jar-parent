package com.njq.common.util.grab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

public class HtmlDecodeUtil {
    private static final Logger logger = LoggerFactory.getLogger(HtmlDecodeUtil.class);

    public static String decodeHtml(String html, String fileJs, String fun) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        FileReader reader = null;
        try {
            //读取文件
            reader = new FileReader(fileJs);
            // 执行指定脚本
            engine.eval(reader);
            if (engine instanceof Invocable) {
                // 调用merge方法，并传入两个参数
                Invocable invoke = (Invocable) engine;
                // 调用了js的fun方法
                String c = (String) invoke.invokeFunction(fun, html);
                return c;
            }
        } catch (Exception e) {
            logger.error("执行js出错", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                logger.error("关闭流出错", e);
            }
        }
        return "";
    }
}
