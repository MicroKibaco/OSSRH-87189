package com.github.microkibaco.ultimate.life;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextPaint;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.github.microkibaco.ultimate.unidramer.R;

public class HomeActivity extends FragmentActivity {
    String[] titles = {"Estimated", "Actual"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SlidingTabLayout slidingTabLayout = findViewById(R.id.market_tab);
        ViewPager viewPager = findViewById(R.id.view_pager);
        UltimateAdapter adapter = new UltimateAdapter(this.getSupportFragmentManager());
        adapter.addFragment(new EstimatedFragment(), "Estimated");
        adapter.addFragment(new ActualFragment(), "Actual");
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager, titles);
        updateTabView(slidingTabLayout, this);
        adapter.notifyDataSetChanged();
    }

    private void updateTabView(SlidingTabLayout tabLayout, Activity activity) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView title = tabLayout.getTitleView(i);
            title.setText(titles[i]);
            title.setTextColor(activity.getColor(R.color.black));
            setTextViewBoldEffect(title);
        }
    }

    private void setTextViewBoldEffect(TextView tv) {
        TextPaint tp = new TextPaint();
        tp.setFakeBoldText(true);
        tv.setPaintFlags(tv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
    }
}
