package com.zebra.jamesswinton.savannaapitest;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.util.logging.Logger;

public class UpcProductAdapter extends RecyclerView.Adapter {

  // Debugging
  private static final String TAG = "UpcProductAdapter";

  // Constants
  private static final int EMPTY_PRODUCT_VIEW_TYPE = 0;
  private static final int UPC_PRODUCT_VIEW_TYPE = 1;

  // Static Variables
  private static UpcProduct mUpcProduct;
  private Context mContext;

  // Non-Static Variables


  public UpcProductAdapter(Context context, UpcProduct upcProduct) {
    mContext = context;
    mUpcProduct = upcProduct;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    switch (viewType) {
      case EMPTY_PRODUCT_VIEW_TYPE:
        return new EmptyProductHolder(LayoutInflater.from(
            parent.getContext()).inflate(R.layout.adapter_empty_product, parent, false));
      case UPC_PRODUCT_VIEW_TYPE:
        return new UpcProductHolder(LayoutInflater.from(
            parent.getContext()).inflate(R.layout.adapter_upc_product, parent, false));
      default:
        return new EmptyProductHolder(null);
    }
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    switch(viewHolder.getItemViewType()) {
      case EMPTY_PRODUCT_VIEW_TYPE:
        Log.i(TAG, "Showing Empty ViewHolder");
        break;
      case UPC_PRODUCT_VIEW_TYPE:
        // Cast ViewHolder to PopulatedViewHolder
        UpcProductHolder vh = (UpcProductHolder) viewHolder;
        // Get Current BasketItem
        Picasso.with(mContext).load(mUpcProduct.items.get(0).images.get(0)).into(vh.productImageView);
        vh.productTitle.setText(mUpcProduct.items.get(0).title);
        vh.productDescription.setText(mUpcProduct.items.get(0).description);
        vh.productPrice.setText(String.valueOf(mUpcProduct.items.get(0).lowestRecordedPrice));
        break;
    }
  }

  @Override
  public int getItemCount() {
    return mUpcProduct == null || mUpcProduct.total == 0 ? 1 : mUpcProduct.total;
  }

  @Override
  public int getItemViewType(int position) {
    return mUpcProduct == null || mUpcProduct.total == 0 ? EMPTY_PRODUCT_VIEW_TYPE : UPC_PRODUCT_VIEW_TYPE;
  }

  public void showNewProduct(UpcProduct upcProduct) {
    mUpcProduct = upcProduct;
    notifyDataSetChanged();
  }

  private class EmptyProductHolder extends ViewHolder {
    public EmptyProductHolder(@NonNull View itemView) { super(itemView); }
  }

  private class UpcProductHolder extends ViewHolder {
    // UI Elements
    ImageView productImageView;
    TextView productTitle, productDescription, productPrice;
    public UpcProductHolder(@NonNull View itemView) {
      super(itemView);
      productTitle = itemView.findViewById(R.id.product_title);
      productPrice = itemView.findViewById(R.id.product_price);
      productImageView = itemView.findViewById(R.id.product_image);
      productDescription = itemView.findViewById(R.id.product_description);
    }
  }
}
