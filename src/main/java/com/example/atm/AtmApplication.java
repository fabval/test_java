

package com.example.atm;

import com.example.atm.controller.AtmController;

public class AtmApplication {

    public static void main(String[] args) {
        AtmController atmController = new AtmController();
        atmController.startApplication();
    }
}