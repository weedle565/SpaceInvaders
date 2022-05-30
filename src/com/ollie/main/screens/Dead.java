package com.ollie.main.screens;

import java.awt.*;

public class Dead {

    public static void drawDead(Graphics g){

        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 100));
        g.drawString("You died!", 320, 360);

    }

}
