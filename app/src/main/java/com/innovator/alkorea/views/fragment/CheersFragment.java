package com.innovator.alkorea.views.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovator.alkorea.R;
import com.innovator.alkorea.library.models.Info;
import com.innovator.alkorea.views.adapter.InfoAdapter;

import java.util.ArrayList;

public class CheersFragment extends Fragment {
    private RecyclerView myrecyclerview;
    private ArrayList<Info> lstCheers;

    public CheersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cheers_fragment, container, false);
        myrecyclerview = (RecyclerView) view.findViewById(R.id.cheers_recyclerView);
        InfoAdapter recyclerAdapter = new InfoAdapter(getContext(), lstCheers);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(recyclerAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstCheers = new ArrayList<>();
        lstCheers.add(new Info(R.drawable.ch1_gosari,
                "고사리",
                "고마운분들께는, 고사리!",
                "고 맙습니다." + "\n\n" + "사 랑합니다." + "\n\n" + "리(이) 해합니다."));
        lstCheers.add(new Info(R.drawable.ch2_nagaja,
                "나가자",
                "수신제가치국평천하 나를 위해, 나가자!",
                "나 라를 위하여," + "\n\n" + "가 정을 위하여," + "\n\n" +"자 신을 위하여!"));
        lstCheers.add(new Info(R.drawable.ch3_namhaeng,
                "남행열차",
                "회사에서의 마음가짐, 남행열차!",
                "남 다른" + "\n\n" + "행 동과" + "\n\n" + "열 정으로," +"\n\n"+"차 세대 리더가 되자!"));
        lstCheers.add(new Info(R.drawable.ch4_donkey,
                "당나귀",
                "귀한 우리모두, 당나귀!",
                "당 신과," + "\n\n" + "나 의," + "\n\n" + "귀 한 만남을 위하여!"));
        lstCheers.add(new Info(R.drawable.ch5_bamboo,
                "대나무",
                "대화가 필요한 우리에겐, 대나무!",
                "대 화를" + "\n\n" + "나 누며," + "\n\n" + "무 한한"+"\n\n"+"성공을 위하여!"));
        lstCheers.add(new Info(R.drawable.ch6_ddukbaegi,
                "뚝배기",
                "지쳐있는 나를 위한 격언, 뚝배기!",
                "뚝 심있게," + "\n\n" + "배 짱있게," + "\n\n" + "기 운차게!"));
        lstCheers.add(new Info(R.drawable.ch7_mamuri,
                "마무리",
                "마음먹기에 달린 우리의 다짐, 마무리!",
                "마 음먹은 것은," + "\n\n" + "무 엇이든," + "\n\n" + "리(이) 루자!"));
        lstCheers.add(new Info(R.drawable.ch8_master,
                "마스터",
                "마음터놓고 싶은 그대에게, 마스터!",
                "마 음껏," + "\n\n" + "스 스럼없이," + "\n\n" + "터 놓고 마시자!"));
        lstCheers.add(new Info(R.drawable.ch9_mujogun,
                "무조건",
                "힘들어도 포기하지말고, 무조건!",
                "무지 힘들어도," + "\n\n" + "조 금만 참고," + "\n\n" + "건 승하자!"));

        lstCheers.add(new Info(R.drawable.ch10_missile,
                "미사일",
                "일과 사랑 둘다 놓치지말고, 미사일!",
                "미 래를 위해," + "\n\n" + "사 랑을 위해," + "\n\n" + "일 을 위해"+"\n\n"+"다같이 발사!"));
        lstCheers.add(new Info(R.drawable.ch11_sauna,
                "사우나",
                "친한친구사이 어딜갈래?, 사우나!",
                "사 랑과," + "\n\n" + "우 정을," + "\n\n" + "나 누자!"));
        lstCheers.add(new Info(R.drawable.ch12_sanghanga,
                "상한가",
                "힘들어도 포기하지말고, 상한가!",
                "상 심하지 말고," + "\n\n" + "한 탄하지 말고," + "\n\n" + "가 슴을 펴자!"));
        lstCheers.add(new Info(R.drawable.ch13_pine,
                "소나무",
                "서로 나누며 돕고사는 우리, 소나무!",
                "소 중한," + "\n\n" + "나 눔의," + "\n\n" + "무 한한"+"\n\n"+"행복을 위하여!"));
        lstCheers.add(new Info(R.drawable.ch14_obama,
                "오바마",
                "앞날을 위해 전진하는 우리모두, 오바마!",
                "오 직," + "\n\n" + "바 라는대로," + "\n\n" + "마 음먹은대로"+"\n\n"+"이루어지길!"));
        lstCheers.add(new Info(R.drawable.ch15_ohaengsi,
                "오행시",
                "소중한 오늘을 위해, 오행시!",
                "오 늘도," + "\n\n" + "행 복한," + "\n\n" + "시 간 되세요!"));
        lstCheers.add(new Info(R.drawable.ch16_uami,
                "우아미",
                "미래를 위해 다같이, 우아미!",
                "우 아하고," + "\n\n" + "아 름다운," + "\n\n" + "미 래를 위하여!"));
        lstCheers.add(new Info(R.drawable.ch17_rebuild,
                "재건축",
                "건강하며 오래오래 삽시다. 재건축!",
                "재 미나고," + "\n\n" + "건 강하게," + "\n\n" + "축 복 받으며"+"\n\n"+"삽시다!"));
        lstCheers.add(new Info(R.drawable.ch18_pot,
                "주전자",
                "부장님! 후배를 위해 한말씀 부탁드려요. 주전자!",
                "주 인답게 살고," + "\n\n" + "전 문성을 갖추고 살고," + "\n\n" + "자 신감을 가지고 살자!"));
        lstCheers.add(new Info(R.drawable.ch19_azalea,
                "진달래",
                "내일을 위해 우리모두 다같이, 진달래!",
                "진 하고," + "\n\n" + "달 콤한," + "\n\n" + "래(내) 일을 위하여!"));
    }
}
