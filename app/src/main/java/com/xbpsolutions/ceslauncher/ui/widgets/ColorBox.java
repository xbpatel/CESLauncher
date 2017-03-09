package com.xbpsolutions.ceslauncher.ui.widgets;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.xbpsolutions.ceslauncher.R;

/**
 * Created by dhruvil on 09-03-2017.
 */

public class ColorBox extends FrameLayout {

  LayoutInflater inflater;
  Context context;
  private ImageView imgBox;
  private View view;


  public String getCurrentColor() {
    return currentColor;
  }

  public void setCurrentColor(String currentColor) {
    this.currentColor = currentColor;

  }

  private String currentColor = "#000000";

  public ColorBox(@NonNull Context context, String color) {
    super(context);
    this.context = context;
    this.currentColor = color;
    init();
  }

  public ColorBox(@NonNull Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    init();
  }

  private void init() {
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    view = inflater.inflate(R.layout.layout_colorbox, this);

  }

  public void setup() {
    imgBox = (ImageView) view.findViewById(R.id.imgBox);
    imgBox.setBackgroundColor(Color.parseColor(currentColor));
  }


}
