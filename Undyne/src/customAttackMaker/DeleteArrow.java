package customAttackMaker;

import defense.Runner;

import java.awt.*;

public class DeleteArrow {

    Rectangle position = new Rectangle();
    int attackNum;
    int arrownum;

    DeleteArrow(int attackNum, int arrownum){
        this.attackNum = attackNum;
        this.arrownum = arrownum;

    }

    @Override
    public String toString() {
        return "DeleteArrow{" +
                "attackNum=" + attackNum +
                ", arrownum=" + arrownum +
                '}';
    }

    public void drawImage(Graphics g, int x, int y){
        g.drawImage(Runner.deleteArrow,x,y,null);
        position.setBounds(x,y,19,17);
    }

    public Rectangle getPosition() {
        return position;
    }

    public int getAttackNum() {
        return attackNum;
    }

    public int getArrownum(){
        return arrownum;
    }

    public void setArrownum(int arrownum) {
        this.arrownum = arrownum;
    }

    public void setAttackNum(int attackNum) {
        this.attackNum = attackNum;
    }
}
