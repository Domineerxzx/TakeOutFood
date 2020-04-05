package com.triplebro.domineer.takeoutfood.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.triplebro.domineer.takeoutfood.R;

import java.util.List;

public class SearchHistoryAdapter extends BaseAdapter {

    private Context context;
    private List<String> strings;
    private TextView tv_no_history;
    private ListView lv_history;

    public SearchHistoryAdapter(Context context, List<String> strings, TextView tv_no_history, ListView lv_history) {
        this.context = context;
        this.strings = strings;
        this.tv_no_history = tv_no_history;
        this.lv_history = lv_history;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return strings.size();
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_search_history, null);
            viewHolder.tv_history = convertView.findViewById(R.id.tv_history);
            viewHolder.iv_delete_history = convertView.findViewById(R.id.iv_delete_history);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_history.setText(strings.get(position));
        viewHolder.iv_delete_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String remove = strings.remove(position);
                SharedPreferences searchHistory = context.getSharedPreferences("searchHistory", Context.MODE_PRIVATE);
                String searchHistoryString = searchHistory.getString("searchHistory", "");
                int i = searchHistoryString.indexOf(remove);
                String substring;
                if (i == -1) {
                    return;
                } else if (i == 0) {
                    substring = searchHistoryString.substring(remove.length() + 1, searchHistoryString.length());
                } else if (i < searchHistoryString.length() - 1 - remove.length()) {
                    substring = searchHistoryString.substring(0, i) + searchHistoryString.substring(i + remove.length()+1, searchHistoryString.length() - 1);
                } else {
                    substring = searchHistoryString.substring(0, i);
                }
                if(substring.length() == 0){
                    tv_no_history.setVisibility(View.VISIBLE);
                    lv_history.setVisibility(View.GONE);
                }else{
                    tv_no_history.setVisibility(View.GONE);
                    lv_history.setVisibility(View.VISIBLE);
                }
                SharedPreferences.Editor edit = searchHistory.edit();
                edit.clear();
                edit.putString("searchHistory", substring);
                edit.commit();
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_history;
        private ImageView iv_delete_history;
    }
}
