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

        Obstacle obstacle = new Obstacle();
        Elf elf = new Elf("E");
        Goblin goblin = new Goblin("G");
        Human human = new Human("H");
        Orc orc = new Orc("O");

        System.out.println("Obstacle type: "+ obstacle.getType());
        System.out.println("Elf type: "+ elf.getType());
        System.out.println("Goblin type: "+ goblin.getType());
        System.out.println("Human type: "+ human.getType());
        System.out.println("Orc type: "+ orc.getType());

        game.getMap().printCellsType();
        game.getMap().print();

        System.out.println("\nPROGRAM ENDED");
    }
}
