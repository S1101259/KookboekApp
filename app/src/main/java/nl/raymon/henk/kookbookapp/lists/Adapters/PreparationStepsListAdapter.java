package nl.raymon.henk.kookbookapp.lists.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nl.raymon.henk.kookbookapp.R;
import nl.raymon.henk.kookbookapp.models.PreparationStep;

public class PreparationStepsListAdapter extends RecyclerView.Adapter<PreparationStepsListAdapter.PreparationStepsViewHolder> {

    private List<PreparationStep> preparationStepList;

    public PreparationStepsListAdapter() {
        this.preparationStepList = new ArrayList<>();
    }

    @Override
    public PreparationStepsListAdapter.PreparationStepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.preparation_step_list_item, parent, false);
        return new PreparationStepsViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(PreparationStepsListAdapter.PreparationStepsViewHolder holder, int position) {
        holder.preparationStepTitle.setText(preparationStepList.get(position).getPart());
        holder.preparationStepDescription.setText(preparationStepList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return preparationStepList.size();
    }

    public void addPreparationStep(PreparationStep preparationStep) {
        this.preparationStepList.add(preparationStep);
    }

    public void removePreparationStep(PreparationStep preparationStep) {
        this.preparationStepList.remove(preparationStep);
    }

    public List<PreparationStep> getPreparationStepList() {
        return preparationStepList;
    }

    public static class PreparationStepsViewHolder extends RecyclerView.ViewHolder {
        TextView preparationStepTitle;
        TextView preparationStepDescription;


        PreparationStepsViewHolder(LinearLayout itemView) {
            super(itemView);
            preparationStepTitle = itemView.findViewById(R.id.preparationStepTitle);
            preparationStepDescription = itemView.findViewById(R.id.preparationStepDescription);
        }
    }
}
