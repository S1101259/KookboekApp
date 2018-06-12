package nl.raymon.henk.kookbookapp.standaloneFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import nl.raymon.henk.kookbookapp.R;
import nl.raymon.henk.kookbookapp.activities.SideNavigationActivity;
import nl.raymon.henk.kookbookapp.lists.Adapters.CookingStepsListAdapter;
import nl.raymon.henk.kookbookapp.lists.Adapters.IngredientsListAdapter;
import nl.raymon.henk.kookbookapp.lists.Adapters.PreparationStepsListAdapter;
import nl.raymon.henk.kookbookapp.models.CookingStep;
import nl.raymon.henk.kookbookapp.models.PreparationStep;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewRecipeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewRecipeFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private RecyclerView cookingStepsView;
    private RecyclerView ingredientsView;
    private RecyclerView preparationStepsView;

    private CookingStepsListAdapter cookingStepsAdapter;
    private IngredientsListAdapter ingredientsListAdapter;
    private PreparationStepsListAdapter preparationStepsListAdapter;

    private TextView cookingStepName;
    private TextView cookingStepDescription;
    private Button cookingStepAddButton;

    private TextView ingredient;
    private Button ingredientsAddButton;

    private TextView preparationStepTitle;
    private TextView preparationStepDescription;
    private Button preparationStepAddButton;

    public NewRecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewRecipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewRecipeFragment newInstance() {
        return new NewRecipeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((SideNavigationActivity) getActivity()).setActionBarTitle("Nieuw Recept");
        ((SideNavigationActivity) getActivity()).setSelectedMenuItem(R.id.new_recipe);

        View view = inflater.inflate(R.layout.fragment_new_recipe, container, false);
        //Create recyclerviews
        cookingStepsView = (RecyclerView) view.findViewById(R.id.cookingStepsRecyclerView);
        cookingStepsView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ingredientsView = (RecyclerView) view.findViewById(R.id.ingredientsRecyclerView);
        ingredientsView.setLayoutManager(new LinearLayoutManager(getActivity()));

        preparationStepsView = (RecyclerView) view.findViewById(R.id.preparationStepsRecyclerView);
        preparationStepsView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //Create adapters
        cookingStepsAdapter = new CookingStepsListAdapter();
        cookingStepsView.setAdapter(cookingStepsAdapter);
        cookingStepsView.getAdapter().notifyDataSetChanged();

        ingredientsListAdapter = new IngredientsListAdapter();
        ingredientsView.setAdapter(ingredientsListAdapter);
        ingredientsView.getAdapter().notifyDataSetChanged();

        preparationStepsListAdapter = new PreparationStepsListAdapter();
        preparationStepsView.setAdapter(preparationStepsListAdapter);
        preparationStepsView.getAdapter().notifyDataSetChanged();


        //Cooking steps fields and button
        cookingStepName = view.findViewById(R.id.cookingStepTitle);
        cookingStepDescription = view.findViewById(R.id.cookingStepDescription);
        cookingStepAddButton = view.findViewById(R.id.cookingStepAddButton);
        cookingStepAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CookingStep cookingStep = new CookingStep(cookingStepName.getText().toString(), cookingStepDescription.getText().toString());
                cookingStepName.setText(null);
                cookingStepDescription.setText(null);
                cookingStepsAdapter.addCookingStep(cookingStep);
                cookingStepsAdapter.notifyDataSetChanged();
            }
        });

        //Preparation steps fields and button
        preparationStepTitle = view.findViewById(R.id.preparationStepTitle);
        preparationStepDescription = view.findViewById(R.id.preparationStepDescription);
        preparationStepAddButton = view.findViewById(R.id.preparationStepAddButton);
        preparationStepAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreparationStep preparationStep = new PreparationStep(preparationStepTitle.getText().toString(), preparationStepDescription.getText().toString());
                preparationStepTitle.setText(null);
                preparationStepDescription.setText(null);
                preparationStepsListAdapter.addPreparationStep(preparationStep);
                preparationStepsListAdapter.notifyDataSetChanged();
            }
        });


        //Ingredients field and button
        ingredient = view.findViewById(R.id.ingredientName);
        ingredientsAddButton = view.findViewById(R.id.ingredientAddButton);
        ingredientsAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientsListAdapter.addIngredient(ingredient.getText().toString());
                ingredientsListAdapter.notifyDataSetChanged();
                ingredient.setText(null);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
