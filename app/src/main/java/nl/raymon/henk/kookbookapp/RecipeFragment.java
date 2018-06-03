package nl.raymon.henk.kookbookapp;

import android.content.Context;
import android.icu.util.Measure;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;

import nl.raymon.henk.kookbookapp.models.Recipe;


public class RecipeFragment extends Fragment{
    private OnFragmentInteractionListener mListener;
    private Recipe recipe;
    private static final String ARG_PARAM1 = "recipe";

    public RecipeFragment() {
        // Required empty public constructor
    }

    public static RecipeFragment newInstance(Recipe recipe) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = (Recipe) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((SideNavigationActivity) getActivity()).setActionBarTitle(recipe.getName());
        View v = inflater.inflate(R.layout.fragment_recipe, container, false);
        ((TextView)v.findViewById(R.id.recipe_type)).setText(recipe.getType());
        ((TextView)v.findViewById(R.id.recipe_preparation_time)).setText((recipe.getCooking_time() + " Minuten"));

        ((TextView) v.findViewById(R.id.recipe_serving_tip)).setText(recipe.getServing().isEmpty()? "Geen Serveertip bekend.": recipe.getServing());

        ExpandableListView cookingSteps = v.findViewById(R.id.cookingSteps);
        ExpandableListAdapter cookingStepsAdapter = new ExpandableListAdapterCooking(this.getContext(), recipe.getCooking());
        cookingSteps.setAdapter(cookingStepsAdapter);
        cookingSteps.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });

        ExpandableListView ingredients = v.findViewById(R.id.ingredients);
        ExpandableListAdapter ingredientsAdapter = new ExpandableListAdapterIngredients(this.getContext(), recipe.getIngredients());
        ingredients.setAdapter(ingredientsAdapter);
        ingredients.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });

        ExpandableListView preparationSteps = v.findViewById(R.id.preparationSteps);
        ExpandableListAdapter preparationStepsAdapter = new ExpendableListAdapterPreparation(this.getContext(), recipe.getPreparation());
        preparationSteps.setAdapter(preparationStepsAdapter);
        preparationSteps.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });


        return v;
    }

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                    DisplayMetrics metrics = getResources().getDisplayMetrics();
                    totalHeight += (listItem.getMeasuredHeight() + metrics.density);
                }
            }
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        totalHeight += (listView.getDividerHeight() * listAdapter.getGroupCount());
        params.height = totalHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        }
}
