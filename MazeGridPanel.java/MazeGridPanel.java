package newpackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Stack;
import javax.swing.JPanel;
/*Pseudocode
Dfs Traversing conditions: No walls, within boundaries, and you havent visited it prior)
Using DFS(With the above conditions), If you can move down, move down
	If else move right, if else move left, if else move up.
	If you cant move in any new direction(else), move back the way you came
		To move back the way you came
			Rechange the color to a
			Remove that last move from the stack


Set a condition for when it reaches the end
Start visited off where the start is at

Push the row,col position that you have traveled to already onto the stack and color it

TODO:
Mark start and Mark end
Make sure the start and end colors arent changed to the current
 */
public class MazeGridPanel extends JPanel{
    private int rows;
    private int cols;
    private Cell[][] maze;

    // extra credit
    public void genDFSMaze() {
        boolean[][] visited;
        Stack<Cell> stack  = new Stack<Cell>();
        Cell start = maze[0][0];
        stack.push(start);
    }

    //homework
    public void solveMaze() {
        Stack<Cell> stack  = new Stack<Cell>();
        Cell start = maze[0][0];
        start.setBackground(Color.GREEN);
        Cell finish = maze[rows-1][cols-1];
        finish.setBackground(Color.RED);
        stack.push(start);

        visited (0,0);//Start visited where start is
        while(!stack.isEmpty() && !stack.peek().equals(finish)) {//Go while its not at the finishline or at empt
            Cell current = stack.peek(); //Value at the top of the staack will be where the current cell is at
            boolean moved = false;//To keep track of where you moved
            if(current == finish){ //Exit a finished maze completed
                return;
            }

            if(current != start && current!=finish){ //stop start and end from being changed
                current.setBackground(Color.YELLOW);
            }
            int r = current.row;
            int c = current.col;
            //Cases to add
            //If within bounds of rows and columns
            // If not visited,
            // If it isn't facing some sort of wall or barrier in direction its going
            //Push the current row and col onto the stack
            //Mark that same current position as visited


            if(r+1 < rows && !visited(r+1,c) && !current.southWall){ //Going down with methods
                stack.push(maze[r+1][c]);
                visited(r+1, c);
                moved = true;
            }else if(c+1 < cols && !visited(r, c+1) && !current.eastWall){//Go to the right
                stack.push(maze[r][c+1]);
                visited(r, c+1);
                moved = true;
            }else if(c-1 >= cols && !visited(r, c-1) && !current.westWall){//Going Left
                stack.push(maze[r][c-1]);
                visited(r, c-1);
                moved = true;
            }else if(r-1 >= rows && !visited(r-1, c) && !current.northWall){ //Go up
                stack.push(maze[r-1][c]);
                visited(r-1, c);
                moved = true;
            }//Go backward if you have no where else you can go
            if(!moved){
                current.setBackground(Color.GRAY);
                stack.pop();
            }
        }

    }
    public boolean visited(int row, int col) {
        Cell c = maze[row][col];
        Color status = c.getBackground();
        if(status.equals(Color.WHITE)  || status.equals(Color.RED)  ) {
            return false;
        }
        return true;
    }
    public void genNWMaze() {

        for(int row = 0; row  < rows; row++) {
            for(int col = 0; col < cols; col++) {

                if(row == 0 && col ==0) {
                    continue;
                }

                else if(row ==0) {
                    maze[row][col].westWall = false;
                    maze[row][col-1].eastWall = false;
                } else if(col == 0) {
                    maze[row][col].northWall = false;
                    maze[row-1][col].southWall = false;
                }else {
                    boolean north = Math.random()  < 0.5;
                    if(north ) {
                        maze[row][col].northWall = false;
                        maze[row-1][col].southWall = false;
                    } else {  // remove west
                        maze[row][col].westWall = false;
                        maze[row][col-1].eastWall = false;
                    }
                    maze[row][col].repaint();
                }
            }
        }
        this.repaint();
    }
    public MazeGridPanel(int rows, int cols) {
        this.setPreferredSize(new Dimension(800,800));
        this.rows = rows;
        this.cols = cols;
        this.setLayout(new GridLayout(rows,cols));
        this.maze =  new Cell[rows][cols];
        for(int row = 0 ; row  < rows ; row++) {
            for(int col = 0; col < cols; col++) {

                maze[row][col] = new Cell(row,col);

                this.add(maze[row][col]);
            }

        }
        this.genNWMaze();
        this.solveMaze();

    }


}
