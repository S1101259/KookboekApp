package nl.raymon.henk.kookbookapp.standaloneFragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import nl.raymon.henk.kookbookapp.R;
import nl.raymon.henk.kookbookapp.activities.SideNavigationActivity;
import nl.raymon.henk.kookbookapp.database.AppDatabase;
import nl.raymon.henk.kookbookapp.lists.Adapters.CookingStepsListAdapter;
import nl.raymon.henk.kookbookapp.lists.Adapters.IngredientsListAdapter;
import nl.raymon.henk.kookbookapp.lists.Adapters.PreparationStepsListAdapter;
import nl.raymon.henk.kookbookapp.models.CookingStep;
import nl.raymon.henk.kookbookapp.models.PreparationStep;
import nl.raymon.henk.kookbookapp.models.Recipe;


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

    private TextView preparationTimeNumber;
    private TextView recipeNameText;
    private Spinner recipyTypeSpinner;
    private Switch shareOnlineSwitch;
    private TextView servingTip;
    private Button addNewRecipeButton;
    private File localFile = null;

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
        preparationTimeNumber = view.findViewById(R.id.preparationTimeNumber);
        recipeNameText = view.findViewById(R.id.recipeNameText);
        recipyTypeSpinner = view.findViewById(R.id.recipyTypeSpinner);
        shareOnlineSwitch = view.findViewById(R.id.shareOnlineSwitch);
        servingTip = view.findViewById(R.id.recipeServingTip);
        addNewRecipeButton = view.findViewById(R.id.addNewRecipe);
        addNewRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewRecipe();
            }
        });


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
                if (cookingStepName.getText().toString().isEmpty() || cookingStepDescription.getText().toString().isEmpty()) {
                    return;
                }
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
                if (preparationStepTitle.getText().toString().isEmpty() || preparationStepDescription.getText().toString().isEmpty()) {
                    return;
                }
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
                if (ingredient.getText().toString().isEmpty()) {
                    return;
                }
                ingredientsListAdapter.addIngredient(ingredient.getText().toString());
                ingredientsListAdapter.notifyDataSetChanged();
                ingredient.setText(null);
            }
        });

        return view;
    }

    public void createNewRecipe() {
        if (cookingStepsAdapter.getCookingStepsList().isEmpty() || ingredientsListAdapter.getIngredientsList().isEmpty() || recipeNameText.getText().toString().isEmpty() || recipyTypeSpinner.getSelectedItem().toString().isEmpty() || preparationTimeNumber.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Nog niet alle verplichte velden zijn ingevuld!", Toast.LENGTH_SHORT).show();
            return;
        }

        final Recipe recipe = new Recipe(
                cookingStepsAdapter.getCookingStepsList(),
                Integer.parseInt(preparationTimeNumber.getText().toString()),
                ingredientsListAdapter.getIngredientsList(),
                recipeNameText.getText().toString(),
                preparationStepsListAdapter.getPreparationStepList(),
                servingTip.getText().toString(),
                recipyTypeSpinner.getSelectedItem().toString(),
                "https://firebasestorage.googleapis.com/v0/b/kookboekapp-1337.appspot.com/o/recipe_images%2Fplaceholder.jpg?alt=media&token=9bba5fce-58ec-48b1-91c9-41a5f669ac0d");

        if (shareOnlineSwitch.isChecked()) {

            if (isOnline()){
                Log.d("RECIPE", "createNewRecipe: ONLINE");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference();
                reference.child("recipes").push().setValue(recipe).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Recept succesvol Aangemaakt", Toast.LENGTH_SHORT).show();
                        createNewLocalRecipe(recipe);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Er is iets misgegaan bij het uploaden van het recept", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "Geen internetverbinding, recept wordt lokaal aangemaakt", Toast.LENGTH_SHORT).show();
                createNewLocalRecipe(recipe);
            }

        } else {
            Toast.makeText(getContext(), "Recept succesvol Aangemaakt", Toast.LENGTH_SHORT).show();
            createNewLocalRecipe(recipe);
        }

        Log.d("RECIPE", "createNewRecipe: " + recipe);
    }

    private void createNewLocalRecipe(Recipe recipe) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(recipe.getImage());
        StorageReference recipeImageRef = storageReference.child("images/recipeImage.jpg");
        try {

            localFile = File.createTempFile("images", ".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                //Temp file created

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Error handling
                localFile = null;
            }
        });

        if (localFile != null) {
            recipe.setImage(localFile.getPath());
        }
        AppDatabase.getInstance(getActivity().getApplicationContext()).recipeDao().insertRecipe(recipe);
//                ((SideNavigationActivity) getActivity()).goToMyRecipes(getView());
        ((SideNavigationActivity) getActivity()).goToHome();
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

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
