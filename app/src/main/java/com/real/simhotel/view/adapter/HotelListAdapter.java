package com.real.simhotel.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.real.simhotel.R;
import com.real.simhotel.model.Hotel;
import com.real.simhotel.view.HotelListView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liudan on 2016/12/12.
 */
public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.HotelViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<Hotel> mHotelsList;

    private OnItemClickListener onItemClickListener;

    @Inject
    public HotelListAdapter(Context context){
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mHotelsList = Collections.emptyList();
    }

    @Override
    public HotelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = this.layoutInflater.inflate(R.layout.row_hotel,parent,false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotelViewHolder holder, int position) {

        Hotel hotel = mHotelsList.get(position);

        holder.hotelTitle.setText("酒店" + hotel.getId());
        holder.hotelInfo.setText(hotel.getInsertTime());
        holder.hotelPrice.setText(hotel.getRoomCost()+"");

        //暂时不加点击事件
        holder.itemView.setOnClickListener(view -> {
            if (HotelListAdapter.this.onItemClickListener != null) {
                HotelListAdapter.this.onItemClickListener.onHotelItemClicked(hotel);
            }
        });

    }


    @Override
    public int getItemCount() {
        return (mHotelsList != null) ? mHotelsList.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setHotelsList(List<Hotel> mHotelsList) {
        this.mHotelsList = mHotelsList;
        this.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onHotelItemClicked(Hotel hotel);
    }
    public class HotelViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.hotel_price)
        TextView hotelPrice;

        @Bind(R.id.hotel_info)
        TextView hotelInfo;

        @Bind(R.id.hotel_name)
        TextView hotelTitle;

        public HotelViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
