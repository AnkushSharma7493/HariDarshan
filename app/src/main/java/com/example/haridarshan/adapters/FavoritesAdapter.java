package com.example.haridarshan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.haridarshan.R;
import com.example.haridarshan.models.Article;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<Article> favoriteArticles;

    private ArticleAdapter.OnItemClickListener listener;

    public FavoritesAdapter(List<Article> savedArticles) {
        this.favoriteArticles = savedArticles;
    }

    public interface OnItemClickListener {
        void onItemClick(Article article);
    }

    public void setOnItemClickListener(ArticleAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = favoriteArticles.get(position);
        holder.title.setText(article.getTitle());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(article);
            }
        });

    }

    @Override
    public int getItemCount() {
        return favoriteArticles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.articleTitle);
            description = itemView.findViewById(R.id.articleDescription);
            image = itemView.findViewById(R.id.articleImage);
        }
    }
}

