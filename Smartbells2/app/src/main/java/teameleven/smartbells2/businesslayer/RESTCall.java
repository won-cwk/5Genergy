package teameleven.smartbells2.businesslayer;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * ideally, this page will deal with all the meat of creating a restful api call.
 * It currently allows synchronous calls, as well as doing it in a separate thread (I think).
 * Methods are in CRUD order, for ease of viewing
 * Created by Andrew Rabb on 2015-10-21.
 */
public class RESTCall extends AsyncTask<String, Void, JSONObject> {
    /**
     * baseURL : An address of the Smartbells API of the SingularityXL
     */
    private final String baseURL = "https://smart-bells-staging.herokuapp.com/api/v1/";
    /**
     * Tag is a text to system print for tracing to debug RESTCALL error
     */
    private final String TAG = "RESTCALL - Debug --";
    /**
     * Context
     */
    private Context context;
    /**
     * Creates a new Object on the server with the supplied data. Accepts the modifier defining
     * the object, as well as the object fields to be created.
     *
     * @param modifier      - URL to the server, specific to the object in question
     * @param createdObject - specifics for the object to be created, in JSON string format.
     * @param accessToken   - the access token that is required for certain post requests
     * @return - the object that was created, in formatted JSON
     */
    private JSONObject postObject(String modifier, String createdObject, String accessToken) {

        HttpURLConnection connection = openConnection(modifier);
        String json = "";
        JSONObject result = null;
        try {
            //set the connection method
            connection.setRequestMethod("POST");
            //set connection headers
            connection.setRequestProperty("Content-type", "application/json");
            if (!accessToken.isEmpty()) {
                connection.setRequestProperty("X-Access-Token", accessToken);
            }
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            Log.d(TAG, "GETTING CONNECTION");


            //send post request
            DataOutputStream os = new DataOutputStream(connection.getOutputStream());
            Log.d(TAG, "SENDING ...");
            os.write(createdObject.getBytes("UTF-8"));
            os.flush();
            os.close();
            Log.d(TAG, "SENT");

            Log.d(TAG, createdObject);
            //print the response code and accompanying message
            Log.d(TAG, "RESPONSE CODE: " + connection.getResponseCode() +
                    " RESPONSE MESSAGE: " + connection.getResponseMessage());
            if (connection.getResponseCode() != 200 && connection.getResponseCode() != 201) {
                throw new RuntimeException("Failed : HTTP error Code : "
                        + connection.getResponseCode());
            }
            //get response from the server
            BufferedReader br = new BufferedReader
                    (new InputStreamReader(connection.getInputStream()));
            String output;
            while ((output = br.readLine()) != null) {
                json += output;
            }
            //convert json response to string
            result = new JSONObject(json);
            //Log.d(TAG + " POST Result", result.toString(4));
            //close reader
            br.close();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
            Log.d(TAG, "RUNTIME ERROR OCCURRED");
        } finally {
            connection.disconnect();

            //moved disconnect and close to a finally block, in case of exception
        }
        return result;
    }

    /**
     * Gets a specific object or a collection of objects from the Server.
     *
     * @param modifier - URL to the specific object or objects in question
     * @return - the string, or all of the strings if multiple strings are found.
     */
    private JSONObject getObject(String modifier, String page) {
        if (page == null){
            page = "";
        }

        HttpURLConnection connection = openConnection(modifier + page);

        if (connection == null) {
            return null;
        }
        String json = "";
        JSONObject result = null;
        try {
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error Code : "
                        + connection.getResponseCode());
            }
            BufferedReader br = new BufferedReader
                    (new InputStreamReader(connection.getInputStream()));

