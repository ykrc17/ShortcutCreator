package com.ykrc17.shortcutcreator.app.pickapp.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.ykrc17.shortcutcreator.R;

public class PickAppBinding {
  public RecyclerView rv_app_list;

  public PickAppBinding() {
  }

  public PickAppBinding(View view) {
    bind(view);
  }

  public void bind(View view) {
    rv_app_list = (RecyclerView) view.findViewById(R.id.rv_app_list);
  }

  public int getLayoutRes() {
    return R.layout.pick_app;
  }
}
