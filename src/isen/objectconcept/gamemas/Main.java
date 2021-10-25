package isen.objectconcept.gamemas;

import isen.objectconcept.gamemas.entities.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("PROGRAM STARTED\n");

        Game game = new Game(20);

        Obstacle obstacle = new Obstacle();
        Elf elf = new Elf();
        Goblin goblin = new Goblin();
        Human human = new Human();
        Orc orc = new Orc();

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
