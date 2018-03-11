package com.example.abhishek.authentication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

/**
 * Created by Abhishek on 08-10-2017.
 */

public class Object_adapter extends RecyclerView.Adapter<Object_adapter.MyViewHolder> implements Filterable {
    private ArrayList<Object> obl;
    private ArrayList<Object> fob1;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView cat;
        public TextView obj;
        public TextView val;
        public TextView msg;
        public MyViewHolder(View view) {
            super(view);
            cat=(TextView)view.findViewById(R.id.tv0);
            obj=(TextView)view.findViewById(R.id.tv1);
            val=(TextView)view.findViewById(R.id.tv2);
            msg=(TextView)view.findViewById(R.id.tv3);
        }
    }

    public Object_adapter() {
        this.obl.add(new Object("","",0,""));
    }

    Object_adapter(ArrayList<Object> obl) {
        this.obl = obl;
        fob1=obl;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.object_list,parent,false);
        return  new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int pos){
        if(obl.size()>0) {
            Object ob = obl.get(pos);
            holder.msg.setText("");
            holder.cat.setText(ob.getOname());
            holder.obj.setText(ob.getOval().toString());
            holder.val.setText(ob.getOmsg());
        }
    }

    @Override
    public int getItemCount(){

        return fob1.size()>0 ? fob1.size():0;
    }

    public void clear(){
        int size= obl.size();
        obl.clear();
        notifyItemRangeRemoved(0,size);
    }

    @Override
    public android.widget.Filter getFilter(){
        return new android.widget.Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString=constraint.toString();
                if(charString.isEmpty()){
                    fob1=obl;
                }else {
                    ArrayList<Object> flist=new ArrayList<>();
                    for(Object ob: obl){
                        if(ob.getOname().toLowerCase().contains(charString)||ob.getOmsg().toLowerCase().contains(charString)||ob.getOval().toString().toLowerCase().contains(charString)){
                            flist.add(ob);
                        }
                    }
                    fob1=flist;
                }
                FilterResults fr=new FilterResults();
                fr.values=fob1;
                return fr;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                obl=(ArrayList<Object>)results.values;
                notifyDataSetChanged();
            }
        };
    }

}
