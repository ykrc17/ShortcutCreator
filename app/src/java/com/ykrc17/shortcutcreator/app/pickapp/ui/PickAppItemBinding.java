package com.ykrc17.shortcutcreator.app.pickapp.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ykrc17.shortcutcreator.R;

public class PickAppItemBinding {
  public ImageView iv_icon;

  public TextView tv_label;

  public TextView tv_package_name;

  public PickAppItemBinding() {
  }

  public PickAppItemBinding(View view) {
    bind(view);
  }

  public void bind(View view) {
    iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
    tv_label = (TextView) view.findViewById(R.id.tv_label);
    tv_package_name = (TextView) view.findViewById(R.id.tv_package_name);
  }

  public int getLayoutRes() {
    return R.layout.pick_app_item;
  }
}
