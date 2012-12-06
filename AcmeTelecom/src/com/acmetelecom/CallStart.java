package com.acmetelecom;

public class CallStart extends CallEvent {
    public CallStart(Caller caller, Callee callee) {
        super(caller, callee, System.currentTimeMillis());
    }
}
