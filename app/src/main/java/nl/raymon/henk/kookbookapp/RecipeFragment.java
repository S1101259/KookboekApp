package nl.raymon.henk.kookbookapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
            ((SideNavigationActivity) getActivity()).setActionBarTitle(recipe.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipe, container, false);
        ((TextView)v.findViewById(R.id.recipe_type)).setText(recipe.getType());
        ((TextView)v.findViewById(R.id.recipe_preparation_time)).setText((recipe.getCooking_time() + " Minuten"));

        ((TextView) v.findViewById(R.id.recipe_serving_tip)).setText(recipe.getServing().isEmpty()? "Geen Serveertip bekend.": recipe.getServing());

        ExpandableListView cookingSteps = v.findViewById(R.id.cookingSteps);
        ExpandableListAdapter cookingStepsAdapter = new ExpandableListAdapterCooking(this.getContext(), recipe.getCooking());
        cookingSteps.setAdapter(cookingStepsAdapter);

        ExpandableListView ingredients = v.findViewById(R.id.ingredients);
        ExpandableListAdapter ingredientsAdapter = new ExpandableListAdapterIngredients(this.getContext(), recipe.getIngredients());
        ingredients.setAdapter(ingredientsAdapter);

        ExpandableListView preparationSteps = v.findViewById(R.id.preparationSteps);
        ExpandableListAdapter preparationStepsAdapter = new ExpendableListAdapterPreparation(this.getContext(), recipe.getPreparation());
        preparationSteps.setAdapter(preparationStepsAdapter);

        return v;
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
