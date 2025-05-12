/*
What to do when you see fox:
- In for loop, set up some things to see if it can and cant move and what it should do
    - Check what happens in the Fox code
    - !!!Everytime the fox comes from SE the rabbit gets stuck if there is a wall to its right or left
*/

public class Rabbit extends Animal {
    public Rabbit(Model model, int row, int column) {
        super(model, row, column);
    }

    int decideMove() {
        for (int i = Model.MIN_DIRECTION; i <= Model.MAX_DIRECTION; i++) {
            if(look(i) == Model.FOX) {
                if(canMove(Model.turn(i, 5))){ //Move SE if fox seen
                    return Model.turn(i, 5);}
                else if (canMove(Model.turn(i, -5))) {//If can't move SE, move SW
                    return Model.turn(i, -5);
                } else if (canMove(Model.turn(i, 2))) {//If cant move prior move E
                    return Model.turn(i, 2);}
                else if (canMove(Model.turn(i, -2))) {//If cant, then move W
                    return Model.turn(i, -2);
                } else if (canMove(Model.turn(i, 7))) {//Move NW
                    return Model.turn(i, 7);}
                else if (canMove(Model.turn(i, -7))) {//Move NE if cant move NW
                    return Model.turn(i, -7);
                }
        }
            }
        return Model.STAY;}}//If Fox is not seen, Stay still





