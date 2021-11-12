package isen.objectconcept.gamemas;

import isen.objectconcept.gamemas.entities.*;
import isen.objectconcept.gamemas.entities.humanbeings.Elf;
import isen.objectconcept.gamemas.entities.humanbeings.Goblin;
import isen.objectconcept.gamemas.entities.humanbeings.Human;
import isen.objectconcept.gamemas.entities.humanbeings.Orc;

public class Main {

    public static void main(String[] args) {
        System.out.println("PROGRAM STARTED\n");

        Game game = new Game();

        game.start();

        System.out.println("\nPROGRAM ENDED");
    }
}
