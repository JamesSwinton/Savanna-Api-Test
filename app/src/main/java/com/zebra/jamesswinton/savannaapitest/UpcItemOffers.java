package com.zebra.jamesswinton.savannaapitest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpcItemOffers {

  @SerializedName("merchant")
  @Expose
  public String merchant;
  @SerializedName("domain")
  @Expose
  public String domain;
  @SerializedName("title")
  @Expose
  public String title;
  @SerializedName("currency")
  @Expose
  public String currency;
//  @SerializedName("list_price")
//  @Expose
//  public int listPrice;
  @SerializedName("price")
  @Expose
  public double price;
  @SerializedName("shipping")
  @Expose
  public String shipping;
  @SerializedName("condition")
  @Expose
  public String condition;
  @SerializedName("availability")
  @Expose
  public String availability;
  @SerializedName("link")
  @Expose
  public String link;
  @SerializedName("updated_t")
  @Expose
  public int updatedT;

  public String getMerchant() {
    return merchant;
  }

  public void setMerchant(String merchant) {
    this.merchant = merchant;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

//  public int getListPrice() {
//    return listPrice;
//  }
//
//  public void setListPrice(int listPrice) {
//    this.listPrice = listPrice;
//  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getShipping() {
    return shipping;
  }

  public void setShipping(String shipping) {
    this.shipping = shipping;
  }

  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public String getAvailability() {
    return availability;
  }

  public void setAvailability(String availability) {
    this.availability = availability;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public int getUpdatedT() {
    return updatedT;
  }

  public void setUpdatedT(int updatedT) {
    this.updatedT = updatedT;
  }
}
