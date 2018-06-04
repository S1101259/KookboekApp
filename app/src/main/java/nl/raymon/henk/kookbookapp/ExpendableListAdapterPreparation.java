package nl.raymon.henk.kookbookapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import nl.raymon.henk.kookbookapp.models.PreparationStep;

public class ExpendableListAdapterPreparation extends BaseExpandableListAdapter {

    private Context context;
    private List<PreparationStep> preparationSteps;

    ExpendableListAdapterPreparation(Context context, List<PreparationStep> preparationSteps){
        this.context = context;
        this.preparationSteps = preparationSteps;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int i) {
        return preparationSteps != null ? preparationSteps.size() : 0;
    }

    @Override
    public Object getGroup(int i) {
        return "Voorbereiding";
    }

    @Override
    public Object getChild(int i, int i1) {
        return this.preparationSteps.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.single_recipe_list_group, null);
        }

        ((TextView)view.findViewById(R.id.group_title)).setText(getGroup(1).toString());

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final PreparationStep preparationStep = preparationSteps.get(i1);


        if (view == null){
            LayoutInflater inflater= (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =  inflater.inflate(R.layout.single_recipe_list_group_item, null);
        }

        ((TextView)view.findViewById(R.id.recipe_group_item_title)).setText(preparationStep.getPart());
        ((TextView)view.findViewById(R.id.recipe_group_item_desc)).setText(preparationStep.getDescription());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}