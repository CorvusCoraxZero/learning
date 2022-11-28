package test.zero.utils;

import cn.hutool.core.io.FileUtil;

import javax.script.*;
import java.lang.reflect.Method;
import java.util.List;

public class loadUtil {

    public static Object execScript(String path, String method, Object... args){
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("ECMAScript");
        try {
            engine.eval(FileUtil.readUtf8String(path));
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        Invocable invocable = (Invocable)engine;
        try {
            return invocable.invokeFunction(method, args);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        // String data = "{\"task_id\":1.0,\"asset_id\":53.0,\"asset\":{\"id\":53,\"assetName\":\"xxxx_WEB\",\"tag\":[\"NGINX\"]},\"vulnerabilitys\":[{\"level\":\"1\",\"module\":0.0,\"policyNo\":\"DirectoryList\",\"taskIds\":[1],\"url\":\"http://192.168.23.177/images/\",\"lastFoundTime\":1668138220109,\"result\":\"eJy9VGtv4jgU/Y7Ef7ibD6uZ0WDHCeENK4bQx5SXBrrVarWqTGISD8HOOIaW/vp1SDulo5bVfqkQwj4+9r3nnss9Hy4A8w2NWIbhYrGYYYJIuXQhM90C0nQQqTWQ4yJSr5dL1xlTlX7EhDkbyweeJBR7yIYPN1yE8i6DyQJqiLThZnpTq7ZhoXhoyLiO7DbMR4OB0wY0MREHo2/gIBt5dt2pH2Eu8pBrsOYLzD7Gqsge/FwN26B2QAiyP0LC1wzOWbCW5VI/CFhqkvyEPz1tKkMRyJCLqAXRA08/h2yVUM3KpYGUa85aMLuYzYfz+aXfTZXi9fVqu9Q/AuEl36PlMvoeyJoi7a8HynRiWHaTkOZZv+6a5BrEqfke6X+pu7ZXa7oeqfntcqn4PFUVHNuG6VW55Ju4LVjE289guzCRO3PiOECcFnFbtgPn40W5NGdqx1QL+ikNYoYdZKyowofr5Vbo7UfYyPB2I4U0B1Xk5skbJ1zkVMj2wDCgDXdcxzDfxjLjojKjOoghVfJ+fxvrTYLzwpLDQ+lex1IYwPgMs2LjoBryDqdZljyFn6ZMzOcjbKMmaqxhFm8zLsXtjGYZExFTuJqb1SgeZSq/Z6MqzPLlzkPEznvrT6r2RtdLV3IfhDbN8otPz/jIRNBxC6oN9xlc7FNTSs3uNc41tYOYqozp7vXirNLIi9/5zZ8OFn/Nhqa5xyOYXX8ZXQ7AqmB84w4w9hd+cWBKB2dc0ATj4cTqlTr5c70SdGJGQ/MLHc11wnqXImT3IFdPf5oOLnDDxI/UzlKG+/wF8grbgKWOpktzpaOV+ca9Dt9EkKmga2EeSJHhZULFGkV8ZQFNdNf6+3Iw/cfqmVBxcYFCrNiqa/0x6E7a065v9SZ0wzqYvs4ZG07f6o1opnNn+Iqz8E3yvCDP+cPbD/oFx2dZoHiqTQs8U/FB1UEZBDLJUiq6lmeyj9URo1RQQtjRhEeGoWVqvVIJGrwohH/5rShEmF9+zglbvRlVpiHA54oFWqr9Y0YF8XexzNL20xYeYyoexdrqAVTgVeb/SPRgr/PCs/H5q6leCbm87YchSkVk9Y53xxn/kqJNKmO6rzg2MVPEzIg6wBvMKnKu3luMzv+KpgmOJR1jJ4SRWqWfqkIY8VreKWHeOwrbUBFyKnQh6Xh3Skyj8nUrcjEeOOSUGIIa7yhG3tEsLZT8XJ6Q4ThHntRaVfdNGVC3/1PG6SmAi1FoFoeh2cHF2M0n97/qypyb\",\"issueStatus\":0,\"createTime\":1.66750761E12,\"assetId\":53,\"proof\":\"http://192.168.23.177/images/\",\"taskId\":100.0,\"assetUrl\":\"http://192.168.23.177:8891/\"}],\"library\":[{\"merge\":false,\"id\":40833,\"type\":\"其他\",\"issueSolution\":\"将您的服务器配置X-XSS-Protection头。\\n    X-XSS-Protection值为：\\n    0 禁止XSS过滤。\\n    1 启用XSS过滤（通常浏览器是默认的）。 如果检测到跨站脚本攻击，浏览器将清除页面（删除不安全的部分）。\\n    1;mode=block  启用XSS过滤。 如果检测到攻击，浏览器将不会清除页面，而是阻止页面加载。\\n    1; report=<reporting-URI>  (Chromium only)  启用XSS过滤。 如果检测到跨站脚本攻击，浏览器将清除页面并使用CSP report-uri指令的功能发送违规报告。\",\"issueDescription\":\"HTTP X-XSS-Protection 响应头是 Internet Explorer，Chrome 和 Safari 的一个特性，当检测到跨站脚本攻击 (XSS)时，浏览器将停止加载页面。若网站设置了良好的 Content-Security-Policy 来禁用内联 JavaScript ('unsafe-inline')，现代浏览器不太需要这些保护， 但其仍然可以为尚不支持 CSP 的旧版浏览器的用户提供保护。\",\"issueLevel\":1,\"info\":[{\"pluginId\":\"NoXXSSProtection\",\"scannerTypeId\":2,\"issueSolution\":\"将您的服务器配置X-XSS-Protection头。\\n    X-XSS-Protection值为：\\n    0 禁止XSS过滤。\\n    1 启用XSS过滤（通常浏览器是默认的）。 如果检测到跨站脚本攻击，浏览器将清除页面（删除不安全的部分）。\\n    1;mode=block  启用XSS过滤。 如果检测到攻击，浏览器将不会清除页面，而是阻止页面加载。\\n    1; report=<reporting-URI>  (Chromium only)  启用XSS过滤。 如果检测到跨站脚本攻击，浏览器将清除页面并使用CSP report-uri指令的功能发送违规报告。\",\"issueDescription\":\"HTTP X-XSS-Protection 响应头是 Internet Explorer，Chrome 和 Safari 的一个特性，当检测到跨站脚本攻击 (XSS)时，浏览器将停止加载页面。若网站设置了良好的 Content-Security-Policy 来禁用内联 JavaScript ('unsafe-inline')，现代浏览器不太需要这些保护， 但其仍然可以为尚不支持 CSP 的旧版浏览器的用户提供保护。\",\"issueLevel\":1}]}]}\n";
        String result = (String)execScript("/Users/guojiaxin/Desktop/JavaScript/generateAsset.js", "test");
        System.out.println(result);
    }

}
