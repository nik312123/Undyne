package customAttackMaker;

import defense.Runner;

import java.awt.*;



public class DropDownButton {

    private  int status;
    private  int index;
    private  Rectangle position = new Rectangle();

     DropDownButton(int index){
        this.index = index;
        status = 1;

    }

    public void drawImage(Graphics g, int x, int y){
        if(status == 1) {
            g.drawImage(Runner.droppedDown, x, y, null);
            position.setBounds(x,y,6,5);
        }
        else if (status == 0){
            g.drawImage(Runner.droppedClosed,x,y,null);
            position.setBounds(x,y,5,6);

        }
    }

    public int getIndex(){
         return index;
    }

    public Rectangle getPosition(){
        return position;
    }

    public String toString(){
        return index+"";
    }

    public void setIndex(int in){
         index = in;
    }

    public int getStatus(){
        return status;
    }

    public void switchStatus(){
        if(status == 1)
            status = 0;
        else if(status == 0)
            status = 1;
    }
}
