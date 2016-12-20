package com.real.simhotel.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.real.simhotel.R;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liudan on 2016/12/19.
 */
public class DyNamicListAdapter extends RecyclerView.Adapter<DyNamicListAdapter.DyNamicBaseViewHolder> {


    private final LayoutInflater layoutInflater;
    private List<DyNamicListModel> mDataList;
    private Context mContext;

    public void setDataList(List<DyNamicListModel> dataList) {
        this.mDataList = dataList;
        this.notifyDataSetChanged();
    }

    @Inject
    public DyNamicListAdapter(Context context){
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mDataList = Collections.emptyList();
        this.mContext = context;
    }

    @Override
    public DyNamicBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //view的调整
        View view = null;
        switch (viewType){
            case DyNamicListModel.TYPE_DEFAULT:

                break;
            case DyNamicListModel.TYPE_PLAINTEXT:

                break;
            case DyNamicListModel.TYPE_CHOOSE:
                view = this.layoutInflater.inflate(R.layout.row_radio_group,parent,false);
                return new RadioGroupViewHolder(view,mContext);

            case DyNamicListModel.TYPE_SEEK:

                view = this.layoutInflater.inflate(R.layout.row_seekbar,parent,false);
                return new SeekBarViewHolder(view);

            default:
                view = this.layoutInflater.inflate(R.layout.row_hotel,parent,false);

        }


        return null;
    }

    @Override
    public int getItemViewType(int position) {

        //更具type来定义
        return mDataList.get(position).itemType;
    }

    @Override
    public void onBindViewHolder(DyNamicBaseViewHolder holder, int position) {
        //绑定数据
        holder.bind(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    //baseView
    public abstract class DyNamicBaseViewHolder extends RecyclerView.ViewHolder{

        public DyNamicBaseViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public abstract void bind(DyNamicListModel model);
    }


    //调节bar
    public class SeekBarViewHolder extends DyNamicBaseViewHolder{

        @Bind(R.id.seek_title)
        TextView title;

        @Bind(R.id.seekbar)
        SeekBar seekBar;

        @Bind(R.id.seek_value)
        TextView info;
        public SeekBarViewHolder(View itemView){
            super((itemView));

        }

        @Override
        public void bind(DyNamicListModel model) {

            title.setText(model.title);

            info.setText(0 + model.unit);

            seekBar.setMax(model.max);

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    info.setText(i + model.unit);

                    model.selectedValue = i;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
    }


    //多选类型
    public class RadioGroupViewHolder extends DyNamicBaseViewHolder{

        @Bind(R.id.radio_title)
        TextView title;

        @Bind(R.id.radio_group)
        RadioGroup group;

        Context mContext;
        public RadioGroupViewHolder(View view ,Context context){
            super(view);
            this.mContext = context;

        }

        @Override
        public void bind(DyNamicListModel model) {

            title.setText(model.title);
            //移除所有框架
            group.removeAllViews();
            RadioGroup.LayoutParams radioParams = new RadioGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            for (int i = 0 ; i < model.mChooseItems.size() ; i++){

                String  temp = model.mChooseItems.get(i);

                RadioButton radio = new RadioButton(mContext);

                radio.setLayoutParams(radioParams);
                radio.setText(temp);
                radio.setTag(i);

                group.addView(radio);
            }

            //改变状态
            group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    RadioButton radioButton = (RadioButton) group.findViewById(i);
                    if (null != radioButton && radioButton.isChecked()) {// 选中的item才会执行这个

                        model.selectedValue = (int) radioButton.getTag();

                    }
                }
            });
        }



    }

}
