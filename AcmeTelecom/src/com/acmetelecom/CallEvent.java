package com.acmetelecom;

public  class CallEvent {
    private Caller caller;
    private Callee callee;
    private long time;

    public CallEvent(Caller caller, Callee callee, long timeStamp) {
        this.caller = caller;
        this.callee = callee;
        this.time = timeStamp;
    }

    public Caller getCaller() {
        return caller;
    }

    public Callee getCallee() {
        return callee;
    }

    public long time() {
        return time;
    }
}
