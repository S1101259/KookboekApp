package nl.raymon.henk.kookbookapp.lists.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nl.raymon.henk.kookbookapp.CircleTransform;
import nl.raymon.henk.kookbookapp.R;
import nl.raymon.henk.kookbookapp.activities.SideNavigationActivity;
import nl.raymon.henk.kookbookapp.models.Recipe;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.OnlineRecipeListViewHolder> {
    public static final int DOWNLOAD = 0;
    public static final int DELETE = 1;

    private SideNavigationActivity sideNavigationActivity;
    private List<Recipe> recipeList;
    private List<Recipe> selectedList;
    private int type;

    public RecipeListAdapter(List<Recipe> recipeList, SideNavigationActivity sideNavigationActivity, int type) {
        this.type = type;
        this.recipeList = recipeList;
        this.sideNavigationActivity = sideNavigationActivity;
        this.selectedList = new ArrayList<>();
    }

    @Override
    public RecipeListAdapter.OnlineRecipeListViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.online_recipe_list_item, parent, false);
        return new OnlineRecipeListViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(OnlineRecipeListViewHolder onlineRecipeListViewHolder, final int position) {
        final Recipe item = recipeList.get(position);

        onlineRecipeListViewHolder.recipeName.setText(item.getName());
        onlineRecipeListViewHolder.recipeType.setText(item.getType());
        onlineRecipeListViewHolder.checkBox.setChecked(item.isChecked());

        String img = item.getImage();
        if (img != null) {
            if (URLUtil.isHttpsUrl(img) || URLUtil.isHttpUrl(img)) {
                Picasso.with(onlineRecipeListViewHolder.itemView.getContext()).load(img).transform(new CircleTransform()).into(onlineRecipeListViewHolder.imageView);
            } else {
                File image = new File(img);
                Picasso.with(onlineRecipeListViewHolder.itemView.getContext()).load(image).transform(new CircleTransform()).into(onlineRecipeListViewHolder.imageView);
            }
        }
        onlineRecipeListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sideNavigationActivity.goToRecipe(item);
            }
        });

        onlineRecipeListViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!item.isChecked()) {
                    item.setChecked(true);
                    selectedList.add(item);
                    if (type == DOWNLOAD) {
                        sideNavigationActivity.setFlag(SideNavigationActivity.DOWNLOAD);
                        return;
                    } else
                        sideNavigationActivity.setFlag(SideNavigationActivity.DELETE);
                    return;
                }

                item.setChecked(false);
                selectedList.remove(item);

                if (selectedList.size() < 1) {
                    sideNavigationActivity.setFlag(SideNavigationActivity.HIDE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public List<Recipe> getSelectedList() {
        return selectedList;
    }

    public static class OnlineRecipeListViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName;
        TextView recipeType;
        CheckBox checkBox;
        public ImageView imageView;


        OnlineRecipeListViewHolder(LinearLayout itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.listItemTitle);
            recipeType = itemView.findViewById(R.id.listItemType);
            checkBox = itemView.findViewById(R.id.recipe_check);
            imageView = itemView.findViewById(R.id.list_image);
        }
    }
}
