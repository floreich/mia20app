package ml.mia20.mia20app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ml.mia20.mia20app.DataAdapter;
import ml.mia20.mia20app.R;
import ml.mia20.mia20app.RecyclerViewAdapter;


public class KlausurenFragment extends Fragment{


    List<DataAdapter> DataAdapterClassList;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;

    ProgressBar progressBar;

    SwipeRefreshLayout mSwipeRefreshLayout;

    JsonArrayRequest jsonArrayRequest;

    ArrayList<String> SubjectNames;

    RequestQueue requestQueue;

    String HTTP_SERVER_URL = "https://stundenplan.mia20.ml/json/StudentDetails.php";

    View ChildView;

    int RecyclerViewClickedItemPOS;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_klausuren, container, false);

        final FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity().getApplicationContext(),"Clicked",Toast.LENGTH_LONG).show();
                Log.v("myTag","FAB Clicked");
            }
        });


       mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                recyclerView.setAdapter(null);
//                recyclerView.removeAllViewsInLayout();
                DataAdapterClassList.clear();
                  JSON_WEB_CALL();

                mSwipeRefreshLayout.setRefreshing(false);

            }
        });


        DataAdapterClassList = new ArrayList<>();

        SubjectNames = new ArrayList<>();

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView1);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0)
                    fab.hide();
                else if (dy < 0)
                    fab.show();
            }
        });

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        mSwipeRefreshLayout.setRefreshing(true);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        // JSON data web call function call from here.
        JSON_WEB_CALL();

        //RecyclerView Item click listener code starts from here.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {

                    //Getting RecyclerView Clicked item value.
                    RecyclerViewClickedItemPOS = Recyclerview.getChildAdapterPosition(ChildView);

                    //Printing RecyclerView Clicked item clicked value using Toast Message.
                    Toast.makeText(getActivity(), SubjectNames.get(RecyclerViewClickedItemPOS), Toast.LENGTH_LONG).show();

                }

                return false;
            }


            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }


        });


        return v;
    }

    public void JSON_WEB_CALL() {


        jsonArrayRequest = new JsonArrayRequest(HTTP_SERVER_URL,


                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        JSON_WEB_CALL();
                        Toast.makeText(getActivity().getApplicationContext(),"No Connection available",Toast.LENGTH_LONG);
                    }
                });

        requestQueue = Volley.newRequestQueue(getActivity());

        if (requestQueue.getCache().get(HTTP_SERVER_URL) != null) {
            //response exists
           requestQueue.getCache().get(HTTP_SERVER_URL);
        } else {
            requestQueue.add(jsonArrayRequest);
        }}



    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array) {

        for (int i = 0; i < array.length(); i++) {

            DataAdapter GetDataAdapter2 = new DataAdapter();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter2.setId(json.getInt("id"));

                GetDataAdapter2.setName(json.getString("fach"));

                //Adding subject name here to show on click event.
                SubjectNames.add(json.getString("thema"));

                GetDataAdapter2.setSubject(json.getString("thema"));

                GetDataAdapter2.setPhone_number(json.getString("wann"));

            } catch (JSONException e) {

                e.printStackTrace();
            }

            DataAdapterClassList.add(GetDataAdapter2);

        }

       mSwipeRefreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.GONE);

        recyclerViewadapter = new RecyclerViewAdapter(DataAdapterClassList, getActivity());

        recyclerView.setAdapter(recyclerViewadapter);

    }





    }
