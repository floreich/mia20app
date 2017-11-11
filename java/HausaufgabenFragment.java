package ml.mia20.mia20app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import ml.mia20.mia20app.DataAdapter;
import ml.mia20.mia20app.R;
import ml.mia20.mia20app.RecyclerViewAdapter;


public class HausaufgabenFragment extends Fragment{



    List<DataAdapter> DataAdapterClassList;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;

    ProgressBar progressBar;

    SwipeRefreshLayout mSwipeRefreshLayout;

    JsonArrayRequest jsonArrayRequest;

    ArrayList<String> SubjectNames;

    RequestQueue requestQueue;

    String HTTP_SERVER_URL = "https://stundenplan.mia20.ml/json/homework.php";

    View ChildView;
    TextView nodata;

    int RecyclerViewClickedItemPOS;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_hausaufgaben, container, false);

        nodata = (TextView) v.findViewById(R.id.tvnodata);
        nodata.setText("Derzeit keine Daten");
        nodata.setVisibility(View.GONE);


       mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainerhw);

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

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewhw);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBarhw);
        mSwipeRefreshLayout.setRefreshing(true);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        // JSON data web call function call from here.

        ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            JSON_WEB_CALL();
        } else {
            Toast.makeText(getActivity().getApplicationContext(),"Bitte Verbinde dich mit dem Internet",Toast.LENGTH_LONG).show();
        }


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
                        //JSON_WEB_CALL();
                        recyclerView.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
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

                GetDataAdapter2.setName(json.getString("was"));

                //Adding subject name here to show on click event.
                SubjectNames.add(json.getString("wann"));

                GetDataAdapter2.setSubject(json.getString("wann"));

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
