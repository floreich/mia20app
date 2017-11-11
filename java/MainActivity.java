package ml.mia20.mia20app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import ml.mia20.mia20app.Fragment.HausaufgabenFragment;
import ml.mia20.mia20app.Fragment.StdplanFragment;
import ml.mia20.mia20app.Fragment.KlausurenFragment;
import ml.mia20.mia20app.Fragment.FehlzeitenFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private ViewPager viewPager;


    //Fragments

    KlausurenFragment klausurenFragment;
    HausaufgabenFragment hausaufgabenFragment;
    StdplanFragment stdplanFragment;
    FehlzeitenFragment fehlzeitenFragment;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_stdplan:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_klausur:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_hausaufgaben:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.action_fehlzeiten:
                                viewPager.setCurrentItem(3);
                                break;
                        }
                        return false;
                    }
                });



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

       /*  //Disable ViewPager Swipe

       viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

        */

        setupViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navbar, menu);
        return true;
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        stdplanFragment =new StdplanFragment();
        klausurenFragment =new KlausurenFragment();
        hausaufgabenFragment =new HausaufgabenFragment();
        fehlzeitenFragment =new FehlzeitenFragment();
        adapter.addFragment(stdplanFragment);
        adapter.addFragment(klausurenFragment);
        adapter.addFragment(hausaufgabenFragment);
        adapter.addFragment(fehlzeitenFragment);
        viewPager.setAdapter(adapter);
    }
}
