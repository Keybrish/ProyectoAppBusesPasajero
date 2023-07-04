// Generated by view binder compiler. Do not edit!
package dev.android.appbuses.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import dev.android.appbuses.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemHistoryBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final ImageView btnEditInfo;

  @NonNull
  public final CardView btnFrequency;

  @NonNull
  public final TextView textView13;

  @NonNull
  public final TextView textView38;

  @NonNull
  public final TextView textView39;

  private ItemHistoryBinding(@NonNull CardView rootView, @NonNull ImageView btnEditInfo,
      @NonNull CardView btnFrequency, @NonNull TextView textView13, @NonNull TextView textView38,
      @NonNull TextView textView39) {
    this.rootView = rootView;
    this.btnEditInfo = btnEditInfo;
    this.btnFrequency = btnFrequency;
    this.textView13 = textView13;
    this.textView38 = textView38;
    this.textView39 = textView39;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemHistoryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemHistoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_history, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemHistoryBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnEditInfo;
      ImageView btnEditInfo = ViewBindings.findChildViewById(rootView, id);
      if (btnEditInfo == null) {
        break missingId;
      }

      id = R.id.btnFrequency;
      CardView btnFrequency = ViewBindings.findChildViewById(rootView, id);
      if (btnFrequency == null) {
        break missingId;
      }

      id = R.id.textView13;
      TextView textView13 = ViewBindings.findChildViewById(rootView, id);
      if (textView13 == null) {
        break missingId;
      }

      id = R.id.textView38;
      TextView textView38 = ViewBindings.findChildViewById(rootView, id);
      if (textView38 == null) {
        break missingId;
      }

      id = R.id.textView39;
      TextView textView39 = ViewBindings.findChildViewById(rootView, id);
      if (textView39 == null) {
        break missingId;
      }

      return new ItemHistoryBinding((CardView) rootView, btnEditInfo, btnFrequency, textView13,
          textView38, textView39);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
