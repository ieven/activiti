package org.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.el.FixedValue;

public class TestListener implements ExecutionListener {

    private FixedValue msg;

    public FixedValue getMsg() {

        return msg;
    }

    public void setMsg(FixedValue msg) {

        this.msg = msg;
    }

    @Override
    public void notify(DelegateExecution execution) throws Exception {

        String eventName = execution.getEventName();
        System.out.println("执行了：" + eventName);
    }

}
