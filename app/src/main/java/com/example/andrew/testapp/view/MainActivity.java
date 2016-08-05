package com.example.andrew.testapp.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.andrew.testapp.R;
import com.example.andrew.testapp.common.BaseActivity;
import com.example.andrew.testapp.di.IHasComponent;
import com.example.andrew.testapp.di.components.DaggerIMainActivityComponent;
import com.example.andrew.testapp.di.components.IMainActivityComponent;
import com.example.andrew.testapp.di.components.ITestAppComponent;
import com.example.andrew.testapp.di.modules.MainActivityModule;
import com.example.andrew.testapp.presenter.MainPresenter;
import com.viewpagerindicator.CirclePageIndicator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements IMainActivity, IHasComponent<IMainActivityComponent> {

    @Inject
    MainPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.indicator)
    CirclePageIndicator springIndicator;


    private SectionsPagerAdapter mSectionsPagerAdapter;
    FragmentManager fragmentManager;
    private IMainActivityComponent mainActivityComponent;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        presenter.initializeDB();
        fragmentManager = getSupportFragmentManager();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mSectionsPagerAdapter);
        springIndicator.setRadius(15);
        springIndicator.setPageColor(getResources().getColor(R.color.empty_indicator));
        springIndicator.setFillColor(getResources().getColor(R.color.colorAccent));
        springIndicator.setAlpha(0.9f);
        animation = AnimationUtils.loadAnimation(this, R.anim.anticipate_overshoot);
        springIndicator.setAnimation(animation);
        springIndicator.setViewPager(viewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setupComponent(ITestAppComponent appComponent) {
        mainActivityComponent = DaggerIMainActivityComponent.builder()
                .iTestAppComponent(appComponent)
                .mainActivityModule(new MainActivityModule(this))
                .build();
        mainActivityComponent.inject(this);
    }

    @Override
    public IMainActivityComponent getComponent() {
        return mainActivityComponent;
    }


    @Override
    public Context getContext() {
        return getBaseContext();
    }

    @Override
    public void popFragmentFromStack() {
        fragmentManager.popBackStack();
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            presenter.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        ViewUserList viewUserList = new ViewUserList();
        MSCCategoryView mscCategoryView = new MSCCategoryView();

        public SectionsPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
           switch (position){
               case 0:
                   return viewUserList;
               case 1:
                   return mscCategoryView;
               default:
                   return mscCategoryView;
           }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "1";
                case 1:
                    return "2";
            }
            return null;
        }
    }
}
