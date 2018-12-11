package com.njq.common.util.grab;

import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class HtmlDecodeUtil {

	public static String decodeHtml(String html) {
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			String jsFileName = "D:\\worksts\\ppcong\\customClearStyle.js";
			FileReader reader = new FileReader(jsFileName); // 执行指定脚本
			engine.eval(reader);
			if (engine instanceof Invocable) {
				Invocable invoke = (Invocable) engine; // 调用merge方法，并传入两个参数
				String c = (String) invoke.invokeFunction("decodeStr",html); // 调用了js的aa方法
				return c;
			}
			return "";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
	}
}
