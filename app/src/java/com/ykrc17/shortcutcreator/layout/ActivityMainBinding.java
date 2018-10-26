package com.ykrc17.shortcutcreator.layout;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ykrc17.shortcutcreator.R;

public class ActivityMainBinding {
  public TextView tv_choose_app;

  public Button btn_choose_app;

  public Button btn_create;

  public ActivityMainBinding() {
  }

  public ActivityMainBinding(View view) {
    bind(view);
  }

  public void bind(View view) {
    tv_choose_app = (TextView) view.findViewById(R.id.tv_choose_app);
    btn_choose_app = (Button) view.findViewById(R.id.btn_choose_app);
    btn_create = (Button) view.findViewById(R.id.btn_create);
  }

  public int getLayoutRes() {
    return R.layout.activity_main;
  }
}
