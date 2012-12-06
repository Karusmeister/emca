package com.acmetelecom;

import com.acmetelecom.model.Callee;
import com.acmetelecom.model.Caller;

public class Runner {
public static void main(String[] args) throws Exception {
System.out.println("Running...");
BillingSystem billingSystem = new BillingSystem();
billingSystem.callInitiated(new Caller("447722113434"), new Callee("447766511332"));
sleepSeconds(35);
billingSystem.callCompleted(new Caller("447722113434"), new Callee("447766511332"));
billingSystem.callInitiated(new Caller("447722113434"), new Callee("447711111111"));
sleepSeconds(30);
billingSystem.callCompleted(new Caller("447722113434"), new Callee("447711111111"));
billingSystem.callInitiated(new Caller("447777765432"), new Callee("447711111111"));
sleepSeconds(60);
billingSystem.callCompleted(new Caller("447777765432"), new Callee("447711111111"));
billingSystem.createCustomerBills();
}
private static void sleepSeconds(int n) throws InterruptedException {
Thread.sleep(n * 1000);
}
}