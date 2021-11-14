package isen.objectconcept.gamemas.map;

import isen.objectconcept.gamemas.enums.EntityType;
import isen.objectconcept.gamemas.map.Cell;

import java.util.LinkedList;

public class ShortestPath {
    private static class SafeCell  {
        int x;
        int y;
        int dist;  	//distance
        SafeCell prev;  //parent cell in the path

        SafeCell(int x, int y, int dist, SafeCell prev) {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.prev = prev;
        }

        @Override
        public String toString(){
            return "(" + x + "," + y + ")";
        }
    }

    public static int[][] toBinary(Cell[][] matrix){
        int[][] binary = new int[matrix.length][matrix[0].length];

        for (Cell[] rows: matrix){
            for (Cell cols: rows){
                if (cols.getEntity().getType() != EntityType.EMPTY){
                    binary[cols.getX()][cols.getY()] = 0;
                }
                else{
                    binary[cols.getX()][cols.getY()] = 1;
                }
            }
        }
        return binary;
    }

    public static ShortestPoint shortestPath(Cell[][] matrix, Point start, Point end) {
        int sx = start.getX(), sy = start.getY();
        int dx = end.getX(), dy = end.getY();


        int[][] binary = toBinary(matrix);
        binary[sx][sy] = 1;

        //if Destination is 0(not empty), return
        if ( binary[dx][dy] == 0) {
            System.out.println("There is no path back home.");
            return null;
        }
        //initialize the cells
        int m = binary.length;
        int n = binary[0].length;
        SafeCell[][] cells = new SafeCell[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (binary[i][j] != 0) {
                    cells[i][j] = new SafeCell(i, j, Integer.MAX_VALUE, null);
                }
            }
        }

        //breadth first search
        LinkedList<SafeCell> queue = new LinkedList<>();
        SafeCell src = cells[sx][sy];
        src.dist = 0;
        queue.add(src);
        SafeCell dest = null;
        SafeCell p;
        while ((p = queue.poll()) != null) {
            //find destination
            if (p.x == dx && p.y == dy) {
                dest = p;
                break;
            }
            // moving up
            visit(cells, queue, p.x - 1, p.y, p);
            // moving down
            visit(cells, queue, p.x + 1, p.y, p);
            // moving left
            visit(cells, queue, p.x, p.y - 1, p);
            //moving right
            visit(cells, queue, p.x, p.y + 1, p);

            // moving up-left
            visit(cells, queue, p.x - 1, p.y - 1, p);
            // moving up-right
            visit(cells, queue, p.x - 1, p.y + 1, p);
            // moving down-left
            visit(cells, queue, p.x + 1, p.y - 1, p);
            //moving down-right
            visit(cells, queue, p.x + 1, p.y + 1, p);
        }

        //compose the path if path exists
        if (dest == null) {
            System.out.println("there is absolutely no path back home");
            return null;
        } else {
            LinkedList<SafeCell> path = new LinkedList<>();
            p = dest;
            do {
                path.addFirst(p);

            } while ((p = p.prev) != null);
            System.out.println(path);
            SafeCell stop;
            int num_of_steps;

            //Move 2 Steps
            if(path.size() >= 3){
                //index 0 is same as start point
                stop = path.get(2);
                num_of_steps = 2;
            }
            // Move 1 Step
            else {
                stop = path.get(path.size()- 1);
                num_of_steps = path.size() - 1;
            }
            return new ShortestPoint(stop.x, stop.y, num_of_steps) ;
        }
    }


    //function to update cell visiting status
    private static void visit(SafeCell[][] cells, LinkedList<SafeCell> queue, int x, int y, SafeCell parent) {
        //out of bounds
        if (x < 0 || x >= cells.length || y < 0 || y >= cells[0].length || cells[x][y] == null) {
            return;
        }
        //update distance, and previous node
        int dist = parent.dist + 1;
        SafeCell p = cells[x][y];
        if (dist < p.dist) {
            p.dist = dist;
            p.prev = parent;
            queue.add(p);
        }
    }

}


