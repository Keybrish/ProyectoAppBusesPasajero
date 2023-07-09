// Generated by view binder compiler. Do not edit!
package dev.android.appbuses.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import dev.android.appbuses.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityPaymentBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView btnBack;

  @NonNull
  public final ImageView btnLess;

  @NonNull
  public final Button btnNext;

  @NonNull
  public final ImageView btnPlus;

  @NonNull
  public final CardView btnProfile;

  @NonNull
  public final CardView btnProfile1;

  @NonNull
  public final CardView btnProfile2;

  @NonNull
  public final CardView btnProfile3;

  @NonNull
  public final CardView btnProfile4;

  @NonNull
  public final ConstraintLayout constraintLayout;

  @NonNull
  public final ImageView imageView15;

  @NonNull
  public final ImageView imageView3;

  @NonNull
  public final ImageView imageView51;

  @NonNull
  public final ImageView imageView56;

  @NonNull
  public final ImageView imageView59;

  @NonNull
  public final ImageView imageView9;

  @NonNull
  public final ImageView imgProfile;

  @NonNull
  public final Spinner spnPayment;

  @NonNull
  public final TextView textView;

  @NonNull
  public final TextView textView1;

  @NonNull
  public final TextView textView2;

  @NonNull
  public final TextView textView22;

  @NonNull
  public final TextView textView23;

  @NonNull
  public final TextView textView24;

  @NonNull
  public final TextView textView25;

  @NonNull
  public final TextView textView26;

  @NonNull
  public final TextView textView28;

  @NonNull
  public final TextView textView29;

  @NonNull
  public final TextView textView3;

  @NonNull
  public final TextView textView30;

  @NonNull
  public final TextView textView31;

  @NonNull
  public final TextView textView33;

  @NonNull
  public final TextView textView34;

  @NonNull
  public final TextView textView35;

  @NonNull
  public final TextView textView36;

  @NonNull
  public final TextView textView50;

  @NonNull
  public final TextView textView55;

  @NonNull
  public final TextView textView58;

  @NonNull
  public final TextView txtAmount;

  @NonNull
  public final TextView txtCooperative;

  @NonNull
  public final TextView txtDate;

  @NonNull
  public final TextView txtDestination;

  @NonNull
  public final TextView txtOrigin;

  @NonNull
  public final TextView txtTime;

  @NonNull
  public final View view2;

  @NonNull
  public final View view6;

  @NonNull
  public final View view7;

  private ActivityPaymentBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView btnBack,
      @NonNull ImageView btnLess, @NonNull Button btnNext, @NonNull ImageView btnPlus,
      @NonNull CardView btnProfile, @NonNull CardView btnProfile1, @NonNull CardView btnProfile2,
      @NonNull CardView btnProfile3, @NonNull CardView btnProfile4,
      @NonNull ConstraintLayout constraintLayout, @NonNull ImageView imageView15,
      @NonNull ImageView imageView3, @NonNull ImageView imageView51, @NonNull ImageView imageView56,
      @NonNull ImageView imageView59, @NonNull ImageView imageView9, @NonNull ImageView imgProfile,
      @NonNull Spinner spnPayment, @NonNull TextView textView, @NonNull TextView textView1,
      @NonNull TextView textView2, @NonNull TextView textView22, @NonNull TextView textView23,
      @NonNull TextView textView24, @NonNull TextView textView25, @NonNull TextView textView26,
      @NonNull TextView textView28, @NonNull TextView textView29, @NonNull TextView textView3,
      @NonNull TextView textView30, @NonNull TextView textView31, @NonNull TextView textView33,
      @NonNull TextView textView34, @NonNull TextView textView35, @NonNull TextView textView36,
      @NonNull TextView textView50, @NonNull TextView textView55, @NonNull TextView textView58,
      @NonNull TextView txtAmount, @NonNull TextView txtCooperative, @NonNull TextView txtDate,
      @NonNull TextView txtDestination, @NonNull TextView txtOrigin, @NonNull TextView txtTime,
      @NonNull View view2, @NonNull View view6, @NonNull View view7) {
    this.rootView = rootView;
    this.btnBack = btnBack;
    this.btnLess = btnLess;
    this.btnNext = btnNext;
    this.btnPlus = btnPlus;
    this.btnProfile = btnProfile;
    this.btnProfile1 = btnProfile1;
    this.btnProfile2 = btnProfile2;
    this.btnProfile3 = btnProfile3;
    this.btnProfile4 = btnProfile4;
    this.constraintLayout = constraintLayout;
    this.imageView15 = imageView15;
    this.imageView3 = imageView3;
    this.imageView51 = imageView51;
    this.imageView56 = imageView56;
    this.imageView59 = imageView59;
    this.imageView9 = imageView9;
    this.imgProfile = imgProfile;
    this.spnPayment = spnPayment;
    this.textView = textView;
    this.textView1 = textView1;
    this.textView2 = textView2;
    this.textView22 = textView22;
    this.textView23 = textView23;
    this.textView24 = textView24;
    this.textView25 = textView25;
    this.textView26 = textView26;
    this.textView28 = textView28;
    this.textView29 = textView29;
    this.textView3 = textView3;
    this.textView30 = textView30;
    this.textView31 = textView31;
    this.textView33 = textView33;
    this.textView34 = textView34;
    this.textView35 = textView35;
    this.textView36 = textView36;
    this.textView50 = textView50;
    this.textView55 = textView55;
    this.textView58 = textView58;
    this.txtAmount = txtAmount;
    this.txtCooperative = txtCooperative;
    this.txtDate = txtDate;
    this.txtDestination = txtDestination;
    this.txtOrigin = txtOrigin;
    this.txtTime = txtTime;
    this.view2 = view2;
    this.view6 = view6;
    this.view7 = view7;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPaymentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPaymentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_payment, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPaymentBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnBack;
      ImageView btnBack = ViewBindings.findChildViewById(rootView, id);
      if (btnBack == null) {
        break missingId;
      }

      id = R.id.btnLess;
      ImageView btnLess = ViewBindings.findChildViewById(rootView, id);
      if (btnLess == null) {
        break missingId;
      }

      id = R.id.btnNext;
      Button btnNext = ViewBindings.findChildViewById(rootView, id);
      if (btnNext == null) {
        break missingId;
      }

      id = R.id.btnPlus;
      ImageView btnPlus = ViewBindings.findChildViewById(rootView, id);
      if (btnPlus == null) {
        break missingId;
      }

      id = R.id.btnProfile;
      CardView btnProfile = ViewBindings.findChildViewById(rootView, id);
      if (btnProfile == null) {
        break missingId;
      }

      id = R.id.btnProfile1;
      CardView btnProfile1 = ViewBindings.findChildViewById(rootView, id);
      if (btnProfile1 == null) {
        break missingId;
      }

      id = R.id.btnProfile2;
      CardView btnProfile2 = ViewBindings.findChildViewById(rootView, id);
      if (btnProfile2 == null) {
        break missingId;
      }

      id = R.id.btnProfile3;
      CardView btnProfile3 = ViewBindings.findChildViewById(rootView, id);
      if (btnProfile3 == null) {
        break missingId;
      }

      id = R.id.btnProfile4;
      CardView btnProfile4 = ViewBindings.findChildViewById(rootView, id);
      if (btnProfile4 == null) {
        break missingId;
      }

      id = R.id.constraintLayout;
      ConstraintLayout constraintLayout = ViewBindings.findChildViewById(rootView, id);
      if (constraintLayout == null) {
        break missingId;
      }

      id = R.id.imageView15;
      ImageView imageView15 = ViewBindings.findChildViewById(rootView, id);
      if (imageView15 == null) {
        break missingId;
      }

      id = R.id.imageView3;
      ImageView imageView3 = ViewBindings.findChildViewById(rootView, id);
      if (imageView3 == null) {
        break missingId;
      }

      id = R.id.imageView51;
      ImageView imageView51 = ViewBindings.findChildViewById(rootView, id);
      if (imageView51 == null) {
        break missingId;
      }

      id = R.id.imageView56;
      ImageView imageView56 = ViewBindings.findChildViewById(rootView, id);
      if (imageView56 == null) {
        break missingId;
      }

      id = R.id.imageView59;
      ImageView imageView59 = ViewBindings.findChildViewById(rootView, id);
      if (imageView59 == null) {
        break missingId;
      }

      id = R.id.imageView9;
      ImageView imageView9 = ViewBindings.findChildViewById(rootView, id);
      if (imageView9 == null) {
        break missingId;
      }

      id = R.id.imgProfile;
      ImageView imgProfile = ViewBindings.findChildViewById(rootView, id);
      if (imgProfile == null) {
        break missingId;
      }

      id = R.id.spnPayment;
      Spinner spnPayment = ViewBindings.findChildViewById(rootView, id);
      if (spnPayment == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.textView1;
      TextView textView1 = ViewBindings.findChildViewById(rootView, id);
      if (textView1 == null) {
        break missingId;
      }

      id = R.id.textView2;
      TextView textView2 = ViewBindings.findChildViewById(rootView, id);
      if (textView2 == null) {
        break missingId;
      }

      id = R.id.textView22;
      TextView textView22 = ViewBindings.findChildViewById(rootView, id);
      if (textView22 == null) {
        break missingId;
      }

      id = R.id.textView23;
      TextView textView23 = ViewBindings.findChildViewById(rootView, id);
      if (textView23 == null) {
        break missingId;
      }

      id = R.id.textView24;
      TextView textView24 = ViewBindings.findChildViewById(rootView, id);
      if (textView24 == null) {
        break missingId;
      }

      id = R.id.textView25;
      TextView textView25 = ViewBindings.findChildViewById(rootView, id);
      if (textView25 == null) {
        break missingId;
      }

      id = R.id.textView26;
      TextView textView26 = ViewBindings.findChildViewById(rootView, id);
      if (textView26 == null) {
        break missingId;
      }

      id = R.id.textView28;
      TextView textView28 = ViewBindings.findChildViewById(rootView, id);
      if (textView28 == null) {
        break missingId;
      }

      id = R.id.textView29;
      TextView textView29 = ViewBindings.findChildViewById(rootView, id);
      if (textView29 == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = ViewBindings.findChildViewById(rootView, id);
      if (textView3 == null) {
        break missingId;
      }

      id = R.id.textView30;
      TextView textView30 = ViewBindings.findChildViewById(rootView, id);
      if (textView30 == null) {
        break missingId;
      }

      id = R.id.textView31;
      TextView textView31 = ViewBindings.findChildViewById(rootView, id);
      if (textView31 == null) {
        break missingId;
      }

      id = R.id.textView33;
      TextView textView33 = ViewBindings.findChildViewById(rootView, id);
      if (textView33 == null) {
        break missingId;
      }

      id = R.id.textView34;
      TextView textView34 = ViewBindings.findChildViewById(rootView, id);
      if (textView34 == null) {
        break missingId;
      }

      id = R.id.textView35;
      TextView textView35 = ViewBindings.findChildViewById(rootView, id);
      if (textView35 == null) {
        break missingId;
      }

      id = R.id.textView36;
      TextView textView36 = ViewBindings.findChildViewById(rootView, id);
      if (textView36 == null) {
        break missingId;
      }

      id = R.id.textView50;
      TextView textView50 = ViewBindings.findChildViewById(rootView, id);
      if (textView50 == null) {
        break missingId;
      }

      id = R.id.textView55;
      TextView textView55 = ViewBindings.findChildViewById(rootView, id);
      if (textView55 == null) {
        break missingId;
      }

      id = R.id.textView58;
      TextView textView58 = ViewBindings.findChildViewById(rootView, id);
      if (textView58 == null) {
        break missingId;
      }

      id = R.id.txtAmount;
      TextView txtAmount = ViewBindings.findChildViewById(rootView, id);
      if (txtAmount == null) {
        break missingId;
      }

      id = R.id.txtCooperative;
      TextView txtCooperative = ViewBindings.findChildViewById(rootView, id);
      if (txtCooperative == null) {
        break missingId;
      }

      id = R.id.txtDate;
      TextView txtDate = ViewBindings.findChildViewById(rootView, id);
      if (txtDate == null) {
        break missingId;
      }

      id = R.id.txtDestination;
      TextView txtDestination = ViewBindings.findChildViewById(rootView, id);
      if (txtDestination == null) {
        break missingId;
      }

      id = R.id.txtOrigin;
      TextView txtOrigin = ViewBindings.findChildViewById(rootView, id);
      if (txtOrigin == null) {
        break missingId;
      }

      id = R.id.txtTime;
      TextView txtTime = ViewBindings.findChildViewById(rootView, id);
      if (txtTime == null) {
        break missingId;
      }

      id = R.id.view2;
      View view2 = ViewBindings.findChildViewById(rootView, id);
      if (view2 == null) {
        break missingId;
      }

      id = R.id.view6;
      View view6 = ViewBindings.findChildViewById(rootView, id);
      if (view6 == null) {
        break missingId;
      }

      id = R.id.view7;
      View view7 = ViewBindings.findChildViewById(rootView, id);
      if (view7 == null) {
        break missingId;
      }

      return new ActivityPaymentBinding((ConstraintLayout) rootView, btnBack, btnLess, btnNext,
          btnPlus, btnProfile, btnProfile1, btnProfile2, btnProfile3, btnProfile4, constraintLayout,
          imageView15, imageView3, imageView51, imageView56, imageView59, imageView9, imgProfile,
          spnPayment, textView, textView1, textView2, textView22, textView23, textView24,
          textView25, textView26, textView28, textView29, textView3, textView30, textView31,
          textView33, textView34, textView35, textView36, textView50, textView55, textView58,
          txtAmount, txtCooperative, txtDate, txtDestination, txtOrigin, txtTime, view2, view6,
          view7);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}