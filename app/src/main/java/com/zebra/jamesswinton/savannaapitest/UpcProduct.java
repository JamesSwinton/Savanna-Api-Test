package com.zebra.jamesswinton.savannaapitest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class UpcProduct {

  @SerializedName("code")
  @Expose
  public String code;
  @SerializedName("total")
  @Expose
  public int total;
  @SerializedName("offset")
  @Expose
  public int offset;
  @SerializedName("items")
  @Expose
  public List<UpcItem> items = null;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getOffset() {
    return offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  public List<UpcItem> getItems() {
    return items;
  }

  public void setItems(List<UpcItem> items) {
    this.items = items;
  }
}
