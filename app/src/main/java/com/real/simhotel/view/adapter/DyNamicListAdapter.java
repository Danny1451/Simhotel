package com.real.simhotel.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.real.simhotel.R;
import com.real.simhotel.utils.log.KLog;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liudan on 2016/12/19.
 */
public class DynamicListAdapter extends RecyclerView.Adapter<DynamicListAdapter.DyNamicBaseViewHolder> {

    private int mSelectPos = -1;

    private final LayoutInflater layoutInflater;
    private List<DynamicListModel> mDataList;
    private Context mContext;

    //多项选择
    private ItemsChooseInterface mItemChooseInterce;
    //两个选择
    private NormalChooseInterface mChooseInterface;
    //行点击接口
    private DynamicListRowInterface mRowInterface;


    public int getSelectPos() {
        return mSelectPos;
    }

    public DynamicListAdapter setChooseInterface(NormalChooseInterface mChooseInterface) {
        this.mChooseInterface = mChooseInterface;
        return this;
    }

    public DynamicListAdapter setRowInterface(DynamicListRowInterface mRowInterface) {
        this.mRowInterface = mRowInterface;
        return this;
    }

    public DynamicListAdapter setItemChooseInterce(ItemsChooseInterface mItemChooseInterce) {
        this.mItemChooseInterce = mItemChooseInterce;
        return this;
    }

