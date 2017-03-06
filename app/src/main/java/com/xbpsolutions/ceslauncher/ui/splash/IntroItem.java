package com.xbpsolutions.ceslauncher.ui.splash;

import java.util.ArrayList;



public class IntroItem {

  public int color;
  public String title;
  public ArrayList<Bullet> bullets;

  public ArrayList<Bullet> getBullets() {
    if (bullets == null) {
      bullets = new ArrayList<>();
    }
    return bullets;
  }

}
