package com.rdpaas.online.debugger.controller;

import com.rdpaas.online.debugger.bean.DebugInfo;
import com.rdpaas.online.debugger.core.Debugger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调试接口
 * @author rongdi
 * @date 2021/1/31
 */
@RestController
public class DebuggerController {

    @RequestMapping("/breakpoint")
    public DebugInfo breakpoint(@RequestParam String tag, @RequestParam String hostname, @RequestParam Integer port, @RequestParam String className, @RequestParam Integer lineNumber) throws Exception {
        Debugger debugger = Debugger.getInstance(tag,hostname,port);
        return debugger.markBpAndGetInfo(className,lineNumber);
    }

    @RequestMapping("/disconnect")
    public DebugInfo disconnect(@RequestParam String tag) throws Exception {
        Debugger debugger = Debugger.getInstance(tag);
        return debugger.disconnect();
    }
}
