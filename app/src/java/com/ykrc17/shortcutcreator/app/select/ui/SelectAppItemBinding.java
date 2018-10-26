package com.ykrc17.shortcutcreator.app.select.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ykrc17.shortcutcreator.R;

public class SelectAppItemBinding {
  public ImageView iv_icon;

  public TextView tv_label;

  public TextView tv_package_name;

  public SelectAppItemBinding() {
  }

  public SelectAppItemBinding(View view) {
    bind(view);
  }

  public void bind(View view) {
    iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
    tv_label = (TextView) view.findViewById(R.id.tv_label);
    tv_package_name = (TextView) view.findViewById(R.id.tv_package_name);
  }

  public int getLayoutRes() {
    return R.layout.select_app_item;
  }
}
