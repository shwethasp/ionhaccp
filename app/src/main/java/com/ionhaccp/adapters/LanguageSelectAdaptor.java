package com.ionhaccp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ionhaccp.R;
import com.ionhaccp.activities.DashBoardActivity;
import com.ionhaccp.model.SelectLanguageModel;
import com.ionhaccp.utils.Config;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by mehtermu on 30-01-2017.
 */

public class LanguageSelectAdaptor extends RecyclerView.Adapter<LanguageSelectAdaptor.MyViewHolder> {

    private ArrayList<SelectLanguageModel> languageArrayList;
    private SharedPreferences sharedPreferences;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_language;
        public ImageView img_language_select;
        public RelativeLayout select_language_item_layout;

        public MyViewHolder(View view) {
            super(view);
            txt_language = (TextView) view.findViewById(R.id.txt_language);
            img_language_select = (ImageView) view.findViewById(R.id.img_select_language);
            select_language_item_layout = (RelativeLayout) view.findViewById(R.id.select_language_item_root);
        }

    }

    public LanguageSelectAdaptor(Context context, ArrayList<SelectLanguageModel> languageArrayList) {

        this.languageArrayList = languageArrayList;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_language_item, parent, false);
        sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.txt_language.setText(languageArrayList.get(position).getStrLanguageName());
        String sharedlanguage = sharedPreferences.getString(Config.selected_language, context.getResources().getString(R.string.default_language_code));
        if (sharedlanguage.equals(languageArrayList.get(position).getStrLanguageCode())) {
            holder.img_language_select.setImageDrawable(context.getResources().getDrawable(R.mipmap.checkbox_selected));
        } else {
            holder.img_language_select.setImageDrawable(context.getResources().getDrawable(R.mipmap.checkbox_unselected));
        }

        holder.select_language_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Config.selected_language, languageArrayList.get(holder.getAdapterPosition()).getStrLanguageCode());
                editor.apply();


                Intent refresh = new Intent(context.getApplicationContext(), DashBoardActivity.class);
                context.startActivity(refresh);
                ((Activity) context).finish();
//                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return languageArrayList.size();
    }


}
