package com.example.practica11_apis_rest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.practica11_apis_rest.models.Pokemon;
import com.example.practica11_apis_rest.models.PokemonList;
import com.example.practica11_apis_rest.pokeapi.PokeApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PokeApiService service = retrofit.create(PokeApiService.class);
        for (int offset = 0; offset < 1154; offset += 20) {
            getPokemonList(service, 20, offset);
        }



    }

    public void pokemonById(PokeApiService pokeService) {
        Call<Pokemon> pokeCall =
                pokeService.getPokemonById(Integer.toString((int) (Math.random() *
                        807 + 1)));
        pokeCall.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call,
                                   Response<Pokemon> response) {
                // ANY CODE THAT DEPENDS ON THE RESULT OF THE SEARCH HAS TO GO HERE
                Pokemon foundPoke = response.body();
                // check if the body isn't null
                if (foundPoke != null) {
                    System.out.println("POKEMON NAME " + foundPoke.getName());
                    System.out.println("POKEMON HEIGHT " + foundPoke.getHeight());
                    System.out.println("POKEMON WEIGHT " + foundPoke.getWeight());
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                // TOAST THE FAILURE
                Toast.makeText(MainActivity.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getPokemonList(PokeApiService pokeService, int limit, int offset) {
        Call<PokemonList> pokeCall =
                pokeService.getPokemonList(limit, offset);
        pokeCall.enqueue(new Callback<PokemonList>() {
            @Override
            public void onResponse(Call<PokemonList> call, Response<PokemonList> response) {
                PokemonList foundList = response.body();

                if (foundList != null) {
                    for (Pokemon name : foundList.getResults()) {
                        System.out.println(name.getName());

                    }
                }
                else {
                    System.out.println("No entra");
                }
            }

            @Override
            public void onFailure(Call<PokemonList> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}