package com.technology.lpjxlove.bfans.Bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LPJXLOVE on 2016/9/28.
 */

public class ImageBean implements Parcelable {
    public String ImageUrl;
    public boolean isCheck;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ImageUrl);
        dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
    }

    public ImageBean() {
    }

    protected ImageBean(Parcel in) {
        this.ImageUrl = in.readString();
        this.isCheck = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ImageBean> CREATOR = new Parcelable.Creator<ImageBean>() {
        @Override
        public ImageBean createFromParcel(Parcel source) {
            return new ImageBean(source);
        }

        @Override
        public ImageBean[] newArray(int size) {
            return new ImageBean[size];
        }
    };
}
