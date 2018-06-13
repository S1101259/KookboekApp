package nl.raymon.henk.kookbookapp.lists.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nl.raymon.henk.kookbookapp.R;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientsViewHolder> {

    private List<String> ingredients;

    public IngredientsListAdapter() {
        this.ingredients = new ArrayList<>();
    }

    @Override
    public IngredientsListAdapter.IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_item, parent, false);
        return new IngredientsViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(IngredientsListAdapter.IngredientsViewHolder holder, int position) {
        holder.ingredient.setText(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }

    public void removeIngredient(String ingredient) {
        this.ingredients.remove(ingredient);
    }

    public List<String> getIngredientsList () {
        return ingredients;
    }


    public static class IngredientsViewHolder extends RecyclerView.ViewHolder {
        TextView ingredient;

        IngredientsViewHolder(LinearLayout itemView) {
            super(itemView);
            ingredient = itemView.findViewById(R.id.ingredient);

        }
    }
}
