package uddipan.com.updatetablayout_main;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FruitAdapter.FruitAdapterListener {

    RecyclerView recyclerView;
    List<Fruits> fruitsList;
    FruitAdapter adapter;
    SearchView searchView;

    //Array of fruits names
    String[] names={"Apple","Strawberry","Pomegranates","Oranges","Watermelon","Bananas","Kiwi","Tomato","Grapes"};
    //Array of fruits desc
    String[] sntfNames={"Malus Domestica","Fragaria Ananassa ","Punica Granatum","Citrus Sinensis","Citrullus Vulgaris","Musa Acuminata","Actinidia Deliciosa","Solanum Lycopersicum","Vitis vinifera"};

    //Array of fruits images
    int[] image ={R.drawable.apple,R.drawable.strawberry,R.drawable.pomegranates,R.drawable.oranges,R.drawable.watermelon,R.drawable.banana,R.drawable.kiwi,R.drawable.tomato,R.drawable.grapes};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finding views
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        //item decorator to separate the items
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        //setting layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("RecyclerView");

        // white background notification bar
        whiteNotificationBar(recyclerView);

        //initialize fruits list
        fruitsList = new ArrayList<>();



        adapter = new FruitAdapter(this,fruitsList,this);

        //method to load fruits
        loadfruits();
        //onItemClickListener
        recyclerView.addOnItemTouchListener(new FruitTouchListener(getApplicationContext(), new FruitTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Place item click action here
                Toast.makeText(getApplicationContext(),"You clicked "+names[position],Toast.LENGTH_SHORT).show();
            }
        }));


    }

    private void loadfruits() {

        for(int i=0; i<names.length;i++) {

            fruitsList.add(new Fruits(names[i], sntfNames[i], image[i]));
        }
         adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setSystemUiVisibility(0);
        }
    }

    @Override
    public void onFruitSelected(Fruits fruits) {
        Toast.makeText(getApplicationContext(), "Selected: " + fruits.getName() , Toast.LENGTH_LONG).show();
    }

}
