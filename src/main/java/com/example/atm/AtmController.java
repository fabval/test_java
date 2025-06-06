

package com.example.atm;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AtmController {

    @PostMapping("/authenticate")
    public String authenticate(@RequestParam("pin") String pin, @RequestParam("initialBalance") double initialBalance) {
        if (isValidPin(pin) && isValidBalance(initialBalance)) {
            try {
                AtmService atmService = new AtmService();
                return atmService.run();
            } catch (Exception e) {
                return "Error occurred while processing the request: " + e.getMessage();
            }
        } else {
            return "Invalid PIN or initial balance";
        }
    }

    private boolean isValidPin(String pin) {
        return pin != null && pin.length() == 4 && pin.matches("\\d+");
    }

    private boolean isValidBalance(double initialBalance) {
        return initialBalance >= 0;
    }

    @PostMapping("/processCommand")
    public String processCommand(@RequestParam("command") String command, @RequestParam("amount") double amount) {
        try {
            AtmService atmService = new AtmService();
            return atmService.processCommand(command, amount);
        } catch (Exception e) {
            return "Error occurred while processing the command: " + e.getMessage();
        }
    }
}