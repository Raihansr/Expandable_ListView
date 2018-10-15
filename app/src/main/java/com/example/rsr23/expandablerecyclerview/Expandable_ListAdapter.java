package com.example.rsr23.expandablerecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;

class Expandable_ListAdapter extends BaseExpandableListAdapter {

    private Context context;
    List<String> listdataHeader;
    HashMap<String,List<String>> listDataChild;


    public Expandable_ListAdapter(Context context, List<String> listdataHeader, HashMap<String, List<String>> listDataChild) {
        this.context = context;
        this.listdataHeader = listdataHeader;
        this.listDataChild = listDataChild;
    }

    @Override
    public int getGroupCount() {
        return listdataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listDataChild.get(listdataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listdataHeader.get(groupPosition);
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listDataChild.get(listdataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerText = (String) getGroup(groupPosition);

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_expandable_group,null);
        }

        TextView textView = convertView.findViewById(R.id.group_Item_id);
        textView.setText(headerText);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String childText = (String) getChild(groupPosition,childPosition);

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_expandable_child,null);
        }

        TextView textView = convertView.findViewById(R.id.child_Item_id);
        textView.setText(childText);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

