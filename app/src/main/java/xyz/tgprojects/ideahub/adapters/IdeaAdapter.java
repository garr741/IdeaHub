package xyz.tgprojects.ideahub.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.tgprojects.ideahub.R;
import xyz.tgprojects.ideahub.models.Idea;

public class IdeaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Idea> ideaList;

    public IdeaAdapter() {
        ideaList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.idea_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder aHolder, int position) {
        Idea idea = ideaList.get(position);
        ViewHolder holder = (ViewHolder) aHolder;
        holder.title.setText(idea.getTitle());
        holder.description.setText(idea.getDescription());
    }

    @Override
    public int getItemCount() {
        return ideaList.size();
    }

    final class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.idea_layout)
        LinearLayout ideaLayout;

        @BindView(R.id.idea_title)
        TextView title;

        @BindView(R.id.idea_description)
        TextView description;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ideaLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public void swapData(List<Idea> newData) {
        ideaList.clear();
        ideaList.addAll(newData);
        notifyDataSetChanged();
    }
}
