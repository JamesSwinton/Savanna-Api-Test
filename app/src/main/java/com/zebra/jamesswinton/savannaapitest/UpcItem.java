package com.zebra.jamesswinton.savannaapitest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class UpcItem {

  @SerializedName("title")
  @Expose
  public String title;
  @SerializedName("description")
  @Expose
  public String description;
  @SerializedName("lowest_recorded_price")
  @Expose
  public Double lowestRecordedPrice;
  @SerializedName("images")
  @Expose
  public List<String> images = null;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getLowestRecordedPrice() {
    return lowestRecordedPrice;
  }

  public void setLowestRecordedPrice(Double lowestRecordedPrice) {
    this.lowestRecordedPrice = lowestRecordedPrice;
  }

  public List<String> getImages() {
    return images;
  }

  public void setImages(List<String> images) {
    this.images = images;
  }
}
