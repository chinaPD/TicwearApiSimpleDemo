package com.mobvoi.ticwear.apisimpledemo;


import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mobvoi.android.common.ConnectionResult;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.wearable.MessageApi;
import com.mobvoi.android.wearable.Node;
import com.mobvoi.android.wearable.NodeApi;
import com.mobvoi.android.wearable.Wearable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pd on 16-4-20.
 */
public class NodeApiFragment extends Fragment implements View.OnClickListener,
                    MobvoiApiClient.OnConnectionFailedListener{

    private static final int REQUEST_RESOLVE_ERROR = 1000;
    private Button checkNodeButton = null;
    private TextView nodeText = null;

    private MobvoiApiClient mobvoiApiClient = null;
    private NodeApi.NodeListener nodeListener = null;
    private boolean isResolvingError = false;
    Collection<String> nodes = null;
    Handler nodeTextHandler = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                                    Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.nodeapi_fragment_inflate,container,false);

        checkNodeButton = (Button) view.findViewById(R.id.nodeCheckButton);
        nodeText = (TextView) view.findViewById(R.id.nodeText);
        nodeText.setMovementMethod(ScrollingMovementMethod.getInstance());

        checkNodeButton.setOnClickListener(this);

        nodeTextHandler = new Handler(){

            @Override
            public void handleMessage(Message msg){
                for (String node:nodes){
                    if (node != null){
                        nodeText.append("The connected node is:" + node + "\n");
                    }
                }
            }
        };

        nodeListener = new NodeApi.NodeListener(){
            @Override
            public void onPeerConnected(final Node peer){
                nodeText.append("onPeerConnected:"+ peer +"\n");
            }

            @Override
            public void onPeerDisconnected(final Node peer){
                nodeText.append("onPeerDisconnected:"+ peer +"\n");
            }
        };

        mobvoiApiClient = new MobvoiApiClient.Builder(getActivity())
                .addApi(Wearable.API)
                .addConnectionCallbacks(new MobvoiApiClient.ConnectionCallbacks(){
                    @Override
                    public void onConnected(Bundle connectionHint){
                        nodeText.append("onConnnected:"+ connectionHint +"\n");
                        Wearable.NodeApi.addListener(mobvoiApiClient,nodeListener);
                    }

                    @Override
                    public void onConnectionSuspended(int cause){
                        nodeText.append("onConnectedSuspended:"+ cause +"\n");
                    }
                })
                .addOnConnectionFailedListener(new MobvoiApiClient.OnConnectionFailedListener(){
                    @Override
                    public void onConnectionFailed(ConnectionResult result){
                        if (isResolvingError){
                            nodeText.append("failed once again!!!\n");
                            return;
                        }else if (result.hasResolution()){
                            try{
                                isResolvingError = true;
                                nodeText.append("failed once again!!!\n");
                                nodeText.append("first failed in connecting\n");
                                result.startResolutionForResult(getActivity(),REQUEST_RESOLVE_ERROR);
                            }catch (IntentSender.SendIntentException e){
                                mobvoiApiClient.connect();
                                nodeText.append("try to connect again\n");
                            }
                        }else{
                            isResolvingError = false;
                            Wearable.NodeApi.removeListener(mobvoiApiClient,nodeListener);
                        }
                    }
                })
                .build();
        if (!isResolvingError){
            mobvoiApiClient.connect();
        }
        return view;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.nodeCheckButton:{
                new StartCheckTask().execute();
                break;
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result){
        if (isResolvingError){
            nodeText.append("TAG failed once again!!!\n");
            return;
        }else if (result.hasResolution()){
            try{
                isResolvingError = true;
                nodeText.append("TAG failed once again!!!\n");
                nodeText.append("TAG first failed in connecting\n");
                result.startResolutionForResult(getActivity(),REQUEST_RESOLVE_ERROR);
            }catch (IntentSender.SendIntentException e){
                mobvoiApiClient.connect();
                nodeText.append("TAG try to connect again\n");
            }
        }else{
            isResolvingError = false;
            Wearable.NodeApi.removeListener(mobvoiApiClient,nodeListener);
        }
    }

    private Collection<String> getNodes(){
        Set<String> results = new HashSet<String>();
        NodeApi.GetConnectedNodesResult nodes =
                Wearable.NodeApi.getConnectedNodes(mobvoiApiClient).await();
        for (Node node:nodes.getNodes()){
            results.add(node.getId());
        }
        return results;
    }

    private class StartCheckTask extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... args){
            nodes = getNodes();
            nodeTextHandler.sendMessage(new Message());
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result){
            nodeText.append("the total node number is:" + nodes.size()+"\n");
        }
    }
}
