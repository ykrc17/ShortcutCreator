package com.ykrc17.shortcutcreator.app.layout;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ykrc17.shortcutcreator.R;

public class ActivityMainBinding {
  public ImageView iv_target_icon;

  public TextView tv_target_label;

  public Button btn_choose_target;

  public Button btn_create;

  public ActivityMainBinding() {
  }

  public ActivityMainBinding(View view) {
    bind(view);
  }

  public void bind(View view) {
    iv_target_icon = (ImageView) view.findViewById(R.id.iv_target_icon);
    tv_target_label = (TextView) view.findViewById(R.id.tv_target_label);
    btn_choose_target = (Button) view.findViewById(R.id.btn_choose_target);
    btn_create = (Button) view.findViewById(R.id.btn_create);
  }

  public int getLayoutRes() {
    return R.layout.main;
  }
}
