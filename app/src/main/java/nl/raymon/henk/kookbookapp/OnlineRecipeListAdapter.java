package nl.raymon.henk.kookbookapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nl.raymon.henk.kookbookapp.models.Recipe;

public class OnlineRecipeListAdapter extends RecyclerView.Adapter<OnlineRecipeListAdapter.OnlineRecipeListViewHolder> {
//    private String[] myDataset;
    private SideNavigationActivity sideNavigationActivity;
private ArrayList<Recipe> myDataset;
    public OnlineRecipeListAdapter(ArrayList<Recipe> myDataset, SideNavigationActivity sideNavigationActivity) {
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
        onlineRecipeListViewHolder.recipeName.setText(myDataset.get(position).getName());
        onlineRecipeListViewHolder.recipeType.setText(myDataset.get(position).getType());
        onlineRecipeListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sideNavigationActivity.goToRecipe(myDataset.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return myDataset.size();
    }

    public static class OnlineRecipeListViewHolder extends RecyclerView.ViewHolder {
        public TextView recipeName;
        public TextView recipeType;

        public OnlineRecipeListViewHolder(LinearLayout itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.listItemTitle);
            recipeType = itemView.findViewById(R.id.listItemType);
        }


    }
}
