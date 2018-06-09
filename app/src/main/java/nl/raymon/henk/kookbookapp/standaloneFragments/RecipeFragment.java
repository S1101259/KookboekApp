package nl.raymon.henk.kookbookapp.standaloneFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import nl.raymon.henk.kookbookapp.R;
import nl.raymon.henk.kookbookapp.activities.SideNavigationActivity;
import nl.raymon.henk.kookbookapp.database.AppDatabase;
import nl.raymon.henk.kookbookapp.lists.Adapters.ExpandableListAdapterCooking;
import nl.raymon.henk.kookbookapp.lists.Adapters.ExpandableListAdapterIngredients;
import nl.raymon.henk.kookbookapp.lists.Adapters.ExpendableListAdapterPreparation;
import nl.raymon.henk.kookbookapp.models.PreparationStep;
import nl.raymon.henk.kookbookapp.models.Recipe;
import nl.raymon.henk.kookbookapp.models.Stats;


public class RecipeFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private Recipe recipe;
    private static final String ARG_PARAM1 = "recipe";
    private ScrollView scrollView;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AppDatabase.getInstance(getContext()).statsDao().insertStat(new Stats(LocalDate.now().toString()));
        ((SideNavigationActivity) getActivity()).setActionBarTitle(recipe.getName());
        View v = inflater.inflate(R.layout.fragment_recipe, container, false);
        scrollView = v.findViewById(R.id.recipe_scroll_view);
        ((TextView) v.findViewById(R.id.recipe_type)).setText(recipe.getType());
        ((TextView) v.findViewById(R.id.recipe_preparation_time)).setText((recipe.getCooking_time() + " Minuten"));

        ((TextView) v.findViewById(R.id.recipe_serving_tip)).setText(recipe.getServing().isEmpty() ? "Geen Serveertip bekend." : recipe.getServing());

        ExpandableListView cookingSteps = v.findViewById(R.id.cookingSteps);
        ExpandableListAdapter cookingStepsAdapter = new ExpandableListAdapterCooking(this.getContext(), recipe.getCooking());
        cookingSteps.setAdapter(cookingStepsAdapter);
        setListViewOnClickListener(cookingSteps);

        ExpandableListView ingredients = v.findViewById(R.id.ingredients);
        ExpandableListAdapter ingredientsAdapter = new ExpandableListAdapterIngredients(this.getContext(), recipe.getIngredients());
        ingredients.setAdapter(ingredientsAdapter);
        setListViewOnClickListener(ingredients);

        ExpandableListView preparationSteps = v.findViewById(R.id.preparationSteps);
        if (recipe.getPreparation() == null) {
            List<PreparationStep> emptyPreparation = new ArrayList<>();
            emptyPreparation.add(new PreparationStep("Voorbereiding", "Geen voorbereiding vereist"));
            recipe.setPreparation(emptyPreparation);
        }
        ExpandableListAdapter preparationStepsAdapter = new ExpendableListAdapterPreparation(this.getContext(), recipe.getPreparation());
        preparationSteps.setAdapter(preparationStepsAdapter);
        setListViewOnClickListener(preparationSteps);

        ImageView imageView = v.findViewById(R.id.recipe_image);
        if (recipe.getImage() != null) {
            if (URLUtil.isHttpsUrl(recipe.getImage()) || URLUtil.isHttpUrl(recipe.getImage())) {
                Picasso.with(this.getContext()).load(recipe.getImage()).into(imageView);
            } else {
                File image = new File(recipe.getImage());
                Picasso.with(this.getContext()).load(image).into(imageView);
            }
        }
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

    private void setListViewOnClickListener(ExpandableListView listView) {
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
    }

    public interface OnFragmentInteractionListener {
    }
}
