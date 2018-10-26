package com.ykrc17.shortcutcreator.app.layout;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.ykrc17.shortcutcreator.R;

public class MainBinding {
  public ImageView iv_target_icon;

  public TextView tv_target_label;

  public Button btn_choose_target;

  public EditText et_shortcut_label;

  public Button btn_choose_icon;

  public Button btn_create;

  public MainBinding() {
  }

  public MainBinding(View view) {
    bind(view);
  }

  public void bind(View view) {
    iv_target_icon = (ImageView) view.findViewById(R.id.iv_target_icon);
    tv_target_label = (TextView) view.findViewById(R.id.tv_target_label);
    btn_choose_target = (Button) view.findViewById(R.id.btn_choose_target);
    et_shortcut_label = (EditText) view.findViewById(R.id.et_shortcut_label);
    btn_choose_icon = (Button) view.findViewById(R.id.btn_choose_icon);
    btn_create = (Button) view.findViewById(R.id.btn_create);
  }

  public int getLayoutRes() {
    return R.layout.main;
  }
}
