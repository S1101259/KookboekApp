package nl.raymon.henk.kookbookapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnlineRecipeListAdapter extends RecyclerView.Adapter<OnlineRecipeListAdapter.OnlineRecipeListViewHolder> {
    private String[] myDataset;

    public OnlineRecipeListAdapter(String[] myDataset) {
        this.myDataset = myDataset;
    }

    @Override
    public OnlineRecipeListAdapter.OnlineRecipeListViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.online_recipe_list_item, parent, false);
        TextView v = (TextView) linearLayout.findViewById(R.id.listItemTitle);
        return new OnlineRecipeListViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(OnlineRecipeListViewHolder onlineRecipeListViewHolder, int position) {
        onlineRecipeListViewHolder.myTextView.setText(myDataset[position]);
    }

    @Override
    public int getItemCount() {
        return myDataset.length;
    }

    public static class OnlineRecipeListViewHolder extends RecyclerView.ViewHolder {
        public TextView myTextView;

        public OnlineRecipeListViewHolder(LinearLayout itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.listItemTitle);
        }


    }
}
