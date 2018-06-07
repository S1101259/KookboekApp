package nl.raymon.henk.kookbookapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import nl.raymon.henk.kookbookapp.database.AppDatabase;
import nl.raymon.henk.kookbookapp.dummy.DummyContent;
import nl.raymon.henk.kookbookapp.dummy.DummyContent.DummyItem;
import nl.raymon.henk.kookbookapp.models.Recipe;

import java.util.ArrayList;
import java.util.List;


public class MyRecipesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private OnlineRecipeListAdapter onlineRecipeListAdapter;
    private View view;
    private FloatingActionButton floatingActionButton;

    public MyRecipesFragment() {
        // Required empty public constructor
    }

    public static MyRecipesFragment newInstance() {
        MyRecipesFragment fragment = new MyRecipesFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        ((SideNavigationActivity) getActivity()).setActionBarTitle("Mijn Recepten");
        ((SideNavigationActivity) getActivity()).setSelectedMenuItem(R.id.my_recipes);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_offline_recipe_list, container, false);
        floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SideNavigationActivity) getActivity()).goToNewRecipe();
            }
        });


        renderRecyclerView();
        view.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRecipes();
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

    public void deleteRecipes(){
        List<Recipe> recipes = this.onlineRecipeListAdapter.getSelectedList();
        if(recipes.size() > 0){
            for (Recipe recipe: recipes) {
                AppDatabase.getInstance(getActivity().getApplicationContext()).recipeDao().delete(recipe);
            }
            Toast.makeText(getContext(), "Recept(en) succesvol verwijderd", Toast.LENGTH_SHORT).show();
            renderRecyclerView();
        }else {
            Toast.makeText(getContext(), "Geen recepten geselecteerd",Toast.LENGTH_LONG).show();
        }
    }

    public void renderRecyclerView(){
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.offlineRecipesRecyclerView);
        onlineRecipeListAdapter = new OnlineRecipeListAdapter(AppDatabase.getInstance(getActivity().getApplicationContext()).recipeDao().getAll(), ((SideNavigationActivity)getActivity()));
        recyclerView.setAdapter(onlineRecipeListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
