package nl.raymon.henk.kookbookapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnlineRecipeListAdapter extends RecyclerView.Adapter<OnlineRecipeListAdapter.OnlineRecipeListViewHolder> {
    private String[] myDataset;
    private SideNavigationActivity sideNavigationActivity;

    public OnlineRecipeListAdapter(String[] myDataset, SideNavigationActivity sideNavigationActivity) {
        this.myDataset = myDataset;
        this.sideNavigationActivity = sideNavigationActivity;
    }

    @Override
    public OnlineRecipeListAdapter.OnlineRecipeListViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.online_recipe_list_item, parent, false);
        return new OnlineRecipeListViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(OnlineRecipeListViewHolder onlineRecipeListViewHolder, final int position) {
        onlineRecipeListViewHolder.mtTitleView.setText(myDataset[position]);
        onlineRecipeListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sideNavigationActivity.goToRecipe(myDataset[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myDataset.length;
    }

    public static class OnlineRecipeListViewHolder extends RecyclerView.ViewHolder{
        public TextView mtTitleView;

        public OnlineRecipeListViewHolder(LinearLayout itemView) {
            super(itemView);
            mtTitleView = itemView.findViewById(R.id.listItemTitle);
        }


    }
}
