package com.example.rsr23.expandablerecyclerview;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private Expandable_ListAdapter expandableAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listDataChild;
    private int lastExpandedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandableListView = findViewById(R.id.expandable_listView_id);

        prepareListData();

        expandableAdapter = new Expandable_ListAdapter(this, listDataHeader,listDataChild);

        if (Build.VERSION.SDK_INT < 23) {
            Set_Initial_Height(expandableListView);
        }
        else {
            Set_Normal_Height(expandableListView);
            expandableListView.setNestedScrollingEnabled(true);
        }

        expandableListView.setAdapter(expandableAdapter);


        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                String GroupName = listDataHeader.get(groupPosition);
                Toast.makeText(MainActivity.this, GroupName+"", Toast.LENGTH_SHORT).show();

                if (Build.VERSION.SDK_INT < 23) {
                    Set_Total_Height(parent, groupPosition);
                }

                return false;
            }
        });


        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                if(lastExpandedPosition != -1 && lastExpandedPosition != groupPosition)
                {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }

                lastExpandedPosition = groupPosition;
            }
        });



        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

                String GroupName = listDataHeader.get(groupPosition);

                Toast.makeText(MainActivity.this, GroupName+" is Collapsed", Toast.LENGTH_SHORT).show();
            }
        });



        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String ChildString = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                Toast.makeText(MainActivity.this, ChildString+"", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

    }


    private void prepareListData() {

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        listDataHeader.add("Group One");
        List<String> g0 = new ArrayList<>();
        g0.add("Child One");
        g0.add("Child Two");
        g0.add("Child Three");
        listDataChild.put(listDataHeader.get(0),g0);

        listDataHeader.add("Group Two");
        List<String> g1 = new ArrayList<>();
        g1.add("Child One");
        g1.add("Child Two");
        g1.add("Child Three");
        listDataChild.put(listDataHeader.get(1),g1);

        listDataHeader.add("Group Three");
        List<String> g2 = new ArrayList<>();
        g2.add("Child One");
        g2.add("Child Two");
        g2.add("Child Three");
        listDataChild.put(listDataHeader.get(2),g2);

        listDataHeader.add("Group Four");
        List<String> g3 = new ArrayList<>();
        g3.add("Child One");
        g3.add("Child Two");
        g3.add("Child Three");
        listDataChild.put(listDataHeader.get(3),g3);

        listDataHeader.add("Group Five");
        List<String> g4 = new ArrayList<>();
        g4.add("Child One");
        g4.add("Child Two");
        g4.add("Child Three");
        listDataChild.put(listDataHeader.get(4),g4);

        listDataHeader.add("Group Six");
        List<String> g5 = new ArrayList<>();
        g5.add("Child One");
        g5.add("Child Two");
        g5.add("Child Three");
        listDataChild.put(listDataHeader.get(5),g5);

        listDataHeader.add("Group Seven");
        List<String> g6 = new ArrayList<>();
        g6.add("Child One");
        g6.add("Child Two");
        g6.add("Child Three");
        listDataChild.put(listDataHeader.get(6),g6);

        listDataHeader.add("Group Eight");
        List<String> g7 = new ArrayList<>();
        g7.add("Child One");
        g7.add("Child Two");
        g7.add("Child Three");
        listDataChild.put(listDataHeader.get(7),g7);

        listDataHeader.add("Group Nine");
        List<String> g8 = new ArrayList<>();
        g8.add("Child One");
        g8.add("Child Two");
        g8.add("Child Three");
        listDataChild.put(listDataHeader.get(8),g8);

        listDataHeader.add("Group Ten");
        List<String> g9 = new ArrayList<>();
        g9.add("Child One");
        g9.add("Child Two");
        g9.add("Child Three");
        listDataChild.put(listDataHeader.get(9),g9);
    }


    /* Set Normal Height - (For API Level >= 23) */
    private void Set_Normal_Height(ExpandableListView listView) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = displayMetrics.heightPixels;

        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    /* Set Custom Initial Height - (For API Level < 23) */
    private void Set_Initial_Height(ExpandableListView listView) {

        if (expandableAdapter == null) {
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < expandableAdapter.getGroupCount(); i++) {

            View mView = expandableAdapter.getGroupView(i, false, null, listView);

            mView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            totalHeight += mView.getMeasuredHeight();
        }


        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (expandableAdapter.getGroupCount() - 1)) + (expandableAdapter.getGroupCount() - 1);
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    /* Set Custom Total Height - (For API Level < 23) */
    private void Set_Total_Height(ExpandableListView listView, int group) {

        if (expandableAdapter == null) {
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < expandableAdapter.getGroupCount(); i++) {

            View groupItem = expandableAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group)) || ((!listView.isGroupExpanded(i)) && (i == group))) {

                for (int j = 0; j < expandableAdapter.getChildrenCount(i); j++) {

                    View listItem = expandableAdapter.getChildView(i, j, false, null, listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                    totalHeight += listItem.getMeasuredHeight();
                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (expandableAdapter.getGroupCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


}
