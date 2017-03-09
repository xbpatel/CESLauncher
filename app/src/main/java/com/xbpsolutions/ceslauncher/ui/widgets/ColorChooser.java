package com.xbpsolutions.ceslauncher.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.xbpsolutions.ceslauncher.R;

/**
 * Created by dhruvil on 09-03-2017.
 */

public class ColorChooser extends HorizontalScrollView {

  Context context;
  LinearLayout.LayoutParams params;
  private LinearLayout linearLayout;
  LayoutInflater inflater;
  String[] colors;

  public void setClickListner(
      OnBoxClickListner clickListner) {
    this.clickListner = clickListner;
  }

  private OnBoxClickListner clickListner;

  public ColorChooser(Context context) {
    super(context);
    this.context = context;
    init();
  }

  public ColorChooser(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    init();
  }

  private void init() {
    colors = getResources().getStringArray(R.array.mdcolor_900);
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
    linearLayout = new LinearLayout(context);
    linearLayout.setGravity(Gravity.LEFT);
    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
    addView(linearLayout, params);
  }

  public void setup() {

    linearLayout.removeAllViews();
    for (int i = 0; i < colors.length; i++) {
      ColorBox box = new ColorBox(context, colors[i]);
      box.setup();
      box.setOnClickListener(boxClickListner);
      linearLayout.addView(box, params);
    }

  }

  private View.OnClickListener boxClickListner = new OnClickListener() {
    @Override
    public void onClick(View view) {

      int pos = linearLayout.indexOfChild((ColorBox) view);
      try {
        clickListner.onBoxClicked(colors[pos]);
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
  };

  public interface OnBoxClickListner {
    void onBoxClicked(String selectedColor);
  }


}
