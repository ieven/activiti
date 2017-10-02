package org.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class TestListener implements ExecutionListener {

    private String msg;

    public String getMsg() {

        return msg;
    }

    public void setMsg(String msg) {

        this.msg = msg;
    }

    @Override
    public void notify(DelegateExecution execution) throws Exception {

        String eventName = execution.getEventName();
        System.out.println("执行了：" + eventName);
    }

}
