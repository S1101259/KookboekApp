package nl.raymon.henk.kookbookapp.lists.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import nl.raymon.henk.kookbookapp.R;

public class ExpandableListAdapterIngredients extends BaseExpandableListAdapter {

    private Context context;
    private List<String> ingredients;

    public ExpandableListAdapterIngredients(Context context, List<String> ingredients){
        this.context = context;
        this.ingredients = ingredients;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int i) {
        return ingredients!= null ? ingredients.size(): 0;
    }

    @Override
    public Object getGroup(int i) {
        return "Ingrediënten:";
    }

    @Override
    public Object getChild(int i, int i1) {
        return this.ingredients.get(i1);
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
        final String ingredient = ingredients.get(i1);


        if (view == null){
            LayoutInflater inflater= (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =  inflater.inflate(R.layout.single_recipe_list_group_item, null);
        }

        ((TextView)view.findViewById(R.id.recipe_group_item_title)).setVisibility(View.GONE);
        ((TextView)view.findViewById(R.id.recipe_group_item_desc)).setText(ingredient);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
