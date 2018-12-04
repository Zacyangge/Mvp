package com.example.chenzaoyang.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chenzaoyang.presenter.IMainActivityPresenter;
import com.example.chenzaoyang.presenter.MainActivityPresenterCompl;
import com.example.chenzaoyang.zac_mvpdemo.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements IMainActivityView, View.OnClickListener {
    private IMainActivityPresenter mainActivityPresenter;
    private List<EditText> editList;
    private ProgressBar progressBar;
    private Button btn_submit;
    private Button btn_clean;
    private Button btn_find;
    private Button btn_update;
    private Button btn_add;
    private Button btn_delete;
    private Effectstype effect;
    NiftyDialogBuilder dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initView();
    }

    @Override
    public void init() {
        mainActivityPresenter = new MainActivityPresenterCompl();
        editList = new ArrayList<>();
    }

    @Override
    public void initView() {
        editList.add((EditText) findViewById(R.id.age));
        editList.add((EditText) findViewById(R.id.gender));
        editList.add((EditText) findViewById(R.id.name));
        editList.add((EditText) findViewById(R.id.hobby));
        progressBar = (ProgressBar) findViewById(R.id.submit_pro);
        btn_clean = (Button) findViewById(R.id.clean);
        btn_submit = (Button) findViewById(R.id.submit);
        btn_add = (Button) findViewById(R.id.add);
        btn_find = (Button) findViewById(R.id.check);
        btn_delete = (Button) findViewById(R.id.delete);
        btn_update = (Button) findViewById(R.id.updata);
        btn_submit.setOnClickListener(this);
        btn_clean.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_find.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        ;
        progressBar.setBackgroundColor(getResources().getColor(R.color.colorSkyBlue));
    }

    @Override
    public void submitData() {
        mainActivityPresenter.submitData(this, editList, progressBar);
    }

    @Override
    public void initData() {
        mainActivityPresenter.initData(editList);
    }

    @Override
    public void addData() {
        mainActivityPresenter.addData(this, editList);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                submitData();
                break;
            case R.id.clean:
                initData();
                break;
            case R.id.add:
                showDialog(view);
                break;
            case R.id.delete:
                break;
            case R.id.updata:
                break;
            case R.id.check:
                break;
        }
    }

    public void showDialog(View v) {
        dialogBuilder = NiftyDialogBuilder.getInstance(this);
        effect = Effectstype.Fadein;
        dialogBuilder
                .withTitle("GreenDao")                                  //.withTitle(null)  no title
                .withTitleColor("#FFFFFF")                                  //def
                .withDividerColor("#11000000")                              //def
                .withMessage("Do you want to add a user?")                     //.withMessage(null)  no Msg
                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                .withDialogColor("#FFE74C3C")                               //def  | withDialogColor(int resid)                               //def
                .withIcon(getResources().getDrawable(R.drawable.icon))
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                .withDuration(700)                                          //def
                .withEffect(effect)                                         //def Effectstype.Slidetop
                .withButton1Text("确认")                                      //def gone
                .withButton2Text("取消")                                  //def gone
                .setCustomView(R.layout.custom_view, v.getContext())         //.setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addData();
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        Toast.makeText(v.getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();

    }
}