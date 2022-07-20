package com.Creswellcrags;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Map;


@SuppressWarnings("deprecation")
public class RequestApiController {
    int timeoutConnection = 6000;
    int responsecode = 0;
    int timeoutSocket = 8000;
    private Map<String, Object> map;
    private Context context;
    private AsyncTaskCompleteListener listener;
    private boolean requestLoading;
    private int requestCode;
    private String method;
    private String message;


    public RequestApiController(Context context, Map<String, Object> map,
                                boolean requestLoading, int requestCode, String method) {
        this(context, map, requestLoading, requestCode, method, null);
    }

    public RequestApiController(Context context, Map<String, Object> map,
                                boolean requestLoading, int requestCode, String method,
                                AsyncTaskCompleteListener listener) {

        this(context, map, requestLoading, requestCode, method, listener, null);

    }

    public RequestApiController(Context context, Map<String, Object> map,
                                boolean requestLoading, int requestCode, String method,
                                AsyncTaskCompleteListener listener, String message) {

        this.map = map;
        this.context = context;
        this.requestLoading = requestLoading;
        this.requestCode = requestCode;
        this.method = method;
        this.message = message;
        if (listener != null)
            this.listener = listener;
        else {
            this.listener = (AsyncTaskCompleteListener) context;
        }

        if (Appconstant.isNetworkAvailable(context)) {
            new AsyncHttpRequest().execute(map.get("urls").toString());
        } else {
            String result = "Network connection not found";
            Toast.makeText(context, result,Toast.LENGTH_SHORT).show();

            if (requestLoading) {
                listener.onTaskCompleted(result, requestLoading, requestCode,
                        responsecode);
            }
            listener.onTaskCompleted(result, requestLoading, requestCode,
                    responsecode);
        }


    }


    public String getpost(String urls, Map<String, Object> map) {
        String responseBody = "";
        try {
            URL mUrl = new URL(urls);
            HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
          //  conn.setRequestProperty("Content-Type", "multipart/form-data");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setReadTimeout(60000);
            conn.setConnectTimeout(60000);
            StringBuilder sbParams = new StringBuilder();
            StringBuilder sbParams1 = new StringBuilder();
            int i = 0;
            for (String key : map.keySet()) {
                if (i != 0) {
                    sbParams.append("&");
                    sbParams1.append("&");
                }
                sbParams1.append(key).append("=")
                        .append(map.get(key).toString());
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(map.get(key).toString(), "UTF-8"));
                i++;
            }
            L.e("param:" + sbParams1.toString());
            String paramsString = sbParams.toString();
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(paramsString);
            wr.flush();
            wr.close();
            responsecode = conn.getResponseCode();
            L.e("responseCode" + responsecode);
            conn.connect();
            InputStream is;
            if (responsecode >= 400 && responsecode <= 499) {
                is = new BufferedInputStream(conn.getErrorStream());
            } else {
                is = new BufferedInputStream(conn.getInputStream());
            }
            //Get Response
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            responseBody = response.toString();
            L.e("responseBody:" + responseBody);

        } catch (UnknownHostException e) {
            return "We are not able to connecting our server.please try after some time!";
        } catch (java.net.SocketTimeoutException e) {
            return "We are not able to connecting our server.please try after some time!";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return responseBody;
    }

    public interface AsyncTaskCompleteListener {
        public void onTaskCompleted(String response, boolean requestLoading, int requestCode, int responsecode);

    }

    class AsyncHttpRequest extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            if (requestLoading) {
                try {
                    pDialog = new ProgressDialog(context);
                    if (message != null) {
                        pDialog.setMessage(message);
                    } else {
                        pDialog.setMessage("Loading...");
                    }
                    pDialog.setCancelable(false);
                    pDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... urls) {
            map.remove("urls");
            L.d("URL: " + urls[0]);
            String responseBody = "";
            try {
                if (method.equals(Appconstant.M_GET)) {
                    URL mUrl = new URL(urls[0]);
                    HttpURLConnection httpConnection = (HttpURLConnection) mUrl.openConnection();
                    httpConnection.setRequestMethod("GET");
                    httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    httpConnection.setRequestProperty("Accept-Charset", "UTF-8");
                    httpConnection.setRequestProperty("Content-length", "0");
                    httpConnection.setUseCaches(false);
                    httpConnection.setAllowUserInteraction(false);
                    httpConnection.setConnectTimeout(100000);
                    httpConnection.setReadTimeout(100000);
                    httpConnection.connect();
                    responsecode = httpConnection.getResponseCode();

                    if (responsecode == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        responseBody = sb.toString();
                    }
                    return responseBody;
                } else {
                 return    getpost(urls[0],map);
                }

            } catch (UnknownHostException e) {
                return "We are not able to connecting our server.please try after some time!";
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            L.d("Response: " + result);
            if (result != null) {
                try {
                    listener.onTaskCompleted(result, requestLoading,
                            requestCode, responsecode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (requestLoading) {
                if (null != pDialog && pDialog.isShowing()) {
                    try {
                        pDialog.dismiss();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            super.onPostExecute(result);
        }
    }


}
