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

public class AlcoholFragment extends Fragment {
    private RecyclerView myrecyclerview;
    private ArrayList<Info> lstAlcohol;

    public AlcoholFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alcohol_fragment, container, false);
        myrecyclerview = (RecyclerView) view.findViewById(R.id.alcohol_recyclerView);
        InfoAdapter recyclerAdapter = new InfoAdapter(getContext(), lstAlcohol);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(recyclerAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstAlcohol = new ArrayList<>();
        lstAlcohol.add(new Info(R.drawable.al1_kojin, "고진감래주", "소주 + 콜라 + 맥주",
                "< Recipe >\n" +
                        "01\n" + "1층 소주잔에 콜라를 따르고 빈 맥주잔에 넣는다.\n" + "\n" +
                        "02\n" + "2층 소주잔에 소주를 따르고 1층 소주잔 위에 쌓는다.\n" + "\n" +
                        "03\n" + "맥주잔에 1, 2층 소주잔들이 잠기도록 맥주를 따른다.\n" + "\n" +
                        "+\n" + "소주+맥주를 다 마신 뒤에야 콜라가 나오도록 설계되어있기 때문에 끝맛은 달게 느껴짐! 말그대로 고.진.감.래!\n"));
        lstAlcohol.add(new Info(R.drawable.al2_sobeksanmaek, "소백산맥주", "소주 + 백세주 + 산사춘 + 맥주",
                "< Recipe >\n" +
                        "01\n" + "얼음을 채운 잔에 소주25%, 백세주 25%를 채운다.\n" + "\n" +
                        "02\n" + "나머지 산사춘 25%, 맥주 25%를 채우고 섞으면 완성!\n" + "\n" +
                        "+\n" + "어디에나 쎈캐는 있기마련! 제조주계의 쎈캐릭터! 맛보단 쓴맛을 즐기는 소!백!산!맥!\n"));
        lstAlcohol.add(new Info(R.drawable.al3_dirtyhoe, "더티호", "호가든 + 기네스",
                "< Recipe >\n" +
                        "01\n" + "먼저 호가든을 거품이 나도록 빨리 반잔만 따라준다!!\n" + " \n" +
                        "02\n" + "기네스가 숟가락 볼록한 등 부분을 타고 천천히 흘러 내릴 수 있게 조심조심 따르면 층이 생기면서 완성!!\n" + "\n" +
                        "+\n" + "아주아주 조심조심 천천천천천천히~~~!! 따라야 어두운 기네스 층이 생기면서 아름다운 더티호의 비쥬얼이 표현된답니다^^\n"));
        lstAlcohol.add(new Info(R.drawable.al4_jagerbomb, "예거밤", "예거마이스터 + 핫식스 + 얼음",
                "< Recipe >\n" +
                        "01\n" + "얼음을 채운 잔에 예거마이스터를 반만 채운다.\n" + "\n" +
                        "02\n" + "핫식스를 나머지 반만큼 넣고 잘 섞어 주면 완성!\n" +
                        "+\n" + "제조도 간단하고 예거마이스터 특유의 쌉싸름한 맛이 없어져 도수가 뭔 상관? 여성분들에게 인기~~ 훅!\n"));
        lstAlcohol.add(new Info(R.drawable.al5_energizer, "에너자이저", "얼음 + 파워에이드 + 핫식스 + 소주",
                "< Recipe >\n" +
                        "01\n" + "얼음을 채운 잔에 소주20% + 핫식스40% + 파워에이드40%를 넣는다!\n" + "\n" +
                        "02\n" + "잘 저어 섞어섞어 원샷!\n" + "\n" +
                        "+\n" + "이름만 들어도 힘이 솟아날 것 같은 저렴한 칵테일!! 약간 시원시원한 청량감이 입에 착 달라붙네요!"));
        lstAlcohol.add(new Info(R.drawable.al6_milkis, "밀키스주", "소주 + 맥주 + 사이다",
                "< Recipe >\n" +
                        "01\n" + "소주20% + 맥주40% + 사이다 40%를 넣고 흔들어 주세요~~\n" + "\n" +
                        "02\n" + "폭탄주처럼 물수건이나 휴지로 잔 주둥이를 막고 바닥에 탁!! 헙!!!\n" + "\n" +
                        "+\n" + "매니아층을 다수 섭렵!! 사이다+소주+맥주가 만들어내는 환상의 하모니!! 싸랑해요~ 밀~키스!!"));
        lstAlcohol.add(new Info(R.drawable.al7_bubletank, "버블탱크", "소주 + 탱크보이 + 사이다",
                "< Recipe >\n" +
                        "01\n" + "얼음을 채운 잔에 소주와 사이다를 따라준다.\n" + "\n" +
                        "02\n" + "녹아서 슬러시 상태가 된 탱크보이 쭈쭈바를 짜 넣고 섞어준다.\n" + "\n" +
                        "+\n" + "기호에 맞게 내용물 조절 가능! 단맛을 즐기려면 탱크보이를 아낌없이 넣어주세요~ 스키틀즈 같은 고명도 얹어주면 금상첨화!!"));
        lstAlcohol.add(new Info(R.drawable.al8_calimocho, "칼리모쵸", "레드와인 + 콜라",
                "< Recipe >\n" +
                        "01\n" + "얼음을 채운 잔에 레드와인을 반 정도 채운다.\n" + "\n" +
                        "02\n" + "남은 반을 콜라로 채우고 잘 섞는다!!\n" + "\n" +
                        "+\n" + "톡 쏘는 콜라와 쌉싸름 레드와인과의 절묘한 조화!! 기호에 맞게 비율 조절이 가능한 강렬한 맛의 콜라보레이션!! 스페인을 느껴보세요!!"));
        lstAlcohol.add(new Info(R.drawable.al9_chewyjelly, "쫄깃젤리", "KGB + 비타민워터 + 봉봉 + 후르츠 칵테일",
                "< Recipe >\n" +
                        "01\n" + "얼음을 채운 잔에 KGB 60ml, 비타민워터 30ml, 봉봉 30ml를 넣는다.\n" + "\n" +
                        "02\n" + "통조림 후르츠칵테일 2Ts를 넣고 잘 섞어주면 끝!\n" + "\n" +
                        "+\n" + "후르츠칵테일과 봉봉 내용물이 들어가 식감과 색감을 살려주는 마법과도 같은 칵테일! 알콜 향을 싫어하는 분들 여기 모두 주목!!"));
        lstAlcohol.add(new Info(R.drawable.al10_hongcho, "홍초불막주", "막걸리 + 홍초 + 번인텐스 + 식혜",
                "< Recipe >\n" +
                        "01\n" + "잔에 막걸리를 절반 정도 따른다.\n" + "\n" +
                        "02\n" + "홍초와 번을 각각 1/5씩 넣고 식혜도 약간 넣어서 살살 섞어준다.\n" + "\n" +
                        "+\n" + "새콤한 붉은 식초, 홍초~! 그리고 구수한 막걸리와의 만남!! 거기에 달달한 식감을 더해줄 식혜와의 콜라보레이션!! 더 이상 어떤 말이 필요한가!!?"));
        lstAlcohol.add(new Info(R.drawable.al11_hongikingan, "홍익인간주", "소주 + 홍초",
                "< Recipe >\n" +
                        "01\n" + "기분 좋~게 소주 1잔 마시고, 남은 소주병에 홍초1: 소주8 비율로!!\n" + "\n" +
                        "02\n" + "흔들어주세요~~ 널리 이롭게 마셔주세요!!\n" + "\n" +
                        "+\n" + "시대의 풍미를 장식했던 과실주계의 풍운아!! 달짝지근한 그의 맛은?? 널리 이롭게 할만한 술이로다 "));
        lstAlcohol.add(new Info(R.drawable.al12_lemonpunch, "레몬펀치", "화이트와인 + 탄산수 + 레몬즙 + 시럽",
                "< Recipe >\n" +
                        "01\n" + "얼음을 채운 잔에 탄산수 25%와 화이트와인 25%를 넣어준다.\n" + "\n" +
                        "02\n" + "레몬즙과 시럽, 레몬을 썰어 넣어 잔을 채우고 잘 섞는다!!\n" + "\n" +
                        "+\n" + "기호에 맞게 비율과 양 조절 가능! 새콤달콤 레몬맛에 한방 맞아봐요 >_O"));
        lstAlcohol.add(new Info(R.drawable.al13_pinkpunch, "핑크펀치", "화이트와인 + 탄산수 + 자몽즙 + 시럽",
                "< Recipe >\n" +
                        "01\n" + "얼음을 채운 잔에 탄산수 25%와 화이트와인 25%를 넣어준다.\n" + "\n" +
                        "02\n" + "자몽즙과 시럽, 레몬을 썰어 넣어 잔을 채우고 잘 섞는다.\n" + "\n" +
                        "+\n" + "기호에 맞게 비율과 양 조절 가능! 달짝지근 자몽맛에 한방 맞아봐요 O_<"));
        lstAlcohol.add(new Info(R.drawable.al14_redeye, "레드아이", "맥주 + 토마토주스",
                "< Recipe >\n" +
                        "01\n" + "맥주와 토마토 주스를 1:1 비율로 섞는다.\n" + "\n" +
                        "02\n" + "기본 비율은 1:1이지만 취향에 따라 조절해도 굿!\n" + "\n" +
                        "+\n" + "맥주 베이스에 토마토의 산뜻한 맛이 부드럽게 느껴지는 칵테일로, 제조가 단순해 누구나 쉽게 만들 수 있는 장점!!"));
        lstAlcohol.add(new Info(R.drawable.al15_shandygaff, "샌디게프", "맥주 + 진저에일",
                "< Recipe >\n" +
                        "01\n" + "잔에 맥주를 반 정도 따른다.\n" + "\n" +
                        "02\n" + "진저에일을 천천히 부은 다음 가볍게~ 젓는다.\n" + "\n" +
                        "+\n" + "진저에일의 생강 맛과 약간의 단맛이 씁쓸한 맥주와의 조화를 이룬 산뜻한 맛의 칵테일!!"));
        lstAlcohol.add(new Info(R.drawable.al16_sowonju, "소원주", "소주 + 원두커피",
                "< Recipe >\n" +
                        "01\n" + "빈잔에 얼음을 채운다.\n" + "\n" +
                        "02\n" + "얼음을 채운 잔에 소주1 : 원두커피5 비율로 섞어준다!!\n" + "\n" +
                        "+\n" + "소원을~ 말해봐~!! 커피가 좋아? 소주가 좋아? 둘다 섞어버려버려버려~~ 은은한 커피향이 나는 술! 가벼운 음주로 제격이겠죠!? 단맛을 즐기고 싶다면 원두 대신 캔커피도 추천드려용+^^+"));
        lstAlcohol.add(new Info(R.drawable.al17_sunset, "선셋", "밀러 + 자몽주스",
                "< Recipe >\n" +
                        "01\n" + "자몽주스 250ml + 밀러 150ml + 딸기시럽 2ts을 넣는다.\n" + "\n" +
                        "02\n" + "잘 저어저어 섞어섞어!! 기호에 따라 크림도 넣어 마셔주자!\n" + "\n" +
                        "+\n" + "자몽소주 저리가라! 달달한 자몽맥주의 최고봉!!! 색감 또한 베리베리굿!!! 석양이~ 진다~~"));
        lstAlcohol.add(new Info(R.drawable.al18_ujjujju, "우쭈쭈메로니", "얼음 + 메로나 + 소주 + 사이다",
                "< Recipe >\n" +
                        "01\n" + "얼음을 채운 잔에 메로나의 막대가 위를 향하도록 꽂아준다.\n" + "\n" +
                        "02\n" + "소주 50ml와 사이다 30ml를 넣고 섞는다.\n" + "\n" +
                        "+\n" + "기호에 맞게 내용물 조절 가능! 메로나는 완전히 다 녹여서 먹거나, 마시면서 천천히 녹여 먹으면 꿀맛!!"));
        lstAlcohol.add(new Info(R.drawable.al19_screwkiss, "스크류키스", "얼음 + 스크류바 + 맥키스 + 토닉워터",
                "< Recipe >\n" +
                        "01\n" + "얼음을 채운 잔에 스크류바의 막대가 위를 향하도록 꽂아준다.\n" + "\n" +
                        "02\n" + "맥키스와 토닉워터를 1:1 비율로 넣어 섞고, 레몬 가니쉬 2~3조각 띄어준다.\n" + "\n" +
                        "+\n" + "기호에 맞게 내용물 조절 가능! 스크류바는 완전히 다 녹여서 먹거나, 마시면서 천천히 녹여 먹으면 꿀맛!"));
        lstAlcohol.add(new Info(R.drawable.al20_somaek, "소맥", "황금 비율은 몇 대 몇?",
                "< Recipe >\n" +
                        "01\n" + "원하는 비율로 소주를 먼저 따르고 남은 부분을 맥주로 채워준다!!\n" + "\n" +
                        "02\n" + "수저로 콕콕! 섞어 주면 부드러운 크림 소맥 완성!!\n" + "\n" +
                        "+\n" + "폭탄주 기본 중에 기본! 하지만 비율은 기호에 따라 제각각!! 우리모두 자신만의 소맥 황금비율을 찾아보자!!\n"));
    }

}
