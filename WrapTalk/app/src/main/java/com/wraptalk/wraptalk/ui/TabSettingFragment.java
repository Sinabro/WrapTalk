package com.wraptalk.wraptalk.ui;


import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.wraptalk.wraptalk.R;
import com.wraptalk.wraptalk.adapter.SettingAdapter;
import com.wraptalk.wraptalk.models.SettingData;
import com.wraptalk.wraptalk.models.UserInfo;
import com.wraptalk.wraptalk.utils.AppSetting;
import com.wraptalk.wraptalk.utils.OnRequest;
import com.wraptalk.wraptalk.utils.RequestUtil;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabSettingFragment extends android.support.v4.app.Fragment {


    Intent intent;
    View view;
    ArrayList<SettingData> source;
    ArrayList<String> settingList;
    SettingAdapter customAdapter = null;
    ListView listView_result;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_setting, container, false);


        initModel();
        initController();
        initView();

        listView_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    intent = new Intent(getActivity(), NoticeActivity.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(getActivity(), FavoriteChannelActivity.class);
                    startActivity(intent);
                    break;
                case 2:
                    onClickOpacity();
                    break;
                case 5:
                    intent = new Intent(getActivity(), ChangePasswordActivity.class);
                    startActivity(intent);
                    break;
                case 6:
                    onClickLogout();
                    break;
                case 7:
                    onClickQuit();
                    break;
                default:
                    break;
            }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void initModel() {
        source = new ArrayList<>();
        settingList = new ArrayList<>();

        settingList.add("공지사항");
        settingList.add("채팅방 즐겨찾기 관리");
        settingList.add("투명도 변경");
        settingList.add("Floating ON/OFF");
        settingList.add("App Version");
        settingList.add("비밀번호 변경");
        settingList.add("로그아웃");
        settingList.add("회원탈퇴");

        listView_result = (ListView) view.findViewById(R.id.listVeiw_setting);
    }

    private void initView() {

        for (int i = 0; i < settingList.size(); i++) {
            SettingData data = new SettingData();
            data.setSetting(settingList.get(i));
            source.add(data);
        }
        customAdapter.notifyDataSetChanged();
    }

    private void initController() {
        customAdapter = new SettingAdapter(getActivity().getApplicationContext(), source);
        listView_result.setAdapter(customAdapter);
    }

    public TabSettingFragment() {
        // Required empty public constructor
    }

    public void onClickOpacity() {
        //res폴더>>layout폴더>>dialog_addmember.xml 레이아웃 리소스 파일로 View 객체 생성
        //Dialog의 listener에서 사용하기 위해 final로 참조변수 선언

        final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_channel_window_opacity, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(dialogView);


        builder.setPositiveButton("설정하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SeekBar seekBar = (SeekBar) dialogView.findViewById(R.id.seekBar_opacity);
                seekBar.setMax(100);
                int progress = seekBar.getProgress();
                commitSharedPreference("chathead_alpha", progress+"");
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        //설정한 값으로 AlertDialog 객체 생성
        AlertDialog dialog = builder.create();

        //Dialog의 바깥쪽을 터치했을 때 Dialog를 없앨지 설정
        dialog.setCanceledOnTouchOutside(false);//없어지지 않도록 설정

        //Dialog 보이기
        dialog.show();
    }

    private void commitSharedPreference(String key, String value) {
        SharedPreferences pref = getActivity().getSharedPreferences("chathead", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void onClickLogout() {
        //res폴더>>layout폴더>>dialog_addmember.xml 레이아웃 리소스 파일로 View 객체 생성
        //Dialog의 listener에서 사용하기 위해 final로 참조변수 선언

        final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_logout_wraptalk, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(dialogView);

        builder.setPositiveButton("LOGOUT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences pref = getActivity().getSharedPreferences("app", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("token", "");
                editor.commit();
                Intent intent = new Intent(getActivity(), SplashActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        //설정한 값으로 AlertDialog 객체 생성
        AlertDialog dialog = builder.create();

        //Dialog의 바깥쪽을 터치했을 때 Dialog를 없앨지 설정
        dialog.setCanceledOnTouchOutside(false);//없어지지 않도록 설정

        //Dialog 보이기
        dialog.show();
    }


    public void onClickQuit() {
        //res폴더>>layout폴더>>dialog_addmember.xml 레이아웃 리소스 파일로 View 객체 생성
        //Dialog의 listener에서 사용하기 위해 final로 참조변수 선언

        final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_quit_wraptalk, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(dialogView);

        builder.setPositiveButton("탈퇴하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String url = AppSetting.REST_URL + "/user/deleteUser?token=" + UserInfo.getInstance().token + "&user_pw=" + UserInfo.getInstance().password;
                RequestUtil.asyncHttp(url, new OnRequest() {
                    @Override
                    public void onSuccess(String url, byte[] receiveData) {
                        SharedPreferences pref = getActivity().getSharedPreferences("app", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("token", "");
                        editor.commit();
                        Toast.makeText(getActivity(), "성공적으로 탈퇴하셨습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), SignInActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }

                    @Override
                    public void onFail(String url, String error) {
                        Toast.makeText(getActivity(), "error : 탈퇴하지 못하셨습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        //설정한 값으로 AlertDialog 객체 생성
        AlertDialog dialog = builder.create();

        //Dialog의 바깥쪽을 터치했을 때 Dialog를 없앨지 설정
        dialog.setCanceledOnTouchOutside(false);//없어지지 않도록 설정

        //Dialog 보이기
        dialog.show();
    }
}