            String output;
            Log.d(TAG, "Output From Server ... \n");
            while ((output = br.readLine()) != null) {
                json += output;
            }
            result = new JSONObject(json);

/*
        if (result.has("meta")) {
            if (!result.getJSONObject("meta").isNull("next_page")) {
                int temp = result.getJSONObject("meta").getInt("current_page") + 1;
                if (temp < 3) {
                    page = "?page=" + temp;
                    JSONArray array = result.getJSONArray("workout_sessions").put(this.getObject(modifier, page).getJSONArray("workout_sessions"));
                    result.remove("workout_sessions");
                    result.put("workout_sessions", array);
                }
            }
        }
*/
            //Log.d("RESTCALL Response!!!", result.toString(4));
        } catch (ProtocolException e) {
            Log.d(TAG, "Protocol Exception, GET method");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "IO Exception, GET method");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.d(TAG, "JSON Exception, GET method");
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return result;
    }

    /**
     * Put methods are for updating data. In this case, this method accepts the modified string,
     * as well as all of the data for the object, and passes it to the API.
     *
     * @param modifier     - URL to the Server, specific to the object in question
     * @param dataToInsert - data to be updated. should be in the format specified in the API
     * @return - the object that was altered
     */
    private JSONObject putObject(String modifier, String dataToInsert, String accessToken) {
        HttpURLConnection connection = openConnection(modifier);
        if (connection == null) {
            return null;
        }
        String json = "";
        JSONObject result = null;

        if (!accessToken.isEmpty()) {
            Log.d("token in if statement", accessToken);
            connection.setRequestProperty("X-Access-Token", accessToken);
        }

        try {
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("content-type", "application/json");

            //send put request
            DataOutputStream os = new DataOutputStream(connection.getOutputStream());
            Log.d(TAG, "SENDING ...");
            os.write(dataToInsert.getBytes("UTF-8"));
            os.flush();
            os.close();
            Log.d(TAG, "SENT");

            //print the response code and accompanying message
            Log.d(TAG, "RESPONSE CODE: " + connection.getResponseCode()
                    + "RESPONSE MESSAGE: " + connection.getResponseMessage());
            if (connection.getResponseCode() != 200 && connection.getResponseCode() != 201) {
                throw new RuntimeException("Failed : HTTP error Code : "
                        + connection.getResponseCode());
            }
            //get response from the server
            BufferedReader br = new BufferedReader
                    (new InputStreamReader(connection.getInputStream()));
            String output;
            while ((output = br.readLine()) != null) {
                json += output;
            }

            //convert json response to string
            result = new JSONObject(json);
            //close reader
            Log.d(TAG, result.toString(4));
            br.close();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return result;
    }

    /**
     * Deletes a specific object from the database/server.
     * Accepts the URL to the specific object in question, and the Access Token
     *
     * @param modifier- URL to a specific object on the Server, including Id of object to be deleted
     * @param accessToken Access token from Server, used for Authentication
     */
    private void deleteObject(String modifier, String accessToken) {
        HttpURLConnection connection = openConnection(modifier);
        try {
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-type", "application/json");
            if (!accessToken.isEmpty()) {
                connection.setRequestProperty("X-Access-Token", accessToken);
            }
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed: HTTP error Code : "
                        + connection.getResponseCode());
            } else if (connection.getResponseCode() == 204) {
                Log.d(TAG, "Object Deleted");
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
    }

    /**
     * This method creates the connection to the Smart Bells Server. It allows easy access to it,
     * through a centralized method.
     *
     * @param modifier - the specific URL addition of the object to be recovered / updated / deleted
     * @return a connection to the API.
     */
    private HttpURLConnection openConnection(String modifier) {
        try {
            String newURL = baseURL + modifier;
            URL url = new URL(newURL);
            Log.d(TAG, url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            return connection;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * accepts multiple parameters String parameters to define the REST Call.
     * Each parameter expects a certain value in a certain order.
     * param[0] should be the modifier to the URL for the specific object in question
     * param[1] should be the REST method parameter ("GET", "POST", "PUT", "DELETE")
     * param[2] should be the JSON Object that is expected by the API
     * param[3] should be the Access Token, if required by the API (PUT, POST, DELETE)
     */
    protected JSONObject doInBackground(String... params) {
        JSONObject result;
        if (params[1] == "GET") {
            Log.d(TAG, "CALLING GET METHOD NOW...........");
            result = getObject(params[0], null);
        } else if (params[1] == "POST") {
            Log.d(TAG, "CALLING POST METHOD NOW...........");
            result = postObject(params[0], params[2], params[3]);
        } else if (params[1] == "PUT") {
            Log.d(TAG, "CALLING PUT METHOD NOW...........");
            result = putObject(params[0], params[2], params[3]);
        } else if (params[1] == "DELETE") {
            Log.d(TAG, "CALLING DELETE METHOD NOW...........");
            deleteObject(params[0], params[3]);
            result = null;
        } else result = null;
        return result;
    }

}
