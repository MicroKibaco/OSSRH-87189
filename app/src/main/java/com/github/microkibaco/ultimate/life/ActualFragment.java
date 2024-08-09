package com.github.microkibaco.ultimate.life;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.microkibaco.ultimate.unidramer.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class ActualFragment extends Fragment {
    private String edStr;
    private String edStr2;
    private String edStr3;
    private String edStr4;
    private PieChart pieChart;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pie_item_actual, container, false);
        EditText etVew = view.findViewById(R.id.et_income);
        EditText etVew2 = view.findViewById(R.id.et_income2);
        EditText etVew3 = view.findViewById(R.id.et_income3);
        EditText etVew4 = view.findViewById(R.id.et_income4);
        text1 = view.findViewById(R.id.text_1);
        text2 = view.findViewById(R.id.text_2);
        text3 = view.findViewById(R.id.text_3);
        text4 = view.findViewById(R.id.text_4);
        pieChart = view.findViewById(R.id.pie_chart);
        pieChart.setVisibility(View.GONE);
        text1.setVisibility(View.GONE);
        text2.setVisibility(View.GONE);
        text3.setVisibility(View.GONE);
        text4.setVisibility(View.GONE);
        etVew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edStr = s.toString();
            }
        });
        etVew2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                edStr2 = s.toString();
            }
        });
        etVew3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edStr3 = s.toString();
            }
        });
        etVew4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edStr4 = s.toString();
            }
        });

        Button btn = view.findViewById(R.id.query_btn);
        btn.setOnClickListener(v -> {
            if (edStr == null|| edStr2 == null || edStr3 == null|| edStr4 == null) {
               Toast.makeText(getActivity(),"content is empty",Toast.LENGTH_SHORT).show();
                pieChart.setVisibility(View.GONE);
                text1.setVisibility(View.GONE);
                text2.setVisibility(View.GONE);
                text3.setVisibility(View.GONE);
                text4.setVisibility(View.GONE);
                return;
            }

            if (edStr.isEmpty() || edStr2.isEmpty() || edStr3.isEmpty() || edStr4.isEmpty()) {
                Toast.makeText(getActivity(),"content is empty",Toast.LENGTH_SHORT).show();
                pieChart.setVisibility(View.GONE);
                text1.setVisibility(View.GONE);
                text2.setVisibility(View.GONE);
                text3.setVisibility(View.GONE);
                text4.setVisibility(View.GONE);
                return;
            }


            if (edStr.startsWith("0") || edStr2.startsWith("0") || edStr3.startsWith("0") || edStr4.startsWith("0")) {
                Toast.makeText(getActivity(), "Numbers cannot start with 0.", Toast.LENGTH_SHORT).show();
                pieChart.setVisibility(View.GONE);
                text1.setVisibility(View.GONE);
                text2.setVisibility(View.GONE);
                text3.setVisibility(View.GONE);
                text4.setVisibility(View.GONE);
                return;
            }

            double transport;
            double education;
            double housing;
            double food;
            try {
                transport = Double.parseDouble(edStr);
                education = Double.parseDouble(edStr2);
                housing = Double.parseDouble(edStr3);
                food = Double.parseDouble(edStr4);
            } catch (NumberFormatException e) {
                Toast.makeText(getActivity(), "Invalid number format.", Toast.LENGTH_SHORT).show();
                return;
            }
            double[] expenditures = calculateExpenditures(education, housing, food,transport);
            setPieChartData(expenditures);
            pieChart.setVisibility(View.VISIBLE);
            text1.setVisibility(View.VISIBLE);
            text2.setVisibility(View.VISIBLE);
            text3.setVisibility(View.VISIBLE);
            text4.setVisibility(View.VISIBLE);
        });

        return view;
    }

    public double[] calculateExpenditures(double educationPercentage, double housingPercentage, double foodPercentage, double transportPercentage) {
        double totalNumber =  educationPercentage +  housingPercentage + foodPercentage +  transportPercentage;
        String transport = String.format("%.2f", (transportPercentage / totalNumber) * 100);
        String education = String.format("%.2f", (educationPercentage / totalNumber) * 100);
        String housing = String.format("%.2f", (housingPercentage / totalNumber) * 100);
        String food = String.format("%.2f", (foodPercentage / totalNumber) * 100);
        text1.setText("Transport: " + transport +"%")  ;
        text2.setText("Education:" + education+"%");
        text3.setText("Housing:" + housing+"%");
        text4.setText("Food:" + food+"%");
        return new double[]{educationPercentage, housingPercentage, foodPercentage, transportPercentage};
    }

    private void setPieChartData(double[] expenditures) {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) expenditures[0], "Education"));
        entries.add(new PieEntry((float) expenditures[1], "Housing"));
        entries.add(new PieEntry((float) expenditures[2], "Food"));
        entries.add(new PieEntry((float) expenditures[3], "Transport"));
        PieDataSet dataSet = new PieDataSet(entries, "Expenditures");

        // 定义颜色列表
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(255, 99, 132)); // 红色
        colors.add(Color.rgb(54, 162, 235)); // 蓝色
        colors.add(Color.rgb(255, 206, 86)); // 黄色
        colors.add(Color.rgb(75, 192, 192)); // 青色
        colors.add(Color.rgb(153, 102, 255)); // 紫色

        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.getDescription().setEnabled(true); // 启用描述标签
        pieChart.setHighlightPerTapEnabled(true); // 启用点击高亮
        pieChart.getDescription().setText("Monthly Expenditures"); // 设置描述文本
        pieChart.getDescription().setTextSize(12f); // 设置字体大小
        pieChart.getDescription().setTextColor(Color.DKGRAY); // 设置文本颜色
        pieChart.getDescription().setPosition(900, 750); // 设置描述文本的位置
        pieChart.invalidate(); // 刷新图表
        pieChart.animateY(1400); // 1400ms 的Y轴方向动画
        pieChart.invalidate(); // 刷新图表
    }
}