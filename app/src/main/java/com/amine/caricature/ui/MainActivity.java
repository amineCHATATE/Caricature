package com.amine.caricature.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.amine.caricature.data.AndroidImageAssets;
import com.amine.caricature.R;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener {

    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (findViewById(R.id.android_me_linear_layout) != null){
            mTwoPane = true;

            Button next = (Button) findViewById(R.id.next_button);
            next.setVisibility(View.GONE);

            GridView gridView = (GridView) findViewById(R.id.images_grid_view);
            gridView.setNumColumns(2);

            if (savedInstanceState == null){
                BodyPartFragment headFragment = new BodyPartFragment();
                headFragment.setImageIds(AndroidImageAssets.getHeads());
                headFragment.setListIndex(1);

                BodyPartFragment bodyFragment = new BodyPartFragment();
                bodyFragment.setImageIds(AndroidImageAssets.getBodies());
                bodyFragment.setListIndex(1);

                BodyPartFragment legFragment = new BodyPartFragment();
                legFragment.setImageIds(AndroidImageAssets.getLegs());
                legFragment.setListIndex(1);

                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.head_container, headFragment)
                        .add(R.id.body_container, bodyFragment)
                        .add(R.id.leg_container, legFragment)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }

    }

    @Override
    public void onImageSelected(int position) {

        //int bodyPartNumber = position / 12;
        int bodyPartNumber = position / 12;


        int lastIndex = position - 12 * bodyPartNumber;


        if (mTwoPane){

            BodyPartFragment bodyPartFragment = new BodyPartFragment();
            switch (bodyPartNumber){
                case 0:
                    bodyPartFragment.setImageIds(AndroidImageAssets.getHeads());
                    bodyPartFragment.setListIndex(lastIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.head_container, bodyPartFragment)
                            .commit();
                    break;
                case 1:
                    bodyPartFragment.setImageIds(AndroidImageAssets.getBodies());
                    bodyPartFragment.setListIndex(lastIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.body_container, bodyPartFragment)
                            .commit();

                    break;
                case 2:
                    bodyPartFragment.setImageIds(AndroidImageAssets.getLegs());
                    bodyPartFragment.setListIndex(lastIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.leg_container, bodyPartFragment)
                            .commit();
                    break;
            }

        } else {
            switch (bodyPartNumber){
                case 0 : headIndex = lastIndex;
                    break;
                case 1 : bodyIndex = lastIndex;
                    break;
                case 2 : legIndex = lastIndex;
                    break;
                default:
                    break;
            }

            Bundle bundle = new Bundle();
            bundle.putInt("headIndex", headIndex);
            bundle.putInt("bodyIndex", bodyIndex);
            bundle.putInt("legIndex", legIndex);

            final Intent intent = new Intent(this, AndroidMeActivity.class);
            intent.putExtras(bundle);

            Button next = (Button) findViewById(R.id.next_button);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intent);
                }
            });
        }
    }
}
