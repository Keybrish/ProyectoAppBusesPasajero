// Generated by view binder compiler. Do not edit!
package dev.android.appbuses.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import dev.android.appbuses.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityHistoryQractivityBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView btnBack;

  @NonNull
  public final ImageView imgQrCode;

  @NonNull
  public final TextView textView30;

  @NonNull
  public final TextView textView6;

  private ActivityHistoryQractivityBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView btnBack, @NonNull ImageView imgQrCode, @NonNull TextView textView30,
      @NonNull TextView textView6) {
    this.rootView = rootView;
    this.btnBack = btnBack;
    this.imgQrCode = imgQrCode;
    this.textView30 = textView30;
    this.textView6 = textView6;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityHistoryQractivityBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityHistoryQractivityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_history_qractivity, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityHistoryQractivityBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnBack;
      ImageView btnBack = ViewBindings.findChildViewById(rootView, id);
      if (btnBack == null) {
        break missingId;
      }

      id = R.id.imgQrCode;
      ImageView imgQrCode = ViewBindings.findChildViewById(rootView, id);
      if (imgQrCode == null) {
        break missingId;
      }

      id = R.id.textView30;
      TextView textView30 = ViewBindings.findChildViewById(rootView, id);
      if (textView30 == null) {
        break missingId;
      }

      id = R.id.textView6;
      TextView textView6 = ViewBindings.findChildViewById(rootView, id);
      if (textView6 == null) {
        break missingId;
      }

      return new ActivityHistoryQractivityBinding((ConstraintLayout) rootView, btnBack, imgQrCode,
          textView30, textView6);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}