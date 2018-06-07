package nl.raymon.henk.kookbookapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nl.raymon.henk.kookbookapp.models.Recipe;

public class OnlineRecipeListAdapter extends RecyclerView.Adapter<OnlineRecipeListAdapter.OnlineRecipeListViewHolder> {
    //    private String[] myDataset;
    private SideNavigationActivity sideNavigationActivity;
    private List<Recipe> myDataset;
    private List<Recipe> selectedList;

    public OnlineRecipeListAdapter(List<Recipe> myDataset, SideNavigationActivity sideNavigationActivity) {
        this.myDataset = myDataset;
        this.sideNavigationActivity = sideNavigationActivity;
        this.selectedList = new ArrayList<>();
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

        String img = myDataset.get(position).getImage();
        Log.d("Recipe Image in list", "onBindViewHolder: Recipe image:" + img);
        if (img != null) {
            File image = new File(img);
            Picasso.with(onlineRecipeListViewHolder.itemView.getContext()).load(image).transform(new CircleTransform()).into(onlineRecipeListViewHolder.imageView);
//            Picasso.with(onlineRecipeListViewHolder.itemView.getContext()).load(img).transform(new CircleTransform()).into(onlineRecipeListViewHolder.imageView);
        }
        onlineRecipeListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sideNavigationActivity.goToRecipe(myDataset.get(position));
            }
        });

        onlineRecipeListViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedList.add(myDataset.get(position));
                } else {
                    selectedList.remove(myDataset.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return myDataset.size();
    }

    public List<Recipe> getSelectedList() {
        return selectedList;
    }

    public static class OnlineRecipeListViewHolder extends RecyclerView.ViewHolder {
        public TextView recipeName;
        public TextView recipeType;
        public CheckBox checkBox;
        public ImageView imageView;


        public OnlineRecipeListViewHolder(LinearLayout itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.listItemTitle);
            recipeType = itemView.findViewById(R.id.listItemType);
            checkBox = itemView.findViewById(R.id.recipe_check);
            imageView = itemView.findViewById(R.id.list_image);
        }
    }
}
