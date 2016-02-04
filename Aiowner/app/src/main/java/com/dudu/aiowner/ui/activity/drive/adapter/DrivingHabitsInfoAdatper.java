package com.dudu.aiowner.ui.activity.drive.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dudu.aiowner.R;
import com.dudu.aiowner.databinding.DrivingHabitsItemBinding;
import com.dudu.aiowner.ui.activity.drive.observable.DrvingHabitsItemObservable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengjun on 2016/2/11.
 * Description :
 */
public class DrivingHabitsInfoAdatper extends RecyclerView.Adapter<DrivingHabitsInfoAdatper.DrivingHabitsItemHolder>{
    private List<DrvingHabitsItemObservable> drvingHabitsItemObservableList;


    public DrivingHabitsInfoAdatper(List<DrvingHabitsItemObservable> drvingHabitsItemObservableList) {
        this.drvingHabitsItemObservableList = drvingHabitsItemObservableList;
        if (drvingHabitsItemObservableList == null){
            this.drvingHabitsItemObservableList = new ArrayList<DrvingHabitsItemObservable>();
            this.drvingHabitsItemObservableList.add(new DrvingHabitsItemObservable());
            this.drvingHabitsItemObservableList.add(new DrvingHabitsItemObservable());
            this.drvingHabitsItemObservableList.add(new DrvingHabitsItemObservable());
            this.drvingHabitsItemObservableList.add(new DrvingHabitsItemObservable());
        }
    }

    @Override
    public DrivingHabitsItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.driving_habits_item, parent, false);

        return new DrivingHabitsItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DrivingHabitsItemHolder holder, int position) {
        holder.bind(drvingHabitsItemObservableList.get(position));
    }

    @Override
    public int getItemCount() {
        return drvingHabitsItemObservableList.size();
    }



    public static  class DrivingHabitsItemHolder extends RecyclerView.ViewHolder{
        private DrivingHabitsItemBinding drivingHabitsItemBinding;
        public DrivingHabitsItemHolder(View itemView) {
            super(itemView);
            drivingHabitsItemBinding= DataBindingUtil.bind(itemView);
        }

        public void bind(@NonNull DrvingHabitsItemObservable dvingHabitsItemObservable){
            drivingHabitsItemBinding.setDrvingHabitsItemObservable(dvingHabitsItemObservable);
        }
    }
}
