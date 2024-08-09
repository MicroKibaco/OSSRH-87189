package com.github.microkibaco.ultimate.life;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.microkibaco.ultimate.unidramer.R;
import com.github.mikephil.charting.charts.PieChart;

import android.graphics.Color;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import java.util.ArrayList;
import java.util.List;

public class EstimatedFragment extends Fragment {
    private String edStr;
    private PieChart pieChart;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private TextView text5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pie_item_estimated, container, false);
        EditText etVew = view.findViewById(R.id.et_income1);
        pieChart = view.findViewById(R.id.pie_chart);
        text1 = view.findViewById(R.id.text_1);
        text2 = view.findViewById(R.id.text_2);
        text3 = view.findViewById(R.id.text_3);
        text4 = view.findViewById(R.id.text_4);
        text5 = view.findViewById(R.id.text_5);
        text1.setVisibility(View.GONE);
        text2.setVisibility(View.GONE);
        text3.setVisibility(View.GONE);
        text4.setVisibility(View.GONE);
        text5.setVisibility(View.GONE);
        pieChart.setVisibility(View.GONE);
        etVew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                edStr = s.toString();
            }
        });
        view.findViewById(R.id.btn_plan_budget).setOnClickListener(v -> {
            if (edStr == null || edStr.isEmpty()) {
                Toast.makeText(getActivity(), "Numbers cannot be null.", Toast.LENGTH_SHORT).show();
                text1.setVisibility(View.GONE);
                text2.setVisibility(View.GONE);
                text3.setVisibility(View.GONE);
                text4.setVisibility(View.GONE);
                text5.setVisibility(View.GONE);
                pieChart.setVisibility(View.GONE);
                return;
            }

            if (edStr.startsWith("0")) {
                text1.setVisibility(View.GONE);
                text2.setVisibility(View.GONE);
                text3.setVisibility(View.GONE);
                text4.setVisibility(View.GONE);
                text5.setVisibility(View.GONE);
                pieChart.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Numbers cannot start with 0.", Toast.LENGTH_SHORT).show();
                return;
            }

            double totalIncome;
            try {
                totalIncome = Double.parseDouble(edStr);
            } catch (NumberFormatException e) {
                Toast.makeText(getActivity(), "Invalid number format.", Toast.LENGTH_SHORT).show();
                return;
            }

            double[] calculated = calculateExpenditures(view, totalIncome);
            setPieChartData(calculated);
            pieChart.setVisibility(View.VISIBLE);
            text1.setVisibility(View.VISIBLE);
            text2.setVisibility(View.VISIBLE);
            text3.setVisibility(View.VISIBLE);
            text4.setVisibility(View.VISIBLE);
            text5.setVisibility(View.VISIBLE);
        });
        return view;
    }

    public double[] calculateExpenditures(View view, double totalIncome) {
        double educationPercentage, housingPercentage, foodPercentage, transportPercentage, balancePercentage;
        TextView text1 = view.findViewById(R.id.text_1);
        TextView text2 = view.findViewById(R.id.text_2);
        TextView text3 = view.findViewById(R.id.text_3);
        TextView text4 = view.findViewById(R.id.text_4);
        TextView text5 = view.findViewById(R.id.text_5);
        if (totalIncome < 50000) {
            educationPercentage = 0.075;
            housingPercentage = 0.325;
            foodPercentage = 0.175;
            transportPercentage = 0.125;
            balancePercentage = 1 - educationPercentage - housingPercentage - foodPercentage - transportPercentage;
        } else if (totalIncome <= 100000) {
            educationPercentage = 0.125;
            housingPercentage = 0.275;
            foodPercentage = 0.125;
            transportPercentage = 0.125;
            balancePercentage = 1 - educationPercentage - housingPercentage - foodPercentage - transportPercentage;
        } else {
            educationPercentage = 0.175;
            housingPercentage = 0.225;
            foodPercentage = 0.075;
            transportPercentage = 0.125;
            balancePercentage = 1 - educationPercentage - housingPercentage - foodPercentage - transportPercentage;
        }

        double educationSpending = calculateSpending(totalIncome, educationPercentage);
        double housingSpending = calculateSpending(totalIncome, housingPercentage);
        double foodSpending = calculateSpending(totalIncome, foodPercentage);
        double transportSpending = calculateSpending(totalIncome, transportPercentage);
        double balancePercentSpending = calculateSpending(totalIncome, balancePercentage);
        text1.setText("Education Expenses: $" + String.format("%.2f", educationSpending));
        text2.setText("Housing Expenses: $" + String.format("%.2f", housingSpending));
        text3.setText("Food Expenses: $" + String.format("%.2f", foodSpending));
        text4.setText("Transport Expenses: $" + String.format("%.2f", transportSpending));
        text5.setText("End-Of-Month Balance: $" + String.format("%.2f", balancePercentSpending));
        return new double[]{educationSpending, housingSpending, foodSpending, transportSpending, balancePercentSpending};
    }

    public double calculateSpending(double totalIncome, double percentage) {
        return totalIncome * percentage;
    }

    private void setPieChartData(double[] expenditures) {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) expenditures[0], "Education"));
        entries.add(new PieEntry((float) expenditures[1], "Housing"));
        entries.add(new PieEntry((float) expenditures[2], "Food"));
        entries.add(new PieEntry((float) expenditures[3], "Transport"));
        entries.add(new PieEntry((float) expenditures[4], "Balance"));
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
