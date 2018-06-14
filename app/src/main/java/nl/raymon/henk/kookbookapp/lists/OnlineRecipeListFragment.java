package nl.raymon.henk.kookbookapp.lists;

import android.content.Context;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.raymon.henk.kookbookapp.R;
import nl.raymon.henk.kookbookapp.activities.SideNavigationActivity;
import nl.raymon.henk.kookbookapp.database.AppDatabase;
import nl.raymon.henk.kookbookapp.lists.Adapters.RecipeListAdapter;
import nl.raymon.henk.kookbookapp.models.CookingStep;
import nl.raymon.henk.kookbookapp.models.PreparationStep;
import nl.raymon.henk.kookbookapp.models.Recipe;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnlineRecipeListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OnlineRecipeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnlineRecipeListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private ProgressBar loadingBar;
    ArrayList<Recipe> onlineRecipes;
    RecipeListAdapter recipeListAdapter;
    private File localFile = null;

    public OnlineRecipeListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OnlineRecipeListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OnlineRecipeListFragment newInstance() {
        return new OnlineRecipeListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        ((SideNavigationActivity) getActivity()).setActionBarTitle("Online Recepten");
        ((SideNavigationActivity) getActivity()).setSelectedMenuItem(R.id.online_recipes);

        View view = inflater.inflate(R.layout.fragment_online_recipe_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.onlineRecipesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadingBar = view.findViewById(R.id.loadingBar);
        loadingBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this.getContext(), R.color.colorAppRed), PorterDuff.Mode.MULTIPLY);
        getOnlineRecipes();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionbar_icon : {
                downloadRecipes();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void downloadRecipes() {
        List<Recipe> download = recipeListAdapter.getSelectedList();

        if (download.size() > 0) {
            for (final Recipe recipe : download) {

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
                ((SideNavigationActivity) getActivity()).goToHome();
                Toast.makeText(getContext(), "Recept(en) succesvol gedownload", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getContext(), "Geen recept(en) geselecteerd",Toast.LENGTH_LONG).show();
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

    private void getOnlineRecipes() {
        onlineRecipes = new ArrayList<Recipe>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("recipes");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<Recipe>> t = new GenericTypeIndicator<ArrayList<Recipe>>() {
                };

                for(DataSnapshot d: dataSnapshot.getChildren()){
                    Recipe recipe = d.getValue(Recipe.class);
//                    recipe.setId(d.getKey());
                    onlineRecipes.add(recipe);
                }


                recipeListAdapter = new RecipeListAdapter(onlineRecipes, ((SideNavigationActivity) getActivity()), RecipeListAdapter.DOWNLOAD);
                recyclerView.setAdapter(recipeListAdapter);
                loadingBar.setVisibility(View.GONE);

                LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), R.anim.layout_animation_fall_down);
                recyclerView.setLayoutAnimation(layoutAnimationController);
                recyclerView.getAdapter().notifyDataSetChanged();
                recyclerView.scheduleLayoutAnimation();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Firebase Err", "onCancelled: " + databaseError.toException());
            }
        });


        List<CookingStep> cookingSteps = Arrays.asList(new CookingStep("Vullen Pan", "Vul de pan met water om bla bla allemaal dingen"), new CookingStep("Verhitten", "Verhit de pan tot 10000 graden Celsius"));
        List<PreparationStep> preparationSteps = Arrays.asList(new PreparationStep("dwadwa", "description"), new PreparationStep("2", "description"));
        List<String> ingredients = Arrays.asList("water", "iets anders");

    }
}
