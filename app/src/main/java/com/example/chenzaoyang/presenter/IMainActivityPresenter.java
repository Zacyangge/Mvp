package com.example.chenzaoyang.presenter;

import android.content.Context;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.List;

/**
 * @author chenzaoyang
 * @date 2018/10/24
 */

public interface IMainActivityPresenter {
    void submitData(Context context , List<EditText> editList, ProgressBar progressBar);
    void initData(List<EditText> editList);
    void addData(Context context , List<EditText> editList);
}