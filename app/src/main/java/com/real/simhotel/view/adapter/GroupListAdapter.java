package com.real.simhotel.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.real.simhotel.R;
import com.real.simhotel.model.Group;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liudan on 2016/12/12.
 */
public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupViewHolder> {


    private final LayoutInflater layoutInflater;
    private List<Group> mGroupsList;

    @Inject
    public GroupListAdapter(Context context){
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mGroupsList = Collections.emptyList();
    }

    @Override
    public GroupListAdapter.GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.row_hotel,parent,false);

        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupListAdapter.GroupViewHolder holder, int position) {

        Group group = mGroupsList.get(position);

        holder.groupTitle.setText(group.getGroupName());
        holder.groupInfo.setText(group.getGroupDes());


    }

    @Override
    public int getItemCount() {
        return (mGroupsList != null) ? mGroupsList.size() : 0 ;
    }

    public void setGroupsList(List<Group> mGroupsList) {
        this.mGroupsList = mGroupsList;
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder{


        @Bind(R.id.hotel_info)
        TextView groupInfo;

        @Bind(R.id.hotel_name)
        TextView groupTitle;

        public GroupViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