    public DynamicListAdapter setDataList(List<DynamicListModel> dataList) {
        this.mDataList = dataList;
        this.notifyDataSetChanged();
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).isSelected) {
                mSelectPos = i;
            }
        }

        return this;
    }

    @Inject
    public DynamicListAdapter(Context context){
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
            case DynamicListModel.TYPE_DEFAULT:

                break;
            case DynamicListModel.TYPE_PLAINTEXT:

                break;
            case DynamicListModel.TYPE_RADIO_BUTTONS:
                view = this.layoutInflater.inflate(R.layout.row_radio_group,parent,false);
                return new RadioGroupViewHolder(view,mContext);

            case DynamicListModel.TYPE_SEEK:

                view = this.layoutInflater.inflate(R.layout.row_seekbar,parent,false);
                return new SeekBarViewHolder(view);

            case DynamicListModel.TYPE_INFO_TIME:

                view = this.layoutInflater.inflate(R.layout.row_info_time,parent,false);
                return new NormalInfoViewHolder(view);
            case DynamicListModel.TYPE_TWO_BUTTONS_CHOOSE:

                view = this.layoutInflater.inflate(R.layout.row_normal_choose,parent,false);
                return new NormalChooseViewHolder(view,mChooseInterface);

            case DynamicListModel.TYPE_TITLE_INFO:

                view = this.layoutInflater.inflate(R.layout.row_title_info,parent,false);
                return new TitlleInfoViewHolder(view);

            case DynamicListModel.TYPE_NUMBER_INFO:

                view = this.layoutInflater.inflate(R.layout.row_number_title_info,parent,false);
                return new NumberTitleInfoViewHolder(view);

            default:


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

        holder.itemView.setTag(position);

        KLog.d("update @ " + position + " isSelected " + mDataList.get(position).isSelected);
        if (mDataList.get(position).isSelected){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorPrimary100));

        }else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        //增加点击效果
        holder.itemView.setOnClickListener(view -> {


            if (mRowInterface != null){

                if (mSelectPos != position ){

                    KLog.d("neeed update @ " + position);
                    //只刷新需要改版的界面
                    mDataList.get(mSelectPos).isSelected = false;
                    notifyItemChanged(mSelectPos);

                    mSelectPos = position;
                    mDataList.get(mSelectPos).isSelected = true;
                    notifyItemChanged(mSelectPos);
                    //设置了点击相应的话 回调

                    //响应时间
                    int pos = (int)view.getTag();
                    mRowInterface.onSelected(pos,mDataList.get(pos));
                }


            }
        });


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


        public abstract void bind(DynamicListModel model);
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
        public void bind(DynamicListModel model) {



            title.setText(model.title);



            seekBar.setMax(model.max);
            seekBar.setProgress(0);
            info.setText(model.minus + model.unit);



            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    model.selectedValue = model.minus + i;
                    info.setText( model.selectedValue + model.unit);
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
        public void bind(DynamicListModel model) {

            title.setText(model.title);


            if (model.selectedValue != -1){
                //已经选中了 之前已经初始化了



            }else {

                //移除所有框架
                group.removeAllViews();
                RadioGroup.LayoutParams radioParams = new RadioGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                for (int i = 0; i < model.mChooseItems.size(); i++) {

                    String temp = model.mChooseItems.get(i);

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


                            if (mItemChooseInterce != null) {

                                int pos = (int) radioButton.getTag();
                                mItemChooseInterce.onItemsSelected(pos, model);
                            }


                        }
                    }
                });

            }

        }

    }

    //普通只有文字
    public class NormalInfoViewHolder extends DyNamicBaseViewHolder{

        @Bind(R.id.normal_info_tv)

        TextView info;
        @Bind(R.id.normal_time_tv)

        TextView time;
        public NormalInfoViewHolder(View view){
            super(view);
        }

        @Override
        public void bind(DynamicListModel model) {

            info.setText(model.info);

            time.setText(model.time);

        }
    }

    public interface NormalChooseInterface{
        void onChoose(DynamicListModel model, Boolean hasConfirm);
    }

    public interface ItemsChooseInterface{
        void onItemsSelected(int pos ,DynamicListModel model);
    }

    public interface DynamicListRowInterface{
        void onSelected(int pos, DynamicListModel model);
    }

    //普通带选择按钮
    public class NormalChooseViewHolder extends DyNamicBaseViewHolder{

        @Bind(R.id.normal_info_tv)
        TextView info;

        @Bind(R.id.normal_choose_info_tv)
        TextView chooseInfo;

        @Bind(R.id.normal_time_tv)
        TextView time;

        @Bind(R.id.normal_cofirm_btn)
        Button confrim;

        @Bind(R.id.normal_cancel_btn)
        Button cancel;

        private NormalChooseInterface mInterface;

        private DynamicListModel mModel;

        private View.OnClickListener listerner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.normal_cancel_btn:
                        if (mInterface != null )
                            mInterface.onChoose(mModel,false);
                        break;
                    case R.id.normal_cofirm_btn:
                        if (mInterface != null )
                            mInterface.onChoose(mModel,true);
                        break;
                }
            }
        };

        public NormalChooseViewHolder(View view , NormalChooseInterface mInterface){
            super(view);
            this.mInterface = mInterface;

            confrim.setOnClickListener(listerner);
            cancel.setOnClickListener(listerner);
        }

        @Override
        public void bind(DynamicListModel model) {
            info.setText(model.info);

            time.setText(model.time);

            mModel = model;

            if (model.hasChoose){

                confrim.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                chooseInfo.setVisibility(View.VISIBLE);
                chooseInfo.setText(model.butonChooseInfo);

            }else {
                confrim.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
                chooseInfo.setVisibility(View.GONE);
            }



        }
    }

    public class TitlleInfoViewHolder extends DyNamicBaseViewHolder{

        @Bind(R.id.row_title_tv)
        TextView name;

        @Bind(R.id.row_info_tv)
        TextView detail;

        public TitlleInfoViewHolder(View view){
            super(view);

        }

        @Override
        public void bind(DynamicListModel model) {

            name.setText(model.title);

            detail.setText(model.info);

        }
    }

    //带数字的 cell
    public class NumberTitleInfoViewHolder extends DyNamicBaseViewHolder{

        @Bind(R.id.row_number_tv)
        TextView number;

        @Bind(R.id.row_number_title_tv)
        TextView title;

        @Bind(R.id.row_number_info_tv)
        TextView info;

        public NumberTitleInfoViewHolder(View view){
            super(view);
        }

        @Override
        public void bind(DynamicListModel model) {

            number.setText(model.number + "");

            title.setText(model.title + "");

            info.setText(model.info + "");

        }
    }


}
