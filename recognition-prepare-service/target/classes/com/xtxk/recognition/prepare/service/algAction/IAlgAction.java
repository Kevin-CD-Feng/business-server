package com.xtxk.recognition.prepare.service.algAction;

public interface IAlgAction {
    default void start() {}
    default void stop(){}

    boolean Test(String resourceId);
}
