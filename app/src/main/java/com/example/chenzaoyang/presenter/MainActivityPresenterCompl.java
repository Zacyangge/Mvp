package com.example.chenzaoyang.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chenzaoyang.model.UserInfo;
import com.martin.studygreendao.db.UserDaoOpe;
import com.martin.studygreendao.greendao.UserInfoDao;

import java.util.List;

/**
 * @author chenzaoyang
 * @date 2018/10/24
 */

public class MainActivityPresenterCompl implements IMainActivityPresenter {
    @Override
    public void submitData(final Context context, final List<EditText> editList, final ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        new Thread() {
            @Override
            public void run() {
                /*模拟提交信息*/
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                UserInfo info = new UserInfo();
                info.setAge(editList.get(0).getText().toString());
                info.setGender(editList.get(1).getText().toString());
                info.setName(editList.get(2).getText().toString());
                info.setHobby(editList.get(3).getText().toString());
                if (editList.get(0).getText().toString().isEmpty()) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(context, "age is empty", Toast.LENGTH_SHORT).show();
                            initData(editList);
                        }
                    });
                } else if (editList.get(1).getText().toString().isEmpty()) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(context, "sex is empty", Toast.LENGTH_SHORT).show();
                            initData(editList);
                        }
                    });
                } else if (editList.get(2).getText().toString().isEmpty()) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(context, "name is empty", Toast.LENGTH_SHORT).show();
                            initData(editList);
                        }
                    });
                } else if (editList.get(3).getText().toString().isEmpty()) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(context, "hobby is empty", Toast.LENGTH_SHORT).show();
                            initData(editList);
                        }
                    });
                } else {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(context, "提交数据完成", Toast.LENGTH_SHORT).show();
                            initData(editList);
                        }
                    });
                }

            }

        }.start();
    }

    @Override
    public void initData(List<EditText> editList) {
        for (EditText editText : editList)
            editText.setText("");
    }
    @Override
    public void addData(final Context context, final List<EditText> editList){
        UserInfo info = new UserInfo();
        info.setAge(editList.get(0).getText().toString());
        info.setGender(editList.get(1).getText().toString());
        info.setName(editList.get(2).getText().toString());
        info.setHobby(editList.get(3).getText().toString());
        UserDaoOpe.insertData(context,info);
        Toast.makeText(context, "Add success", Toast.LENGTH_SHORT).show();
    }
}

