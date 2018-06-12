package nl.raymon.henk.kookbookapp.lists.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nl.raymon.henk.kookbookapp.R;
import nl.raymon.henk.kookbookapp.models.CookingStep;

public class CookingStepsListAdapter extends RecyclerView.Adapter<CookingStepsListAdapter.CookingStepsViewHolder> {

    private List<CookingStep> cookingStepList;

    public CookingStepsListAdapter() {
        this.cookingStepList = new ArrayList<>();
    }


    @Override
    public CookingStepsListAdapter.CookingStepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.cooking_step_list_item, parent, false);
        return new CookingStepsViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(CookingStepsListAdapter.CookingStepsViewHolder holder, int position) {
        holder.cookingStepTitle.setText(cookingStepList.get(position).getStep());
        holder.cookingStepDescription.setText(cookingStepList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return cookingStepList.size();
    }

    public void addCookingStep(CookingStep cookingStep) {
        this.cookingStepList.add(cookingStep);
    }

    public void removeCookingStep(CookingStep cookingStep) {
        this.cookingStepList.remove(cookingStep);
    }

    public static class CookingStepsViewHolder extends RecyclerView.ViewHolder {
        TextView cookingStepTitle;
        TextView cookingStepDescription;


        CookingStepsViewHolder(LinearLayout itemView) {
            super(itemView);
            cookingStepTitle = itemView.findViewById(R.id.cookingStepTitle);
            cookingStepDescription = itemView.findViewById(R.id.cookingStepDescription);
        }
    }
}

