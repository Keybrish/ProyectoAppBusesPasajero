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

public final class ItemFrequencyBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final ImageView btnDetail;

  @NonNull
  public final CardView btnFrequency;

  @NonNull
  public final CardView btnProfile;

  @NonNull
  public final ImageView imgBus;

  @NonNull
  public final TextView txtFrequency;

  private ItemFrequencyBinding(@NonNull CardView rootView, @NonNull ImageView btnDetail,
      @NonNull CardView btnFrequency, @NonNull CardView btnProfile, @NonNull ImageView imgBus,
      @NonNull TextView txtFrequency) {
    this.rootView = rootView;
    this.btnDetail = btnDetail;
    this.btnFrequency = btnFrequency;
    this.btnProfile = btnProfile;
    this.imgBus = imgBus;
    this.txtFrequency = txtFrequency;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemFrequencyBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemFrequencyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_frequency, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemFrequencyBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnDetail;
      ImageView btnDetail = ViewBindings.findChildViewById(rootView, id);
      if (btnDetail == null) {
        break missingId;
      }

      id = R.id.btnFrequency;
      CardView btnFrequency = ViewBindings.findChildViewById(rootView, id);
      if (btnFrequency == null) {
        break missingId;
      }

      id = R.id.btnProfile;
      CardView btnProfile = ViewBindings.findChildViewById(rootView, id);
      if (btnProfile == null) {
        break missingId;
      }

      id = R.id.imgBus;
      ImageView imgBus = ViewBindings.findChildViewById(rootView, id);
      if (imgBus == null) {
        break missingId;
      }

      id = R.id.txtFrequency;
      TextView txtFrequency = ViewBindings.findChildViewById(rootView, id);
      if (txtFrequency == null) {
        break missingId;
      }

      return new ItemFrequencyBinding((CardView) rootView, btnDetail, btnFrequency, btnProfile,
          imgBus, txtFrequency);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}