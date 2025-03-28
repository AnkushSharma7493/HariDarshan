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

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private List<Article> articleList;
    private ArticleAdapter.OnItemClickListener listener;

    public ArticleAdapter(List<Article> articleList) {
        this.articleList = articleList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.articleTitle.setText(article.getTitle());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(article);
            }
        });

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView articleTitle, articleDescription;
        ImageView articleImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            articleTitle = itemView.findViewById(R.id.articleTitle);
            articleDescription = itemView.findViewById(R.id.articleDescription);
            articleImage = itemView.findViewById(R.id.articleImage);
        }
    }
}
