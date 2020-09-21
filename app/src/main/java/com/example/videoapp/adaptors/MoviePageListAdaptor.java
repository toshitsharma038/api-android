package com.example.videoapp.adaptors;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videoapp.OnItemClick;
import com.example.videoapp.R;
import com.example.videoapp.databinding.VideoItemsBinding;
import com.example.videoapp.models.Result;
import com.squareup.picasso.Picasso;

import static com.example.videoapp.constant.Constant.IMAGE_SIZE;
import static com.example.videoapp.constant.Constant.IMAGE_URL;

public class MoviePageListAdaptor extends PagedListAdapter<Result,MoviePageListAdaptor.MViewModel> {


    private OnItemClick itemClick;

    public MoviePageListAdaptor(OnItemClick itemClick) {
        super(diffCallback);
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public MViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VideoItemsBinding itemsBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.video_items,parent,false
        );
        return new MViewModel(itemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MViewModel holder, int position) {
        holder.bind(getItem(position));

    }

    public static DiffUtil.ItemCallback<Result> diffCallback =
            new DiffUtil.ItemCallback<Result>() {
                @Override
                public boolean areItemsTheSame(@NonNull Result oldItem, @NonNull Result newItem) {
                    return oldItem.getId()==newItem.getId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Result oldItem, @NonNull Result newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public class MViewModel extends RecyclerView.ViewHolder implements View.OnClickListener {

        private VideoItemsBinding itemsBinding;
        public MViewModel(VideoItemsBinding videoItemsBinding) {
            super(videoItemsBinding.getRoot());

            itemsBinding = videoItemsBinding;
            itemView.setOnClickListener(this);
        }

        public void bind(Result item) {
            if (item!=null){
                String thumUrl = IMAGE_URL + IMAGE_SIZE + item.getBackdropPath();

                Picasso.get().load(thumUrl).into(itemsBinding.thumb);
                itemsBinding.mtitle.setText(item.getTitle());
            }
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            Result movie = getItem(pos);
            itemClick.OnMovieItemClick(movie);
        }
    }
}
