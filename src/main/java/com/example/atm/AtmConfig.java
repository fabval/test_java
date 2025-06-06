

package com.example.atm;

public class AtmConfig {
    private static final int MIN_PIN_CODE_LENGTH = 4;
    private static final int MAX_PIN_CODE_LENGTH = 6;
    private static final double MIN_INITIAL_BALANCE = 0.0;
    private static final double MAX_INITIAL_BALANCE = 100000.0;

    private int pinCodeLength;
    private double initialBalance;

    public AtmConfig(int pinCodeLength, double initialBalance) {
        if (pinCodeLength < MIN_PIN_CODE_LENGTH || pinCodeLength > MAX_PIN_CODE_LENGTH) {
            throw new IllegalArgumentException("Invalid PIN code length. It should be between " + MIN_PIN_CODE_LENGTH + " and " + MAX_PIN_CODE_LENGTH);
        }
        if (initialBalance < MIN_INITIAL_BALANCE || initialBalance > MAX_INITIAL_BALANCE) {
            throw new IllegalArgumentException("Invalid initial balance. It should be between " + MIN_INITIAL_BALANCE + " and " + MAX_INITIAL_BALANCE);
        }
        this.pinCodeLength = pinCodeLength;
        this.initialBalance = initialBalance;
    }

    public int getPinCodeLength() {
        return pinCodeLength;
    }

    public double getInitialBalance() {
        return initialBalance;
    }
}