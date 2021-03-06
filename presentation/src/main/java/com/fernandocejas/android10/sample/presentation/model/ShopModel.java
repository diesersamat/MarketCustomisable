package com.fernandocejas.android10.sample.presentation.model;

import android.text.TextUtils;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ShopModel extends RealmObject {

    RealmList<CategoryModel> categoryModels;
    String primaryColor;
    String accentColor;
    String backgroundColor;
    String textColor;
    String paymentKey;
    @PrimaryKey
    String id;
    int themeId;
    boolean isDarkTheme;
    String name;

    public ShopModel() {
    }

    public String getBackgroundColor() {
        return TextUtils.isEmpty(backgroundColor) ? "" : "#" + backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTextColor() {
        return TextUtils.isEmpty(textColor) ? "" : "#" + textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public boolean isPaymentEnabled() {
//        return !TextUtils.isEmpty(paymentKey);
        return true;
    }

    public String getYandexMoneyAccount() {
        return TextUtils.isEmpty(paymentKey) ? "410011354575504" : paymentKey;
    }

    public void setYandexMoneyAccount(String yandexMoneyAccount) {
        this.paymentKey = yandexMoneyAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryModel> getCategoryModels() {
        return categoryModels;
    }

    public void setCategoryModels(List<CategoryModel> categoryModels) {
        this.categoryModels = new RealmList<>();
        this.categoryModels.addAll(categoryModels);
    }

    public String getPrimaryColor() {
        return TextUtils.isEmpty(primaryColor) ? "" : "#" + primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getAccentColor() {
        return TextUtils.isEmpty(accentColor) ? "" : "#" + accentColor;
    }

    public void setAccentColor(String accentColor) {
        this.accentColor = accentColor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public boolean isDarkTheme() {
        return isDarkTheme;
    }

    public void setDarkTheme(boolean darkTheme) {
        isDarkTheme = darkTheme;
    }

}
