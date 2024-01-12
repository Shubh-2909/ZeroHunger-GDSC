// Generated by view binder compiler. Do not edit!
package com.example.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.app.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityViewlistBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final GridView gridView;

  private ActivityViewlistBinding(@NonNull RelativeLayout rootView, @NonNull GridView gridView) {
    this.rootView = rootView;
    this.gridView = gridView;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityViewlistBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityViewlistBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_viewlist, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityViewlistBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.gridView;
      GridView gridView = ViewBindings.findChildViewById(rootView, id);
      if (gridView == null) {
        break missingId;
      }

      return new ActivityViewlistBinding((RelativeLayout) rootView, gridView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
