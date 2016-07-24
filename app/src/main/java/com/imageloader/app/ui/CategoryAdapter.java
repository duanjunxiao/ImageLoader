package com.imageloader.app.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.imageloader.app.R;

import org.simple.imageloader.core.SimpleImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duanjunxiao.
 */
public class CategoryAdapter extends RecyclerView.Adapter {

    public interface Listener {
        void onActionCategoryItemClick(int position);
    }

    public static final int TYPE_ITEM = 1;

    public static final int LINE_COUNT = 3;

    public static final int ITEM_SPAN_SIZE = 1;

    List<String> contentBeanList;

    private Listener listener;
    private Context context;

    public CategoryAdapter(Context context) {
        this(context, null);
    }

    public CategoryAdapter(Context context, List<String> data) {
        this.context = context;
        this.contentBeanList = new ArrayList<>();
        if (data != null && !data.isEmpty()) {
            contentBeanList.addAll(data);
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void clear() {
        int size = contentBeanList.size();
        contentBeanList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(List<String> beans) {
        if (beans == null || beans.isEmpty()) {
            return;
        }
        int start = beans.size();
        contentBeanList.addAll(beans);
        notifyItemRangeInserted(start, beans.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_ITEM) {
            //inflate your layout and pass it to view holder
            View content = inflater.inflate(R.layout.layout_category_item,
                    parent, false);
            return new VHItem(content);
        }
        throw new RuntimeException("there is no type that matches the type " +
                viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VHItem) {
            VHItem vhItem = (VHItem) holder;
            SimpleImageLoader.getInstance().displayImage(vhItem.icon, getItem(position));
            vhItem.divider.setVisibility(isPositionHide(position) ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return contentBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    private String getItem(int position) {
        return contentBeanList.get(position);
    }

    private boolean isPositionHide(int position) {
        if (position % LINE_COUNT == 0) {
            return true;
        }
        return false;
    }

    class VHItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView icon;
        public final View divider;

        public VHItem(View v) {
            super(v);
            this.icon = (ImageView) v.findViewById(R.id.icon);
            this.divider = v.findViewById(R.id.divider);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener == null) {
                return;
            }
            int position = getAdapterPosition();
            if (listener != null) {
                listener.onActionCategoryItemClick(position);
            }
        }
    }

}