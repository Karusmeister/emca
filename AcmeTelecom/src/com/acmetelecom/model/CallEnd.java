package com.acmetelecom.model;

public class CallEnd extends CallEvent {
    public CallEnd(Caller caller, Callee callee) {
        super(caller, callee, System.currentTimeMillis());
    }
}
