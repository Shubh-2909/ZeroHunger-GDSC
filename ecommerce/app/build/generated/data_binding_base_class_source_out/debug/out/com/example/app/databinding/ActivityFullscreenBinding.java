// Generated by view binder compiler. Do not edit!
package com.example.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.app.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityFullscreenBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final Button dummyButton;

  @NonNull
  public final TextView fullscreenContent;

  @NonNull
  public final LinearLayout fullscreenContentControls;

  private ActivityFullscreenBinding(@NonNull FrameLayout rootView, @NonNull Button dummyButton,
      @NonNull TextView fullscreenContent, @NonNull LinearLayout fullscreenContentControls) {
    this.rootView = rootView;
    this.dummyButton = dummyButton;
    this.fullscreenContent = fullscreenContent;
    this.fullscreenContentControls = fullscreenContentControls;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityFullscreenBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityFullscreenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_fullscreen, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityFullscreenBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.dummy_button;
      Button dummyButton = ViewBindings.findChildViewById(rootView, id);
      if (dummyButton == null) {
        break missingId;
      }

      id = R.id.fullscreen_content;
      TextView fullscreenContent = ViewBindings.findChildViewById(rootView, id);
      if (fullscreenContent == null) {
        break missingId;
      }

      id = R.id.fullscreen_content_controls;
      LinearLayout fullscreenContentControls = ViewBindings.findChildViewById(rootView, id);
      if (fullscreenContentControls == null) {
        break missingId;
      }

      return new ActivityFullscreenBinding((FrameLayout) rootView, dummyButton, fullscreenContent,
          fullscreenContentControls);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
