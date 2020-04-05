package com.triplebro.domineer.takeoutfood.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.triplebro.domineer.takeoutfood.R;
import com.triplebro.domineer.takeoutfood.models.TypeConcreteInfo;
import com.triplebro.domineer.takeoutfood.models.TypeGeneralizeInfo;

import java.util.List;

public class TypeNameAdapter extends BaseAdapter {

    private Context context;
    private List<TypeGeneralizeInfo> typeGeneralizeInfoList;
    private View lastTagView;
    private RecyclerView rv_type_content;
    private List<TypeConcreteInfo> concreteTypeList;
    private TypeContentAdapter adapter;

    public TypeNameAdapter(Context context, List<TypeGeneralizeInfo> typeGeneralizeInfoList, RecyclerView rv_type_content) {
        this.context = context;
        this.typeGeneralizeInfoList = typeGeneralizeInfoList;
        this.rv_type_content = rv_type_content;
    }

    @Override
    public int getCount() {
        return typeGeneralizeInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_type_name, null);
            viewHolder.tv_type_name = convertView.findViewById(R.id.tv_type_name);
            viewHolder.v_tag = convertView.findViewById(R.id.v_tag);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_type_name.setText(typeGeneralizeInfoList.get(position).getType_generalize_name());
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_type_name;
        private View v_tag;
    }
}
